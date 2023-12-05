package org.ot5usk.models.get_all_books_by_author;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ot5usk.entities.Author;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllBooksByAuthorResponse {

    @XmlElement(name = "author", required = true)
    private Author author;

    @XmlElement(name = "book_title", required = true)
    @JsonProperty("bookTitle")
    private String bookTitle;

    @XmlElement(name = "updated")
    @JsonProperty("updated")
    private Date updated;

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, bookTitle, updated);
    }
}