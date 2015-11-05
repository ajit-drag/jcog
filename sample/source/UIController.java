/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.jcog.Jcog;
import org.json.JSONException;

/**
 * FXML Controller class
 *
 * @author Ajit-PC
 */
public class UIController implements Initializable {

    Jcog cog = new Jcog();
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    String otpStart;
    @FXML
    public TextField appID;
    @FXML
    public TextField accessToken;
    @FXML
    public TextField mobileNumber;
    @FXML
    public TextField otp;
    @FXML
    public Text statusText;
    @FXML
    public Button submit01;
    @FXML
    public Button submit02;
    @FXML
    public Button reset01;
    @FXML
    public Button reset02;

    public UIController() {
    }

    @FXML
    public void closeButton() {
        Platform.exit();
    }

    @FXML
    public void submitButton(ActionEvent event) throws IOException, JSONException, Exception {
        Object obj = event.getSource();
        if (obj instanceof Button) {
            Button clickedButton = (Button) obj;
            if (clickedButton.getId().equals("submit01")) {
                System.out.println("Submit 01 Button Pressed.");
                cog.setAppID((String) appID.getText());
                cog.setAccessToken((String) accessToken.getText());
                statusText.setText("Processing...");
                String getCallStatus = cog.recieveOtp(mobileNumber.getText());
                if (getCallStatus.equalsIgnoreCase("success")) {
                    statusText.setText("Missed call placed, enter otp...");
                } else {
                    statusText.setText(getCallStatus);
                }
                otpStart = cog.otpStart;
                System.out.println(cog.statusMissedCall);
                otp.setText(otpStart);
            }
            if (clickedButton.getId().equals("submit02")) {
                System.out.println("Submit 02 Button Pressed.");
                String correctedOtp = otp.getText().replaceAll("[+]", "");
                System.out.println(correctedOtp);
                String status;
                status = cog.sendOtp((String) otp.getText());
                System.out.println(status);
                if (status.equalsIgnoreCase("success")) {
                    statusText.setText("Mobile Number Verified SuccessFully");
                } else {
                    statusText.setText("Mobile Number Verification Failed");
                }

            }
        }
    }

    @FXML
    public void resetButton(ActionEvent event) {
        Object obj = event.getSource();
        if (obj instanceof Button) {
            Button clickedButton = (Button) obj;
            if (clickedButton.getId().equals("reset01")) {
                System.out.println("Reset button of Section 1 was clicked.");
                if (appID != null) {
                    appID.setText(null);
                }
                if (accessToken != null) {
                    accessToken.setText(null);
                }
                if (mobileNumber != null) {
                    mobileNumber.setText(null);
                }
            }
            if (clickedButton.getId().equals("reset02")) {
                System.out.println("Reset button of Section 2 was clicked.");
                if (otp != null) {
                    otp.setText(otpStart);
                }
            }
        }
    }
    String urlString ="http://ajitsingh.co.nr";
    @FXML
    void openWebpage() {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (IOException | URISyntaxException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        statusText.setText("");
    }

}
