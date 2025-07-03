package BoutiqueOnline.Controlador;

import BoutiqueOnline.Servicio.DetalleOrdenServicio;
import BoutiqueOnline.Servicio.OrdenServicio;
import BoutiqueOnline.Servicio.ProductoServicio;
import BoutiqueOnline.modelo.DetalleOrden;
import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.servicio.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HomeControlador {

    private final Logger log = LoggerFactory.getLogger(HomeControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private OrdenServicio ordenServicio;

    @Autowired
    private DetalleOrdenServicio detalleServico;

    //para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    //datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String Inicar(Model model, HttpSession session) {
        log.info("\nSession del Usuario: {}", session.getAttribute("idusuario"));
        model.addAttribute("productos", productoServicio.findAll());
        return "usuario/index";
    }

    @GetMapping("/productoHome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) {

        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoServicio.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/productoHome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> optionalProducto = productoServicio.get(id);
        log.info("\nProducto añadido: {}", optionalProducto.get());
        log.info("\nCantidad: {}", cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añada 2 veces
        Integer idproducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idproducto);
        if (!ingresado) {
            detalles.add(detalleOrden);
        }

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        //detalles.add(detalleOrden);

        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    //quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProducto(@PathVariable Integer id, Model model) {
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNueva.add(detalleOrden);
            }
        }
        //poner la nueva lista con los productos restantes
        detalles = ordenesNueva;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model) {
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "/usuario/carrito";
    }

    @GetMapping("/order")
    public String orden(Model model, HttpSession session) {

        Object idUsuarioAttr=session.getAttribute("idusuario");
        if(idUsuarioAttr==null){
        return "redirect:/usuario/login";
        }
        
        int idUsuario=Integer.parseInt(idUsuarioAttr.toString());
        Usuario usuario=usuarioServicio.finsById(idUsuario).orElse(null);
        
        if(usuario==null){ //usuario no  existe
        return "redirect:/usuario/login";
        }
        
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/resumenorden";
    }

    @GetMapping("/saveOrder")
    private String saveOrder(HttpSession session) {

        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenServicio.generarNumeroOrden());
        //usario que guarda el orden
        Usuario usuario = usuarioServicio.finsById(Integer.parseInt((session.getAttribute("idusuario").toString()))).get();

        orden.setUsuario(usuario);
        orden.setDetalle(detalles);
        ordenServicio.save(orden);
        
        
        //guardar detalles del orden 
        for (DetalleOrden dt : detalles) {
            dt.setOrden(orden);
            detalleServico.save(dt);
        }
        //limpiar lista y orden
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    @PostMapping("/buscar")
    public String searchProducto(@RequestParam String nombre, Model model) {
        log.info("\nNombre del producto: {}", nombre);

        String nombreLower = nombre.toLowerCase(); // convierte el input a minúsculas

        List<Producto> productos = productoServicio.findAll().stream()
                .filter(p -> p.getNombre() != null && p.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());

        model.addAttribute("productos", productos); // para mostrar en la vista
        return "usuario/home";
    }

    @ModelAttribute
    public void agregarUsuarioAlModelo(Model model, HttpSession session) {
        if (session.getAttribute("idusuario") != null) {
            model.addAttribute("usuario",(Usuario)session.getAttribute("usuario"));
        }
    }

}
