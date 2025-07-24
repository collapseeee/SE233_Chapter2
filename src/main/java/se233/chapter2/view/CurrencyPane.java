package se233.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se233.chapter2.controller.AllEventHandlers;
import se233.chapter2.controller.draw.DrawCurrencyInfoTask;
import se233.chapter2.controller.draw.DrawGraphTask;
import se233.chapter2.controller.draw.DrawTopAreaTask;
import se233.chapter2.model.Currency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class CurrencyPane extends BorderPane {
    private Currency currency;
    private Button watch;
    private Button unwatch;
    private Button delete;
    public CurrencyPane(Currency currency) {
        this.watch = new Button("Watch");
        this.unwatch = new Button("Unwatch");
        this.delete = new Button("Delete");
        this.watch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onWatch(currency.getShortCode());
            }
        });
        this.unwatch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onUnwatch(currency.getShortCode());
            }
        });
        this.delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AllEventHandlers.onDelete(currency.getShortCode());
            }
        });
        this.setPadding(new Insets(0));
        this.setPrefSize(640,300);
        this.setStyle("-fx-border-color: black");
        try {
            this.refreshPane(currency);
        } catch (ExecutionException e) {
            System.out.println("Encountered an execution exception.");
        } catch (InterruptedException e) {
            System.out.println("Encountered an interruption exception.");
        }
    }
    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException {
        this.currency = currency;
        FutureTask currentInfoTask = new FutureTask<VBox>(new DrawCurrencyInfoTask(currency));
        FutureTask futureTask = new FutureTask<VBox>(new DrawGraphTask(currency));
        FutureTask topAreaTask = new FutureTask<HBox>(new DrawTopAreaTask(watch,unwatch,delete));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(futureTask);
        executor.execute(currentInfoTask);
        executor.execute(topAreaTask);
        VBox currencyGraph = (VBox) futureTask.get();
        VBox currentInfo = (VBox) currentInfoTask.get();
        HBox topArea = (HBox) topAreaTask.get();
        this.setTop(topArea);
        this.setLeft(currentInfo);
        this.setCenter(currencyGraph);
    }
}
