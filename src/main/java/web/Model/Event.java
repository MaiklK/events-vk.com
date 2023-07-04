package web.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event")
    private int id;

    @Column(name = "name")
    private String name;

    public Event() {}

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
