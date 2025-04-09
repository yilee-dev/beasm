package yilee.beasm.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yilee.beasm.member.domain.dtos.MemberDto;
import yilee.beasm.member.domain.entities.Member;
import yilee.beasm.member.domain.enums.MemberRole;
import yilee.beasm.member.repository.MemberRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(MemberDto memberDto) {
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .nickname(memberDto.getNickname())
                .password(memberDto.getPassword())
                .memberRoleList(memberDto.getMemberRoleList().stream().map(MemberRole::valueOf).collect(Collectors.toList()))
                .build();

        memberRepository.save(member);
    }
}
