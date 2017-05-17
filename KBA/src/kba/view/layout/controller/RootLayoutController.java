package kba.view.layout.controller;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kba.MainApp;
import kba.model.User;

public class RootLayoutController {

	@FXML
	private ImageView logoImgView;
	@FXML
	private ImageView profileImgView;
	
	private MainApp mainApp;
	private User connectedUser;
	
	@FXML
    private void initialize() {
		Image image = null;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new FileInputStream("resources/logo.png"));
			image = SwingFXUtils.toFXImage(img, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logoImgView.setImage(image);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}
	
	@FXML
	private void handleDisconnect() {
		mainApp.initLoginLayout();
	}
	
	public void setUserProfileImg() {
		if (connectedUser != null) {
            profileImgView.setImage(connectedUser.getProfileImg());
        } else {
        	System.out.println("nop");
            Image image = null;
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new FileInputStream("resources/user.png"));
			    image = SwingFXUtils.toFXImage(img, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
            profileImgView.setImage(image);
        }
	}
}
