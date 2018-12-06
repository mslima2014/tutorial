/*
 *  Petrobras Distribuidora S.A.
 *  Todos os direitos reservados.
 *  Copyright (C) 2018 bq4d
 */
package lab.br.tutorial.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *
 * @author bq4d
 */
@SpringBootApplication
public class ApplicationServer extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationServer.class, args);
    }
}
