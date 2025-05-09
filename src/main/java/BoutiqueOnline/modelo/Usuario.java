package BoutiqueOnline.modelo;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//represanta la entidad de usuario
@Entity
@Table(name = "usuarios", uniqueConstraints =@UniqueConstraint(columnNames = "email"))
public class Usuario {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //nombre del usuario(requerido,entre 2 y 30 caracteres)
    @NotBlank(message="El nombre no puede estar vacío")
    @Size(min=2, max=30, message="El nombre debe tener entre 2 y 30 caracteres")
    @Column(name = "nombre")
    private String nombre;
    
    //apellido del usuario
    @NotBlank(message="El apellido no puede estar vacío")
    @Size(min=2, max=30, message="El apellido debe tener entre 2 y 30 caracteres")
    @Column(name = "apellido")
    private String apellido;


    //email unico, requerido y formato valido
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message="Debe ser un correo válido")
    private String email;
    
    //contraseña encriptada
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    //para la vlaidacion de contrasñea confirmar
    @Transient
    private String ConfirmarPassword;
    
    
    //relacion de muchos a muchos entre usuarios y roles
    @Size(min=1, message = "debe tenern al menos un rol asigando")
    @ManyToMany(fetch = FetchType.EAGER) //se cargan los roles
    @JoinTable(
            name = "usuarios_roles", //tabal para establecer relacion
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Collection<Rol> roles;

    public Usuario(Long id, String nombre, String apellido, String email, String password, Collection<Rol> roles) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    //contructores para almacenar datos
    public Usuario(String nombre, String apellido, String email, String password, Collection<Rol> roles) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public Usuario() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Rol> getRoles() {
        return roles;
    }
   
    //metodos sobreescritos para comparacion y loggin

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + 
                ", nombre=" + nombre + 
                ", apellido=" + apellido + 
                ", email=" + email + 
                ", roles=" + roles + '}';
    }
   
    
    @Override
    public boolean equals(Object o){
    if(this==o) return true;
    if(!(o instanceof Usuario usuario)) return false;
    return Objects.equals(id,usuario.id) && Objects.equals(email, usuario.email);
    }
    
    @Override
    public int hashCode(){
    return Objects.hash(id, email);
    }
    
}
