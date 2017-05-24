package kba;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import kba.model.Basket;
import kba.model.Category;
import kba.model.Group;
import kba.model.Preference;
import kba.model.Product;
import kba.model.User;
import kba.view.dialog.controller.PluginDialogController;
import kba.view.dialog.controller.ProductDetailDialogController;
import kba.view.dialog.controller.UserEditDialogController;
import kba.view.layout.controller.AccountLayoutController;
import kba.view.layout.controller.BasketManagementLayoutController;
import kba.view.layout.controller.GroupManagementLayoutController;
import kba.view.layout.controller.LoginLayoutController;
import kba.view.layout.controller.MainLayoutController;
import kba.view.layout.controller.PreferenceManagementLayoutController;
import kba.view.layout.controller.ProductListLayoutController;
import kba.view.layout.controller.RootLayoutController;
import kba.view.menu.controller.LeftMenuLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private User connectedUser;
    private Group defaultGroup;

    //some data do delete when db is up
    private ObservableList<Basket> basketData = FXCollections.observableArrayList();
    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<Group> groupData = FXCollections.observableArrayList();
    private ObservableList<Category> categoriesData = FXCollections.observableArrayList();
    private Preference preference;
    
    //Constructor
    public MainApp() {
        // Add some sample data to remove when db is up -----
    	this.preference = new Preference(1L, connectedUser);
    	
    	Category cat1 = new Category("Tendance");
    	Category cat2 = new Category("fourniture");
    	Category cat3 = new Category("papeterie");
    	categoriesData.add(cat1);
    	categoriesData.add(cat2);
    	categoriesData.add(cat3);
    	
    	Image image = null;
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new FileInputStream("resources/stylo.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	
	    	Product product1 = new Product(01L,"Stylo 4 couleurs","bic","stylo 4 couleurs classique", 1.0);    
	    	product1.addCategory(cat1);
	    	product1.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/papier.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product2 = new Product(02L,"papier","canson","150 feuillesvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv oui", 3.0);
	    	product2.addCategory(cat2);
	    	product2.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/gomme.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product3 = new Product(03L,"Gomme","bic","Accedebant enim eius asperitati, ubi inminuta vel laesa amplitudo imperii dicebatur, et iracundae suspicionum quantitati proximorum cruentae blanditiae exaggerantium incidentia et dolere inpendio simulantium, si principis periclitetur vita, a cuius salute velut filo pendere statum orbis terrarum fictis vocibus exclamabant.", 0.5);
	    	product3.addCategory(cat1);
	    	product3.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/crayon.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product4 = new Product(04L,"Crayon de papier","bic","mine 0.5", 1.0);
	    	product4.addCategory(cat1);
	    	product4.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/cahier.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product5 = new Product(05L,"Cahier","Canson","50 pages", 5.0);
	    	product5.addCategory(cat1);
	    	product5.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/scotch.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product6 = new Product(06L,"Ruban adhesif","Scotch","attention ca colle", 2.0);
	    	product6.addCategory(cat1);
	    	product6.setProductImg(image);
	    	
	    	productData.add(product1);
	    	productData.add(product2);
	    	productData.add(product3);
	    	productData.add(product4);
	    	productData.add(product5);
	    	productData.add(product6);

            User user1 = new User("john", "john", "johnjohn", "jhn@jhn.fr", "thisisapass", "05/05/2015", "3rue truc", "ville truc", "75000");
            User user2 = new User("johana", "johana", "johanajohana", "jhna@jhna.fr", "thisisapass", "05/05/2015", "3rue truc", "ville truc", "75000");
            userData.add(user1);
            userData.add(user2);

            img = ImageIO.read(new FileInputStream("resources/user.png"));
            image = SwingFXUtils.toFXImage(img, null);
            defaultGroup = new Group("Default", connectedUser, image);

            Group group2 = new Group("group2", userData, image);
            group2.addUserToGroup(user1);
            group2.addUserToGroup(user2);
            group2.addUserToGroup(connectedUser);

            groupData.add(defaultGroup);
            groupData.add(group2);
	    	
	    	Basket basket1 = new Basket("basket1");
	    	basket1.addProduct(product1,1);
	    	basket1.addProduct(product2,1);
	    	basket1.addProduct(product3,2);
	    	basket1.addProduct(product4,2);
	    	Basket basket2 = new Basket("basket2");
	    	basket2.addProduct(product4,5);
	    	Basket basket3 = new Basket("basket3");
            basket1.setGroup(defaultGroup);
            basket2.setGroup(defaultGroup);
            basket3.setGroup(group2);
	    	basketData.add(basket1);
	    	basketData.add(basket2);
	    	basketData.add(basket3);
	    	


		} catch (Exception e) {
			e.printStackTrace();
		}
    	//----------------------------------------
    }
    
    //to delete-----------------
    public ObservableList<Basket> getBasketData() {
        return basketData;
    }
    public ObservableList<Product> getProductData() {
        return productData;
    }
    public ObservableList<User> getUserData() {
        return userData;
    }
    public ObservableList<Group> getGroupData() {
        return groupData;
    } 
    public ObservableList<Category> getCategoriesData() {
        return categoriesData;
    }
    public Preference getPreference() {
    	return preference;
    }
    public void setBasketData(ObservableList<Basket> basketData) {
        this.basketData = basketData;
    }
    public void setProductData(ObservableList<Product> productData) {
        this.productData = productData;
    }
    public void setUserData(ObservableList<User> userData) {
        this.userData = userData;
    }
    public void setGroupData(ObservableList<Group> groupData) {
        this.groupData = groupData;
    } 
    public void setCategoriesData(ObservableList<Category> categoriesData) {
        this.categoriesData = categoriesData;
    }
    public void setPreference(Preference preference) {
    	this.preference = preference;
    }
    //---------------------------
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("K-BA");

        initLoginLayout();
    }
    
    public void initLoginLayout() {
    	try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/layout/LoginLayout.fxml"));
            AnchorPane loginLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            // Give the controller access to the main app.
            LoginLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/layout/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
	        //set the current user into the layout and call the refreshRootImg
            controller.setConnectedUser(connectedUser);
            controller.setUserProfileImg();
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainLayout() {
        try {
            // Load mainLayout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/layout/MainLayout.fxml"));
            AnchorPane mainLayout = (AnchorPane) loader.load();
            
            //Load mainMenu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("view/menu/MainLeftMenuLayout.fxml"));
            AnchorPane menuLayout = (AnchorPane) menuLoader.load();

            // Set mainLayout into the center of root layout.
            rootLayout.setCenter(mainLayout);
            rootLayout.setLeft(menuLayout);
            rootLayout.setCenterShape(false);
            
            // Give the controller access to controller
            MainLayoutController coreController = loader.getController();
            coreController.setMainApp(this);
            coreController.setDefaultBasketTotal();

	        //set the menu
            LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void changeLayoutToAccount(){
    	try {
	    	// Load accountLayout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/layout/AccountLayout.fxml"));
	        AnchorPane layout = (AnchorPane) loader.load();

	        //Load accountMenu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("view/menu/AccountLeftMenuLayout.fxml"));
            AnchorPane menuLayout = (AnchorPane) menuLoader.load();
            
	        // Set accountLayout into the center of root layout.
	        rootLayout.setCenter(layout);
	        rootLayout.setLeft(menuLayout);
	        rootLayout.setCenterShape(false);
	        
	        // Give the controller access to the main app.
	        AccountLayoutController coreController = loader.getController();
	        coreController.setMainApp(this);
	        
	        //set the current user into the layout
	        coreController.setUser(connectedUser);
	        coreController.showUserDetails();
	        
	        //set the menu
	        LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    public void changeLayoutToBasketManagement(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/layout/BasketManagementLayout.fxml"));
	        AnchorPane layout = (AnchorPane) loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("view/menu/BasketLeftMenuLayout.fxml"));
            AnchorPane menuLayout = (AnchorPane) menuLoader.load();
            
	        // Set Layout into the center of root layout.
	        rootLayout.setCenter(layout);
	        rootLayout.setLeft(menuLayout);
	        rootLayout.setCenterShape(false);
	        
	        // Give the controller access to the main app.
	        BasketManagementLayoutController coreController = loader.getController();
	        coreController.setMainApp(this);
	        coreController.setDataInTable();
	        
	        //set the menu
	        LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    public void changeLayoutToPreferenceManagement(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/layout/PreferenceManagementLayout.fxml"));
	        AnchorPane layout = (AnchorPane) loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("view/menu/PreferenceLeftMenuLayout.fxml"));
            AnchorPane menuLayout = (AnchorPane) menuLoader.load();
            
	        // Set Layout into the center of root layout.
	        rootLayout.setCenter(layout);
	        rootLayout.setLeft(menuLayout);
	        rootLayout.setCenterShape(false);
	        
	        // Give the controller access to the main app.
	        PreferenceManagementLayoutController coreController = loader.getController();
	        coreController.setMainApp(this);
	        coreController.setDataInTable();

	        //set the menu
	        LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    public void changeLayoutToProductList(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/layout/ProductListLayout.fxml"));
	        AnchorPane layout = (AnchorPane) loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("view/menu/ProductLeftMenuLayout.fxml"));
            AnchorPane menuLayout = (AnchorPane) menuLoader.load();
            
	        // Set Layout into the center of root layout.
	        rootLayout.setCenter(layout);
	        rootLayout.setLeft(menuLayout);
	        rootLayout.setCenterShape(false);
	        
	        // Give the controller access to the main app.
	        ProductListLayoutController coreController = loader.getController();
	        coreController.setMainApp(this);
	        
	        //set some data
	        coreController.setCategoriesInMenuItem();

	        //set the menu
	        LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    public void changeLayoutToGroupManagement(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/layout/GroupManagementLayout.fxml"));
	        AnchorPane layout = (AnchorPane) loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("view/menu/GroupLeftMenuLayout.fxml"));
            AnchorPane menuLayout = (AnchorPane) menuLoader.load();
            
	        // Set Layout into the center of root layout.
	        rootLayout.setCenter(layout);
	        rootLayout.setLeft(menuLayout);
	        rootLayout.setCenterShape(false);
	        
	        // Give the controller access to the main app.
	        GroupManagementLayoutController coreController = loader.getController();
	        coreController.setMainApp(this);

	        //set the menu
	        LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    public boolean showUserEditDialog(User user, String type) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialog/UserEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(type);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the user into the controller.
            UserEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(user);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showProductDetailDialog(Product product, boolean isPreference) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialog/ProductDetailDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Detail produit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the user into the controller.
            ProductDetailDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setChoiceBoxes();
            controller.setProduct(product);
            if (isPreference) {
            	controller.setButtonDisable();
            }

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
    }
    
    public void showPluginManagement() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/dialog/PluginManagementDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(" Gestion des plugins");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            PluginDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showBasketDetailDialog(Basket selectedBasket) {
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}

	public static void main(String[] args) {
        launch(args);
    }

}
