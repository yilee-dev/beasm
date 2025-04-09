package yilee.beasm.member.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import yilee.beasm.member.domain.enums.MemberRole;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "memberRoleList")
@EqualsAndHashCode(of = "id")
public class Member {

    @Id
    private String email;

    private String nickname;

    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<MemberRole> memberRoleList = new ArrayList<>();
}
