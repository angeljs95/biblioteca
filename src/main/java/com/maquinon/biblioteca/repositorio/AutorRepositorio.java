
package com.maquinon.biblioteca.repositorio;

import com.maquinon.biblioteca.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AutorRepositorio extends JpaRepository <Autor,String >  {
    
}
