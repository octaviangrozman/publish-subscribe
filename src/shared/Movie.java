package shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Movie implements Serializable {

    private int id;
    private String name;
    private String author;
    private Date dateCreated;
    private long durationInSec;

    public Movie(String name, String author, Date dateCreated, long durationInSec) {
        this.id = 1 + (int)(Math.random() * ((20000 - 1) + 1));
        this.name = name;
        this.author = author;
        this.dateCreated = dateCreated;
        this.durationInSec = durationInSec;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(long durationInSec) {
        this.durationInSec = durationInSec;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", dateCreated=" + dateCreated +
                ", durationInSec=" + durationInSec +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return durationInSec == movie.durationInSec &&
                Objects.equals(name, movie.name) &&
                Objects.equals(author, movie.author) &&
                Objects.equals(dateCreated, movie.dateCreated);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, author, dateCreated, durationInSec);
    }
}
