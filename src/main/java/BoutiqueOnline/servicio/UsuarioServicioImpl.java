package BoutiqueOnline.servicio;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.modelo.Rol;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.repositorio.UsuarioRespositorio;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{

    //para codificar las contraseñas y almacenar 
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    // para acceder a los datos de usuario en la base de datos
    private UsuarioRespositorio usuarioRespositorio;

    //constructor de dependenicas del repositorio
    public UsuarioServicioImpl(UsuarioRespositorio usuarioRespositorio) {
        super();
        this.usuarioRespositorio = usuarioRespositorio;
    }

    //guarda un nuevo usuario en la base de datos 
    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Usuario usuario=new Usuario(registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()),/*encriptar la contraseña*/
                //aqui establecer el rol de adminitrador y usuario
                Arrays.asList(new Rol("ROL_USER"))); //asignar rol por defecto 
        return usuarioRespositorio.save(usuario);
    }

    //busca un usaurio por su email y retorna los detalles
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario=usuarioRespositorio.findByEmail(username);
        if(usuario==null){
            //lanza una excepsion si el usuario no existe
            throw new UsernameNotFoundException("USuario o contraseña invalido");
        }
        return new User(usuario.getEmail(), 
                usuario.getPassword(),mapearAutoridadesRoles(usuario.getRoles()));
    }
    
    //aqui asignar quien tiene los permisos 
    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.nombre())).collect(Collectors.toList());
    }

    //retorna la lista completa de usuario registrados
    @Override
    public List<Usuario> listarUsuario() {
            return usuarioRespositorio.findAll();
    }
    
    //por hacer : una lista de los administadores
}
