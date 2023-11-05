
package com.maquinon.biblioteca.controladores;


import com.maquinon.biblioteca.entidades.Usuario;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.servicios.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
// se configurara cual es la url que escuchara este controlador
// en este caso se escuchara luego de la barra
public class PortalControladores {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    // mapeara la url cuando se haya ingresado la barra
    public String index() {

        return "index.html";
        // aqui estariamos retornando la vista
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email,
                           @RequestParam String password, String password2, ModelMap modelo, MultipartFile archivo) throws MiExceptions {
        try {
            usuarioService.registrar(archivo, nombre, email, password, password2);
            modelo.put("exito", "El usuario se ha registrado correctamente");
            return "index.html";
        } catch (MiExceptions ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }


    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "El usuario o la contraseña es incorrecta");
        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
     Usuario usuario= (Usuario) session.getAttribute("usuariosession");
     modelo.put("usuario", usuario);
     return "usuario_modificar.html";
   }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre,
                             @RequestParam String email, @RequestParam  String password,
                             @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioService.actualizar(archivo,id,nombre,email,password,password2);
            modelo.put("exito", "El usuario se ha actualizado correctamente");

            return "inicio.html";
        } catch(MiExceptions ex){
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "usuario_modificar.html";

        }


    }

}
