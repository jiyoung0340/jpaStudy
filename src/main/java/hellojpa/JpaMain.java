package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("C");

            System.out.println("====================");
            // call next value for MEMBER_SEQ 이 호출됨!
            // 영속성 컨텍스트에 저장하기 전에 pk값을 알아야하는데 seq값도 DB가 알고있으니까 persist 하기전에 sq전략이 sequence모드이면 해당 쿼리를 호출해서 member에 id값을 세팅해줌!

            /*
            * allocationSize?
            * 매 객체를 persist할 때마다 next val을 호출하는것은 성능에 좋지 않다!(결국 네트워크를 타는거니깐)
            * 그러므로 allocationSize를 사용한다.
            * allocationSize는 한번 call해 50까지 seq값을 메모리에 저장한다.
            * 애플리케이션에서는 1부터 차근차근 객체를 저장하되, seq값이 50과 같아지면 그때 다시한번 next value를 call하고 memory에 100을 저장해 사용한다.
            * 그렇다고 이 값을 충분히 늘리기에는 중간에 구멍이 생길 수 있으므로 적당한 값(보통 50, 100)으로 설정해 사용한다.
            * */
            em.persist(member);
            System.out.println("member.id======"+member.getId()); // persist시점에 insert쿼리를 실행해 id값을 알 수 있다.
            System.out.println("====================");

            tx.commit(); // 버퍼안에 insert쿼리를 저장해뒀다가 한번에 DB에 반영이 가능!(IDENTITY는 insert를 해야만 id를 알 수 있기때문에 불가능함)
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
