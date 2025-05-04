package BoutiqueOnline.servicio;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.modelo.Rol;
import BoutiqueOnline.modelo.Usuario;
import BoutiqueOnline.repositorio.UsuarioRespositorio;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{

    private UsuarioRespositorio usuarioRespositorio;

    public UsuarioServicioImpl(UsuarioRespositorio usuarioRespositorio) {
        super();
        this.usuarioRespositorio = usuarioRespositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Usuario usuario= new Usuario(
                registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                registroDTO.getPassword(),
                Arrays.asList(new Rol("ROL_USER")));
        return usuarioRespositorio.save(usuario);
    }
}
