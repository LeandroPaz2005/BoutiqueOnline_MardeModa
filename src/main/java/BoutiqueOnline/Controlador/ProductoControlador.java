package BoutiqueOnline.Controlador;

import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String show() {
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto) {
        LOGGER.info("Este es el objeto producto {} ", producto);
        Usuario u=new Usuario(2, "", "","", "", "");
        producto.setUsuario(u);
        productoServicio.save(producto);
        return "redirect:/productos/show";
    }
}
