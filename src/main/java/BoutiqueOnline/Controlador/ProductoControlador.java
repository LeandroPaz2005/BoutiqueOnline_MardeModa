package BoutiqueOnline.Controlador;

import BoutiqueOnline.Servicio.ProductoServicio;
import BoutiqueOnline.Servicio.UploadFileService;
import BoutiqueOnline.Servicio.UsuarioServicio;
import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/productos")
public class ProductoControlador {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UploadFileService uplaod;
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    //mostrrar la visat de GestionProducto
    @GetMapping("/MostrarProducto")
    public String MostrarProductoUsuario(Model model) {
        List<Producto> producto = productoServicio.findAll();
        model.addAttribute("productos", producto);
        return "usuario/home";
    }
     @GetMapping("/RegistroProductos")
    public String MostrarProductoAdmin(Model model) {
        List<Producto> producto = productoServicio.findAll();
        model.addAttribute("productos", producto);
        return "productos/gestionProducto";
    }

    //mostrar la vista de productos la tabla 
    @GetMapping("/gestionProducto")
    public String show(Model model) {
        model.addAttribute("productos", productoServicio.findAll());
        return "productos/show";
    }

    //visat de crear prodcutos
    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("\nEste es el objeto producto {}: ", producto);
        
        Usuario u = usuarioServicio.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        producto.setUsuario(u);

        //subir la imagen en el servidor y el nombre
        if (producto.getId() == null) {//cuando se crea un producto
            String nombreImagen = uplaod.saveImage(file);
            producto.setImagen(nombreImagen);
        } else {

        }

        productoServicio.save(producto);
        return "redirect:/productos/gestionProducto";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoServicio.get(id);
        producto = optionalProducto.get();

        LOGGER.info("\nProducto buscado: {}", producto);
        model.addAttribute("producto", producto);

        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            Producto p = new Producto();
            p = productoServicio.get(producto.getId()).get();
            producto.setImagen(p.getImagen());
        } else {//cuando se edita tambien la imgan
            Producto p = new Producto();
            p = productoServicio.get(producto.getId()).get();

            //eliminar cuando no sea por defecto
            if (p.getImagen().equals("default.jpg")) {
                uplaod.deleteImagen(p.getImagen());
            }

            String nombreImagen = uplaod.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        productoServicio.update(producto);
        return "redirect:/productos/gestionProducto";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Producto p = new Producto();
        p = productoServicio.get(id).get();

        //eliminar cuando no sea por defecto
        if (p.getImagen().equals("default.jpg")) {
            uplaod.deleteImagen(p.getImagen());
        }

        productoServicio.delete(id);
        return "redirect:/productos/gestionProducto";
    } 
    
    @ModelAttribute
    public void agregarUsuarioAlModelo(Model model, HttpSession session){
    if(session.getAttribute("usuario")!=null){
    model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
    }
    }
}
