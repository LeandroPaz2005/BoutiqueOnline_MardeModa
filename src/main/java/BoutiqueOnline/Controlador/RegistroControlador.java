
package BoutiqueOnline.Controlador;

//gestiona las vistas del sistema 

import BoutiqueOnline.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistroControlador {

    //servicio que gestiona la relacion con los usuarios
    @Autowired
    private UsuarioServicio servicio;

    //muestra de la vista del login
    @GetMapping("/login")
    public String inicarSesion() {
        return "login";
    }

    //para para los usuarios normales
    @GetMapping("/")
    public String VerpaginaInicio(Model model) {
        //agregar el nombre del usuario 
        model.addAttribute("usuarios",servicio.listarUsuario());
        return "panel_Admin";
    }
}
