package dev.pe.app.controllers.auth.service;

import dev.pe.app.models.User;
import dev.pe.app.controllers.auth.IUserRepo;
import dev.pe.app.services.jwt.JwtService;
import dev.pe.app.domain.utils.request.AuthenticationRequest;
import dev.pe.app.domain.utils.responses.AuthenticationResponse;
import dev.pe.app.domain.utils.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final IUserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager manager;

  public AuthenticationResponse register(RegisterRequest request) {
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

    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    manager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );

    var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);

    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

}
