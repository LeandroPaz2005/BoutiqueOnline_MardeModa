package BoutiqueOnline.servicio;

import BoutiqueOnline.modelo.Orden;
import BoutiqueOnline.repositorio.OrdenRepositorio;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenServiceImp implements OrdenService {

    @Autowired
    private OrdenRepositorio ordenRepositorio;

    @Override
    public Orden save(Orden orden) {
        return ordenRepositorio.save(orden);
    }

    @Override
    public List<Orden> findAll() {
        return ImmutableList.copyOf(ordenRepositorio.findAll());
    }

    @Override
    public String generarNumeroOrden() {
        List<Orden> ordenes = findAll();

        // transforma números de orden a enteros
        Optional<Integer> max = ordenes.stream()
                .map(o -> Ints.tryParse(o.getNumero()))
                .filter(n -> n != null)
                .max(Integer::compareTo);

        int nuevoNumero = max.map(n -> n + 1).orElse(1);

        // usar Strings.padStart de guava
        return Strings.padStart(String.valueOf(nuevoNumero), 10, '0');
    }
}