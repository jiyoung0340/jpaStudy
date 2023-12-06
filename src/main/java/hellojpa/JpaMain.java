package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            // code (추가)
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

//            // code (수정)
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");

            // 전체 조회를 한다면? => JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                                    .setFirstResult(1) // paging
                                    .setMaxResults(8) // paging
                                    .getResultList();
            // Member 객체를 대상으로 쿼리를 작성한다!

            for (Member member : result) {
                System.out.println("member.name =" + member.getName());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
