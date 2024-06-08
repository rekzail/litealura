package com.zaildev.LiterAlura.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.zaildev.LiterAlura.dto.AutorDTO;
import com.zaildev.LiterAlura.model.*;
import com.zaildev.LiterAlura.repository.AutorRepository;
import com.zaildev.LiterAlura.repository.IdiomaRepository;
import com.zaildev.LiterAlura.repository.LibroRepository;
import com.zaildev.LiterAlura.service.ConsumoApi;
import com.zaildev.LiterAlura.service.ConverteDados;

public class Principal {
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private IdiomaRepository repositorioIdioma;

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private String endereco;

   
    private final String URL_BASE_GT = "https://gutendex.com/books?search="; 
  

    public Principal() {}

    public Principal(LibroRepository repositorioLibro, AutorRepository repositorioAutor, IdiomaRepository repositorioIdioma) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
        this.repositorioIdioma = repositorioIdioma;
    }


    public void Menu() {
        var opc = -1;

        while (opc!= 9) {
            var menu = """
              
                   
                       
                             
                          1- Buscar libro por título                                                          
                          2- Listar libros registrados                   
                          3- Listar autores registrados
                          4- Listar autores vivos en un determinado año  
                          5- Listar libros por idiomas      
                          6- Top 10 Libros mas buscado                
                                                                           
                          9- Sair                                        
                                                                            
                         
                          """;
            System.out.println(menu);

            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, digite un año válido:");
                teclado.next();
            }

            opc = teclado.nextInt();
            teclado.nextLine();

            switch (opc) {
                
                case 1:
                    buscaLibro();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresVivosAno();
                    break;
                case 5:
                    libroPorIdioma();
                    break;
                case 6:
                    top10();
                    break;


                case 9:
                    System.out.println("Cerrando aplicacion!");
                    break;
                default:
                    System.out.println("Opçion inválida!");
            }
        }
    }

    private void top10() {
        List<Libro> libros = repositorioLibro.findTop10ByOrderByDownloadsDesc();

        if(!libros.isEmpty()){
            final int[] contt = {1};
            libros.forEach( l -> {

                System.out.printf(
                        """
                         ---------------------------------------- [%s] Libro ----------------------------------------
                         Título: %s
                         Autor: %s
                         Idioma(s): %s
                        """, contt[0]++, l.getNombre(), l.getAutor().toString(), l.getIdiomas().toString()
                );
            });
        }else{
            System.out.print(
                    "--------------------------------------- No se ha encontrado el libro! ----------------------------------------"
            );
        }
    }


    public void buscaLibro(){
        System.out.println("Introduce el nombre del libro: ");
        String nombreLibro = teclado.nextLine();
            nombreLibro = nombreLibro.trim().replaceAll(" ", "%20");

        endereco = URL_BASE_GT + nombreLibro;
        String json = consumo.obterDados(endereco);

        RLibro listaRt = conversor.obterDados(json, RLibro.class);

        if(listaRt.results().isEmpty()){
            System.out.println("---------------------- NINGUN LIBRO ENCONTRADO ----------------------");
        }else{
            System.out.println("---------------------- LIbRO(S) ENCONTRADO(S) ----------------------\n");
            final int[] cont = {1};
            listaRt.results().forEach( l -> {

                System.out.printf(
                        """
                         ---------------------------------------- [%s] Libro ----------------------------------------
                         Título: %s
                         Autor: %s
                         Idioma(s): %s
                        """, cont[0]++, l.nombre(), (l.autorNombre() != null) ? l.autorNombre() : "No encontrado", (l.idiomas() != null && !l.idiomas().isEmpty()) ? l.idiomas().toString() : "Não encontrado"
                );
            });

            System.out.println("\nInforme o número de libro: ");
            int numLibro = teclado.nextInt();

            while (numLibro < 1 || numLibro > cont[0]) {
                System.out.println("Valor no encontrado!\n");
                numLibro = teclado.nextInt();
            }

            numLibro--;

            
            Optional<Libro> verificaLibro = repositorioLibro.findByNombre(listaRt.results().get(numLibro).nombre());
            if(verificaLibro.isEmpty()) {


                Libro libro = new Libro();
                libro.setNombre(listaRt.results().get(numLibro).nombre());
                libro.setDownloads(listaRt.results().get(numLibro).downloads());
                libro.setFavorito(false);

          
                Autor autor = new Autor("No encontrado.");
                if (listaRt.results().get(numLibro).autor() != null &&
                        !listaRt.results().get(numLibro).autor().isEmpty()) {

                  
                    Optional<Autor> verificaAutor = repositorioAutor.findByNombre(listaRt.results().get(numLibro).autor().get(0).nombre());
                    if (verificaAutor.isEmpty()) {
                        autor = new Autor(listaRt.results().get(numLibro).autor().get(0));
                    }else{
                        autor = new Autor(verificaAutor.get());
                    }
                }


               
                Idioma idioma = new Idioma("No encontrado.");
                Optional<Idioma> verificaIdioma = repositorioIdioma.findByNombre(listaRt.results().get(numLibro).idiomas().get(0).getNombre());
                if(verificaIdioma.isEmpty()) {
                    if (!listaRt.results().get(numLibro).idiomas().isEmpty()) {
                        idioma = new Idioma(listaRt.results().get(numLibro).idiomas().get(0).getNombre());
                    }
                }else{
                    idioma = new Idioma(verificaIdioma.get());
                    System.out.println("Agregado");
                }

                repositorioAutor.save(autor);
                repositorioIdioma.save(idioma);

                libro.adicionarIdioma(idioma);
                libro.setAutor(autor);
                repositorioLibro.save(libro);

                System.out.println("-------------------------------- ¡El Libro ha sido registrado exitosamente! --------------------------------");
            }else{
                System.out.println("-------------------------------- ¡El Libro ya está registrado en el banco! --------------------------------");
            }
        }

    }

    public void librosRegistrados(){
        List<Libro> libros = repositorioLibro.findAll();

        if(!libros.isEmpty()){
            final int[] cont = {1};
            libros.forEach( l -> {

                System.out.printf(
                        """
                         ---------------------------------------- [%s] Libro ----------------------------------------
                         Título: %s
                         Autor: %s
                         Idioma(s): %s
                        """, cont[0]++, l.getNombre(), l.getAutor().toString(), l.getIdiomas().toString()
                );
            });
        }else{
            System.out.print(
                    "--------------------------------------- Ningun Libro Encontrado! ----------------------------------------"
            );
        }
}

    public void autoresRegistrados(){
        List<Autor> autores = repositorioAutor.findAll();

        if(!autores.isEmpty()){
            final int[] cont = {1};
            autores.forEach( a -> {

                System.out.printf(
                        """
                             ---------------------------------------- [%s] Autor ----------------------------------------
                             Nombre: %s
                             Nacimiento: %s
                             Falecimento: %s
                            """, cont[0]++, a.getNombre(), a.getNascimento(), a.getFalecimento()
                    );
                });
        }else{
            System.out.print(
                    "--------------------------------------- Ningun Autor Encontrado! ----------------------------------------"
            );
        }
    }

    public void autoresVivosAno(){
        System.out.println("Informe o Ano: ");

        while (!teclado.hasNextInt()) {
            System.out.println("Por favor, digite un año válido:");
            teclado.next();
        }

        int ano = teclado.nextInt();

        List<Autor> autores = repositorioAutor.verificaAutoVivoData(ano);

        if(!autores.isEmpty()){
            final int[] cont = {1};
            autores.forEach( a -> {

                System.out.printf(
                        """
                             ---------------------------------------- [%s] Autor ----------------------------------------
                             Nombre: %s
                             Naciminto: %s
                             Fallecimento: %s
                            """, cont[0]++, a.getNombre(), a.getNascimento(), a.getFalecimento()
                );
            });
        }else{
            System.out.print(
                    "--------------------------------------- Ningun Autor Encontrado! ----------------------------------------"
            );
        }
    }

    public void libroPorIdioma(){

        List<Idioma> idiomas = repositorioIdioma.findAll();

        if(!idiomas.isEmpty()){
            final int[] cont = {1};

            idiomas.forEach( i -> {

                System.out.printf(
                        """
                             ---------------------------------------- [%s] %s ----------------------------------------
                            """, cont[0]++, i.getNombre()
                );
            });

            System.out.println("\nIngrese el número de idioma deseado: ");

            while (!teclado.hasNextInt()) {
                System.out.println("Por favor, digite un año válido:");
                teclado.next();
            }

            int numIdioma = teclado.nextInt();

            while (numIdioma < 0 || numIdioma > cont[0]) {
                System.out.println("Valor no encontrado!\n");
                numIdioma = teclado.nextInt();
            }

            numIdioma--;

            List<Libro> libros = repositorioLibro.findAllByIdiomasNombreContainingIgnoreCase(idiomas.get(numIdioma).getNombre());

            if(!libros.isEmpty()){
                final int[] contt = {1};
                libros.forEach( l -> {

                    System.out.printf(
                            """
                             ---------------------------------------- [%s] Libro ----------------------------------------
                             Título: %s
                             Autor: %s
                             Idioma(s): %s
                            """, contt[0]++, l.getNombre(), l.getAutor().toString(), l.getIdiomas().toString()
                    );
                });
            }else{
                System.out.print(
                        "--------------------------------------- Ningun Libro Encontrado! ----------------------------------------"
                );
            }

        }else{
            System.out.print(
                    "--------------------------------------- Ningun Idioma Encontrado! ----------------------------------------"
            );
        }
    }
}
