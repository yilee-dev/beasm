package yilee.beasm.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import yilee.beasm.member.domain.dtos.MemberDto;
import yilee.beasm.member.domain.entities.Member;
import yilee.beasm.member.domain.enums.MemberRole;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void insertTest() {
        for (int i = 0; i < 10; i++) {

            MemberDto dto = MemberDto.builder()
                    .email("member" + i + "@aaa.com")
                    .nickname("member" + i)
                    .password(passwordEncoder.encode("1111"))
                    .memberRoleList(List.of("USER", "MANAGER"))
                    .build();

            memberService.join(dto);
        }
    }

}