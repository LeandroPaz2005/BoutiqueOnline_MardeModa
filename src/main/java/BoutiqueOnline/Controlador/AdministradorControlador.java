
package BoutiqueOnline.Controlador;

import BoutiqueOnline.Servicio.OrdenServicio;
import BoutiqueOnline.Servicio.ProductoServicio;
import BoutiqueOnline.Servicio.UsuarioServicio;
import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.modelo.Usuario;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {
   
    private Logger logg=LoggerFactory.getLogger(AdministradorControlador.class);
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private OrdenServicio ordenServicio;
    
    @Autowired
    private ProductoServicio productoServicio;
    

    @GetMapping("/panelAdmin")
    public String mostrarVistaPrincipal(){
    return "administrador/panel_Admin";
    }
    
    @GetMapping("/")
    public String inicio(){
    return "redirect:/administrador/panelAdmin";
    }
    
    @GetMapping("/gestionUsuario")
    public String MostrarUsuario(Model model){
        model.addAttribute("usuarios", usuarioServicio.findAll());
        return "administrador/gestionUsuario";
    }
    
    @GetMapping("/gestionOrdenes")
    public String GestionOrdenes(Model model){
        model.addAttribute("ordenes", ordenServicio.findAll());
        
    return "administrador/ordenes";
    }
    
    @GetMapping("/detalle/{id}")
    public String DetalleOrdenes(Model model, @PathVariable Integer id){
    logg.info("\nID de la orden: {}",id);
        Orden orden=ordenServicio.findById(id).get();
        
        model.addAttribute("detalles", orden.getDetalle());
    
    return "administrador/detalleOrdenes";
    }
    
    @ModelAttribute
    public void agregarUsuarioAlModelo(Model model, HttpSession session){
    if(session.getAttribute("usuario")!=null){
    model.addAttribute("usuario", (Usuario)session.getAttribute("usuario"));
    }
    }
}
