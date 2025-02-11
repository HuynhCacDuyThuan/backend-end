//package com.example.demo.config;
//
//import java.util.Date;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//public class JwtTokenProvider {
//
//    private String jwtSecret = "yourSecretKey"; // You should store this securely
//
//    // Method to generate a token
//    public String generateToken(Authentication authentication) {
//        User principal = (User) authentication.getPrincipal();
//        return Jwts.builder()
//                .setSubject(principal.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token validity for 24 hours
//                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512) // Use Keys.hmacShaKeyFor instead
//                .compact();
//    }
//
//    // Method to extract the username from the token
//    public String getUsernameFromToken(String token) {
//        Claims claims = Jwts.parserBuilder()  // Update to parserBuilder for newer versions of JJWT
//                .setSigningKey(jwtSecret.getBytes())  // Pass byte array of secret key
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//
//    // Method to validate the token
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()  // Use parserBuilder instead
//                    .setSigningKey(jwtSecret.getBytes())  // Pass byte array of secret key
//                    .build()
//                    .parseClaimsJws(token);  // Validate the token
//            return true;
//        } catch (JwtException e) {
//            // Handle the exception and return false
//            return false;
//        }
//    }
//}
