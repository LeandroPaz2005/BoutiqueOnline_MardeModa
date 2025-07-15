
package BoutiqueOnline.Servicio;

import BoutiqueOnline.Repositorio.ProductoRepository;
import BoutiqueOnline.modelo.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServicioImple implements ProductoServicio{

    @Autowired
    private ProductoRepository productoRepositorio;
    
    @Override
    public Producto save(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return productoRepositorio.findById(id);
    }

    @Override
    public void update(Producto producto) {
       productoRepositorio.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRepositorio.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepositorio.findAll();
    }
    // metodos para calcular el dasboard
    public int totalStock(){
    return productoRepositorio.findAll()
            .stream()
            .mapToInt(Producto::getCantidad)
            .sum();
    }
    public int totalVendidos(){
    return productoRepositorio.findAll()
            .stream()
            .mapToInt(Producto:: getUnidadesVendidas)
            .sum();
    }
    
    public int totalProductos(){
    return (int) productoRepositorio.count();
    }

    @Override
    public void eliminarLogico(Integer id) {
        Producto producto=productoRepositorio.findById(id)
                .orElseThrow(()->new RuntimeException("Producto no encontradi"));
        producto.setActivo(false);
        productoRepositorio.save(producto);
    }
}
