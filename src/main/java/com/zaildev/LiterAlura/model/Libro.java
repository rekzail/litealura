package com.zaildev.LiterAlura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private boolean favorito;

    private int downloads;

    @ManyToOne
    private Autor autor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "libro_idioma",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "idioma_id"))
    private Set<Idioma> idiomas = new HashSet<>();


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public Set<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public void adicionarIdioma(Idioma idioma) {
        this.idiomas.add(idioma);
        idioma.getLibros().add(this); 
    }

    @Override
    public String toString() {
        return "Libro{" +
                "nombre='" + nombre + '\'' +
                ", autor=" + autor +
                ", idiomas=" + idiomas +
                '}';
    }
}
