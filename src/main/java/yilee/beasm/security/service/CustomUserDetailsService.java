package yilee.beasm.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import yilee.beasm.member.domain.dtos.MemberContext;
import yilee.beasm.member.domain.dtos.MemberDto;
import yilee.beasm.member.domain.entities.Member;
import yilee.beasm.member.domain.enums.MemberRole;
import yilee.beasm.member.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.getWithRoles(username)
                .orElseThrow(() -> new NoSuchElementException("Not found email = " + username));

        List<String> memberRoleList = member.getMemberRoleList().stream().map(MemberRole::name).collect(Collectors.toList());

        MemberDto dto = MemberDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .memberRoleList(memberRoleList)
                .build();

        List<GrantedAuthority> roles = dto.getMemberRoleList().stream()
                .map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole)).collect(Collectors.toList());

        return new MemberContext(dto, roles);
    }
}
