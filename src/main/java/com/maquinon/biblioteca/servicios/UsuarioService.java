package com.maquinon.biblioteca.servicios;

import com.maquinon.biblioteca.entidades.Imagen;
import com.maquinon.biblioteca.entidades.Usuario;
import com.maquinon.biblioteca.enumeraciones.Rol;
import com.maquinon.biblioteca.exceptions.MiExceptions;

import com.maquinon.biblioteca.repositorio.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenService imagenService;


    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiExceptions {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        Imagen imagen = imagenService.guardar(archivo);

        usuario.setImagen(imagen);
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiExceptions {
        validar(nombre, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);

            String idImagen = null;
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            Imagen imagen = imagenService.actualizar(archivo, idImagen);

            usuario.setImagen(imagen);
            usuarioRepositorio.save(usuario);
        }


    }

    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }

    @Transactional(readOnly=true)
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios= new ArrayList();
        usuarios = usuarioRepositorio.findAll();
      return usuarios;


    }

    @Transactional
    public void cambiarRol(String id){
        Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
        if (respuesta.isPresent()){
            Usuario usuario= respuesta.get();
            if(usuario.getRol().equals(Rol.USER)){
                usuario.setRol(Rol.ADMIN);
            } else if ( usuario.getRol().equals(Rol.ADMIN)){
                usuario.setRol(Rol.USER);
            }
        }
    }

    private void validar(String nombre, String email, String password, String password2) throws MiExceptions {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExceptions("El nombre es nulo o incorrecto");
        }
        if (email == null || email.isEmpty()) {
            throw new MiExceptions("El email es nulo o incorrecto");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiExceptions("El password no puede estar vacio y debe contener + de 5 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MiExceptions("El password no coincide");
        }
    }

    // buscaremos un usuario en nuestro repositorio para pasarlo a spring segurity,
    // traemos el usuario y lo tranformamos al domnio de spting
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {
            // instanciamos grantherauthority pra acceder a los permisos de usiuario que contiene la clase
            List<GrantedAuthority> permisos = new ArrayList();
            // le daremos estos permisso a usuarios que tengan un rol determinado
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);


            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
