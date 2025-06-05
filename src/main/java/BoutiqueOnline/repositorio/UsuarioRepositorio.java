
package BoutiqueOnline.repositorio;

import BoutiqueOnline.modelo.Usuario;
import jakarta.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{


    
    //metodo para encontrar el email
    public Usuario findByEmail(String email);
    
}
