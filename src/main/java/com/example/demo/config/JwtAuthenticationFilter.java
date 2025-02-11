//package com.example.demo.config;
//
//import java.io.IOException;
//import java.util.Collections;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private JwtTokenProvider jwtTokenProvider;
//
//    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
// 
//
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		  String token = getJwtFromRequest(request);
//
//	        if (token != null && jwtTokenProvider.validateToken(token)) {
//	            String username = jwtTokenProvider.getUsernameFromToken(token);
//
//	            // Set the authentication in the SecurityContextHolder
//	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//	                    username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//	            SecurityContextHolder.getContext().setAuthentication(authentication);
//	        }
//
//	        filterChain.doFilter(request, response);
//		
//	}
//}
