
package BoutiqueOnline.servicio;

import BoutiqueOnline.modelo.DetalleOrden;
import BoutiqueOnline.repositorio.DetalleOrdenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImple implements DetalleOrdenService{

    //declar variable
    @Autowired
    private DetalleOrdenRepositorio detalleOrdenRespositorio;
    
    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRespositorio.save(detalleOrden);
    }
    
}
