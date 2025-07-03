
package BoutiqueOnline.Servicio;

import BoutiqueOnline.Repositorio.OrdenRepositorio;
import BoutiqueOnline.Repositorio.ProductoRepository;
import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.modelo.Producto;
import BoutiqueOnline.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServicioImpl implements OrdenServicio{

    @Autowired
    private OrdenRepositorio ordenRespositorio;
    
    @Autowired
    private ProductoRepository productoRepositorio;
    
    @Override
    public Orden save(Orden orden) {
        //asignar estado por defecto
        if(orden.getEstado()==null || orden.getEstado().trim().isEmpty()){
        orden.setEstado("NUEVO");
        }
        
        
        Orden ordenGuardada=ordenRespositorio.save(orden);
        
        //Restar stcok de cada producto vendido
        if(ordenGuardada.getDetalle()!=null){
        ordenGuardada.getDetalle().forEach(detalle ->{
            Producto producto=detalle.getProducto();
            
            //verificar que haya stock suficiente 
            if(producto.getCantidad()>= detalle.getCantidad()){
            producto.setCantidad((int) (producto.getCantidad()-detalle.getCantidad()));
            }else{
            //en caso que no haya stock suficiente lazar una excepcion
            producto.setCantidad(0);
            }
            
            //Incrementar unidades vendidad
            producto.setUnidadesVendidas(producto.getUnidadesVendidas()+(int) detalle.getCantidad());
            
            productoRepositorio.save(producto);
        });
        }
        
        return ordenGuardada;
    }

    @Override
    public List<Orden> findAll() {
        return ordenRespositorio.findAll();
    }
    
    public String generarNumeroOrden(){
    int numero=0;
    String numeroConcatenado="";
        
        List<Orden> ordenes=findAll();
        
        List<Integer> numeros=new ArrayList<Integer>();
        
        ordenes.stream().forEach(o->numeros.add(Integer.parseInt(o.getNumero())));
        if(ordenes.isEmpty()){
        numero=1;
        }else{
        numero=numeros.stream().max(Integer::compare).get();
        numero++;
        }
        
        if(numero<10){
        numeroConcatenado="000000000"+String.valueOf(numero);
        }else if(numero<100){
        numeroConcatenado="00000000"+String.valueOf(numero);
        }else if(numero<1000){
        numeroConcatenado="0000000"+String.valueOf(numero);
        }else if(numero<1000){
        numeroConcatenado="000000"+String.valueOf(numero);
        }
        return numeroConcatenado;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
        return ordenRespositorio.findByUsuario(usuario);
    }

    @Override
    public Optional<Orden> findById(Integer id) {
        return ordenRespositorio.findById(id);
    }
    
}
