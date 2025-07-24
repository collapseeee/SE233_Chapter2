package se233.chapter2.controller.draw;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.concurrent.Callable;

public class DrawTopAreaTask implements Callable<HBox> {
    private Button watch;
    private Button unwatch;
    private Button delete;
    public DrawTopAreaTask (Button watch, Button unwatch, Button delete) {
        this.watch = watch;
        this.unwatch = unwatch;
        this.delete = delete;
    }
    @Override
    public HBox call() throws Exception {
        HBox topArea = new HBox(10);
        topArea.setPadding(new Insets(5));
        topArea.getChildren().addAll(watch, unwatch, delete);
        ((HBox) topArea).setAlignment(Pos.CENTER_RIGHT);
        return topArea;
    }
}
