package ticket;

import client.Client;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import planet.Planet;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "ticket")
@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_planet_id", nullable = false)
    private Planet fromPlanet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_planet_id", nullable = false)
    private Planet toPlanet;

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, client, fromPlanet, toPlanet);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) obj;

        return Objects.equals(this.id, ticket.getId())
                && this.createdAt.equals(ticket.getCreatedAt())
                && this.client.equals(ticket.getClient())
                && this.fromPlanet.equals(ticket.getFromPlanet())
                && this.toPlanet.equals(ticket.getToPlanet());
    }
}
