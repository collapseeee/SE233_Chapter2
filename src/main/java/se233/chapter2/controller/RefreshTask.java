package se233.chapter2.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import se233.chapter2.Launcher;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RefreshTask extends Task<Void> {
    @Override
    protected Void call() throws InterruptedException {
        for(;;) {
            try {
                Thread.sleep((long) (60 * 1e3));
            } catch (InterruptedException e) {
                System.out.println("Encountered an interrupted exception");
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Launcher.refreshPane();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            FutureTask futureTask = new FutureTask(new WatchTask());
            Platform.runLater(futureTask);
            try {
                futureTask.get();
            } catch (InterruptedException e) {
                System.out.println("Encountered an interrupted exception");
            } catch (ExecutionException e) {
                System.out.println("Encountered a execution exception");
            }
        }
    }
}
