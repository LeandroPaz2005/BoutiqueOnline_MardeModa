package BoutiqueOnline.Controlador;

import BoutiqueOnline.Repositorio.UsuarioRepositorio;
import BoutiqueOnline.Servicio.OrdenServicio;
import BoutiqueOnline.Servicio.ProductoServicio;
import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.*;
import BoutiqueOnline.util.ListarUsuariosExcel;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {

    private Logger logg = LoggerFactory.getLogger(AdministradorControlador.class);

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private OrdenServicio ordenServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioRepositorio usuariorepositorio;

    private final Logger logger = LoggerFactory.getLogger(HomeControlador.class);

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    @GetMapping("/panelAdmin")
    public String mostrarVistaPrincipal() {
        return "administrador/panel_Admin";
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/administrador/panelAdmin";
    }

    //========CRUD DE USUARIO========
    @GetMapping("/gestionUsuario")
    public String MostrarUsuario(Model model) {
        model.addAttribute("usuarios", usuarioServicio.findAll());
        return "administrador/gestionUsuario";
    }

    //Formulario para crear nuevo usuario
    @GetMapping("/usuario/nuevo")
    public String nuevoUusario(Model model) {
        model.addAttribute("usuarios", new Usuario());

        return "usuario/create";
    }

    @PostMapping("/RegistroUsuario")
    public String save(@ModelAttribute("usuarios") Usuario usuario, HttpSession session) {
        logger.info("\nUsuario recibido del formulario create: {}", usuario);

        usuario.setId(null);

        usuario.setPassword(passEncode.encode(usuario.getPassword()));
        //rol por defecto
        if (usuario.getTipo() == null || usuario.getTipo().isEmpty()) {
            usuario.setTipo("USER");
        }

        usuarioServicio.save(usuario);
        return "redirect:/administrador/gestionUsuario";
    }

    //Formulario para editar usuario
    @GetMapping("/usuario/editarUsuario/{id}")
    public String editarUsuario(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioServicio.finsById(id).orElse(null);
        model.addAttribute("usuario", usuario);
        return "usuario/edit";
    }

    //Eliminar usuario
    @GetMapping("/usuario/eliminarUsuario/{id}")
    public String eliminar(@PathVariable("id") Integer id) {

        usuariorepositorio.deleteById(id);
        return "redirect:/administrador/gestionUsuario";

    }

    @PostMapping("/usuario/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioServicio.save(usuario);
        return "redirect:/administrador/gestionUsuario";
    }

    //gerenar el excel de usuario
    @GetMapping("/exportarExcel")
    public ModelAndView exportarUsuarioExcel() {
        List<Usuario> listarUsuario = usuarioServicio.findAll();
        return new ModelAndView(new ListarUsuariosExcel(), Map.of("usuarios", listarUsuario));

    }

    //===== MOSTRAR DETALLES DE PEDIDIOS======
    @GetMapping("/gestionOrdenes")
    public String GestionOrdenes(Model model) {
        model.addAttribute("ordenes", ordenServicio.findAll());
        return "administrador/ordenes";
    }

    @GetMapping("/detalle/{id}")
    public String DetalleOrdenes(Model model, @PathVariable Integer id) {
        logg.info("\nID de la orden: {}", id);
        Orden orden = ordenServicio.findById(id).get();

        model.addAttribute("detalles", orden.getDetalle());

        return "administrador/detalleOrdenes";
    }

    @ModelAttribute
    public void agregarUsuarioAlModelo(Model model, HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        }
    }
}
