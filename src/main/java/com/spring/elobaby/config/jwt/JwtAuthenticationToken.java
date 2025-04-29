package com.spring.elobaby.config.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.spring.elobaby.dal.model.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Guillaume on 07/08/2017.
 * back
 */
public class JwtAuthenticationToken implements Authentication {

  private TokenPayload tokenPayload;

  private final List<GrantedAuthority> authorities;
  private final Map<String, Claim> claims;
  private boolean isAuthenticated;

  JwtAuthenticationToken(DecodedJWT jwt) {
    List<GrantedAuthority> tmp = new ArrayList<>();


    Long id = jwt.getClaim("id").asLong();
    String role = jwt.getClaim("role").asString();
    this.tokenPayload = new TokenPayload();
    this.tokenPayload.setId(id);
    this.tokenPayload.setRole(role);
    getUserRoles(role)
            .forEach(e -> tmp.add(new SimpleGrantedAuthority(e)));
    this.authorities = Collections.unmodifiableList(tmp);
    this.claims = jwt.getClaims();
    this.isAuthenticated = false;
  }

  private List<String> getUserRoles(String role){
    Role roleEnum = Role.valueOf(role);
    var roles = Arrays.stream(Role.values()).toList();
    return roles.subList(0,roles.indexOf(roleEnum)+1)
            .stream()
            .map(Enum::toString)
            .collect(Collectors.toList());

  }

  @Override
  public List<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public Object getCredentials() {
    return "";
  }

  @Override
  public Object getDetails() {
    return claims;
  }

  @Override
  public Object getPrincipal() {
    return tokenPayload;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean b) throws IllegalArgumentException {
    this.isAuthenticated = b;
  }

  @Override
  public String getName() {
    return null;
  }
}
