package com.mycompany.jwtdemo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mycompany.jwtdemo.service.CustomUserDetailsService;
import com.mycompany.jwtdemo.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		// get the jwt token from request header

		// validate that jwt token

		String bearerToken = httpServletRequest.getHeader("Authorization");
		String username = null;
		String token = null;

		// check if token exists or has bearer text

		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			// extract jwt token from bearerToken

			token = bearerToken.substring(7);

			try {
				//extract username from the token
				username = jwtUtil.extractUsername(token);
				
				//get userDetails for this user
				
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			//security checks
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				
				
				SecurityContextHolder.getContext().setAuthentication(upat);
			}else {
				System.out.println("Invalid Token !!");
			}
				
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			System.out.println("Invalid Bearer token format!!");
		}
		
		
		//if all is well forward the filter request to the requested endpoint
filterChain.doFilter(httpServletRequest, response);
	}
}
