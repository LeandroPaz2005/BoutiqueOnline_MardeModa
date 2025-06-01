
package BoutiqueOnline.servicio;

//un servicio para subir imagenes
import java.io.*;
import java.nio.file.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileService {
    private String folder = "images//";

    
    //guardar una imagen
    public String saveImages(MultipartFile file) throws IOException{
        if(!file.isEmpty()){ //cuando sube la imagen
            byte [] bytes=file.getBytes();
            Path path= Paths.get(folder+file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        }//cuando no sube una imagen se subiera por defecto 
    return "default.jpg";
    }
    
    //eliminar una imagen
    public void deleteImagen(String nombre){
        String ruta="images//";
        File file=new File(ruta+nombre);
        file.delete();
    }
}
