
package BoutiqueOnline.servicio;

import BoutiqueOnline.modelo.Orden;
import java.util.List;

public interface OrdenService {
    //metodo para guardar la orden
    Orden save(Orden orden);
    //metodo para obtner todas las ordenes en una lista
    List<Orden> findAll();
    //metodo de generar el numero de orden 
    String generarNumeroOrden();
}
