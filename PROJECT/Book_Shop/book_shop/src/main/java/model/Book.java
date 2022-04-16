package model;

import java.util.ArrayList;

public class Book {
    private String title;
    private int numberOfPages;
    private int availableQuantity;
    private double price;
    private int publicationYear;
    private String language;
    private String publisher;
    private ArrayList<Author> authors = new ArrayList<>();
    private ArrayList<String> genres = new ArrayList<>();

    public Book(String title, int numberOfPages, int availableQuantity, double price, int publicationYear, String language, String publisher, ArrayList<Author> authors, ArrayList<String> genres) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.availableQuantity = availableQuantity;
        this.price = price;
        this.publicationYear = publicationYear;
        this.language = language;
        this.publisher = publisher;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(String title, int availableQuantity) {
        this.title = title;
        this.availableQuantity = availableQuantity;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
