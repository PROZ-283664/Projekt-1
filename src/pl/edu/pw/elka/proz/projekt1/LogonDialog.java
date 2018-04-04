package pl.edu.pw.elka.proz.projekt1;

import java.util.HashMap;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * Implementation of Logon Dialog class. The class creates dialog with login
 * fields and supports the login process.
 * 
 * @author Konrad Bereda
 * @version 1.0
 */
public class LogonDialog {
	private Dialog<ButtonType> dialog;
	private HashMap<String, Environment> environments;

	private Label envLabel;
	private ChoiceBox<Environment> envChoiceBox;

	private Label usrLabel;
	private ComboBox<String> usrComboBox;

	private Label passLabel;
	private PasswordField passField;

	private ButtonType loginButton;
	private ButtonType cancelButton;

	/** Default constructor for this class */
	public LogonDialog() {
		this("", "");
	}

	/**
	 * Creates a new LogonDialog object that represents the logon dialog.
	 * 
	 * @param title
	 *            Title displayed at top of window
	 * @param header
	 *            Text displayed above all items in window
	 */
	public LogonDialog(String title, String header) {
		dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setHeaderText(header);

		createEnvDB();

		initControls();
		createLayout();

		dialog.getDialogPane().lookupButton(loginButton).setDisable(true);
	}

	/**
	 * Assistance method that creates environments available in application and adds
	 * example users to them.
	 */
	private void createEnvDB() {
		environments = new HashMap<>();

		environments.put("Deweloperskie", new Environment("Deweloperskie"));
		environments.put("Testowe", new Environment("Testowe"));
		environments.put("Produkcyjne", new Environment("Produkcyjne"));

		environments.get("Deweloperskie").addUser("Marek", "Marek123");
		environments.get("Deweloperskie").addUser("Ola", "Ola123");
		environments.get("Deweloperskie").addUser("Patryk", "Patryk123");
		environments.get("Deweloperskie").addUser("Wiktoria", "Wiktoria123");
		environments.get("Deweloperskie").addUser("Zuzia", "Zuzia123");
		environments.get("Deweloperskie").addUser("Bartek", "Bartek123");
		environments.get("Deweloperskie").addUser("Admin", "Admin123");

		environments.get("Testowe").addUser("Szymon", "Szymon123");
		environments.get("Testowe").addUser("Patrycja", "Patrycja123");
		environments.get("Testowe").addUser("Wojtek", "Wojtek123");
		environments.get("Testowe").addUser("Kamila", "Kamila123");
		environments.get("Testowe").addUser("Mi³osz", "Mi³osz123");
		environments.get("Testowe").addUser("Natalia", "Natalia123");
		environments.get("Testowe").addUser("Admin", "Admin123");

		environments.get("Produkcyjne").addUser("Tomek", "Tomek123");
		environments.get("Produkcyjne").addUser("Ewa", "Ewa123");
		environments.get("Produkcyjne").addUser("Robert", "Robert123");
		environments.get("Produkcyjne").addUser("Ania", "Ania123");
		environments.get("Produkcyjne").addUser("Karolina", "Karolina123");
		environments.get("Produkcyjne").addUser("Przemek", "Przemek123");
		environments.get("Produkcyjne").addUser("Rafa³", "Rafa³123");
		environments.get("Produkcyjne").addUser("Admin", "Admin123");
	}

	/**
	 * Method responsible for layout of dialog components.
	 */
	private void createLayout() {
		GridPane gridpane = new GridPane();

		gridpane.setAlignment(Pos.CENTER);
		gridpane.setPadding(new Insets(30, 20, 30, 20));

		gridpane.setHgap(30);
		gridpane.setVgap(15);

		gridpane.add(envLabel, 0, 0);
		gridpane.add(envChoiceBox, 1, 0);
		envChoiceBox.setPrefWidth(250);

		gridpane.add(usrLabel, 0, 1);
		gridpane.add(usrComboBox, 1, 1);
		usrComboBox.setPrefWidth(250);

		gridpane.add(passLabel, 0, 2);
		gridpane.add(passField, 1, 2);
		passField.setPrefWidth(250);

		dialog.getDialogPane().setContent(gridpane);
		dialog.getDialogPane().getButtonTypes().add(cancelButton);
		dialog.getDialogPane().getButtonTypes().add(loginButton);
	}

	/**
	 * Creates controls for dialog and defines their properties.
	 */
	private void initControls() {
		envLabel = new Label("Œrodowisko:");
		envChoiceBox = new ChoiceBox<>();
		envChoiceBox.valueProperty().addListener((observable, oldVal, newVal) -> envChoiceBox_Changed(newVal));
		envChoiceBox.setItems(FXCollections.observableArrayList(environments.values()));

		usrLabel = new Label("U¿ytkownicy:");
		usrComboBox = new ComboBox<>();
		usrComboBox.setEditable(true);
		usrComboBox.valueProperty().addListener((observable, oldVal, newVal) -> usrComboBox_Changed(newVal));

		passLabel = new Label("Has³o:");
		passField = new PasswordField();
		passField.textProperty().addListener((observable, oldVal, newVal) -> passField_Changed(newVal));

		loginButton = new ButtonType("Zaloguj", ButtonData.OK_DONE);
		cancelButton = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);

		envChoiceBox.getSelectionModel().selectFirst();
	}

	/**
	 * Controls actions performed after Environment field has changed
	 */
	private void envChoiceBox_Changed(Environment newVal) {
		usrComboBox.setItems(newVal.getUsers());
		passField.clear();
	}

	/**
	 * Controls actions performed after User field has changed
	 */
	private void usrComboBox_Changed(String newVal) {
		changeLoginButtonState();
	}

	/**
	 * Controls actions performed after Password field has changed
	 */
	private void passField_Changed(String newVal) {
		changeLoginButtonState();
	}

	/**
	 * Sets loginButton to active when username and password are entered. Disable it
	 * otherwise.
	 */
	private void changeLoginButtonState() {
		if (usrComboBox.getValue() != null && usrComboBox.getValue().length() > 0 && passField.getText().length() > 0)
			dialog.getDialogPane().lookupButton(loginButton).setDisable(false);
		else
			dialog.getDialogPane().lookupButton(loginButton).setDisable(true);
	}

	/**
	 * Converts results returned by dialog window.
	 * 
	 * @param buttonType
	 *            Button clicked by user
	 * @return Pair of environment and username if login was successful. Null
	 *         otherwise.
	 */
	private Pair<Environment, String> resultConverter(Optional<ButtonType> buttonType) {
		if (buttonType.get() == loginButton) {
			if (envChoiceBox.getValue().isPasswordCorrect(usrComboBox.getValue(), passField.getText())) {
				return new Pair<Environment, String>(envChoiceBox.getValue(), usrComboBox.getValue());
			}
		}
		return null;
	}

	/**
	 * Redefinition of showAndWait method for dialog.
	 * 
	 * @return Pair of environment and username processed by resultConverter();
	 */
	public Optional<Pair<Environment, String>> showAndWait() {
		return Optional.ofNullable(resultConverter(dialog.showAndWait()));
	}
}
