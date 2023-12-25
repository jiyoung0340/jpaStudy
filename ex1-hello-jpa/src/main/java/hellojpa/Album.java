package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") // default: Entity name
public class Album extends Item{
    private String artist;
}
