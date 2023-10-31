/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.imagen.controller;

import com.imagen.entity.Imagen;
import com.imagen.service.imagenIServ;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Controller {
    
    @Autowired
    private imagenIServ imaIServ;
    
    @PostMapping("/upload")
public ResponseEntity <String> uploadImage( @RequestParam("id") Long id,
        @RequestParam("file") MultipartFile file) {
    // Aquí puedes realizar validaciones o procesamientos adicionales antes de guardar el archivo
    
    
    try {
        // Genera un ID único para el archivo
        String fileName = UUID.randomUUID().toString();
        byte [] bytes = file.getBytes();
        
        String fileOriginalName = file.getOriginalFilename();
        String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        
        String newFileName= fileName + fileExtension;
        
        File folder = new File("/var/www/html/picture/");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        
        Path path = Paths.get("/var/www/html/picture/"+ newFileName);
        String ruta= path.toString();
        
        Files.write(path, bytes);
        
        
        Imagen imagen = new Imagen();
        imagen.setId(id);
        imagen.setNombre(ruta);
        imaIServ.nuevaImagen(imagen);
        
        
        // Guarda la información del archivo en la base de datos
        // Aquí deberás utilizar tu lógica para guardar los datos en tu base de datos MySQL
        
        return ResponseEntity.ok("Archivo guardado exitosamente");
    } catch (IOException e) {
       
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el archivo");
    }
}

@GetMapping ("/ver")
public List <Imagen> verImagenes(){
    return imaIServ.verImagenes();
}

}
