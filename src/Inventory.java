import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class Inventory {
    TreeMap<String, LinkedList> productTreeMap = new TreeMap<>();


    public ArrayList getGenres(){
        String validGenres[] = {"Action", "Action-Adventure", "Adventure", "Role-Playing", "Simulation",
                                "Strategy", "Sports", "MMO"};
        ArrayList<String> genres = new ArrayList<>(Arrays.asList(validGenres));
        return genres;
    }
    public ArrayList getProducts(){
        Product product1 = new Product("Rocket League", "E", "Sports", 29.99, 7, new Image("Images/rocketleague.jpg"));
        Product product2 = new Product("Grand Theft Auto V", "M", "Action", 29.99, 4, new Image("Images/gta5.jpg"));
        Product product3 = new Product("Red Dead Redemption 2", "M", "Action-Adventure", 79.99, 10, new Image("Images/rdr2.jpg"));
        Product product4 = new Product("Firewatch", "M", "Adventure", 14.99, 9, new Image("Images/firewatch.jpg"));
        Product product5 = new Product("Skyrim", "M", "Role-Playing", 19.99, 3, new Image("Images/skyrim.png"));
        Product product6 = new Product("Arma3", "M", "Simulation", 39.99, 11, new Image("Images/a3.jpg"));
        Product product7 = new Product("Civilization VI", "E10+", "Strategy", 59.99, 5, new Image("Images/civvi.jpg"));
        Product product9 = new Product("ZrcheAge", "M", "MMO", 8.99, 11, new Image("Images/archeage.jpg"));
        Product product8 = new Product("ArcheAge", "M", "MMO", 0, 11, new Image("Images/archeage.jpg"));

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product9);
        products.add(product8);
        return products;
    }

    /**
     * Returns all products in selected genre
     * @param genre
     * @return
     */
    public ArrayList getProductsInGenre(String genre){
        ArrayList<Product> productsInGenre = new ArrayList<>();
        productsInGenre.addAll(productTreeMap.get(genre));

        return productsInGenre;
    }

    /**
     * Check if the genre exists and add product to the genre
     * otherwise create a new genre and add product
     * @param product
     */
    public void addProductToGenre(Product product){
        //LinkedList<Product> productLinkedList = new LinkedList<>();
        if (!productTreeMap.containsKey(product.getGenre())){
            LinkedList<Product> productLinkedList = new LinkedList<>();
            productLinkedList.add(product);
            productTreeMap.put(product.getGenre(), productLinkedList);
        } else {
            productTreeMap.get(product.getGenre()).add(product);
        }
    }

    public void setProductTreeMap(ArrayList<Product> products){
        for (Product product : products){
            addProductToGenre(product);
        }
    }
}
