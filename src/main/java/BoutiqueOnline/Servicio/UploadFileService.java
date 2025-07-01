package BoutiqueOnline.Servicio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

    private String folder = "images//";

    public String saveImage(MultipartFile file, String subcarpeta) throws IOException {
        if (!file.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String carpetaDestino = folder + subcarpeta + "/";
            Path path = Paths.get(carpetaDestino + nombreArchivo);
            Files.createDirectories(Paths.get(carpetaDestino)); // crea la carpeta si no existe
            Files.write(path, file.getBytes());
            return nombreArchivo;
        }
        return "default.jpg";
    }

    public void deleteImagen(String subcarpeta, String nombre) {
        String ruta = folder + subcarpeta + "/" + nombre;
        File file = new File(ruta);
        file.delete();
    }
}
