
package BoutiqueOnline.test;

import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.repositorio.ProductoRepositorio;
import BoutiqueOnline.servicio.ProductoServicioImple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ProductoServicioTets {
    @Mock
    private ProductoRepositorio productoRepositorio;
    
    @InjectMocks
    private ProductoServicioImple productoServicio;
    
    @Test
    public void tetsGuardarProducto(){
    
    //arrange
        Producto producto=new Producto("Vestido azul", 25.50);
        when(productoRepositorio.save(producto)).thenReturn(producto);
        
        //act
        Producto resultado=productoServicio.save(producto);
        
        //assert
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals("Vestido azul", resultado.getNombre());
        Assertions.assertEquals(25.50, resultado.getPrecio());
        verify(productoRepositorio).save(producto);
    }
}
