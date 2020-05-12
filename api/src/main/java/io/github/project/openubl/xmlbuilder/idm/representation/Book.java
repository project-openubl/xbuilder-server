package io.github.project.openubl.xmlbuilder.idm.representation;


import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class Book {

    @Pattern(regexp = "^[F|f|B|b].*$")
    @NotBlank(message = "Title may not be blank")
    private String title;

    @Size(min = 1, max = 10)
    @NotBlank(message = "Author may not be blank")
    private String author;

    @Max(20)
    @Min(message = "Author has been very lazy", value = 1)
    private double pages;

    @NotNull
    @NotEmpty
    private List<String> algo;

    @NotNull
    @Min(1)
    private BigDecimal nada;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPages() {
        return pages;
    }

    public void setPages(double pages) {
        this.pages = pages;
    }

    public List<String> getAlgo() {
        return algo;
    }

    public void setAlgo(List<String> algo) {
        this.algo = algo;
    }

    public BigDecimal getNada() {
        return nada;
    }

    public void setNada(BigDecimal nada) {
        this.nada = nada;
    }
}
