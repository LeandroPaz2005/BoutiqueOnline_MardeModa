
package BoutiqueOnline.Controlador;

import BoutiqueOnline.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {
    
    @Autowired
    private ProductoServicio productoServicio;
    
    @GetMapping("/gestionProducto")
    public String mostrarProducto(Model model){
    model.addAttribute("Productos", productoServicio.findAll());
        return "productos/gestionProducto";
    }
}
