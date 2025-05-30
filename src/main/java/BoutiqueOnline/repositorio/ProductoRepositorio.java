
package BoutiqueOnline.repositorio;

//es un DAO 

import BoutiqueOnline.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Integer>{
    
}
