
package BoutiqueOnline.Servicio;

import BoutiqueOnline.Repositorio.DetalleOrdenRepositorio;
import BoutiqueOnline.modelo.DetalleOrden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServicioImpl implements  DetalleOrdenServicio{

    @Autowired
    private DetalleOrdenRepositorio detalleRepositorio;
    
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleRepositorio.save(detalleOrden);
    }
    
}
