package com.maquinon.biblioteca.repositorio;

import com.maquinon.biblioteca.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ImagenRepositorio  extends JpaRepository <Imagen, String> {

}
