package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.sound.midi.MetaMessage;
import java.sql.PseudoColumnUsage;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//
//            Member member = em.find(Member.class, 1L);
//            printMember(member); // member를 조회할 때, team 을 조회하지 않는 편이 더 좋음
//            printMemberAndTeam(member);

            Member member = new Member();
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear();

//            // [proxy]
//            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.getReference( Member.class, member.getId());
//            System.out.println("findMember.class =" +findMember.getClass()); // 출력결과 :class hellojpa.Member$HibernateProxy$WqZ2PIDH (hibernate가 만든 가짜 프록시 객체)
//            System.out.println("findMember.id =" + findMember.getId());
//            // 사이에서 select 쿼리가 실행됨 : username을 모르니 DB에서 가져옴!
//            System.out.println("findMember.userName =" + findMember.getUsername());


//            // [JPA는 트랜잭션 안에서 같은 것에대해 보장을 한다1]
//            // 결과가 (Member)로 같게 나온다
//            // 이미 member를 1차캐시에 가져왔기 때문에 굳이 프록시로 가져올 필요 없음
//            // 또한, JPA는 한 트랜잭션 안에서 같은 것에 대해 보장을 해줌.
//            Member m1 = em.find(Member.class, member.getId());
//            System.out.println("m1 = " + m1.getClass());
//
//            Member m2 = em.getReference(Member.class, member.getId());
//            System.out.println("m2 = " + m2.getClass());


//            // [JPA는 트랜잭션 안에서 같은 것에대해 보장을 한다2]
//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember = " + refMember.getClass()); // proxy
//
//            Member findMember = em.find(Member.class, member.getId()); // select 쿼리 실행
//            System.out.println("findMember = " + findMember.getClass()); // 출력 : proxy
//            // getReference를 하고 난 후, find하면 그 객체를 proxy로 반환함.


//            // [준영속 상태일 때, 프록시를 초기화하면 exception이 발생한다.]
//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember.getClass() = " + refMember.getClass());
//
//            em.detach(refMember); // refMember를 준영속 상태로 만듦
//            System.out.println("refMember.getUsername() = " + refMember.getUsername()); // LazyInitializationException  발생

            // [프록시 Util 함수]
            Member refMember = em.getReference(Member.class, member.getId());
            System.out.println("refMember.getClass() = " + refMember.getClass());

            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); // false

            Hibernate.initialize(refMember); // 강제 초기(JPA표준에는 없음)
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); // true

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("userName :" + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        String userName = member.getUsername();
        System.out.println("userName : " + userName);

        Team team = member.getTeam();
        System.out.println("team : " + team.getName());
    }
}
