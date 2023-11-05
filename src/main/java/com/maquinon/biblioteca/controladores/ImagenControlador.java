package com.maquinon.biblioteca.controladores;

import com.maquinon.biblioteca.entidades.Usuario;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    UsuarioService usuarioServicio;

    //cuando usamos pathvariable hacemos referencia de que el atributo viaja en el html y lo recibe el metodo
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) throws MiExceptions {
        Usuario usuario= usuarioServicio.getOne(id);
        byte[] imagen= usuario.getImagen().getContenido();
//http header le dira al navegador que lo que estamos devolviendo esr una imagen
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        //al retornar httpStatuss nos dira que el estatus de la pag es correcto, nos traeria una respuesta num 200
        return new ResponseEntity<>(imagen,headers, HttpStatus.OK);







    }
}