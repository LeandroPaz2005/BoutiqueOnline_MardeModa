package BoutiqueOnline.servicio;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.modelo.Rol;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.repositorio.UsuarioRepositorio;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    //registro desde el formulario con DTO
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = new Usuario(registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()),
                Arrays.asList(new Rol("ROL_USER")));
        return usuarioRepositorio.save(usuario);
    }

    //listar usuarios
    @Override
    public List<Usuario> listarUsuario() {
        return usuarioRepositorio.findAll();
    }

    //login (spring security)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o contraseña invalida");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.nombre())).collect(Collectors.toList());
    }

    //metodo del crud para guardar el usuario
    @Override
    public Usuario save(Usuario usuario) {
        // En caso de que quieras encriptar la contraseña si es nueva
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) { // ya codificada
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepositorio.save(usuario);
    }

    //metodo para actulizar un usuario
    @Override
    public void update(Usuario usuario) {
        // Opcional si vuelve a codificar la contraseña si fue cambiada
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        usuarioRepositorio.save(usuario);
    }

    @Override
    public Optional<Usuario> get(Integer id) {
        return usuarioRepositorio.findById(Long.valueOf(id)); // Convierte a Long
    }

    @Override
    public void delete(Integer id) {
        usuarioRepositorio.deleteById(Long.valueOf(id)); // Convierte a Long
    }

}
