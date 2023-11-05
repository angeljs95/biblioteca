/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maquinon.biblioteca.servicios;

import com.maquinon.biblioteca.entidades.Autor;
import com.maquinon.biblioteca.entidades.Editorial;
import com.maquinon.biblioteca.entidades.Libro;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.repositorio.AutorRepositorio;
import com.maquinon.biblioteca.repositorio.EditorialRepositorio;
import com.maquinon.biblioteca.repositorio.LibroRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    
    @Autowired
   private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    // crear libro crea un libro la info entra como un formulario 
    // transactional indica si el metodo se ejecuta sin problemas hace un commit en bd y aplica los cambios
    // si lanza una excepcion y no es atrapada se vuelve atras y no se ejecuta
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor,String idEditorial) throws MiExceptions {
          
       validacion(isbn, titulo,idAutor,idEditorial, ejemplares);
        
        Autor autor= autorRepositorio.findById(idAutor).get();
        Editorial editorial= editorialRepositorio.findById(idEditorial).get();
        Libro libro= new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
       libroRepositorio.save(libro);
        
    }
    
   
    
    // Listamos todos los libros de BD
    
    public List<Libro>  listarLibros() {
        
        List<Libro> libros= new ArrayList();
        
        libros= libroRepositorio.findAll();
        return libros;
        
        
    }
    
    public List<Libro> listarLibroXAutor(String nombre){
        
        List<Libro> libros= libroRepositorio.buscarPorAutor(nombre);
        
        return libros ;
    }
    
   // Para modificar el libro
  
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiExceptions {
        validacion(isbn, titulo,idAutor,idEditorial, ejemplares);
        
        // optional, objeto contenedor que puede contener o no nulo, nos trae false o true si tiene
        
        Optional<Libro> respuesta= libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor= autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial= editorialRepositorio.findById(idEditorial);
        
        Autor autor= new Autor();
        Editorial editorial= new Editorial();
        
        if(respuestaAutor.isPresent()){
            autor= respuestaAutor.get();
        }
        if(respuestaEditorial.isPresent()){
            editorial= respuestaEditorial.get();
        }
        if(respuesta.isPresent()) {
            Libro libro= respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro);
        }
    }
    
    // se crea en privado ya que solo esta clase tendra acceso a los datos del metodo
    // se valida cada uno de los atributos
    private void validacion(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiExceptions {
        
         if( isbn==null ){
        throw new MiExceptions(" El isbn no puede ser leido") ;
        }
         
         if (titulo.isEmpty()|| titulo == null){
             throw new MiExceptions("El titulo no puede estar vacio o ser nulo");
         }
         
          if (ejemplares == null){
             throw new MiExceptions("Los ejemplares no pueden ser nulos");    
         }
           if (idAutor.isEmpty()|| idAutor == null){
             throw new MiExceptions("El titulo no puede estar vacio o ser nulo");
        }
            if (idEditorial.isEmpty() || idEditorial == null){
             throw new MiExceptions("El titulo no puede estar vacio o ser nulo");
         }
      
    }
    
    public Libro getOne(Long isbn){
                
                return libroRepositorio.getOne(isbn);
               
            }
    
    
    
    
    
}
