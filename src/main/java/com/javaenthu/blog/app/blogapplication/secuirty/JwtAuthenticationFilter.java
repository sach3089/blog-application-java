package com.javaenthu.blog.app.blogapplication.secuirty;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter implements Filter {

	@Autowired
	private CustomUserSecurity customUserSecurity;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
//		httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
//		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//		httpResponse.setHeader("Access-Control-Max-Age", "3600");
//		httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

		// step-1 : get token
		String requestToken = httpRequest.getHeader("Authorization");
		System.out.println(requestToken);
		String token = null;
		String userName = null;
		try {
			token = requestToken.split(" ")[1];
		} catch (Exception e) {
			System.out.println("JWT Token is Empty or Null");
		}
		if (Objects.nonNull(token)) {
			try {
				userName = jwtTokenHelper.extractUsername(token);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("JWT Web token does not start with bearer");
		}
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = customUserSecurity.loadUserByUsername(userName);
			if (jwtTokenHelper.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken userNamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				userNamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthToken);
			}
		}
		chain.doFilter(request, response);
	}
}
