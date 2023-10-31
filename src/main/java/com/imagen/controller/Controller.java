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
import org.apache.commons.io.FilenameUtils;
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
        String fileId = UUID.randomUUID().toString();
        
        // Obtiene la extensión del archivo
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        
        // Define la ruta relativa donde se guardará el archivo
        
        //local
        String filePath = "C:/Java/Images/" + fileId + "." + fileExtension;
        //produccion
       // String filePath = "/var/www/html/images/" + fileId + "." + fileExtension;
        Path rutaCarpetaImagenes = Paths.get("/var/www/html/images");
        //Path rutaCarpetaImagenes = Paths.get("C:/java");
        
        
        // Guarda el archivo en la ruta especificada
        
        Path rutaImagen = rutaCarpetaImagenes.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), rutaImagen);
            
            String rutaa= rutaImagen.toString();
        
        
        //File dest = new File(filePath);
        //file.transferTo(dest);
        
        Imagen imagen = new Imagen();
        imagen.setId(id);
        imagen.setNombre(rutaa);
        imaIServ.nuevaImagen(imagen);
        
        
        // Guarda la información del archivo en la base de datos
        // Aquí deberás utilizar tu lógica para guardar los datos en tu base de datos MySQL
        
        return ResponseEntity.ok("Archivo guardado exitosamente");
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el archivo");
    }
}

@GetMapping ("/ver")
public List <Imagen> verImagenes(){
    return imaIServ.verImagenes();
}

}
