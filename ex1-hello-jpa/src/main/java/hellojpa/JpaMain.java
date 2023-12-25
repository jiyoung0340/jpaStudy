package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sound.midi.MetaMessage;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Movie movie = new Movie();
            movie.setDirector("A");
            movie.setActor("BB");
            movie.setName("바람과함께 사라지다");
            movie.setPrice(10000);

            em.persist(movie);
            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId()); // join을 해서 select함
            System.out.println("Movie : " + findMovie);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
