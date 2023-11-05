
package com.maquinon.biblioteca.servicios;

import com.maquinon.biblioteca.entidades.Editorial;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.repositorio.EditorialRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialService {


    @Autowired
    EditorialRepositorio editorialRepositorio;
    // Creamos una editorial solo con el string el id se genera automatico
     // transactional indica si el metodo se ejecuta sin problemas hace un commit en bd y aplica los cambios
    // si lanza una excepcion y no es atrapada se vuelve atras y no se ejecuta
    @Transactional
    public void crearEditorial(String nombre) throws MiExceptions {
        validar(nombre);
        
        Editorial editorial= new Editorial();
        
        editorial.setNombre(nombre);
        
        editorialRepositorio.save(editorial);
        
        
    }
    
    // Listamos todas las editoriales de BD
 public List<Editorial> listarEditorial(){
        List<Editorial> editoriales= new ArrayList();
        
        editoriales= editorialRepositorio.findAll();
        return editoriales;
        
    }
 
  public void modificarEditorial(String nombre, String id) throws MiExceptions {
      validar(nombre);
        
        Optional<Editorial> respuesta= editorialRepositorio.findById(id);
       
        if (respuesta.isPresent()){
            Editorial editorial= respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
      
        
    }
  
  private void validar(String nombre) throws MiExceptions {
        
        if (nombre==null || nombre.isEmpty()){
            throw new MiExceptions ("El nombre es nulo o incorrecto");
        }
    }
  
   public Editorial getOne(String id){
        
        return editorialRepositorio.getOne(id);
    }
 
    
}
