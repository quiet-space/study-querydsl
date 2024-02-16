package study.querydsl.entity;

import jakarta.persistence.*;
import lombok.*;


// 가급적 실무에서는 Setter 사용 지양
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // producted로 기본 생성자
@ToString(of = {"id", "username", "age"}) // 연관관계 필드는 포함 금 (무한루프 발생)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    public Member(String username) {
        this(username, 0);
    }
    public Member(String username, int age) {
        this(username, age, null);
    }
    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}