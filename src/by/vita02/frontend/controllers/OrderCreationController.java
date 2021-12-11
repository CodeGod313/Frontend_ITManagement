package by.vita02.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class OrderCreationController {

    @FXML
    private Text RadioValidationField;

    @FXML
    private Button calculateButton;

    @FXML
    private Button calculateButton1;

    @FXML
    private RadioButton corpSiteRadio;

    @FXML
    private RadioButton internetShopRadio;

    @FXML
    private RadioButton mobileAppRadio;

    @FXML
    private TextField quantityOfConventionalUnitsTextField;

    @FXML
    private Text quantityOfConventionalUnitsValidationField;

    @FXML
    private RadioButton siteCatalogRadio;

    @FXML
    private TextField targetTextField;

    @FXML
    private Text targetValidationField;

    @FXML
    private RadioButton visitCardRadio;

    @FXML
    private Text totalPriceField;

    @FXML
    void initialize(){
        visitCardRadio.setOnAction(actionEvent -> {
            if(!mobileAppRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!corpSiteRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!internetShopRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!siteCatalogRadio.isDisable()){
                mobileAppRadio.fire();
            }
        });
        corpSiteRadio.setOnAction(actionEvent -> {
            if(!mobileAppRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!visitCardRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!internetShopRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!siteCatalogRadio.isDisable()){
                mobileAppRadio.fire();
            }
        });
        mobileAppRadio.setOnAction(actionEvent -> {
            if(!visitCardRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!corpSiteRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!internetShopRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!siteCatalogRadio.isDisable()){
                mobileAppRadio.fire();
            }
        });
        internetShopRadio.setOnAction(actionEvent -> {
            if(!mobileAppRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!corpSiteRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!visitCardRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!siteCatalogRadio.isDisable()){
                mobileAppRadio.fire();
            }
        });
        siteCatalogRadio.setOnAction(actionEvent -> {
            if(!mobileAppRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!corpSiteRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!internetShopRadio.isDisable()){
                mobileAppRadio.fire();
            }
            if(!visitCardRadio.isDisable()){
                mobileAppRadio.fire();
            }
        });
    }
}
