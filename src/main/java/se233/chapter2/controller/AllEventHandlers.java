package se233.chapter2.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter2.Launcher;
import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {
    private static final Logger logger = LogManager.getLogger(AllEventHandlers.class);
    public static void onBaseCurrencyChange() {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            currencyList.removeIf(c -> c.getShortCode().equals(Launcher.getBaseCurrency()));
            for (Currency c : currencyList) {
                onUnwatch(c.getShortCode());
                List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(), 30);
                c.setHistorical(cList);
                c.setCurrent(cList.get(cList.size() - 1));
            }
            Launcher.setCurrencyList(currencyList);
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void onRefresh() {
        try {
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void onAdd() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait();
            if (code.isPresent()) {
                List<Currency> currencyList = Launcher.getCurrencyList();
                try {
                    Currency c = new Currency(code.get().toUpperCase());
                    List<CurrencyEntity> cList = FetchData.fetchRange(c.getShortCode(),30);
                    c.setHistorical(cList);
                    c.setCurrent(cList.get(cList.size() - 1));
                    currencyList.add(c); // Add currency
                    logger.info("Currency: " + c.getShortCode() + " has been added.");
                } catch (Exception e) {
                    Alert invalidCurrencyAlert = new Alert(Alert.AlertType.ERROR);
                    invalidCurrencyAlert.setHeaderText("Invalid Currency Input");
                    invalidCurrencyAlert.setContentText("Currency: " + code.get().toUpperCase() + " is invalid. \nPlease input a valid currency!");
                    invalidCurrencyAlert.showAndWait();
                } finally {
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void onDelete(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i=0; i<currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                currencyList.remove(index); // Remove currency
                logger.info("Currency: " + code + " has been removed.");
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void onWatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for(int i=0; i<currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Watch");
                dialog.setContentText("Rate:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                Optional<String> retrievedRate = dialog.showAndWait();
                if (retrievedRate.isPresent()) {
                    try {
                        double rate = Double.parseDouble(retrievedRate.get());
                        currencyList.get(index).setWatch(true);
                        currencyList.get(index).setWatchRate(rate);
                        Launcher.setCurrencyList(currencyList);
                        Launcher.refreshPane();
                    } catch (NumberFormatException e){
                        Alert invalidWatchAlert = new Alert(Alert.AlertType.ERROR);
                        invalidWatchAlert.setHeaderText("Invalid Watch Input");
                        invalidWatchAlert.setContentText("Please input a double value of Watch!");
                        invalidWatchAlert.showAndWait();
                    }
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static void onUnwatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i=0; i<currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                if (currencyList.get(index).getWatch()) {
                    currencyList.get(index).setWatch(false);
                    currencyList.get(index).setWatchRate(null);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
