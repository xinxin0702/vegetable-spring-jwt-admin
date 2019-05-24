package com.github.liuzhuoming23.jwtback.common.filter;

import com.github.liuzhuoming23.jwtback.common.exception.JwtbackException;
import com.github.liuzhuoming23.jwtback.common.jwt.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * jwt鉴权过滤器
 *
 * @author liuzhuoming
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        if (!JwtUtil.isAnonUrl(request)) {
            try {
                request = JwtUtil.validateToken(request);
            } catch (Exception e) {
                throw new JwtbackException(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
