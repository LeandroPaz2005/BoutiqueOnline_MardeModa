
package BoutiqueOnline.repositorio;

import BoutiqueOnline.modelo.Usuario;
import jakarta.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{


    // Buscar usuario por correo
    Usuario findByEmail(String email);
    
// Este ya existe por herencia, pero puedes redefinirlo si deseas
  
    Optional<Usuario> findById(Long id);

}
