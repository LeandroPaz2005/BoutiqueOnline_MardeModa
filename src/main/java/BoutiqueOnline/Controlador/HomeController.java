package BoutiqueOnline.Controlador;

import BoutiqueOnline.Reportes.ReportePDFServicio;
import BoutiqueOnline.modelo.DetalleOrden;
import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.DetalleOrdenService;
import BoutiqueOnline.servicio.OrdenService;
import BoutiqueOnline.servicio.ProductoServicio;
import BoutiqueOnline.servicio.UsuarioServicio;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador principal del home para la tienda online. Gestiona la
 * visualización de productos, carrito de compras y orden de pedido.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    //lista para almcenar los detalles de productos agregados al carrrito
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public UsuarioServicio usuarioServicio;

    @Autowired
    private ProductoServicio productoServicio; // Cambiado a ProductoServicio

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    //implemntar un objeto de servicioPDF
    @Autowired
    private ReportePDFServicio reportePDF;

    // Para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    // Datos de la orden 
    Orden orden = new Orden();

    //mostrar la galería de productos home usuario 
    @GetMapping("/MostrarProducto")
    public String home(Model model) {
        model.addAttribute("productos", productoServicio.findAll());
        return "usuario/home";
    }

    /**
     * Muestra el detalle de un producto específico seleccionado por el usuario.
     *
     * @param id ID del producto
     */
    @GetMapping("/Productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) {
        System.out.println("Id producto enviado como parámetro: " + id);
        //mensaje para comprobar que el producto visualizado
        log.info("===== MÉTODO PRODUCTO HOME EJECUTADO =====");
        log.info("Id producto enviado como parámetro: " + id);
        Producto producto = new Producto();
        //ver la existencia del producto
        Optional<Producto> productoOptional = productoServicio.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);

        return "usuario/productohome";
    }

    /**
     * Agrega un producto al carrito.
     *
     * @param id ID del producto
     * @param cantidad Cantidad seleccionada
     */
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {

        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0.0;

        Optional<Producto> optionalProducto = productoServicio.get(id);
        log.info("===== MÉTODO ADD CART EJECUTADO =====");
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);

        producto = optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se haya añadido previamente
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito"; // Asegúrate de que esta ruta sea correcta
    }

    /**
     * Elimina un producto del carrito por su ID.
     *
     * @param id ID del producto a eliminar
     */
    @GetMapping("/delete/cart/{id}")
    public String deleteCart(@PathVariable Integer id, Model model) {
        log.info("===== MÉTODO DELETE CART EJECUTADO =====");
        log.info("Id producto a eliminar: {}", id);

        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (!detalleOrden.getProducto().getId().equals(id)) {
                ordenesNueva.add(detalleOrden);
            }
        }

        // Poner la nueva lista con los  productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0.0;

        detalles.removeIf(dt -> dt.getProducto().getId().equals(id));

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito"; // Asegúrate de que esta ruta sea correcta
    }

    /**
     * Visualiza el contenido del carrito.
     */
    @GetMapping("/getCart")
    public String getMethodName(Model model) {

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }

    @GetMapping("/reporteCarrito/pdf")
    public void generarPdfCarrito(HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=carrito.pdf");

        /*
        String email = authentication.getName();
        Usuario usuario = usuarioServicio.findByEmail(email).get(); // asegúrate que tienes este método
        */
        //por mientras hasta ver como lo manejo 
        Usuario usuario = usuarioServicio.findById(14).get();
        
        ByteArrayInputStream pdf = reportePDF.generarPdfCarrito(detalles, usuario);
        org.apache.commons.io.IOUtils.copy(pdf, response.getOutputStream());
    }

    @GetMapping("/orden")
    public String orden(Model model) {
        log.info("===== MÉTODO ORDEN EJECUTADO ====="); //mensaje en el terminanl

        Usuario usuario = usuarioServicio.findById(14).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        return "usuario/resumenorden";
    }

    /**
     * Guardar orden de usaurio con fecha, numero de pedido, los detalles
     */
    @GetMapping("/saveOrder")
    public String saveOrden(Authentication authentication) {
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());
        //guardar datos del usaurio tambien
        String email = authentication.getName();

        Usuario usuario = usuarioServicio.findById(4).get();

        orden.setUsuario(usuario);
        ordenService.save(orden);

        //guardar detalles
        for (DetalleOrden dt : detalles) {
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }
        //limpiar la lista y orden de productos
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    /**
     * Para buscar productos
     *
     * @param nombre
     * @return
     */
    @PostMapping("/buscar")
    public String buscarProducto(@RequestParam String nombre, Model model) {
        log.info("Nombre del producto {}", nombre);
        List<Producto> productos = productoServicio.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);

        return "usuario/home";
    }
}
