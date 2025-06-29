
package BoutiqueOnline.Repositorio;

import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.modelo.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepositorio extends JpaRepository<Orden, Integer>{
    List<Orden> findByUsuario(Usuario usuario);
}
