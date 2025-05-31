package BoutiqueOnline.Controlador;

import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.ProductoServicio;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoControlador {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/gestionProducto")
    public String VistaProducto() {
        return "productos/gestionProducto";
    }

    @GetMapping("/show")
    public String show(Model model) {
        model.addAttribute("productos", productoServicio.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto) {
        LOGGER.info("Este es el objeto producto {} ", producto);
        Usuario u = new Usuario(2, "", "", "", "", "");
        producto.setUsuario(u);
        productoServicio.save(producto);
        return "redirect:/productos/show";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoServicio.get(id);
        producto = optionalProducto.get();

        LOGGER.info("Producto buscado: {}", producto);
        model.addAttribute("producto", producto);

        return "productos/edit";
    }
    
    @PostMapping("/update")
    public String updale(Producto producto){
    productoServicio.update(producto);
        return "redirect:/productos/show";
    }
    
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        productoServicio.delete(id);
    return "redirect:/productos/show";
    }
}
