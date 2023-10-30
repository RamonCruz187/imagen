
package com.imagen.repository;

import com.imagen.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface imagenRepository extends JpaRepository <Imagen, Long> {
    
}
