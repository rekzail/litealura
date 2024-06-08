package com.zaildev.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zaildev.LiterAlura.model.Autor;
import com.zaildev.LiterAlura.model.Idioma;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public record  LibroDTO (
    @JsonAlias("title")
    String nombre,

    @JsonAlias({"download_count", "readinglog_count"})
    int downloads,

    @JsonAlias("author_name")
    List<Autor> autorNombre,

    @JsonAlias({"authors"})
    List<AutorDTO> autor,

    @JsonAlias({"languages", "language"})
    List<Idioma> idiomas
) { }