package co.com.pragma.model.usuario.gateways;

import java.util.Map;

public interface JwtProviderRepository {
    String generateToken(String subject, Map<String, Object> claims);
    boolean validateToken(String token);
    String getSubject(String token);
    String getClaim(String token, String claimName);
    long getExpirationTimestamp();
}