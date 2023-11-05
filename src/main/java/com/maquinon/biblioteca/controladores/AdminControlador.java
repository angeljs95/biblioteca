package com.maquinon.biblioteca.controladores;
import com.maquinon.biblioteca.entidades.Usuario;
import com.maquinon.biblioteca.servicios.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar( ModelMap modelo){
        List<Usuario> usuarios= usuarioService.listarUsuarios();
        modelo.put("usuarios", usuarios);
        return "usuario_list.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        usuarioService.cambiarRol(id);
        return "redirect:/admin/usuarios";

    }

    @GetMapping("/modificar/{id}")
    public String perfil( ModelMap modelo, @PathVariable String id) {
        Usuario usuario= usuarioService.getOne(id);
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }
}
