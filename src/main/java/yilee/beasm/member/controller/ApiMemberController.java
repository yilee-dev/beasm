package yilee.beasm.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yilee.beasm.member.domain.dtos.MemberDto;
import yilee.beasm.member.domain.entities.Member;
import yilee.beasm.member.repository.MemberRepository;
import yilee.beasm.member.service.MemberService;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class ApiMemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/{email}")
    public MemberDto getMember(@PathVariable("email") String email) {
        Member member = memberRepository.getWithRoles(email).orElseThrow(() -> new NoSuchElementException("Not found user"));
        MemberDto build = MemberDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .memberRoleList(member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()))
                .build();
        return build;
    }

}
