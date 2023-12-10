package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq") // sequence전략
@TableGenerator(
        name = "MEMBER_SQ_GENERATOR",
        table = "MY_SEQUENCE",
        pkColumnName = "MEMBER_SEQ", allocationSize = 1)
public class Member {

    @Id // 직접 할당시 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SQ_GENERATOR") // DB방언에 따라 달라짐
    // GenerationType.IDENTITY // DB에 위임
    // GenerationType.SEQUENCE // Sequence 객체를 생성
    private Long id;
    @Column(name = "name", nullable = false)
    private String username;
    public Member() {
    }

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
}
