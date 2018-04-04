package pl.edu.pw.elka.proz.projekt1;

import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		Optional<Pair<Environment, String>> result = (new LogonDialog("Logowanie", "Logowanie do systemu STYLEman"))
				.showAndWait();

		if (result.isPresent()) {
			System.out.println("Zalogowano u¿ytkownika: " + result.get().getValue());
			System.out.println("Do œrodowiska: " + result.get().getKey());
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
