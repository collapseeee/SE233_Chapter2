package se233.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import se233.chapter2.Launcher;
import se233.chapter2.controller.AllEventHandlers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TopPane extends FlowPane {
    private Label baseCurrencyLbl;
    private ComboBox<String> baseCurrencySelect;
    private Button refresh;
    private Button add;
    private Label update;
    public TopPane() {
        this.setPadding(new Insets(10));
        this.setHgap(15);
        this.setPrefSize(640,20);
        baseCurrencyLbl = new Label();
        baseCurrencyLbl.setText("Base Currency: ");
        baseCurrencySelect = new ComboBox<>();
        baseCurrencySelect.getItems().addAll("THB", "CNY", "JPY", "EUR", "USD");
        baseCurrencySelect.setValue(Launcher.getBaseCurrency());
        baseCurrencySelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Launcher.setBaseCurrency(baseCurrencySelect.getValue());
                AllEventHandlers.onBaseCurrencyChange();
            }
        });
        HBox baseCurrencyBox = new HBox(10);
        baseCurrencyBox.getChildren().addAll(baseCurrencyLbl,baseCurrencySelect);
        add = new Button("Add");
        refresh = new Button("Refresh");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onRefresh();
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onAdd();
            }
        });
        update = new Label();
        refreshPane();
        this.getChildren().addAll(baseCurrencyBox,refresh,add,update);
    }
    public void refreshPane() {
        update.setText(String.format("Last update: %s", LocalDateTime.now().toString()));
    }
}
