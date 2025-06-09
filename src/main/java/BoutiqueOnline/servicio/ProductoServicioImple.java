package BoutiqueOnline.servicio;

import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.repositorio.ProductoRepositorio;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ProductoServicioImple implements ProductoServicio {

    private final ProductoRepositorio productoRepositorio;

    //caché de productos para lecturas rápidas con google.guava
    private final Cache<Integer, Producto> productoCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    @Autowired
    public ProductoServicioImple(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @Override
    public Producto save(Producto producto) {
        Producto saved = productoRepositorio.save(producto);
        productoCache.put(saved.getId(), saved); // actualizar caché al guardar
        return saved;
    }

    @Override
    public Optional<Producto> get(Integer id) {
        Producto producto = productoCache.getIfPresent(id);
        if (producto == null) {
            producto = productoRepositorio.findById(id).orElse(null);
            if (producto != null) {
                productoCache.put(id, producto);
            }
        }
        return Optional.ofNullable(producto);
    }

    @Override
    public void update(Producto producto) {
        productoRepositorio.save(producto);
        productoCache.put(producto.getId(), producto); // actualiza caché también
    }

    @Override
    public void delete(Integer id) {
        productoRepositorio.deleteById(id);
        productoCache.invalidate(id); // eliminar del caché
    }

    @Override
    public List<Producto> findAll() {
        return ImmutableList.copyOf(productoRepositorio.findAll());
    }
}