package br.com.pitang.user.car.api.filter;

import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.repository.UserRepository;
import br.com.pitang.user.car.api.service.impl.UserDetailsServiceImpl;
import br.com.pitang.user.car.api.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    private static final List<String> AUTH_WHITE_LIST = List.of(
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-resources/**",
            "/auth/**",
            "/users/**");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){

        return AUTH_WHITE_LIST.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = parseJwt(request);


        if (jwt != null) {
            var login = jwtUtils.validateJwtTokenAndReturnLogin(jwt);

            User user = userDetailsService.loadUserByUsername(login);
            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }else{
            logger.error("token not send");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }


    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
