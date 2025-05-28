package client;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Table(name = "client")
@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Client client = (Client) obj;

        return this.id == client.getId() && this.name.equals(client.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
