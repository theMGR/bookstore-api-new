package com.codesimple.bookstore.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.codesimple.bookstore.common.AccessDeniedException;
import com.codesimple.bookstore.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private static final String secret = "This_is_secret_This_is_secret_his_is_secret_This_is_secret_his_is_secret_This_is_secret_his_is_secret_This_is_secret_his_is_secret_This_is_secret_his_is_secret_This_is_secret_"; // Ideally,

	// private static final String secret = "This_is_secret"; // Ideally,

	// store
	// variables or a secrets manager)
	private static final long expiryDuration = 60 * 60; // Token expiration time (1 hour)

	/**
	 * Generate a JWT token for the given user.
	 *
	 * @param user The user for whom the token is being generated.
	 * @return The generated JWT token.
	 */
	public String generateJwt(User user) {
		if (user.getName() != null || user.getEmailId() != null || user.getId() != null) {

			long milliTime = System.currentTimeMillis();
			long expiryTime = milliTime + expiryDuration * 1000;

			Date issuedAt = new Date(milliTime);
			Date expiryAt = new Date(expiryTime);

			// Create a SecretKey instance from the secret string
			SecretKey secretKey = null;
			try {
				secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// Generate JWT using claims and HS512 algorithm with the secret key
			try {
				JwtBuilder jwtBuilder = Jwts.builder().issuer(user.getId().toString()).issuedAt(issuedAt)
						.expiration(expiryAt).claim("type", user.getUserType());

				// Add custom claims
				jwtBuilder.claim("name", user.getName()).claim("emailId", user.getEmailId());

				// if(user.getId() != null) { jwtBuilder.claim("userId", user.getId()); }

				String jwtKey = jwtBuilder.signWith(secretKey).compact();

				return jwtKey;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new AccessDeniedException("Error generating JWT: " + e.getMessage());
			}
		} else {
			throw new AccessDeniedException("Error generating JWT: email/name/id cannot be empty");
		}
	}

	/**
	 * Verify the JWT token.
	 *
	 * @param authorization The JWT token to be verified.
	 * @return The claims of the JWT if valid.
	 * @throws AccessDeniedException If the JWT token is invalid or expired.
	 */
	public Claims verify(String authorization) throws AccessDeniedException {

		try {

			// Create a SecretKey instance from the secret string
			SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

			// Parse the JWT and validate it using the secret key
			Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(authorization).getPayload();

			return claims;
		} catch (Exception e) {
			throw new AccessDeniedException("Access Denied: Invalid or Expired JWT Token - " + e.getMessage());
		}
	}
}
