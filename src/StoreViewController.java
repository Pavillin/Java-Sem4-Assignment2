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

    @FXML private ListView<Product> ListView;
    @FXML private ImageView ImageView;
    @FXML private Button SellButton;
    @FXML private ComboBox<String> ComboBox;
    @FXML private RadioButton RadioButtonHL;
    @FXML private RadioButton RadioButtonLH;
    @FXML private RadioButton RadioButtonAZ;
    @FXML private RadioButton RadioButtonZA;
    @FXML private Label LabelInvValue;
    @FXML private Label LabelGenreValue;

    // Create local list of products that can be manipulated
    ArrayList<Product> inventory = Inventory.getProducts();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Setup view objects
        ComboBox.getItems().addAll(Inventory.getGenres());
        ListView.getItems().addAll(inventory);

        RadioButtonAZ.fire();
        initListView();

        // Add all radio buttons into a toggle group
        ToggleGroup sortToggleGroup = new ToggleGroup();
        RadioButtonHL.setToggleGroup(sortToggleGroup);
        RadioButtonLH.setToggleGroup(sortToggleGroup);
        RadioButtonAZ.setToggleGroup(sortToggleGroup);
        RadioButtonZA.setToggleGroup(sortToggleGroup);

        // Update the view when a new product is selected
        ListView.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> updateViewWithSelectedProduct()
        );

        // Update the list to show products in the selected genre
        ComboBox.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> genreSelected()
        );

        // Sort the products list when a radio button is selected
        sortToggleGroup.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> sortProducts()
        );

        // Sell the selected product
        SellButton.setOnAction((event -> sellButtonPushed()));

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
        ListView.getSelectionModel().selectFirst();
        ListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Update the view to match the selected product in the list
     */
    public void updateViewWithSelectedProduct() {
        Product product = (Product) ListView.getSelectionModel().getSelectedItem();
        if (product != null){
            ImageView.setImage(product.getImage());
        }
    }

    /**
     * Sort the products in the list based on the selected radio button
     */
    public void sortProducts(){
        ArrayList<Product> sorted;
        if (RadioButtonAZ.isSelected()) {
            if (ComboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted);
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
            } else {
                String selectedGenre = ComboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted);
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
            }
        }
        if (RadioButtonZA.isSelected()) {
            if (ComboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted, Collections.reverseOrder());
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
            } else {
                String selectedGenre = ComboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted, Collections.reverseOrder());
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
            }
        }
        if (RadioButtonHL.isSelected()) {
            if (ComboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted, (a,b) -> (a.getPrice() < b.getPrice()) ? 1 : -1);
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
            } else {
                String selectedGenre = ComboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted, (a,b) -> (a.getPrice() < b.getPrice()) ? 1 : -1);
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
            }
        }
        if (RadioButtonLH.isSelected()) {
            if (ComboBox.getValue() == null) {
                sorted = inventory;
                Collections.sort(sorted, (a,b) -> (a.getPrice() > b.getPrice()) ? 1 : -1);
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
            } else {
                String selectedGenre = ComboBox.getValue();
                sorted = Inventory.getProductsInGenre(selectedGenre);
                Collections.sort(sorted, (a,b) -> (a.getPrice() > b.getPrice()) ? 1 : -1);
                ListView.getItems().clear();
                ListView.getItems().addAll(sorted);
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
        LabelInvValue.setText("$ "+ df.format(val));
    }

    /**
     * Calculate and display the value of products in selected genre
     * If not genre is selected "N/A" is displayed
     */
    public void genreVal(){
        if (ComboBox.getValue() == null){
            LabelGenreValue.setText("N/A");
        }else {
            DecimalFormat df = new DecimalFormat("#.00");
            Double val = ListView.getItems().stream()
                    .mapToDouble(p -> p.getPrice() * p.getStock())
                    .sum();
            LabelGenreValue.setText("$ " + df.format(val));
        }
    }

    /**
     * Sell an item from stock and update the
     * inventory and stock values
     */
    public void sellButtonPushed(){
        Product p = ListView.getSelectionModel().getSelectedItem();
        inventory.remove(p);
        p.sellItem();
        inventory.add(p);
        ListView.refresh();
        invVal();
        genreVal();
    }

    /**
     * Update the list with the products in the selected genre
     */
    public void genreSelected(){
        ListView.getItems().clear();
        ListView.getItems().addAll(Inventory.getProductsInGenre((String) ComboBox.getValue()));
        initListView();
        sortProducts();
        genreVal();
    }
}
