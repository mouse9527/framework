package com.mouse.framework.jwt.verify.filter;

import com.google.common.net.HttpHeaders;
import com.mouse.framework.security.Token;
import com.mouse.framework.security.TokenHolder;
import com.mouse.framework.security.TokenParser;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenLoaderFilter extends OncePerRequestFilter {
    private final TokenParser tokenParser;
    private final TokenHolder tokenHolder;

    public TokenLoaderFilter(TokenParser tokenParser, TokenHolder tokenHolder) {
        this.tokenParser = tokenParser;
        this.tokenHolder = tokenHolder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            Optional<Token> token = tokenParser.parse(getToken(bearerToken));
            token.ifPresent(tokenHolder::refresh);

            filterChain.doFilter(request, response);
        } finally {
            tokenHolder.clean();
        }
    }

    private String getToken(String bearerToken) {
        return bearerToken.replaceAll("Bearer ", "");
    }
}
