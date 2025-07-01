
package BoutiqueOnline.servicio;

//UserDetail para integrar con spring securtiy


import BoutiqueOnline.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioServicio{ //busqueda de un suario heredar
    
    Optional<Usuario> finsById (Integer id);
    //guardar usuario
    Usuario save(Usuario usuario);
    //para ingresar por email
    Optional<Usuario> findByEmail(String email);
    //mostrar  lista de usuario
    List<Usuario> findAll();
    //elimaar
    void deleteById(Integer id);
}
