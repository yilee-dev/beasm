package yilee.beasm.member.domain.dtos;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MemberContext implements UserDetails {

    private MemberDto memberDto;

    private final List<GrantedAuthority> roles;

    public Map<String, Object> claims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", memberDto.getEmail());
        claims.put("nickname", memberDto.getNickname());
        claims.put("password", memberDto.getPassword());
        claims.put("roles", memberDto.getMemberRoleList());
        return claims;
    }

    public MemberContext(MemberDto memberDto, List<GrantedAuthority> roles) {
        this.memberDto = memberDto;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDto.getEmail();
    }
}
