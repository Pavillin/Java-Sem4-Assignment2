import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

public class Product {
    private String title, rating, genre;
    private double price;
    private int stock;
    private Image image;

    public Product(String title, String rating, String genre, double price, int stock, Image image) {
        setTitle(title);
        setRating(rating);
        setGenre(genre);
        setPrice(price);
        setStock(stock);
        setImage(image);
    }

    public String getTitle() {
        return title;
    }

    /**
     * Verify title isn't empty
     * @param title
     */
    public void setTitle(String title) {
        if (title.isEmpty()){
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    /**
     * Verify rating isn't empty and matches one of the valid ratings
     * @param rating
     */
    public void setRating(String rating) {
        if (rating.isEmpty()){
            throw new IllegalArgumentException("Rating cannot be empty.");
        }
        //Everyone, Everyone 10 and up, Teen, Mature, Adult Only, Rating Pending
        String validRating[] = {"E", "E10+", "T", "M", "AO", "RP"};
        for (int i = 0; i < validRating.length; i++){
            if (rating.equalsIgnoreCase(validRating[i])){
                this.rating = rating;
                break;
            }
            if (i == validRating.length - 1){
                throw new IllegalArgumentException("Rating must be one of the following: E, E10+, T, M, AO, or RP.");
            }
        }
    }

    public String getGenre() {
        return genre;
    }

    /**
     * Verify genre isn't empty and is a valid genre
     * @param genre
     */
    public void setGenre(String genre) {
        if (genre.isEmpty()){
            throw new IllegalArgumentException("Genre cannot be empty.");
        }
        ArrayList<String> validGenres = Inventory.getGenres();
        for (int i = 0; i < validGenres.size(); i++){
            if (validGenres.contains(genre)){
                this.genre = genre;
                break;
            }
            if (i == validGenres.size() - 1){
                throw new IllegalArgumentException("Genre must be one of the following: " + validGenres);
            }
        }
    }

    public double getPrice() {
        return price;
    }

    /**
     * Verify the price isn't negative and is greater than $0
     * @param price
     */
    public void setPrice(double price) {
        if (price < 0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }else{
            this.price = price;
        }
    }

    public int getStock() {
        return stock;
    }

    /**
     * Verify stock isn't a negative number
     * @param stock
     */
    public void setStock(int stock) {
        if (stock < 0){
            throw new IllegalArgumentException("Stock cannot be negative.");
        } else{
            this.stock = stock;
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Method to display product name, units in stock and price
     * @return
     */
    @Override
    public String toString(){
        return ("Title: "+title+" Stock: "+stock+" Price: "+price);
    }

    /**
     * Sell an item if the item is in stock
     * @return
     */
    public int sellItem(){
        if (stock > 0){
            stock--;
        }else{
            throw new IllegalArgumentException("The selected item is out of stock and cannot be sold.");
        }
        return stock;
    }
}
