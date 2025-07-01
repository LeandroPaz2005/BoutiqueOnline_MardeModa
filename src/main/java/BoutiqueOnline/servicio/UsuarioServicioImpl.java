package BoutiqueOnline.servicio;

import BoutiqueOnline.Repositorio.UsuarioRepositorio;
import BoutiqueOnline.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public Optional<Usuario> finsById(Integer id) {
        return usuarioRepositorio.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

    
}


