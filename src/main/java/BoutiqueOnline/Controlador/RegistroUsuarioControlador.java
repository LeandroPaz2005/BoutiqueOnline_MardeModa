
package BoutiqueOnline.Controlador;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.servicio.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//controlador que gestiona el proceso de registro de usuarios
@Controller
@RequestMapping("/registro")
public class RegistroUsuarioControlador {

    //servicio encargado de la persistencia y validacion de usuario
    private UsuarioServicio usuarioServicio;

    //constructor de dependenias para el servicio de usuario
    public RegistroUsuarioControlador(UsuarioServicio usuarioServicio) {
        super();
        this.usuarioServicio = usuarioServicio;
    }

    //crea un objeto
    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNueviUsuarioRegistroDTO() {
        return new UsuarioRegistroDTO();
    }

    //muestra el formulario de registro al usuario
    @GetMapping
    public String mostrarFormulario() {
        return "administrador/registro";
    }

    //procesa el registro enviado
    @PostMapping
    public String registrarCuenta(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO,
                                    BindingResult resultado,
                                    Model model) {
        //guardar el usuario si todo es 
        usuarioServicio.guardar(registroDTO);
        return "redirect:/registro?exito";
    }

}
