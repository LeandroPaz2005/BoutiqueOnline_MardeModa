
package BoutiqueOnline.modelo;

//entidad JPA representa un rol de usuario para definir los perfiles : adm y usuario

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol { 

    //para identifar el rol
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //nombre de rol
    private String nombre; 
    
    //por hacer: realizar dos roles para administrador y usuario
    public Rol(Long id, String nombre) {
        super();
        this.id = id;
        this.nombre = nombre;
    }

    public Rol() {
        super();
    }

    //contructor que nos ayuda a crear nuevos roles 
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

