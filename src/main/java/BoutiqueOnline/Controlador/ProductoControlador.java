package BoutiqueOnline.Controlador;

import BoutiqueOnline.Servicio.ProductoServicio;
import BoutiqueOnline.Servicio.UploadFileService;
import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.UsuarioServicio;
import BoutiqueOnline.util.ListarProductosExcel;
import BoutiqueOnline.util.ListarProductosPdf;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
import org.springframework.web.servlet.ModelAndView;

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
        List<Producto> producto = productoServicio.findAll()
                .stream()
                .filter(p -> p.getCantidad()>0)
                .filter(p->Boolean.TRUE.equals(p.isActivo()))
                .collect(Collectors.toList());
        model.addAttribute("productos", producto);
        return "usuario/home";
    }
     @GetMapping("/RegistroProductos")
    public String MostrarProductoAdmin(Model model) {
        List<Producto> producto = productoServicio.findAll()
                .stream()
                .filter(p->Boolean.TRUE.equals(p.isActivo()))
                .collect(Collectors.toList());
        model.addAttribute("productos", producto);
        return "productos/gestionProducto";
    }

    //mostrar la vista de productos la tabla 
    @GetMapping("/gestionProducto")
    public String show(Model model) {
        List<Producto> productos=productoServicio.findAll()
                .stream()
                .filter(p->Boolean.TRUE.equals(p.isActivo()))
                .collect(Collectors.toList());
        model.addAttribute("productos", productos);
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
        
        Usuario u = usuarioServicio.finsById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();

        producto.setUsuario(u);

        //subir la imagen en el servidor y el nombre
        if (producto.getId() == null) {//cuando se crea un producto
            String nombreImagen = uplaod.saveImage(file, "productos");
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
                uplaod.deleteImagen("productos", p.getImagen());
            }

            String nombreImagen = uplaod.saveImage(file, "productos");
            producto.setImagen(nombreImagen);
        }
        productoServicio.update(producto);
        return "redirect:/productos/gestionProducto";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
      Optional<Producto> optionalProducto=productoServicio.get(id);
      if(optionalProducto.isPresent()){
      Producto producto=optionalProducto.get();
      
      //eliminar imagen si no es por defecto
      if(!producto.getImagen().equals("default.jpg")){
          uplaod.deleteImagen("productos", producto.getImagen());
      }
      
      producto.setActivo(false);
      productoServicio.update(producto);
      }
        return "redirect:/productos/gestionProducto";
    } 
    
    @GetMapping("/reporteExcel")
    public ModelAndView generarExcel(){
    List<Producto> productos=productoServicio.findAll();
    return new ModelAndView(new ListarProductosExcel(), Map.of("productos",productos));
    }
         
    @GetMapping("/reportePDF")
    public ModelAndView generarPDF(){
    List<Producto> productos = productoServicio.findAll();
    return new ModelAndView(new ListarProductosPdf(), Map.of("productos", productos));
    }
    
    @ModelAttribute
    public void agregarUsuarioAlModelo(Model model, HttpSession session){
    if(session.getAttribute("usuario")!=null){
    model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
    }
    }
}
