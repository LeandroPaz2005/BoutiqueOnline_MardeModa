
package BoutiqueOnline.Repositorio;

import BoutiqueOnline.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByActivoTrue();
}
