package com.zaildev.LiterAlura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "idioma")

public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany(mappedBy = "idiomas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Libro> libros = new HashSet<>();


    public Idioma() {
    }

    public Idioma(String nombre) {
        this.nombre = nombre.replaceAll("'", "");
    }

    public Idioma(Idioma idioma) {
        this.id = idioma.id;
        this.nombre = idioma.nombre;
        this.libros = idioma.libros;
    }


    public void adicionarLibro(Libro libro) {
        libros.add(libro);
        libro.getIdiomas().add(this); 
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  nombre  ;
    }
}
