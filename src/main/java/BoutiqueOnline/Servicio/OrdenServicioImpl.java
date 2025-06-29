
package BoutiqueOnline.Servicio;

import BoutiqueOnline.Repositorio.OrdenRepositorio;
import BoutiqueOnline.modelo.Orden;
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
    
    @Override
    public Orden save(Orden orden) {
        return ordenRespositorio.save(orden);
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
