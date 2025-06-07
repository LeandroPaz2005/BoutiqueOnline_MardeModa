
package BoutiqueOnline.servicio;

//UserDetail para integrar con spring securtiy

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioServicio extends UserDetailsService{ //busqueda de un suario heredar
   
   //metodo para guardar 
    public Usuario save(Usuario usuario);

    //obtner un producto
    public Optional<Usuario> get(Integer id);

    //metodo para actualizar
    public void update(Usuario usuario);

    //metodo para eliminar
    public void delete(Integer id);

    //para hacer una lista de usuario
    public List<Usuario> listarUsuario();
    
    //para guardar un usuarioDTO
    Usuario guardar(UsuarioRegistroDTO registroDTO);
    //para listar por id
    public Optional<Usuario>listarId(Long id);
    //para eliminar usuario
    public void eliminnar(Long id);

}
