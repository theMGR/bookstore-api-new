package com.codesimple.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.codesimple.bookstore.dto.RequestMeta;
import com.codesimple.bookstore.util.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtils jwtUtils;

	// Field injection for RequestMeta
	private RequestMeta requestMeta;

	// Constructor without RequestMeta to avoid circular dependency during bean
	// initialization
	public JwtInterceptor() {
	}

	// Inject RequestMeta via @Autowired or @PostConstruct
	@Autowired
	public void setRequestMeta(RequestMeta requestMeta) {
		this.requestMeta = requestMeta;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String auth = request.getHeader("authorization");

		// Skip JWT validation for login and signup endpoints
		if (request.getRequestURI().contains("login") || request.getRequestURI().contains("signup")
				|| request.getRequestURI().contains("swagger") || request.getRequestURI().contains("api")
				|| request.getRequestURI().contains("v3") || request.getRequestURI().contains("actuator")
				|| request.getRequestURI().contains("openapi")) {
			// || request.getRequestURI().contains("getAllUsers")
			return true;
		} else {
			try {
				// Verify the JWT token
				Claims claims = jwtUtils.verify(auth);

				// Set user details in RequestMeta
				requestMeta.setUserName(claims.get("name").toString());
				requestMeta.setUserId(Long.valueOf(claims.getIssuer()));
				requestMeta.setUserType(claims.get("type").toString());
				requestMeta.setEmailId(claims.get("emailId").toString());

				return true; // Allow the request to proceed
			} catch (Exception e) {
				// Handle invalid or missing token
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Unauthorized: Invalid or missing token");
				return false; // Stop the request from proceeding
			}
		}

	}
}
