package yilee.beasm.member.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String email;
    private String nickname;
    private String password;
    @Builder.Default
    private List<String> memberRoleList = new ArrayList<>();
}
