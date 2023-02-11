package com.ems.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ems.model.sub.LoginLog;
import com.ems.repository.LoginLogRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * <h1>Jwt Utils</h2>
 * 
 * @author Daniel
 *
 */
@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Autowired
	private LoginLogRepository loginLogRepository;

	@Value("${ems.app.jwtSecret}")
	private String jwtSecret;

	@Value("${ems.app.jwtExpirationMs}")
	private Long jwtExpirationMs;

	/**
	 * <h2>Generate JWT Token</h2>
	 * 
	 * @param authentication
	 * @return
	 */
	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		// calling login log log method to store logs
		loginLog(authentication);
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * <h2>Login Log</h2>
	 * 
	 * Store every user's login time, user name and role
	 * 
	 * @param authentication
	 */
	public void loginLog(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		LoginLog newUserLog = new LoginLog();
		newUserLog.setAccesstime(newUserLog.getAccesstime());
		newUserLog.setUserName(userPrincipal.getUsername());
		newUserLog.setRole(userPrincipal.getAuthorities().toString());
		loginLogRepository.save(newUserLog);
		logger.info(authentication.getName(), " log saved");
	}

	/**
	 * <h2>Get User Name From JWT Token</h2>
	 * 
	 * @param token
	 * @return
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * <h2>Validate JWT Token</h2>
	 * 
	 * @param authToken
	 * @return
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
