package com.imagen.controller;

import com.imagen.entity.Imagen;
import com.imagen.service.imagenIServ;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Controller {

    @Value("${image.storage.path}")
    private String storagePath;

    @Autowired
    private imagenIServ imaIServ;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file) {

        try {
            
            String fileName = file.getOriginalFilename();
            Path imagePath = Paths.get(storagePath + fileName);
            
            String ruta = "http://191.96.251.43/prueba/"+fileName;

            File folder = new File("images/");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

//        String fileName = UUID.randomUUID().toString();
//        
//        String fileOriginalName = file.getOriginalFilename();
//        String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
//        
//        String newFileName= fileName + fileExtension;
//        
//        Path path = Paths.get("/var/www/html/images/"+ newFileName);
//        String ruta= path.toString();
            Imagen imagen = new Imagen();
            imagen.setNombre(ruta);
            imaIServ.nuevaImagen(imagen);

            return ResponseEntity.ok("Archivo guardado exitosamente");
        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el archivo");
        }
    }

    @GetMapping("/ver")
    public List<Imagen> verImagenes() {
        return imaIServ.verImagenes();
    }

    @GetMapping("/imagen/{nombre}")
    public ResponseEntity<ByteArrayResource> obtenerImagen(@PathVariable String nombre) throws IOException {
        // Lógica para obtener la imagen de la carpeta app/images
        // ...
        // Devuelve la imagen como un recurso ResponseEntity
        Path rutaImagen = Paths.get("images/" + nombre);
        byte[] imagenBytes = Files.readAllBytes(rutaImagen);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new ByteArrayResource(imagenBytes));
    }

}
