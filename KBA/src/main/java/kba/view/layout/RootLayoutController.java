package kba.view.layout;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import kba.MainApp;
import kba.PluginSignature;
import kba.model.PluginHolder;
import kba.model.User;

public class RootLayoutController {

	@FXML
	private ImageView logoImgView;
	@FXML
	private ImageView profileImgView;
	@FXML
	private GridPane gridPane;
	
	private MainApp mainApp;
	private User connectedUser;
	private List<Button> buttonList = new ArrayList<>();
	
	@FXML
    private void initialize() {
		Image image = null;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new FileInputStream("resources/logo.png"));
			image = SwingFXUtils.toFXImage(img, null);
			logoImgView.setImage(image);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}

	public void removeButton() {
        for(Button button : buttonList) {
            gridPane.getChildren().remove(button);
        }
        buttonList.clear();
    }

    /**
     * used by the plugin detection thread keep the root plugin button updated
     * Max 5 buttons (can be modified but need some adapt. on FX)
     * @param pluginList
     */
	public void addButton(Map<String, PluginHolder> pluginList) {
	    removeButton();
        int index = 4;

        Iterator it = pluginList.keySet().iterator();
        while (it.hasNext()){
            String key = it.next().toString();
            PluginHolder value = pluginList.get(key);

            final Button temp = new Button(key);
            temp.getStyleClass().add("plugin");
            temp.setPrefSize(100, 26);
            temp.setMinSize(100, 26);
            temp.setMaxSize(100, 26);
            temp.setId(key);
            temp.setOnAction(lambda -> {
                PluginSignature plugin;
                plugin = value.getPlugin();
				plugin.run();
            });
            buttonList.add(temp);
		    gridPane.add(temp, 0, index--);

            if (index == 0) {
                break;
            }
        }
	}
	
	@FXML
	private void handleDisconnect() {
		mainApp.initLoginLayout();
	}
	
	public void setUserProfileImg() {
		if (connectedUser != null) {
            profileImgView.setImage(connectedUser.getProfileImg());
        } else {
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
