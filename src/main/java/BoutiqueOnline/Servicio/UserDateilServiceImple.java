
package BoutiqueOnline.Servicio;

import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDateilServiceImple implements UserDetailsService{

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    HttpSession session;
    
    private Logger log=LoggerFactory.getLogger(UserDateilServiceImple.class);
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        log.info("\n este es el username");
        Optional<Usuario> optinalUser=usuarioServicio.findByEmail(username);
        if(optinalUser.isPresent()){
            log.info("\nEsto es el id del uusuario: {}", optinalUser.get().getId());
            session.setAttribute("idusuario",optinalUser.get().getId());
            Usuario usuario=optinalUser.get();
            return User.builder()
                    .username(usuario.getNombre())
                    .password(usuario.getPassword())
                    .roles(usuario.getTipo())
                    .build();   
        }else{
        throw new UsernameNotFoundException("Usuario no encontrado");
        }       
    }   
}
