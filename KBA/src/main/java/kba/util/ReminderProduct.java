package kba.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kba.MainApp;
import kba.model.Basket;
import kba.model.BasketProduct;
import kba.model.Preference;
import kba.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ReminderProduct {

    public static void autoUpdatePreferences(Basket basketToAnalyze, MainApp mainApp /*TODELETE*/) {

        List<Product> productsToAnalyze = new ArrayList<>();
        boolean found;

        //get the preference list in DB
        //TODO

        //to delete
        Preference preference = mainApp.getPreference();

        //get the product that are not on the preferences
        for (BasketProduct basketProduct : basketToAnalyze.getProductList()) {
            found = false;
            for (Product product : preference.getPreferenceList()) {
                if (basketProduct.getProduct().equals(product)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                productsToAnalyze.add(basketProduct.getProduct());
            }
        }

        //get the cleverBasket in DB
        //TODO

        //to delete
        Basket cleverBasket = mainApp.getCleverBasket();

        //check if the product had been already order
        for (Product productToCompare : productsToAnalyze) {
            found = false;
            for (BasketProduct basketProductInClever : cleverBasket.getProductList()) {
                if (basketProductInClever.getProduct().equals(productToCompare)) {
                    basketProductInClever.addQuantity(1L);
                    //check if the number of order is > than 10 and add it to the preferences
                    if (basketProductInClever.getQuantity() >= 10) {
                        preference.addPreferenceList(basketProductInClever.getProduct());
                        //then remove from clever so if the user remove the product from his pref the analysis can be made again
                        cleverBasket.removeProduct(basketProductInClever, basketProductInClever.getQuantity().intValue());
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                cleverBasket.addProduct(productToCompare, 1);
            }
        }

        //save all data in DB
        //TODO

        //to delete
        mainApp.setPreference(preference);
        mainApp.setCleverBasket(cleverBasket);

    }

    public static ObservableList<Product> getReminder(MainApp mainApp /*TODELETE*/, Basket favouriteBasket) {

        //get the preference in DB
        //TODO

        //to delete
        Preference preference = mainApp.getPreference();

        ObservableList<Product> productsToRemind = FXCollections.observableArrayList();
        boolean found;
        int counter = 0;

        for (Product product : preference.getPreferenceList()) {
            found = false;
            for (BasketProduct basketProduct : favouriteBasket.getProductList()) {
                if (product.equals(basketProduct.getProduct())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                productsToRemind.add(product);
                counter++;
                if (counter == 10) {
                    return productsToRemind;
                }
            }
        }

        return productsToRemind;
    }

}
