package com.github.liuzhuoming23.svea.common.jwt;

import static com.github.liuzhuoming23.svea.common.cons.RedisKey.TOKEN_HASH_KEY;
import static com.github.liuzhuoming23.svea.common.cons.TokenInfo.AUTH_HEADER_KEY;
import static com.github.liuzhuoming23.svea.common.cons.TokenInfo.EXPIRATION;
import static com.github.liuzhuoming23.svea.common.cons.TokenInfo.JWT_SECRET;

import com.github.liuzhuoming23.svea.app.domain.Account;
import com.github.liuzhuoming23.svea.app.service.AccountService;
import com.github.liuzhuoming23.svea.common.context.AccountContext;
import com.github.liuzhuoming23.svea.common.context.SpringContext;
import com.github.liuzhuoming23.svea.common.exception.TokenException;
import com.github.liuzhuoming23.svea.common.properties.SysProperties;
import com.github.liuzhuoming23.svea.common.redis.RedisOperation;
import com.github.liuzhuoming23.svea.util.EncryptType;
import com.github.liuzhuoming23.svea.util.EncryptUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * jwt生成解析及验证
 *
 * @author liuzhuoming
 */
public class JwtUtil {

    private static final SysProperties SYS_PROPERTIES = SpringContext.getBean(SysProperties.class);
    private static final RedisOperation REDIS_OPERATION = SpringContext
        .getBean(RedisOperation.class);
    private static final AccountService ACCOUNT_SERVICE = SpringContext
        .getBean(AccountService.class);
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 生成jwt
     *
     * @param username 用户名
     */
    public static String generateToken(String username) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);

        String token = Jwts.builder()
            .setClaims(map)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(SignatureAlgorithm.HS512, EncryptUtil.encode(JWT_SECRET, EncryptType.BASE64))
            .compact();
        REDIS_OPERATION.hash().put(TOKEN_HASH_KEY, username, token);
        return token;
    }

    /**
     * token有效性验证
     *
     * @param request request
     */
    public static HttpServletRequest validateToken(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_KEY);
        if (token != null) {
            try {
                Claims claims = Jwts.parser()
                    .setSigningKey(EncryptUtil.encode(JWT_SECRET, EncryptType.BASE64))
                    .parseClaimsJws(token).getBody();
                String username = (String) claims.get("username");
                Date expiration = claims.getExpiration();
                //验证用户是否存在
                Account account = ACCOUNT_SERVICE.selectOneByUsername(username);
                if (account == null) {
                    throw new TokenException("account not exist");
                }
                //验证用户是否有效
                if (account.getEnable() != 0) {
                    throw new TokenException("invalid account");
                }
                //验证token是否存在
                String tokenInRedis = REDIS_OPERATION.hash().get(TOKEN_HASH_KEY, username);
                if (StringUtils.isEmpty(tokenInRedis) || !token.equals(tokenInRedis)) {
                    throw new TokenException("token expired");
                }
                //验证token是否过期
                if (System.currentTimeMillis() > expiration.getTime()) {
                    throw new TokenException("token expired");
                }
                //通过验证则把当前登录用户信息保存到ThreadLocal
                AccountContext.set(account);
                return new JwtHttpServletRequest(request, claims);
            } catch (Exception e) {
                throw new TokenException(e.getMessage());
            }
        } else {
            throw new TokenException("missing token");
        }
    }

    /**
     * 是否免登录url
     */
    public static boolean isAnonUrl(HttpServletRequest request) {
        List<String> anons = SYS_PROPERTIES.getAnonUrls();
        for (String anon : anons) {
            if (PATH_MATCHER.match(anon, request.getServletPath())) {
                return true;
            }
        }
        return false;
    }

    public static class JwtHttpServletRequest extends HttpServletRequestWrapper {

        private Map<String, String> claims;

        JwtHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (claims != null && claims.containsKey(name)) {
                return Collections.enumeration(Collections.singletonList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

        public Map<String, String> getClaims() {
            return claims;
        }
    }
}
