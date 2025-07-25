package se233.chapter2.controller.draw;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import se233.chapter2.model.Currency;

import java.util.concurrent.Callable;

public class DrawCurrencyInfoTask implements Callable<VBox> {
    Currency currency;
    public DrawCurrencyInfoTask(Currency currency) {
        this.currency = currency;
    }
    @Override
    public VBox call() throws Exception {
        VBox currencyInfoPane = new VBox(10);
        currencyInfoPane.setPadding(new Insets(5,25,5,25));
        currencyInfoPane.setAlignment(Pos.CENTER);
        Label exchangeString = new Label("");
        Label watchString = new Label("");
        exchangeString.setStyle("-fx-font-size: 20");
        watchString.setStyle("-fx-font-size: 14");
        if (currency != null) {
            exchangeString.setText(String.format("%s: %.4f", this.currency.getShortCode(),this.currency.getCurrent().getRate()));
            if (currency.getWatch() == true) {
                watchString.setText(String.format("(Watch @%.4f)",this.currency.getWatchRate()));
            }
        }
        currencyInfoPane.getChildren().addAll(exchangeString,watchString);
        return currencyInfoPane;
    }
}
