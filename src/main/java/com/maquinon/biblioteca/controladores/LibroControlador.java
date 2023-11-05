
package com.maquinon.biblioteca.controladores;

import com.maquinon.biblioteca.entidades.Autor;
import com.maquinon.biblioteca.entidades.Editorial;
import com.maquinon.biblioteca.entidades.Libro;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.servicios.AutorService;
import com.maquinon.biblioteca.servicios.EditorialService;
import com.maquinon.biblioteca.servicios.LibroService;
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
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private EditorialService editorialService;

    
    @GetMapping("/registrar") // localhost: 8080/registrar
    public String registrar ( ModelMap modelo) {
      List<Autor> autores= autorService.listarAutores();
      List<Editorial> editoriales= editorialService.listarEditorial();
      
      modelo.addAttribute("autores",autores);
      modelo.addAttribute("editoriales", editoriales);
      
        return "libro_form.html";
        
    }// el pamaetro modelo hace que inssertemos toda info que mostraremos en pantalla
    @PostMapping("/registro") // localhost: 8080/registro
    public String registro(@RequestParam(required=false) Long isbn, @RequestParam String titulo, 
            @RequestParam(required=false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial,  ModelMap modelo ) throws MiExceptions {
      
        try{
            libroService.crearLibro( isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "el libro fue cargado exitosamente");
        }
        catch (MiExceptions ex){
            modelo.put("error", ex.getMessage());
             List<Autor> autores= autorService.listarAutores();
      List<Editorial> editoriales= editorialService.listarEditorial();
      
      modelo.addAttribute("autores",autores);
      modelo.addAttribute("editoriales", editoriales);
      
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);
            
            return "libro_form.html";
        }
        
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> libros= libroService.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
        
    }
    // th:href="@{/libro/lista_autor/__${libro.autor.nombre}__}"
    @GetMapping("/lista_autor/{nombre}")
    public String listarLibrosXAutor( ModelMap modelo, @PathVariable String nombre){
        
        List<Libro> librosAutor= libroService.listarLibroXAutor(nombre);
        modelo.addAttribute("librosAutor", librosAutor);
       
        return "libros_autor.html";
    }
    
    
    
    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo){
         modelo.put("libro", libroService.getOne(isbn));
        List<Autor> autores= autorService.listarAutores();
        List<Editorial> editoriales= editorialService.listarEditorial();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
         
        return "libro_modificar.html";
    }
    
    @PostMapping("modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial,
            ModelMap modelo) {
        
        try{
              List<Autor> autores= autorService.listarAutores();
        List<Editorial> editoriales= editorialService.listarEditorial();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
         
            libroService.modificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);
            return"redirect:../lista";
            
        } catch(MiExceptions ex){
             List<Autor> autores= autorService.listarAutores();
        List<Editorial> editoriales= editorialService.listarEditorial();
            
             modelo.put("error", ex.getMessage());
               modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
            return"libro_modificar.html";
        }
     
    }
           
    
}
