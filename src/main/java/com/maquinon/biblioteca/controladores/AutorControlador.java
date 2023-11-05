
package com.maquinon.biblioteca.controladores;

import com.maquinon.biblioteca.entidades.Autor;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.servicios.AutorService;
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
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {
    
    @Autowired
    private AutorService autorService;
    
    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        
        return "autor_form.html";
        // aqui retornamos la vista
    }
     
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) throws MiExceptions {
        System.out.println("Nombre: "+ nombre);
        try{
        autorService.crearAutor(nombre);
        modelo.put("exito", "Se ha registrado el autor correctamente");
        } catch (MiExceptions ex){
             modelo.put("error", ex.getMessage());
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
           
            return "autor_form.html";   
        }
        
        return "index.html";
         }
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Autor> autores= autorService.listarAutores();
        modelo.addAttribute("autores", autores);
       
        return "autor_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorService.getOne(id) );
    return "autor_modificar.html";
            }
    
    @PostMapping("/modificar/{id}")
    public String modificar( @PathVariable String id, String nombre, ModelMap modelo){
        
        try{
            autorService.modificarAutor(nombre, id);
            return"redirect:../lista";
            
        }catch(MiExceptions ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
            
        }
        
    }
}
