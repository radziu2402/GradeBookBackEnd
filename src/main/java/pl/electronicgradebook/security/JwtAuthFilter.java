package pl.electronicgradebook.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.electronicgradebook.dto.ErrorDto;

import java.io.IOException;

@RequiredArgsConstructor
@NonNullApi
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    Authentication authentication;

//                    if ("GET".equals(request.getMethod())) {
//                        authentication = userAuthenticationProvider.validateToken(authElements[1]);
//                    } else {
                        authentication = userAuthenticationProvider.validateTokenStrongly(authElements[1]);
//                    }

                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        handleInvalidToken(response);
                        return;
                    }
                } catch (JWTDecodeException e) {
                    handleInvalidToken(response);
                    return;
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private void handleInvalidToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorDto errorDto = new ErrorDto("Invalid token");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorDto);
    }
}

