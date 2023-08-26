package dev.pe.app.v1.controllers.auth.service;

import dev.pe.app.v1.models.User;
import dev.pe.app.v1.controllers.auth.IUserRepo;
import dev.pe.app.v1.services.jwt.JwtService;
import dev.pe.app.v1.domain.utils.request.AuthenticationRequest;
import dev.pe.app.v1.domain.dto.AuthenticationDTO;
import dev.pe.app.v1.domain.utils.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final IUserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager manager;

  public AuthenticationDTO register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password_(passwordEncoder.encode(request.getPassword()))
        .docId(request.getDocId())
        .nickname(request.getNickname())
        .typeDoc(request.getTypeDoc())
        .build();

    userRepo.save(user);
    var jwtToken = jwtService.generateToken(user);

    return AuthenticationDTO.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationDTO authenticate(AuthenticationRequest request) {
    manager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);

    return AuthenticationDTO.builder()
        .token(jwtToken)
        .build();
  }

}
