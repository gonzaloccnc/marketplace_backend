package dev.pe.app.models;

import dev.pe.app.domain.utils.enums.Docs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data @Entity(name = "users")
public class User implements UserDetails {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
  private UUID idUser;

  @Column(name = "username")
  private String nickname;
  private String lastname;
  private String firstname;

  @Enumerated(EnumType.STRING)
  private Docs typeDoc;

  private String password_;

  private String docId;

  private String email;

  private String jwtToken;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return password_;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
