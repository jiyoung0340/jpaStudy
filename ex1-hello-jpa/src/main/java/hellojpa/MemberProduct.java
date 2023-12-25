package hellojpa;

import javax.persistence.*;

// 다대다의 한계 극복 : 연결 테이블을 엔티티로 승격하여 사용한다.
@Entity
public class MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID ")
    private Product product;

    // 다른 컬럼 추가 가
    private int cnt;
    private int price;
}
