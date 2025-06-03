package com.linkSync.utils;

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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.linkSync.entity.AuthUserTokens;
import com.linkSync.repository.TokenRepository;
import com.linkSync.services.MyUserDetailService;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private MyUserDetailService myUserDetailService;
	
	@Autowired
	private JwtUtil jwtUtilToken;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fltr)
			throws ServletException, IOException {
		// get jwt
		// Bearer
		//validate
		
		String requestTokenHeader = request.getHeader("Authorization");
		String username=null;
		String jwtToken=null;
		
		if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
		{
			jwtToken= requestTokenHeader.substring(7);
			
			AuthUserTokens userTokens = this.tokenRepository.getByToken(jwtToken);
			try {
					if(userTokens!=null)
					{
						username = this.jwtUtilToken.extractUsername(jwtToken);
					}
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			UserDetails userDetails = this.myUserDetailService.loadUserByUsername(username);
			//security
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				userNamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(userNamePasswordAuthenticationToken);
			}
			else
			{
				System.out.println("Token is not validated...");
			}
		}
		else
		{
			
		}
		
		fltr.doFilter(request, response);
	}

}
