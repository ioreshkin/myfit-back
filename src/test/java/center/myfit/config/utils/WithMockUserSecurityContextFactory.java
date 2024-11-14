package center.myfit.config.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashMap;
import java.util.Map;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", annotation.email());
        claims.put("id", annotation.id());
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("alg", "RS256");
        Jwt principal = new Jwt("token", null, null, headers, claims);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(principal);
        context.setAuthentication(jwtAuthenticationToken);
        return context;
    }
}
