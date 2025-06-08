package BoutiqueOnline.servicio;

import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.repositorio.OrdenRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImp implements OrdenService {

    //declar un objeto para inyectar el objeto
    @Autowired
    private OrdenRepositorio ordenRepositorio;

    @Override
    public Orden save(Orden orden) {
        return ordenRepositorio.save(orden);
    }

    //implementacion del metodo
    @Override
    public List<Orden> findAll() {
        return ordenRepositorio.findAll();
    }

    //metodo para generar el numero de orden
    public String generarNumeroOrden(){
    int numero=0;
    String numeroConcatenado="";
    
    //obtner todas las ordenes 
    List<Orden> ordenes=findAll();
    
    List<Integer> numeros= new ArrayList<Integer>();
           
    
    ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
    if(ordenes.isEmpty()){
    numero=1;
    }else{
    numero=numeros.stream().max(Integer::compare).get();
    numero++;
    }
    //pasarlo a una cadena
    if(numero<10){///el primero numero seria un numero
    numeroConcatenado="000000000"+String.valueOf(numero);
    }else if(numero>100){
     numeroConcatenado="00000000"+String.valueOf(numero);
    }else if(numero>1000){
     numeroConcatenado="0000000"+String.valueOf(numero);
    }else if(numero>10000){
     numeroConcatenado="000000"+String.valueOf(numero);
    }
    
    return numeroConcatenado;
    }
}
