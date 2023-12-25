package hellojpa;

import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column (name = "MEMBER_ID")
    private Long id;

    @Column (name = "USERNAME")
    private String username;

//    @Column (name = "TEAM_ID")
//    private Long teamId;

    // 오류('Basic' attribute type should not be 'Persistence Entity' ) : 두 객체사이의 관계를 jpa에 알려줘야함!
    @ManyToOne(fetch = FetchType.LAZY) // Member를 기준으로 하나의 팀에 여러개의 멤버가 소속되므로 ManyToOne
    @JoinColumn (name = "TEAM_ID") // @JoinColumn(name="TEAM_ID") 를 통해 join 컬럼을 설정해줌.
    private Team team; // Team에서 mappedBy에 이 변수명의 이름으로 연결됨!

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 연관관계 편의 메소드를 제공 -> 이름은 setTeam말고 changeTeam이런식으로 변경한다!
    }
}
