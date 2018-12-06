import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class StoreViewController implements Initializable {

    @FXML private ListView<?> ListView;
    @FXML private ImageView ImageView;
    @FXML private Button SellButton;
    @FXML private ComboBox<?> ComboBox;
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
                })
        );

        Inventory.setProductTreeMap();
        updateViewWithSelectedProduct();
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
}
