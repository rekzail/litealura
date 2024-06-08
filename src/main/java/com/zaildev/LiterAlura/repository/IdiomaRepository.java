package com.zaildev.LiterAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zaildev.LiterAlura.model.Idioma;

import java.util.Optional;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {

    Optional<Idioma> findByNombre(String nombre);
}
