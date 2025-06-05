
package BoutiqueOnline.Controlador;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.ProductoServicio;
import BoutiqueOnline.servicio.UsuarioServicio;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ProductoServicio productoServicio;
    
    //controlador para ver el registro Usuario
    @GetMapping("/Usuarios")
    public String mostrarUsuario(Model model){
        List<Usuario>usuarios=usuarioServicio.listarUsuario();
        model.addAttribute("usuarios", usuarios);
        return "administrador/gestionUsuario";
    }
    //controlador para agregar usuario desde administrador
    @GetMapping("/nuevoUsuario")
    public String agregarUsuario(Model model){
        model.addAttribute("usuarios",new Usuario());
        return "form";
    }
    //guardar nuebo usuario
    @PostMapping("/guardar")
    public String guardar(@Valid UsuarioRegistroDTO u,Model model){
        usuarioServicio.guardar(u);
        return "redirect:administrador/gestionUsuario";
    }
    //editar un usuario
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,Model model){
        Optional<Usuario>usuario=usuarioServicio.listarId(id);
        model.addAttribute("usuario",usuario);
        return "form";
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,Model model){
        usuarioServicio.eliminnar(id);
        return "redirect:administrador/gestionUsuario";
    }
    /*control para ver los detalles de los productos
    @GetMapping("/productos")
    public String mostrarProductos(Model model){
        List<Producto> productos=productoServicio.findAll();
        model.addAttribute("productos", productos);
        
    return "productos/gestionProducto";
    }
}
