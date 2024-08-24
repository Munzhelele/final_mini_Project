/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Munzhelele Awelani 221016083 
 * @version computer Science 3A miniProject 
 * VoterClient that extends the javaFX application and handles the responses from the server class
 * 
 */
public class VoterClient extends Application  {
/**
 * Attributes   
 */ 
	private static final String SERVER_IP ="127.0.0.1";
    private static final int PORT =8080;
    
    // Streams 
    private BufferedReader rd;
    private PrintWriter wr;
    private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
		primaryStage.setTitle("Elections");
		
		connectServer(); 
		
		Label titlelabel= new Label("Main menu");
		titlelabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
		Button registerButton = new Button("Register");
		registerButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		Button loginButton = new Button("Login");
	     loginButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		Button voteButton = new Button("Cast Vote");
		voteButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		Button resultsButton = new Button("Results");
		resultsButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
		
		registerButton.setOnAction(e -> openRegisterPortal());
		loginButton.setOnAction(e -> openloginPortal());
		voteButton.setOnAction(e-> openCastVote());
		resultsButton.setOnAction(e-> displayResults()); 
		
		
		VBox vBox = new VBox(20); 
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(20)); 
		vBox.getChildren().addAll(titlelabel,registerButton,loginButton,voteButton,resultsButton);
		
		// creating a Scene 
	    Scene scene = new Scene(vBox,400,300);
	    primaryStage.setScene(scene);
	    primaryStage.show();
		
	}
	private void displayResults() {
		// TODO Auto-generated method stub
		
	}
	private void openCastVote() {
		// TODO Auto-generated method stub
	}
	private void  openloginPortal() {
		// Implementation of the login form
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");       
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 16px; -fx-background-color: #555555; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            login(username, password);
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 16px; -fx-background-color: #555555; -fx-text-fill: white;");
        cancelButton.setOnAction(e -> primaryStage.close());

        VBox loginLayout = new VBox(20);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(20));
        loginLayout.getChildren().addAll(
                titleLabel,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton, cancelButton
        );

        Scene loginScene = new Scene(loginLayout, 400, 300);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
		
	}
	private void login(String username, String password) {
		// sending login command to the server
		wr.println("LOGIN " + username + " " + password);
        try {
            String response = rd.readLine();
            if (response.equals("LOGIN_SUCCESS")) {
                displayMessage("Login successful!");
            } else {
                displayError("Login failed. Please check your credentials.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            displayError("An error occurred during login.");
        }
		
	}
	private void  openRegisterPortal() {
		Stage registerportal = new Stage();
		registerportal.setTitle("Registration Portal");
	   
        Label titleLabel = new Label("Registration Portal");
        
        // Create UI elements
        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");

        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your address");

        Label partyAffiliationLabel = new Label("Party Affiliation:");
        TextField partyAffiliationField = new TextField();
        partyAffiliationField.setPromptText("Enter your party affiliation");

        Label voterIdLabel = new Label("Voter ID:");
        TextField voterIdField = new TextField();
        voterIdField.setPromptText("Enter your voter ID");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        // Set button action
     // Inside the openRegisterPortal() method
        submitButton.setOnAction(event -> {
            System.out.println("Submit button clicked"); // Debugging output
            String username = usernameField.getText();
            String password = passwordField.getText();
            String fullName = nameField.getText();
            LocalDate dob = dobPicker.getValue();
            String address = addressField.getText();
            String partyAffiliation = partyAffiliationField.getText();
            String voterId = voterIdField.getText();
            registerUser(username, password);
            registerportal.close();   
            if (isValidRegistration(username, password, fullName, dob, address, partyAffiliation, voterId)) {
                registerUser(username, password);
            } else {
                submitButton.setDisable(false); // Re-enable the button if registration is unsuccessful
            } 
            submitButton.setDisable(true);

            Task<Void> registerTask = new Task<>() {
                @Override
                protected Void call() {
                	try {
                	    registerUser(username, password);
                	} catch (Exception e) { // Catch any other unexpected exceptions
                	    this.setException(new RuntimeException("An unexpected error occurred during registration.", e));
                	}
                    return null;
                }
            };

            registerTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    submitButton.setDisable(false);
                    displayMessage("Registration successful!");
                    registerportal.close(); 
                });
            });

            registerTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    submitButton.setDisable(false);
                    Throwable exception = registerTask.getException();
                    if (exception instanceof IOException) {
                        displayError("Network error during registration.");
                    } else {
                        displayError("Registration failed: " + exception.getMessage());
                    }
                    exception.printStackTrace(); // Still print for debugging
                });
            });


            new Thread(registerTask).start(); 

         // Validation checks
            if (fullName.isEmpty() || dob == null || address.isEmpty() || partyAffiliation.isEmpty() || 
                voterId.isEmpty() || username.isEmpty() || password.isEmpty()) {
                displayError("Please fill in all fields.");
                return;
            }

            // Check if age is valid
            if (LocalDate.now().minusYears(18).isBefore(dob)) {
                displayError("You must be at least 18 years old to register.");
                return;
            }

         // Additional validation checks for voter ID can be added here
            if (!isValidVoterId(voterId)) {
                displayError("Invalid voter ID format. Please enter a valid voter ID.");
                return;
            }
        });
        VBox registrationLayout = new VBox(20);
        registrationLayout.setAlignment(Pos.CENTER);
        registrationLayout.setPadding(new Insets(40));
        registrationLayout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        registrationLayout.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        registrationLayout.getChildren().addAll(
                titleLabel,
                nameLabel, nameField,
                dobLabel, dobPicker,
                addressLabel, addressField,
                partyAffiliationLabel, partyAffiliationField,
                voterIdLabel, voterIdField,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                submitButton 
                
        );
        
        Scene registrationScene = new Scene(registrationLayout, 600, 800);
        primaryStage.setScene(registrationScene);
        primaryStage.show();
	}
	private boolean isValidRegistration(String username, String password, String fullName, LocalDate dob,
			String address, String partyAffiliation, String voterId) {
	    if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || dob == null ||
	        address.isEmpty() || partyAffiliation.isEmpty() || voterId.isEmpty()) {
	        displayError("Please fill in all fields.");
	        return false;
	    }

	    // Validate age
	    LocalDate today = LocalDate.now();
	    LocalDate eighteenYearsAgo = today.minusYears(18);
	    if (dob.isAfter(eighteenYearsAgo)) {
	        displayError("You must be at least 18 years old to register.");
	        return false;
	    }

	    // Validate voter ID format and content
	    if (!isValidVoterId(voterId)) {
	        displayError("Invalid voter ID format. Please enter a valid voter ID.");
	        return false;
	    }

	    // If all checks pass, the registration is valid
	    return true; 
	}
	private boolean isValidVoterId(String voterId) {
        // Check if the voter ID is in the correct format (13 digits)
        if (!voterId.matches("\\d{13}")) {
            return false;
        }

        // Extract birth date from the voter ID
        String birthDateStr = voterId.substring(0, 6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);

        // Check if the person is 18 or older
        LocalDate today = LocalDate.now();
        LocalDate eighteenYearsAgo = today.minusYears(18);
        if (birthDate.isAfter(eighteenYearsAgo)) {
            return false;
        }

        // Check if the ID starts with 0 or 1 (South African citizens)
        char firstDigit = voterId.charAt(0);
        return firstDigit == '0' || firstDigit == '1';
    }

	private void registerUser(String username, String password) {
	    try {
	        // Connect to the server
	        Socket socket = new Socket(SERVER_IP, PORT);

	        // Create PrintWriter for sending data to the server
	        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

	        // Create BufferedReader for receiving data from the server
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	        // Send registration command to the server
	        out.println("REGISTER " + username + " " + password);

	        // Read the server's response
	        String response = in.readLine();

	        // Handle the server's response
	        switch (response) {
	            case "REGISTER_SUCCESS":
	                // Registration successful
	                displayMessage("Registration successful!");
	                break;
	            case "ERROR: Username already exists.":
	                // Username already exists
	                displayError("Username already exists. Please choose another username.");
	                break;
	            case "ERROR: Invalid voter ID format.":
	                // Invalid voter ID format
	                displayError("Invalid voter ID format. Please enter a valid voter ID.");
	                break;
	            default:
	                // Unexpected server response
	                displayError("Registration failed. Unexpected server response: " + response);
	        }

	        // Close the socket
	        socket.close();
	    } catch (IOException ex) {
	        // Handle IOException
	        ex.printStackTrace();
	        displayError("An error occurred during registration. Please try again later.");
	    }
	}


	private void connectServer() {
		 try {
	            Socket socketConnection = new Socket(SERVER_IP, PORT);
	            rd = new BufferedReader(new InputStreamReader(socketConnection.getInputStream()));
	            wr = new PrintWriter(socketConnection.getOutputStream(), true);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            displayError("Unable to connect to the server.");
	        }
		
	} 
	public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void displayMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void displayError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
	public static void main(String[] args) {
        launch(args);
    }
}
