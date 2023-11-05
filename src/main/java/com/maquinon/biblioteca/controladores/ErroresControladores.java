package com.maquinon.biblioteca.controladores;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErroresControladores implements ErrorController {
    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
// modelandview trabaja parecido a model pero este tira una vista
        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = " ";

        Integer httpErrorCode = getErrorCode(httpRequest);
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "El recuso solicitado no existe";
                break;
            }
            case 403: {
                errorMsg = "No tiene permisos para acceder al recurso";
                break;
            }
            case 401: {
                errorMsg = "No se encuentra autorizado";
                break;
            }
            case 404: {
                errorMsg = "El recurso solicitado no fue encontrado";
                break;
            }
            case 500: {
                errorMsg = " ocurrio un error interno";
                break;
            }
        }
            errorPage.addObject("codigo" ,httpErrorCode);
            errorPage.addObject("mensaje", errorMsg);
            return errorPage;

    }

    //esta metodo nos obtiene el error a traves de un get. Obtrine el estado de la consulta request, lo pasa a metodo
    // y retorna en formato entero
    private Integer getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    public String getErrorPath() {
        return "/error.html";
    }

}
/*
Manejaremos todos los errores que presentes los controladores
En error controller entrara
error controler es una clase de framework spring, entrara todo recurso con bara eror
*/