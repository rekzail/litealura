package com.zaildev.LiterAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaildev.LiterAlura.model.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findAll();

    List<Libro> findAllByIdiomasNombreContainingIgnoreCase(String nombre);

    List<Libro> findTop10ByOrderByDownloadsDesc();

    Optional<Libro> findByNombre(String nombre);
}
