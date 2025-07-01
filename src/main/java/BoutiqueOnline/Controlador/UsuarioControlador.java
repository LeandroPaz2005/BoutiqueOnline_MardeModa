package BoutiqueOnline.Controlador;

import BoutiqueOnline.Servicio.OrdenServicio;
import BoutiqueOnline.Servicio.ProductoServicio;
import BoutiqueOnline.Servicio.UploadFileService;
import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final Logger logger = LoggerFactory.getLogger(HomeControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private OrdenServicio ordenServicio;

    @Autowired
    private UploadFileService uploadFileService;

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    @GetMapping("/registro")
    public String Registro() {

        return "administrador/registro";
    }

    @PostMapping("/procesarRegistro")
    public String save(Usuario usuario, HttpSession session) {
        logger.info("\nUsuario registrado: {}", usuario);
        usuario.setTipo("USER");
        usuario.setPassword(passEncode.encode(usuario.getPassword()));
        usuarioServicio.save(usuario);

        //evitar sobrescribir la sesion si ya hay alguien logeado 
        if (session.getAttribute("usuario") == null) {
            session.setAttribute("usuario", usuario);
            session.setAttribute("idusuario", usuario.getId());
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "administrador/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        logger.info("\nAcceso: {}", usuario);

        Optional<Usuario> user = usuarioServicio.findByEmail(usuario.getEmail());

        if (user.isPresent()) {
            Usuario usuarioEncontrado = user.get();
            logger.info("\nUsuario de bd: {}", usuarioEncontrado.getId());

            // Guarda el objeto completo
            session.setAttribute("idusuario", usuarioEncontrado.getId());
            session.setAttribute("usuario", usuarioEncontrado); // ← Esto faltaba

            if ("ADMIN".equals(usuarioEncontrado.getTipo())) {
                return "redirect:/administrador/panelAdmin";
            } else {
                return "redirect:/";
            }
        } else {
            logger.info("Usuario no existe");
            return "redirect:/usuario/login?error"; // muestra mensaje de error si quieres
        }
    }

    @GetMapping("/compras")
    public String obtnerCompras(Model model, HttpSession session) {
        model.addAttribute("session", session.getAttribute("idusuario"));

        Usuario usuario = usuarioServicio.finsById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        List<Orden> ordenes = ordenServicio.findByUsuario(usuario);
        model.addAttribute("ordenes", ordenes);

        return "usuario/compras";
    }

    @GetMapping("/detalle/{id}")
    public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {

        logger.info("\nID de la orden: {}", id);
        Optional<Orden> orden = ordenServicio.findById(id);

        model.addAttribute("detalles", orden.get().getDetalle());

        //sesion
        model.addAttribute("session", session.getAttribute("idusuario"));

        return "usuario/detalleCompra";
    }

    @GetMapping("/cerrar")
    public String CerrarSesion(HttpSession session) {
        session.removeAttribute("usuario");
        session.removeAttribute("idusuario");
        return "redirect:/";
    }

    //modificar para que se puede editar el perfil
    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "usuario/perfil"; // debes crear esta vista
    }

    @GetMapping("/perfil/editar")
    public String editarPerfil(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "usuario/perfil-edit"; 
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@ModelAttribute("usuario") Usuario usuario,
            @RequestParam("imagen") MultipartFile imagen,
            HttpSession session) throws IOException {

        // Obtener usuario actual (por seguridad)
        Usuario usuarioActual = usuarioServicio.finsById(usuario.getId()).orElse(null);

        // Actualizar campos
        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setApellido(usuario.getApellido());
        usuarioActual.setEmail(usuario.getEmail());
        usuarioActual.setTelefono(usuario.getTelefono());
        usuarioActual.setDireccion(usuario.getDireccion());

        // Foto de perfil
        if (!imagen.isEmpty()) {
            // Eliminar imagen anterior si no es default
            if (usuarioActual.getFoto() != null && !usuarioActual.getFoto().equals("default.jpg")) {
                uploadFileService.deleteImagen("usuarios", usuarioActual.getFoto());
            }

            String nombreImagen = uploadFileService.saveImage(imagen, "usuarios");
            usuarioActual.setFoto(nombreImagen);
        }

        usuarioServicio.save(usuarioActual);

        // Actualizar sesión si es el usuario activo
        session.setAttribute("usuario", usuarioActual);

        return "redirect:/usuario/perfil";
    }

    @ModelAttribute
    public void agregarUsuarioAlModelo(Model model, HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
        }
    }

}
