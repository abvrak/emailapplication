package emailapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmailAppGUI extends Application {

    private TextField firstNameField;
    private TextField lastNameField;
    private ComboBox<String> departmentComboBox;
    private TextField emailTextField;
    private PasswordField passwordField;
    private TextField alternateEmailField;
    private TextField mailboxCapacityField;
    private Email emailAccount;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Email Administration System");

        // Create the main layout
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Create account section
        Label createAccountLabel = new Label("Create New Email Account");
        createAccountLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane createAccountGrid = new GridPane();
        createAccountGrid.setHgap(10);
        createAccountGrid.setVgap(10);
        createAccountGrid.setPadding(new Insets(10));

        // First Name field
        Label firstNameLabel = new Label("First Name:");
        firstNameField = new TextField();
        createAccountGrid.add(firstNameLabel, 0, 0);
        createAccountGrid.add(firstNameField, 1, 0);

        // Last Name field
        Label lastNameLabel = new Label("Last Name:");
        lastNameField = new TextField();
        createAccountGrid.add(lastNameLabel, 0, 1);
        createAccountGrid.add(lastNameField, 1, 1);

        // Department selection
        Label departmentLabel = new Label("Department:");
        departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll("None", "Sales", "Development", "Accounting");
        departmentComboBox.setValue("None");
        createAccountGrid.add(departmentLabel, 0, 2);
        createAccountGrid.add(departmentComboBox, 1, 2);

        // Create account button
        Button createAccountButton = new Button("Create Email Account");
        createAccountButton.setOnAction(e -> createEmailAccount());
        HBox createButtonBox = new HBox(createAccountButton);
        createButtonBox.setAlignment(Pos.CENTER_RIGHT);
        createAccountGrid.add(createButtonBox, 1, 3);

        // Email Information section
        Label emailInfoLabel = new Label("Email Account Information");
        emailInfoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane emailInfoGrid = new GridPane();
        emailInfoGrid.setHgap(10);
        emailInfoGrid.setVgap(10);
        emailInfoGrid.setPadding(new Insets(10));

        // Email address field
        Label emailLabel = new Label("Email Address:");
        emailTextField = new TextField();
        emailTextField.setEditable(false);
        emailInfoGrid.add(emailLabel, 0, 0);
        emailInfoGrid.add(emailTextField, 1, 0);

        // Password field
        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        passwordField.setEditable(false);
        Button showPasswordButton = new Button("Show");
        showPasswordButton.setOnAction(e -> showPassword());
        HBox passwordBox = new HBox(10, passwordField, showPasswordButton);
        emailInfoGrid.add(passwordLabel, 0, 1);
        emailInfoGrid.add(passwordBox, 1, 1);

        // Account Management section
        Label accountMgmtLabel = new Label("Account Management");
        accountMgmtLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane accountMgmtGrid = new GridPane();
        accountMgmtGrid.setHgap(10);
        accountMgmtGrid.setVgap(10);
        accountMgmtGrid.setPadding(new Insets(10));

        // Change password field
        Label changePasswordLabel = new Label("Change Password:");
        TextField newPasswordField = new TextField();
        Button changePasswordButton = new Button("Change");
        changePasswordButton.setOnAction(e -> {
            if (emailAccount != null) {
                emailAccount.changePassword(newPasswordField.getText());
                passwordField.setText(emailAccount.getPassword());
                newPasswordField.clear();
                showAlert("Success", "Password changed successfully.");
            } else {
                showAlert("Error", "Please create an email account first.");
            }
        });
        HBox changePasswordBox = new HBox(10, newPasswordField, changePasswordButton);
        accountMgmtGrid.add(changePasswordLabel, 0, 0);
        accountMgmtGrid.add(changePasswordBox, 1, 0);

        // Alternate email field
        Label alternateEmailLabel = new Label("Set Alternate Email:");
        alternateEmailField = new TextField();
        Button setAltEmailButton = new Button("Set");
        setAltEmailButton.setOnAction(e -> {
            if (emailAccount != null) {
                emailAccount.setAlternateEmail(alternateEmailField.getText());
                showAlert("Success", "Alternate email set to: " + emailAccount.getAlternateEmail());
            } else {
                showAlert("Error", "Please create an email account first.");
            }
        });
        HBox altEmailBox = new HBox(10, alternateEmailField, setAltEmailButton);
        accountMgmtGrid.add(alternateEmailLabel, 0, 1);
        accountMgmtGrid.add(altEmailBox, 1, 1);

        // Mailbox capacity field
        Label mailboxCapacityLabel = new Label("Set Mailbox Capacity:");
        mailboxCapacityField = new TextField();
        Button setCapacityButton = new Button("Set");
        setCapacityButton.setOnAction(e -> {
            if (emailAccount != null) {
                try {
                    int capacity = Integer.parseInt(mailboxCapacityField.getText());
                    emailAccount.setMailboxCapacity(capacity);
                    showAlert("Success", "Mailbox capacity set to: " + emailAccount.getMailboxCapacity() + "MB");
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter a valid number for mailbox capacity.");
                }
            } else {
                showAlert("Error", "Please create an email account first.");
            }
        });
        HBox capacityBox = new HBox(10, mailboxCapacityField, setCapacityButton);
        accountMgmtGrid.add(mailboxCapacityLabel, 0, 2);
        accountMgmtGrid.add(capacityBox, 1, 2);

        mainLayout.getChildren().addAll(
                createAccountLabel, createAccountGrid,
                emailInfoLabel, emailInfoGrid,
                accountMgmtLabel, accountMgmtGrid
        );

        Scene scene = new Scene(mainLayout, 400, 525);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void createEmailAccount() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            showAlert("Error", "Please enter both first and last name.");
            return;
        }

        // Create a modified version of Email
        emailAccount = new Email(firstName, lastName) {
            @Override
            protected String setDepartment() {
                String selection = departmentComboBox.getValue();
                switch (selection) {
                    case "Sales":
                        return "sales";
                    case "Development":
                        return "dev";
                    case "Accounting":
                        return "acc";
                    default:
                        return "";
                }
            }
        };

        // Update UI with email information
        emailTextField.setText(emailAccount.getEmail());
        passwordField.setText(emailAccount.getPassword());

        showAlert("Success", "Email account created successfully!");
    }

    private void showPassword() {
        if (emailAccount != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password");
            alert.setHeaderText(null);
            alert.setContentText("Your password is: " + emailAccount.getPassword());
            alert.showAndWait();
        } else {
            showAlert("Error", "No email account created yet.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}