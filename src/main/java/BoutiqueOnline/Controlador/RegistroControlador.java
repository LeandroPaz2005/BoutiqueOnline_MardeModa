package BoutiqueOnline.Controlador;
//controlador del login

import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//gestiona las vistas del sistema 
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

//para ver la pagina inicial
    @GetMapping("/inicio")
    public String mostrarUsuarios(Model model) { //objeto que trasporta datos a la vista
        model.addAttribute("usuarios", servicio.listarUsuario()); //lista obtenida del servicio
        return "index"; 
    }

}
