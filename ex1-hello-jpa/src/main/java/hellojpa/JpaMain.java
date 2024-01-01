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
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member member = new Member();
            member.setUsername("hello1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member.getId());
//
//            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass()); // Team 객체
//
//            System.out.println("===================");
//            m.getTeam().getName(); // 조회 쿼리 안나감
//            System.out.println("===================");

            // JPQL N+1 문제 예시
            List<Member> resultList = em.createQuery("select m from Member m ", Member.class).getResultList();
            // JPQL은 쿼리를 SQL 그대로 번역함.
            // 따라서 Member를 조회할 때, SELECT * FROM MEMBER; 가 그대로 실행되고
            // EAGER로 연결된 Team이 따로 쿼리가 또 나감.
            // SQL : select * from Team where TEAM_ID = ***
            // 최초 쿼리가 (1) 외에 N개의 쿼리가 나감

//            // FetchJoin
//            List<Member> resultList_fetchjoin = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();


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
