package center.myfit.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@Slf4j
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder().username("admin").password("password").roles("KEYCLOAK").build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    static class MyFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if ("OPTIONS".equals(request.getMethod())) {
                filterChain.doFilter(request, response);
                return;
            }

            String header = request.getHeader("Authorization");
            String token = header.substring("bearer ".length());
            try {
                JWT jwt = JWTParser.parse(token);
                Object iss = jwt.getJWTClaimsSet().getClaim("iss");
                log.info("********************************************");
                log.info(String.valueOf(iss));
            } catch (Exception e) {
                log.error("******************************************");
                log.error(e.getMessage());
            }


            filterChain.doFilter(request, response);
        }
    }

}
