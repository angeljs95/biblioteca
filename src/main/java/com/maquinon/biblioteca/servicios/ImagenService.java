package com.maquinon.biblioteca.servicios;

import com.maquinon.biblioteca.entidades.Imagen;
import com.maquinon.biblioteca.exceptions.MiExceptions;
import com.maquinon.biblioteca.repositorio.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;


@Service
public class ImagenService {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    // el multipartFile es el tipo de archivo donde se almacenara la imagen
    public Imagen guardar(MultipartFile archivo) throws MiExceptions {
        if (archivo != null) {

            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiExceptions {

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());

            }

        }
        return null;
    }
}
