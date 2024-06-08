package com.zaildev.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public record AutorDTO(
        @JsonAlias("name")
        String nombre,

        @JsonAlias("birth_year")
        int nascimento,

        @JsonAlias("birth_date")
        String nascimentoTexto,

        @JsonAlias("death_year")
        int falecimento
) { }
