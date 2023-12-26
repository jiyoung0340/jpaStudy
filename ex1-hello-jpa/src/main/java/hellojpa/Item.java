package hellojpa;

import javax.persistence.*;

@Entity
// 상속관계의 DB설계가 바뀌더라도 strategy 전략만 변경해주면 소스코드의 변경 없이 DB와의 매핑이 가능함!  -> JPA 의 장점
//@Inheritance(strategy = InheritanceType.JOINED) // 조인전략
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 구현클래스마다 테이블 전
@DiscriminatorColumn // DTYPE(default/name으로 수정가능)이 생겨서 Entity명이 들어감(조인전략에서는 필수)
                    // 단일 테이블에서는 없어도 가능한 어노테이션 대신 DTYPE은 필수
                    // 구현클래스마다 테이블 전략에서도 필요 없음
public abstract class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
