package com.zaildev.LiterAlura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zaildev.LiterAlura.principal.Principal;
import com.zaildev.LiterAlura.repository.AutorRepository;
import com.zaildev.LiterAlura.repository.IdiomaRepository;
import com.zaildev.LiterAlura.repository.LibroRepository;

@SpringBootApplication
public class LiterAluraProApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repositorioLibro;

	@Autowired
	private AutorRepository repositorioAutor;

	@Autowired
	private IdiomaRepository repositorioIdioma;


	public static void main(String[] args) {
		SpringApplication.run(LiterAluraProApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLibro, repositorioAutor, repositorioIdioma);
		principal.Menu();
	}
}
