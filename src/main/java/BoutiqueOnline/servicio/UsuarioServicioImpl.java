package BoutiqueOnline.servicio;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.modelo.Rol;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.repositorio.UsuarioRepositorio;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = new Usuario(
                registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()),
                ImmutableList.of(new Rol("ROL_USER")) //lista inmutable de guava
        );
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuario() {

        return ImmutableList.copyOf(usuarioRepositorio.findAll()); //evitar modificación externa con guava

        return (List<Usuario>)usuarioRepositorio.findAll();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Preconditions.checkNotNull(username, "El nombre de usuario no puede ser null"); //validación segura con guava
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o contraseña invalida");
        }
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                mapearAutoridadesRoles(usuario.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.nombre()))
                .collect(Collectors.toList());
    }

    //metodo del crud para guardar el usuario

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        usuarioRepositorio.save(usuario);
    }

    @Override
    public Optional<Usuario> get(Integer id) {
        return usuarioRepositorio.findById(Long.valueOf(id));
    }

    @Override
    public void delete(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepositorio.findById(id);
    }
}

}


