
package BoutiqueOnline.Servicio;

import BoutiqueOnline.modelo.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioServicio {
    Optional<Usuario> findById(Integer id);
    //guardar usuario
    Usuario save(Usuario usuario);
    //para ingresar por email
    Optional<Usuario> findByEmail(String email);
    //mostrar lista de usuario
    List<Usuario> findAll();
}
