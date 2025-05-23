
package BoutiqueOnline.DTO;


public class UsuarioRegistroDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;

    public UsuarioRegistroDTO(String apellido, String email, Long id, String nombre, String password) {
        super();
        this.apellido = apellido;
        this.email = email;
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public UsuarioRegistroDTO(String apellido, String email, String nombre, String password) {
        super();
        this.apellido = apellido;
        this.email = email;
        this.nombre = nombre;
        this.password = password;
    }

    public UsuarioRegistroDTO(String email) {
        super();
        this.email = email;
    }

    public UsuarioRegistroDTO() {
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

}


