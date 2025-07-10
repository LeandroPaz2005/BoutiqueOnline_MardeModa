/*
package BoutiqueOnline.servicio;

import BoutiqueOnline.Repositorio.UsuarioRepositorio;
import BoutiqueOnline.modelo.Usuario;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioImplTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioServicioImpl usuarioServicio;

    @Test
    void testBuscarPorId() {
        System.out.println("Ejecutando test: buscar usuario por ID");
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Karol");

        when(usuarioRepositorio.findById(1)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioServicio.finsById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Karol", resultado.get().getNombre());
        verify(usuarioRepositorio).findById(1);
        
        System.out.println("Test buscar usuario por ID completado");
    }

    @Test
    void testGuardarUsuario() {
        System.out.println("Ejecutando test: guardar nuevo usuario");
        
        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");

        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioServicio.save(usuario);

        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
        verify(usuarioRepositorio).save(usuario);
        
        System.out.println("Test guardar usuario ejecutado correctamente");
    }
    
    
    @Test
    void testBuscarPorEmail() {
        System.out.println("Ejecutando test: buscar usuario por email");
        
        String email = "test@correo.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre("Prueba");

        when(usuarioRepositorio.findByEmail(email)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioServicio.findByEmail(email);

        assertTrue(resultado.isPresent());
        assertEquals("Prueba", resultado.get().getNombre());
        verify(usuarioRepositorio).findByEmail(email);
        
        System.out.println("Test buscar por emial exitoso");
    }

    @Test
    void testListarTodosLosUsuarios() {
        System.out.println("Ejecutando tets: listar todos los usuarios");
        Usuario u1 = new Usuario();
        u1.setNombre("Karol");

        Usuario u2 = new Usuario();
        u2.setNombre("Andrea");

        when(usuarioRepositorio.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> usuarios = usuarioServicio.findAll();

        assertEquals(2, usuarios.size());
        assertEquals("Karol", usuarios.get(0).getNombre());
        assertEquals("Andrea", usuarios.get(1).getNombre());
        verify(usuarioRepositorio).findAll();
        System.out.println("Test listar usuario completado sin errores");
    }

   
}
*/