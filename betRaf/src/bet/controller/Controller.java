package bet.controller;

import bet.model.*;
import bet.utils.Constant;
import bet.utils.Validation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;

public class Controller {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField name;
    @FXML
    private Label error;
    @FXML
    private Button close;
    @FXML
    private TextField regUser;
    @FXML
    private PasswordField regPassw;
    @FXML
    private PasswordField regConfPassw;
    @FXML
    private TextField regEmail;
    @FXML
    private Label regError;
    @FXML
    private CheckBox cb1;
    @FXML
    private CheckBox cb2;
    @FXML
    private CheckBox cb3;
    @FXML
    private CheckBox cb4;
    @FXML
    private RadioButton visa;
    @FXML
    private RadioButton mastercard;
    @FXML
    private RadioButton maestro;
    @FXML
    private RadioButton sms;
    @FXML
    private RadioButton paypal;
    @FXML
    private ComboBox comboNum;
    @FXML
    private TextField age;
    @FXML
    private Button create;
    @FXML
    private Label warning;
    @FXML
    private TableView table;
    @FXML
    private TextField id;
    @FXML
    private Button regSignin;
    @FXML
    private Label regAdminLabel;
    @FXML
    private Label regUserLabel;
    @FXML
    private Label userShow;
    @FXML
    private Button logout;
    @FXML
    private Slider betSlider;
    @FXML
    private Label betLabel;
    @FXML
    private ComboBox Live;


    User user2;
    Admin admin2;

    ResultSet rsAllEntries;
    ObservableList<ObservableList> data = FXCollections.observableArrayList();

    //login window
    public void login(ActionEvent event) {
        if (Validation.isValidUsername(username.getText()) && Validation.isValidPassword(password.getText())) {
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.login(username.getText(), password.getText());
            if (msg.contains("Successful")) {
                User user = userDAO.getUser(username.getText());
                System.out.println("pavyko");
                dashboard2(event, user);
            } else {
                error.setText(msg);
            }
        }
        if (Validation.isValidUsername(username.getText()) && Validation.isValidPassword(password.getText())) {
            AdminDAO adminDAO = new AdminDAO();
            String msg = adminDAO.regLogin(username.getText(), password.getText());
            if (msg.contains("Successful")) {
                Admin admin = adminDAO.getUser(username.getText());
                dashboard(event, admin);
            } else {
                error.setText(msg);
            }

        } else {
            error.setText("Wrong user name or password!");
        }
        error.setVisible(true);
    }

    // button 'X' closes stage(window)
    public void closeWindow(ActionEvent event) {
        if (event.getSource() == close) {
            System.exit(0);
        }
    }


    public void register(ActionEvent event) {

        try {
            // we are in controller folder, but our view is not here, so we need to go one step up - ../
            Parent root = FXMLLoader.load(getClass().getResource("../view/register.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root, 450, 350));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Register as Admin and User
    public void registerLogin(ActionEvent event) throws IOException {

        boolean isRegistered = true;

        // clear errors on btn pressed
        regError.setText("");
        if (!Validation.isValidUsername(regUser.getText())) {
            regError.setText("Username is incorrect (letters and numbers only, at least 5 char)");
            isRegistered = false;
        } else if (!Validation.isValidPassword(regPassw.getText())) {
            regError.setText("Password is incorrect (letters and numbers only, at least 5 char)");
            isRegistered = false;
        } else if (!regConfPassw.getText().equals(regPassw.getText())) {
            regError.setText("Password doesn't match");
            isRegistered = false;
        } else if (!Validation.isValidEmail(regEmail.getText())) {
            regError.setText("Email is not correct, pattern- dakar@one.lt");
            isRegistered = false;
        }


        if (regSignin.getText().equals("Admin")) {
            if (isRegistered) {
                Admin admin = new Admin(regUser.getText(), regPassw.getText(), regEmail.getText());
                AdminDAO adminDAO = new AdminDAO();
                String msg1 = adminDAO.regAdmin(admin);
                if (msg1.contains("admin")) {
                    Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Login");
                    stage.setScene(new Scene(root, 450, 350));
                    stage.show();
                    // hides current stage (window)
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                }
            }
        } else if (regSignin.getText().equals("Register")) {
            if (isRegistered) {
                User user = new User(regUser.getText(), regPassw.getText(), regEmail.getText());
                UserDAO userDAO = new UserDAO();
                String msg = userDAO.register(user);
                if (msg.contains("user")) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Login");
                        stage.setScene(new Scene(root, 450, 350));
                        stage.show();
                        // hides current stage (window)
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    regError.setText(msg);
                }
            }
        }
    }

    public void dashboard(ActionEvent event, Admin admin) {
        try {
            //Admin Dashboard
            // we are in controller folder, but our view is not here, so we need to go one step up - ../
            Parent root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root, 1300, 850));

            admin2 = admin;

            userShow = (Label) root.lookup("#userShow");
            if (userShow != null) userShow.setText(admin.getUsername());


            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboard2(ActionEvent event, User user) {
        //User DASHBOARD
        try {
            // we are in controller folder, but our view is not here, so we need to go one step up - ../
            Parent root = FXMLLoader.load(getClass().getResource("../view/dashboardUser.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root, 1300, 850));

            user2 = user;

            userShow = (Label) root.lookup("#userShow");
            if (userShow != null) userShow.setText(user.getUsername());

            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void slider(MouseEvent mouseEvent) {
        Double s = betSlider.getValue();
        betLabel.textProperty().bind(Bindings.format("%.2f",
                betSlider.valueProperty()));
    }

   // String name, int age, String country, int bet, String event, String game, String payment
    public void create(ActionEvent event) {
        String Name1 = name.getText();
        String age1 = (String) age.getText();
        int bet1 = (int) betSlider.getValue();

        String country = "";
        if (comboNum.getSelectionModel().isEmpty()){
            comboNum.getSelectionModel().selectFirst();
            country = comboNum.getSelectionModel().getSelectedItem().toString();
        } else if (!comboNum.getSelectionModel().isEmpty()){
            country = comboNum.getSelectionModel().getSelectedItem().toString();
        } else {
            warning.setText("Country required");
        }

        String eventLive = "";
        if (!Live.getSelectionModel().isEmpty()) {
            eventLive = Live.getSelectionModel().getSelectedItem().toString();
        } else {
            warning.setText("Please check events");
        }

        String game = "";
        if (cb1.isSelected()) {
            game += cb1.getText() + ",";
        }
        if (cb2.isSelected()) {
            game += cb2.getText() + ",";
        }
        if (cb3.isSelected()) {
            game += cb3.getText() + ",";
        }
        if (cb4.isSelected()){
            game += cb4.getText() + ",";
        }

        String payment = "";
        if (paypal.isSelected()) {
            payment += paypal.getText();
        } else if (visa.isSelected()) {
            payment += visa.getText();
        } else if (maestro.isSelected()) {
            payment += maestro.getText();
        } else if (mastercard.isSelected()){
            payment += mastercard.getText();
        } else if (sms.isSelected()){
            payment += sms.getText();
        }


        if (!Validation.isValidName(Name1)) {
            warning.setText("Name Required");
        } else if (!Validation.isValidAge(age1)) {
            warning.setText("Age Required");
        }
        else {
           // public Bet(String name, int age, String country, int bet, String event, String game, String payment)
            Bet bet = new Bet(Name1, age1, country, bet1, eventLive, game, payment );
            BetDAO betDAO = new BetDAO();
            String msg = betDAO.add(bet);
            warning.setText(msg);


        }
    }

    public void search() {

    }




    public void delete() {
        BetDAO betDAO = new BetDAO();
        betDAO.deleteById(Integer.parseInt((String) (id.getText())));


    }



    public void admin(MouseEvent mouseEvent) throws IOException {
        regSignin.setText("Admin");
        regAdminLabel.setVisible(false);
        regUserLabel.setVisible(true);
        System.out.println(regSignin.getText());
    }

    public void user(MouseEvent mouseEvent) {
        regSignin.setText("Register");
        regAdminLabel.setVisible(true);
        regUserLabel.setVisible(false);
        System.out.println(regSignin.getText());
    }

    public void logout(ActionEvent event){
        try {
            // we are in controller folder, but our view is not here, so we need to go one step up - ../
            Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 450  , 350));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleShowDetails(){

        Connection c = null;
        Statement stmt = null;

        try {
            c = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT  * FROM  " + Constant.TABLE_NAME + " Where username = '" + name+ "'");

            while ( rs.next()) {
                String username = rs.getString("username");
                // String id = rs.getString("id");

                userShow.setText(username);
                // userShow.setText(id);

                System.out.println( "username = " + username );
                // System.out.println("Id = " + id);
                System.out.println();

            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }

    public void update(ActionEvent event) {

    }




    public void cmbLive(ActionEvent event) {
        if (Live.getSelectionModel().getSelectedItem().equals("Football")){
            cb1.setText("Brazil");
            cb2.setText("Portugal");
            cb3.setText("Spain");
            cb4.setText("France");
        }
        if (Live.getSelectionModel().getSelectedItem().equals("Basketball")){
            cb1.setText("LOS Lakers");
            cb2.setText("Team2");
            cb3.setText("Sparta");
            cb4.setText("Zalgiris");
        }
        if (Live.getSelectionModel().getSelectedItem().equals("Boxing")){
            cb1.setText("Zambidis");
            cb2.setText("Roosmalen");
            cb3.setText("Tyson");
            cb4.setText("Ali");
        }
    }
}
