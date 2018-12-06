/*
 *  Petrobras Distribuidora S.A.
 *  Todos os direitos reservados.
 *  Copyright (C) 2018 bq4d
 */
package lab.br.tutorial.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author bq4d
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping
    public String home() {
        return "forward:/index.html";
    }
}
