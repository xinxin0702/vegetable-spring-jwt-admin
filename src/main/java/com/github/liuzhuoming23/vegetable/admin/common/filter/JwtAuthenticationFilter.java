package com.github.liuzhuoming23.vegetable.admin.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.github.liuzhuoming23.vegetable.admin.common.domain.Result;
import com.github.liuzhuoming23.vegetable.admin.common.exception.TokenException;
import com.github.liuzhuoming23.vegetable.admin.common.jwt.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * jwt鉴权过滤器
 *
 * @author liuzhuoming
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {
        if (!JwtUtil.isAnonUrl(request)) {
            try {
                request = JwtUtil.validateToken(request);
            } catch (TokenException e) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(401);
                String uri = request.getRequestURI();
                Result result = new Result().fail(HttpStatus.UNAUTHORIZED, e.getMessage(), uri);
                String json = JSONObject.toJSONString(result);
                response.getWriter().print(json);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
