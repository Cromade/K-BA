package kba.util;

import javafx.scene.image.ImageView;

public class UtilImage {
	private ImageView image;
	
	public UtilImage(ImageView img) {
        this.image = img;
    }

    public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }
}
