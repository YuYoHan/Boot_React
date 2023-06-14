package com.example.jwt.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@ToString
@NoArgsConstructor
public class LoginDTO {

    @NotNull(message = "이메일은 필수 입력사항 입니다.")
    @Size(min = 3, max=50)
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String userEmail;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    // @JsonProperty : json의 이름을 정할수 있고 아래와 같이 사용이 가능하다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 100)
    private String userPw;

    @Builder
    public LoginDTO(String userEmail, String userPw) {
        this.userEmail = userEmail;
        this.userPw = userPw;
    }
}
