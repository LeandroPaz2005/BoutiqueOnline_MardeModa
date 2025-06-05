
package BoutiqueOnline.Controlador;

import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.servicio.ProductoServicio;
import BoutiqueOnline.servicio.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ProductoServicio productoServicio;
    
    //controlador para ver el registro Usuario
    @GetMapping("/usuarios")
    public String mostrarUsuario(Model model){
    model.addAttribute("usuarios", usuarioServicio.listarUsuario());
    return "administrador/gestionUsuario";
    }
    
    //control para ver los detalles de los productos
    @GetMapping("/productos")
    public String mostrarProductos(Model model){
        List<Producto> productos=productoServicio.findAll();
        model.addAttribute("productos", productos);
        
    return "productos/gestionProducto";
    }
}
