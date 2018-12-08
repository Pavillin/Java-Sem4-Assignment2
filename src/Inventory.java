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
        Product product2 = new Product("Madden 19", "E", "Sports", 89.99, 6, new Image("Images/madden19.jpeg"));
        Product product3 = new Product("Grand Theft Auto V", "M", "Action", 29.99, 4, new Image("Images/gta5.jpg"));
        Product product4 = new Product("No Man's Sky", "T", "Action", 66.49, 9, new Image("Images/nomanssky.jpg"));
        Product product5 = new Product("Red Dead Redemption 2", "M", "Action-Adventure", 79.99, 10, new Image("Images/rdr2.jpg"));
        Product product6 = new Product("The Last Of Us", "M", "Action-Adventure", 19.99, 3, new Image("Images/tlou.jpg"));
        Product product7 = new Product("Firewatch", "M", "Adventure", 14.99, 9, new Image("Images/firewatch.jpg"));
        Product product8 = new Product("Minecraft", "E10+", "Adventure", 35.99, 10, new Image("Images/Minecraft.jpg"));
        Product product9 = new Product("Skyrim", "M", "Role-Playing", 19.99, 3, new Image("Images/skyrim.png"));
        Product product10 = new Product("Fallout3", "M", "Role-Playing", 9.99, 5, new Image("Images/fallout3.png"));
        Product product11 = new Product("Arma3", "M", "Simulation", 39.99, 11, new Image("Images/a3.jpg"));
        Product product12 = new Product("Cities: Skylines", "E", "Simulation", 29.99, 11, new Image("Images/citiesskylines.jpg"));
        Product product13 = new Product("Civilization VI", "E10+", "Strategy", 59.99, 5, new Image("Images/civvi.jpg"));
        Product product14 = new Product("XCOM 2", "T", "Strategy", 79.99, 6, new Image("Images/xcom2.jpg"));
        Product product15 = new Product("ArcheAge", "M", "MMO", 0, 11, new Image("Images/archeage.jpg"));
        Product product16 = new Product("RuneScape", "T", "MMO", 0, 8, new Image("Images/rs.jpg"));

        ArrayList<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
        products.add(product11);
        products.add(product12);
        products.add(product13);
        products.add(product14);
        products.add(product15);
        products.add(product16);
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
