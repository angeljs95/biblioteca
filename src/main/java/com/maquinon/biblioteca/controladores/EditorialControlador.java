
package com.maquinon.biblioteca.controladores;

import com.maquinon.biblioteca.entidades.Editorial;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.servicios.EditorialService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial") //localhost:8080/editorial
public class EditorialControlador {
    
    @Autowired
    private EditorialService editorialService;
    
    @GetMapping("/registrar")
    public String registrar() throws MiExceptions {
        
        return "editorial_form.html"; 
}
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiExceptions {
        System.out.println("Nombre: "+ nombre);
         
        try{ 
            editorialService.crearEditorial(nombre);
            modelo.put("exito"," La editorial se registro correctamente");
           
        } catch( MiExceptions ex){
            modelo.put("error", ex.getMessage());
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, ex);
            
           return "editorial_form.html";
        }
         return "index.html";
        
    }  
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Editorial> editoriales= editorialService.listarEditorial();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";  
    }
    
     @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialService.getOne(id) );
    return "editorial_modificar.html";
            }
    
    @PostMapping("/modificar/{id}")
    public String modificar( @PathVariable String id, String nombre, ModelMap modelo){
        
        try{
            editorialService.modificarEditorial(nombre, id);
            return"redirect:../lista";
            
        }catch(MiExceptions ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
            
        }
        
    }
    }
    
