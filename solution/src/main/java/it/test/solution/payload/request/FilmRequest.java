package it.cgmconsulting.solution.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FilmRequest {

    @NotBlank @Size( max = 100)
    private String title;

    private String description;

    @Min(1895)
    private short releaseYear;

    @Min(1)
    private long languageId;

    @Min(1)
    private long genreId;

}
