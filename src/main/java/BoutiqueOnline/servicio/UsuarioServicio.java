package BoutiqueOnline.servicio;

import BoutiqueOnline.DTO.UsuarioRegistroDTO;
import BoutiqueOnline.modelo.Usuario;

public interface UsuarioServicio {
    public Usuario guardar(UsuarioRegistroDTO registroDTO);


}
