package com.zaildev.LiterAlura.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "libro_idioma")
public class LibroIdioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "idioma_id")
    private Idioma idioma;

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Set<Idioma> get() {
        return Set.of();
    }
}