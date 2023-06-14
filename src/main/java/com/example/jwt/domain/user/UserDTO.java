package com.example.jwt.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.*;

@ToString
@Getter
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotNull(message = "이메일은 필수 입력사항 입니다.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String userEmail;

    @NotBlank(message = "이름은 필수 입력사항 입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    // @JsonProperty : json의 이름을 정할수 있고 아래와 같이 사용이 가능하다.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPw;
    private Role role;

    @Builder
    public UserDTO(String userEmail, String userName, String userPw, Role role) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPw = userPw;
        this.role = role;
    }
}
