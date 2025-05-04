package BoutiqueOnline.Controlador;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.servicio.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioControlador {

    private UsuarioServicio usuarioServicio;

    public RegistroUsuarioControlador(UsuarioServicio usuarioServicio) {
        super();
        this.usuarioServicio = usuarioServicio;
    }

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNueviUsuarioRegistroDTO(){
        return new UsuarioRegistroDTO();
    }

    @GetMapping
    public String mostrarFormulario(){
        return "registro";
    }

    @PostMapping
    public String registrarCuenta(@ModelAttribute("usuario")UsuarioRegistroDTO registroDTO ){
        usuarioServicio.guardar(registroDTO);
        return "redirect:/registro?exito";
    }

}
