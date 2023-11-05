
package com.maquinon.biblioteca.servicios;

import com.maquinon.biblioteca.entidades.Autor;
import com.maquinon.biblioteca.repositorio.AutorRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maquinon.biblioteca.exceptions.MiExceptions;


@Service
public class AutorService {
    
    @Autowired
    AutorRepositorio autorRepositorio;
    // Creamos un autor, solo con el nombre, el id se autogenera
     // transactional indica si el metodo se ejecuta sin problemas hace un commit en bd y aplica los cambios
    // si lanza una excepcion y no es atrapada se vuelve atras y no se ejecuta
    //Todo lo que genere algun cambio en la bd tiene que tener una etiqueta transactional, para informar que se hace un cambio
    @Transactional
    public void crearAutor(String nombre)throws MiExceptions {
        validar(nombre);
        Autor autor= new Autor();
        
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
        
       
        
        
    }
 
    // Listamos todos los autories de BD
    public List<Autor> listarAutores(){
        List<Autor> autores= new ArrayList();
        
        autores=autorRepositorio.findAll();
        
        return autores;
     }
   
    
    
    public void modificarAutor(String nombre, String id) throws MiExceptions {
        validar(nombre);
        Optional<Autor> respuesta= autorRepositorio.findById(id);
       
        if (respuesta.isPresent()){
            Autor autor= respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
      
        
    }
    
    private void validar(String nombre) throws MiExceptions {
        
        if (nombre==null || nombre.isEmpty()){
            throw new MiExceptions ("El nombre es nulo o incorrecto");
        }
    }
    
    public Autor getOne(String id){
        
        return autorRepositorio.getOne(id);
    }
}
