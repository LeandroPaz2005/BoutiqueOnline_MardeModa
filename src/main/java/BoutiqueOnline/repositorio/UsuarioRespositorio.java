package BoutiqueOnline.repositorio;

import BoutiqueOnline.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//se extiende Jpa para acceder a las operaciones CRUD
@Repository
public interface UsuarioRespositorio extends JpaRepository<Usuario, Long> {

    //metodo para buscar usaurio por el correo electronico o podemos cambiar a nombre
    public Usuario findByEmail(String email);
}
