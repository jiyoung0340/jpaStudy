package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent") // 기본이 LAZY니까 신경 안써도 됨!
    private List<Category> chile = new ArrayList<>();

    @ManyToMany
    // jointable 생성
    @JoinTable(name = "CATEGORY_ITEM "
                , joinColumns = @JoinColumn(name = "CATEGORY_ID") // jointable에서 조인하려는 컬럼과
                , inverseJoinColumns = @JoinColumn(name="ITEM_ID")) // 조인되는 (반대펀) 컬럼 선언
    private List<Item> items = new ArrayList<>();

}
