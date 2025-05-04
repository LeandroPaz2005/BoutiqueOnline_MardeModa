package BoutiqueOnline.repositorio;

import BoutiqueOnline.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRespositorio extends JpaRepository<Usuario, Long> {

}
