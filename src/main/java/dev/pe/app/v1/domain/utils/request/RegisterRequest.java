package dev.pe.app.v1.domain.utils.request;

import dev.pe.app.v1.domain.utils.enums.Docs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String nickname;
  private Docs typeDoc;
  private String docId;
}
