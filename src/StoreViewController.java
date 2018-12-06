import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
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

        ListView.getSelectionModel().selectFirst();
        ListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ListView.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> updateViewWithSelectedProduct()
        );

        updateViewWithSelectedProduct();
    }

    public void updateViewWithSelectedProduct() {
        Product product = (Product) ListView.getSelectionModel().getSelectedItem();
        ImageView.setImage(product.getImage());
    }
}
