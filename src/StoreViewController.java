import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class StoreViewController implements Initializable {

    @FXML private ListView<Product> listView;
    @FXML private ImageView imageView;
    @FXML private Button sellButton;
    @FXML private ComboBox<String> comboBox;
    @FXML private RadioButton radioButtonHL;
    @FXML private RadioButton radioButtonLH;
    @FXML private RadioButton radioButtonAZ;
    @FXML private RadioButton radioButtonZA;
    @FXML private Label labelInvValue;
    @FXML private Label labelGenreValue;

    // Create local list of products that can be manipulated
    ArrayList<Product> inventory = Inventory.getProducts();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Setup view objects
        comboBox.getItems().addAll(Inventory.getGenres());
        listView.getItems().addAll(inventory);

        radioButtonAZ.fire();
        initListView();

        // Add all radio buttons into a toggle group
        ToggleGroup sortToggleGroup = new ToggleGroup();
        radioButtonHL.setToggleGroup(sortToggleGroup);
        radioButtonLH.setToggleGroup(sortToggleGroup);
        radioButtonAZ.setToggleGroup(sortToggleGroup);
        radioButtonZA.setToggleGroup(sortToggleGroup);

        // Update the view when a new product is selected
        listView.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> updateViewWithSelectedProduct()
        );

        // Update the list to show products in the selected genre
        comboBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> genreSelected()
        );

        // Sort the products list when a radio button is selected
        sortToggleGroup.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> sortProducts()
        );

        // Sell the selected product
        sellButton.setOnAction((event -> sellButtonPushed()));

        // Finalize view setup
        Inventory.setProductTreeMap(inventory);
        updateViewWithSelectedProduct();
        sortProducts();
        invVal();
        genreVal();
    }

    /**
     * Setup the ListView by only allowing one item to be selected at a time
     * and automatically selecting the first item in the list
     */
    public void initListView(){
        listView.getSelectionModel().selectFirst();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Update the view to match the selected product in the list
     */
    public void updateViewWithSelectedProduct() {
        Product product = (Product) listView.getSelectionModel().getSelectedItem();
        if (product != null){
            imageView.setImage(product.getImage());
        }
    }

    /**
     * Sort the products in the list based on the selected radio button
     */
    public void sortProducts(){
        ArrayList<Product> sorted;
        if (radioButtonAZ.isSelected()) {
            if (comboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted);
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            } else {
                String selectedGenre = comboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted);
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            }
        }
        if (radioButtonZA.isSelected()) {
            if (comboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted, Collections.reverseOrder());
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            } else {
                String selectedGenre = comboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted, Collections.reverseOrder());
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            }
        }
        if (radioButtonHL.isSelected()) {
            if (comboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted, (a,b) -> (a.getPrice() < b.getPrice()) ? 1 : -1);
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            } else {
                String selectedGenre = comboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted, (a,b) -> (a.getPrice() < b.getPrice()) ? 1 : -1);
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            }
        }
        if (radioButtonLH.isSelected()) {
            if (comboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted, (a,b) -> (a.getPrice() > b.getPrice()) ? 1 : -1);
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            } else {
                String selectedGenre = comboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted, (a,b) -> (a.getPrice() > b.getPrice()) ? 1 : -1);
                listView.getItems().clear();
                listView.getItems().addAll(sorted);
            }
        }

        initListView();
    }

    /**
     * Calculate and display the total value of the inventory
     */
    public void invVal(){

        DecimalFormat df = new DecimalFormat("#.00");
        Double val = inventory.stream()
                .mapToDouble(p -> p.getPrice() * p.getStock())
                .sum();
        labelInvValue.setText("$ "+ df.format(val));
    }

    /**
     * Calculate and display the value of products in selected genre
     * If not genre is selected "N/A" is displayed
     */
    public void genreVal(){
        if (comboBox.getValue() == null){
            labelGenreValue.setText("N/A");
        }else {
            DecimalFormat df = new DecimalFormat("#.00");
            Double val = listView.getItems().stream()
                    .mapToDouble(p -> p.getPrice() * p.getStock())
                    .sum();
            labelGenreValue.setText("$ " + df.format(val));
        }
    }

    /**
     * Sell an item from stock and update the
     * inventory and stock values
     */
    public void sellButtonPushed(){
        Product p = listView.getSelectionModel().getSelectedItem();
        inventory.remove(p);
        p.sellItem();
        inventory.add(p);
        listView.refresh();
        invVal();
        genreVal();
    }

    /**
     * Update the list with the products in the selected genre
     */
    public void genreSelected(){
        listView.getItems().clear();
        listView.getItems().addAll(Inventory.getProductsInGenre((String) comboBox.getValue()));
        initListView();
        sortProducts();
        genreVal();
    }
}
