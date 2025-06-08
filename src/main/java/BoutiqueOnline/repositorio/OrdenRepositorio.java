
package BoutiqueOnline.repositorio;

import BoutiqueOnline.modelo.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepositorio extends JpaRepository<Orden, Integer>{
    
}
