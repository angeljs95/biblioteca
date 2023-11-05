package com.maquinon.biblioteca.repositorio;

import com.maquinon.biblioteca.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository <Usuario, String>{

    @Query( "Select u from Usuario u where u.email= :email")
    public Usuario buscarPorEmail(@Param("email")String email);

}


