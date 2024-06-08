package com.zaildev.LiterAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zaildev.LiterAlura.model.Autor;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findAll();

    @Query("select a from Autor a WHERE :ano BETWEEN a.nascimento AND a.falecimento")
    List<Autor> verificaAutoVivoData(int ano);

    Optional<Autor> findByNombre(String nombre);
}
