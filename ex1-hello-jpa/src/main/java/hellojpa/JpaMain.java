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

            Member m = em.find(Member.class, member.getId());

            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass()); // proxy객체

            System.out.println("===================");
            m.getTeam(); // 은 아직 프록시객체를 사용!
            m.getTeam().getName(); // 이때 DB에서 객체를 가져옴!
            System.out.println("===================");

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
