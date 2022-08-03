package com.example.week04.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @JsonIgnore
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum authority;

    public String getAuthority() {
        return authority.getAuthority();
    }

    public Member(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.authority = UserRoleEnum.MEMBER;
    }
}
