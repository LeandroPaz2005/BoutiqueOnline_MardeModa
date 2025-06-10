package BoutiqueOnline.Controlador;

import BoutiqueOnline.Reportes.ReporteExcelServicio;
import BoutiqueOnline.modelo.Rol;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.UsuarioServicio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {
    
    @Autowired
    private ReporteExcelServicio reporteExcel;

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Vista principal de gestión de usuarios
    @GetMapping("/gestionUsuario")
    public String vistaUsuario(Model model) {
        model.addAttribute("usuarios", usuarioServicio.listarUsuario());
        return "administrador/gestionUsuario";
    }

    // Mostrar formulario para crear usuario
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/create";
    }
    /*para asignar roles
    @GetMapping("/create")
    public String create(@RequestParam(name = "tipo", required = false) String tipo, Model model) {
        Usuario usuario = new Usuario();

        if ("admin".equalsIgnoreCase(tipo)) {
            usuario.setRoles(List.of(new Rol("ROL_ADMIN")));
            model.addAttribute("esAdmin", true);
        } else {
            model.addAttribute("esAdmin", false);
        }

        model.addAttribute("usuario", usuario);
        return "usuario/create";
    }*/

    // Guardar nuevo usuario
    @PostMapping("/save")
    public String save(@ModelAttribute Usuario usuario) {
        usuarioServicio.save(usuario);
        return "redirect:/usuarios/gestionUsuario";
    }

    // Editar usuario por ID
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Usuario> optionalUsuario = usuarioServicio.get(id);
        if (optionalUsuario.isPresent()) {
            model.addAttribute("usuario", optionalUsuario.get());
            return "usuario/edit";
        } else {
            return "redirect:/usuarios/gestionUsuario"; // Usuario no encontrado
        }
    }

    // Actualizar usuario
    @PostMapping("/update")
    public String update(@ModelAttribute Usuario usuario) {
        usuarioServicio.update(usuario);
        return "redirect:/usuarios/gestionUsuario";
    }

    //  Eliminar usuario
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        usuarioServicio.delete(id);
        return "redirect:/usuarios/gestionUsuario";
    }
    
        // Nuevo endpoint para reporte Excel
    @GetMapping("/reporteUsuario")
    public ResponseEntity<byte[]> generarReporteUsuarios() throws IOException {
        List<Usuario> usuarios = usuarioServicio.listarUsuario();
        ByteArrayInputStream excelStream = reporteExcel.generarReporteExcelUsuario(usuarios);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_usuarios.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelStream.readAllBytes());
    }

}
