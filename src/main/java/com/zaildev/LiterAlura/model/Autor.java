package com.zaildev.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.zaildev.LiterAlura.dto.AutorDTO;



@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private int nascimento;

    private int falecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();


    public Autor(){}

    public Autor(String autor){
        this.nombre = autor;
    }

    public Autor(AutorDTO autorDto){
        this.nombre = autorDto.nombre();
        this.nascimento = autorDto.nascimento();
        this.falecimento = autorDto.falecimento();
    }

    public Autor(Autor autor) {
        this.id = autor.id;
        this.nombre = autor.nombre;
        this.nascimento = autor.nascimento;
        this.falecimento = autor.falecimento;
        this.libros = autor.libros;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFalecimento() {
        return falecimento;
    }

    public void setFalecimento(int falecimento) {
        this.falecimento = falecimento;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", nascimento=" + nascimento +
                ", falecimento=" + falecimento +
                '}';
    }
}
