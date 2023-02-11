package com.ems.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ems.model.sub.AuthenticationLog;
import com.ems.repository.AuthLogRepository;

import lombok.extern.log4j.Log4j2;

/**
 * <h1>Authentication Token Filter</h2>
 * 
 * @author Daniel
 *
 */
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthLogRepository authLogRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

				// Every API request stored as log in database
				AuthenticationLog currentUserAuthLog = new AuthenticationLog();
				currentUserAuthLog.setAccesstime(currentUserAuthLog.getAccesstime());
				currentUserAuthLog.setUserName(username);
				currentUserAuthLog.setRole(userDetails.getAuthorities().toString());
				currentUserAuthLog.setAccessedMethod(request.getMethod());
				currentUserAuthLog.setRequestURL(request.getRequestURI());
				authLogRepository.save(currentUserAuthLog);
				log.debug("{} Log Stored", currentUserAuthLog.getUserName());
			}
		} catch (Exception e) {
			log.error("Cannot set user authentication: {0}", e);
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * <h2>Parse JWT</h2>
	 * 
	 * Get token from header
	 * 
	 * @param request
	 * @return
	 */
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
