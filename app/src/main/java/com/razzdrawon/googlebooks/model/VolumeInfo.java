package com.razzdrawon.googlebooks.model;

import java.util.List;

public class VolumeInfo {

    private String title;
    private String publishedDate;
    private String description;
    private ImageLink imageLinks;
    private List<String> authors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageLink getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLink imageLinks) {
        this.imageLinks = imageLinks;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getAuthorsString() {
        String authors = "";
        for(String author: getAuthors()){
            if(authors == ""){
                authors = author;
            }
            else{
                authors = authors + "\n" + author;
            }

        }
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
