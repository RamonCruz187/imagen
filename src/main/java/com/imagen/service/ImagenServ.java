
package com.imagen.service;

import com.imagen.entity.Imagen;
import com.imagen.repository.imagenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenServ implements imagenIServ{
    
    @Autowired
    public imagenRepository imaRepo;
    
    @Override
    public void nuevaImagen(Imagen ima) {
      imaRepo.save(ima);
    }

    @Override
    public List<Imagen> verImagenes() {
        return imaRepo.findAll();
    }

    @Override
    public void eliminarImagen(Long id) {
        imaRepo.deleteById(id);
    }

    @Override
    public Imagen verImagen(Long id) {
        return imaRepo.findById(id).orElse(null);
    }
    
    
    
}
