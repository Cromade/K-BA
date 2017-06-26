package kba;

import java.io.File;

import javafx.stage.Stage;

public interface DataRepository {

	public Stage getPrimaryStage();

	public File getDefaultCssFile();

	public void setNewCss(File newCss);

	public void resetDefaultCss();

}
