package HamzaKababji.payroll;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class PayrollApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PayrollApplication.class.getResource("PAYROLL-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PAYROLL");
        stage.getIcons().add(new Image("file:src/main/resources/HamzaKababji/payroll/WesternLogo.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}