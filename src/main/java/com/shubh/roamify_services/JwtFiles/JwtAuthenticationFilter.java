package com.shubh.roamify_services.JwtFiles;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private final JwtService jwtService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
     @NonNull HttpServletRequest request,
     @Nonnull HttpServletResponse response,
     @NonNull FilterChain filterChain)
      throws ServletException, IOException {

     if (request.getServletPath().contains("/api/v1/auth")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String authheader = request.getHeader("Authorization");
    final String jwtToken;
    final String username;



    if (authheader == null || !authheader.startsWith("Bearer ")) {
      System.out.println("Token not available");
      filterChain.doFilter(request, response);
      return;
    }

    if(authheader != null && authheader.startsWith("Bearer ")){
      jwtToken = authheader.substring(7);
    
      try{
       username = jwtService.extractUsername(jwtToken);
    }catch(ExpiredJwtException e){
      System.out.println("Token Expired");
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
      return;
    }catch(Exception e){
      System.out.println("Invalid Token");
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
      return;
    }
    
  
  } else {
    System.out.println("Token not available");
    filterChain.doFilter(request, response);
    return;
  }
  


  if (username != null &&
    SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (jwtService.isTokenValid(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken authTocken = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        

        authTocken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authTocken);

      } else {
        System.out.println("Invalid Token");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        return;
      }
    } else {
      System.out.println("username is null or context is not null");
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "username is null or context is not null");
      return;
    }

    filterChain.doFilter(request, response);


  }
  
  
}
