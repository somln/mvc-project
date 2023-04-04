package hello.mvc2.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    public Member(){
    }

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}

