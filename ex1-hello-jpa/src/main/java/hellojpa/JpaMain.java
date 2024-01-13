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

            Parent parent = new Parent();
            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);
            parent.addChild(child2);

            // 일반적으로 persist를 여러번 해야함
            // parent를 중심으로 child가 자동으로 persist 되려면?
            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

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
