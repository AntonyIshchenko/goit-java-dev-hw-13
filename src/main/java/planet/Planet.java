package planet;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Table(name = "planet")
@Entity
@Data
public class Planet {
    @Id
    private String id;

    @Column
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Planet planet = (Planet) obj;

        return this.id.equals(planet.getId()) && this.name.equals(planet.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
