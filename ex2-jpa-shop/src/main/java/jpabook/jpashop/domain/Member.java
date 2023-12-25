package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;

    // 양방향 관계 추가
    @OneToMany(mappedBy = "member")
    private List <Order> orders = new ArrayList<>(); // 관례상 여러 오류를 위해 초기화

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
