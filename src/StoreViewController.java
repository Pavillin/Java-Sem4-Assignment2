import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
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
    @FXML private Label LabelCatValue;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        ComboBox.getItems().addAll(Inventory.getGenres());
        ListView.getItems().addAll(Inventory.getProducts());
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
                ((observable, oldValue, newValue) -> {
                    ListView.getItems().clear();
                    ListView.getItems().addAll(Inventory.getProductsInGenre((String) ComboBox.getValue()));
                    initListView();
                    sortProducts();
                })
        );

        //Sort the products list when a radio button is selected
        sortToggleGroup.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> sortProducts()
        );

        Inventory.setProductTreeMap();
        updateViewWithSelectedProduct();
        sortProducts();
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
        ImageView.setImage(product.getImage());
    }

    /**
     * Sort the products in the list based on the selected radio button
     */
    public void sortProducts(){
        ArrayList<Product> sorted;
        if (RadioButtonAZ.isSelected()) {
            if (ComboBox.getValue() == null) {
                sorted = Inventory.getProducts();
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
                sorted = Inventory.getProducts();
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
                sorted = Inventory.getProducts();
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
                sorted = Inventory.getProducts();
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
}
