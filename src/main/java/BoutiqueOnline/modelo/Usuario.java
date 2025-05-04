package BoutiqueOnline.modelo;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "usuarios", uniqueConstraints =@UniqueConstraint(columnNames = "email"))
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;


    private String email;
    private String password;

    //relacion de muchos a muchos
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name="usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id",referencedColumnName = "id")
    )
    private Collection<Rol> roles;

    public Usuario(String apellido, String email, Long id, String nombre, String password, Collection<Rol> roles) {
        super();
        this.apellido = apellido;
        this.email = email;
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.roles = roles;
    }

    public Usuario(String apellido, String email, String nombre, String password, Collection<Rol> roles) {
        super();
        this.apellido = apellido;
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.roles = roles;
    }

    public Usuario() {
        super();
    }

    public String apellido() {
        return apellido;
    }

    public Usuario setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String email() {
        return email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long id() {
        return id;
    }

    public Usuario setId(Long id) {
        this.id = id;
        return this;
    }

    public String password() {
        return password;
    }

    public Usuario setPassword(String password) {
        this.password = password;
        return this;
    }

    public String nombre() {
        return nombre;
    }

    public Usuario setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Collection<Rol> roles() {
        return roles;
    }

    public Usuario setRoles(Collection<Rol> roles) {
        this.roles = roles;
        return this;
    }
}
