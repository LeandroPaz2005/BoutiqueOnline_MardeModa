package BoutiqueOnline.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;


    public Rol(Long id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public Rol() {
        super();
    }

    public Rol(String nombre) {
        super();
        this.nombre = nombre;
    }

    public Long id() {
        return id;
    }

    public Rol setId(Long id) {
        this.id = id;
        return this;
    }

    public String nombre() {
        return nombre;
    }

    public Rol setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
}
