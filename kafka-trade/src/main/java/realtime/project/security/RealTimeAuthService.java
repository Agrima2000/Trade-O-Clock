package realtime.project.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

public class RealTimeAuthService {

    private static final String AUTH_TOKEN_HEADER_NAME = "KAFKA-TRADE-API-KEY";
    private static final String AUTH_TOKEN = "1234";

    public static Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new RealTimeApiKeyAuth(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
