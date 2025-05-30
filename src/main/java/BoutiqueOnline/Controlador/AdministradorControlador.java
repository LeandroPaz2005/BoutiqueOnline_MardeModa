
package BoutiqueOnline.Controlador;

import BoutiqueOnline.servicio.UsuarioServicio;
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
    
    @GetMapping("/gestionUsuario")
    public String mostrarUsuario(Model model){
    model.addAttribute("usuarios", usuarioServicio.listarUsuario());
    return "administrador/gestionUsuario";
    }
}
