
package com.imagen.service;

import com.imagen.entity.Imagen;
import java.util.List;


public interface imagenIServ {
    public void nuevaImagen (Imagen ima);
    public List <Imagen> verImagenes();
    public void eliminarImagen (Long id);
    public Imagen verImagen(Long id);
    
}
