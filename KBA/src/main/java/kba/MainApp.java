package kba;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import kba.model.*;
import kba.util.PluginParser;
import kba.util.ScanDirectory;
import kba.util.ScanDirectoryCase;
import kba.view.dialog.*;
import kba.view.layout.AccountLayoutController;
import kba.view.layout.BasketManagementLayoutController;
import kba.view.layout.GroupManagementLayoutController;
import kba.view.layout.LoginLayoutController;
import kba.view.layout.MainLayoutController;
import kba.view.layout.PreferenceManagementLayoutController;
import kba.view.layout.ProductListLayoutController;
import kba.view.layout.RootLayoutController;
import kba.view.menu.LeftMenuLayoutController;
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
    private Map<String, PluginHolder> pluginList = new ConcurrentHashMap<>();
    private DataRepository dataRepository;
    private File defaultCss;
    private File currentCss;
    private boolean isStart;

    //some data do delete when db is up
    private ObservableList<Basket> basketData = FXCollections.observableArrayList();
    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<User> userData = FXCollections.observableArrayList();
    private ObservableList<Group> groupData = FXCollections.observableArrayList();
    private ObservableList<Category> categoriesData = FXCollections.observableArrayList();
    private Preference preference;
    private Basket cleverBasket;


    /**
     * constructor of the application
      */
    public MainApp() {

        // Add some sample data to remove when db is up -----
    	this.preference = new Preference(1L, connectedUser);

    	Category cat1 = new Category("Tous");
    	Category cat2 = new Category("fourniture");
    	Category cat3 = new Category("papeterie");
    	categoriesData.add(cat2);
    	categoriesData.add(cat3);

    	Image image;
		BufferedImage img;
		try {
		    img = ImageIO.read(new FileInputStream("resources/stylo.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	
	    	Product product1 = new Product(1L,"Stylo 4 couleurs","bic","stylo 4 couleurs classique", 1.0);
	    	product1.addCategory(cat1);
	    	product1.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/papier.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product2 = new Product(2L,"papier","canson","150 feuillesvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv oui", 3.0);
	    	product2.addCategory(cat1);
	    	product2.addCategory(cat2);
	    	product2.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/gomme.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product3 = new Product(3L,"Gomme","bic","Accedebant enim eius asperitati, ubi inminuta vel laesa amplitudo imperii dicebatur, et iracundae suspicionum quantitati proximorum cruentae blanditiae exaggerantium incidentia et dolere inpendio simulantium, si principis periclitetur vita, a cuius salute velut filo pendere statum orbis terrarum fictis vocibus exclamabant.", 0.5);
	    	product3.addCategory(cat1);
	    	product3.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/crayon.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product4 = new Product(4L,"Crayon de papier","bic","mine 0.5", 1.0);
	    	product4.addCategory(cat1);
	    	product4.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/cahier.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product5 = new Product(5L,"Cahier","Canson","50 pages", 5.0);
	    	product5.addCategory(cat1);
	    	product5.setProductImg(image);
	    	
	    	img = ImageIO.read(new FileInputStream("resources/scotch.jpg"));
		    image = SwingFXUtils.toFXImage(img, null);
	    	Product product6 = new Product(6L,"Ruban adhesif","Scotch","attention ca colle", 2.0);
	    	product6.addCategory(cat1);
	    	product6.setProductImg(image);
	    	
	    	productData.add(product1);
	    	productData.add(product2);
	    	productData.add(product3);
	    	productData.add(product4);
	    	productData.add(product5);
	    	productData.add(product6);

	    	connectedUser = new User();
	    	connectedUser.setId(123456L);
            connectedUser.setLastname("Jean");
            connectedUser.setFirstname("Person");
            connectedUser.setUsername("Mr nobody");
            connectedUser.setEmail("email@email.fr");
            connectedUser.setPassword("password");
            connectedUser.setBirthday("06/09/1994");
            connectedUser.setAddress("5 rue de truc");
            connectedUser.setCity("cityville");
            connectedUser.setPostalCode("75000");
            img = ImageIO.read(new FileInputStream("resources/usertmp.jpg"));
            image = SwingFXUtils.toFXImage(img, null);
            connectedUser.setProfileImg(image);

            img = ImageIO.read(new FileInputStream("resources/user.png"));
            image = SwingFXUtils.toFXImage(img, null);
            User user1 = new User("john", "john", "johnjohn", "jhn@jhn.fr", "thisisapass", "05/05/2015", "3rue truc", "ville truc", "75000");
            user1.setProfileImg(image);
            user1.setId(789456L);
            User user2 = new User("johana", "johana", "johanajohana", "jhna@jhna.fr", "thisisapass", "05/05/2015", "3rue truc", "ville truc", "75000");
            user2.setProfileImg(image);
            user2.setId(321879L);
            userData.add(user1);
            userData.add(user2);

            img = ImageIO.read(new FileInputStream("resources/user.png"));
            image = SwingFXUtils.toFXImage(img, null);
            defaultGroup = new Group(connectedUser.getUsername(), connectedUser, image, user1);

            Group group2 = new Group("Famille", userData, image, user2);
            group2.addUserToGroup(connectedUser);

            groupData.add(defaultGroup);
            groupData.add(group2);
	    	
	    	Basket basket1 = new Basket("basket1");
	    	basket1.addProduct(product1,1);
	    	basket1.addProduct(product2,1);
	    	basket1.addProduct(product3,2);
	    	basket1.addProduct(product5,1);
	    	basket1.addProduct(product1,1);
	    	Basket basket2 = new Basket("basket2");
	    	basket2.addProduct(product4,5);
	    	basket2.addProduct(product2,2);
	    	Basket basket3 = new Basket("basket3");
            basket1.setGroup(defaultGroup);
            basket2.setGroup(defaultGroup);
            basket2.setFavourite(true);
            basket3.setGroup(group2);
	    	basketData.add(basket1);
	    	basketData.add(basket2);
	    	basketData.add(basket3);

            cleverBasket = new Basket("cleverBasket");
            cleverBasket.setGroup(defaultGroup);
            cleverBasket.addProduct(product1, 9);

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
    public Basket getCleverBasket() {
        return cleverBasket;
    }
    public void setBasketData(ObservableList<Basket> basketData) {
        this.basketData = basketData;
    }
    public void setGroupData(ObservableList<Group> groupData) {
        this.groupData = groupData;
    }
    public void setPreference(Preference preference) {
    	this.preference = preference;
    }
    public void setCleverBasket(Basket cleverBasket) {
        this.cleverBasket = cleverBasket;
    }
    //---------------------------

    /**
     * start method of the FX
     * @param primaryStage the primary stage (FX)
     */
    @Override
    public void start(Stage primaryStage) {
        InputStream inputStream = getClass().getResourceAsStream("/style/MainTheme.css");
        try {
            File tempDefaultCss = File.createTempFile("javafx_stylesheet", "");
            tempDefaultCss.deleteOnExit();
            Files.copy(inputStream, tempDefaultCss.toPath(), StandardCopyOption.REPLACE_EXISTING);
            defaultCss = tempDefaultCss;
            currentCss = tempDefaultCss;
        } catch (IOException e) {
            e.printStackTrace();
        }
        initDataRepository();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("K-BA");
        this.isStart = true;

        //Plugin detection first launch
        pluginList = PluginParser.pluginParser(dataRepository);

        initLoginLayout();
    }

    /**
     * launch the login layout
      */
    public void initLoginLayout() {
    	try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/layouts/LoginLayout.fxml"));
            AnchorPane loginLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(loginLayout);
            scene.getStylesheets().add(currentCss.toURI().toString());
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
     * launch the root layout start the plugin thread
      */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/layouts/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(currentCss.toURI().toString());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            
            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
	        //set the current user into the layout and call the refreshRootImg
            controller.setConnectedUser(connectedUser);
            controller.setUserProfileImg();
            controller.addButton(pluginList);

            //Plugin detection thread
            ScanDirectory.scanDirectory(new File(new File(System.getProperty("user.home")), "KBA/Plugin"), (d,n) -> n.toLowerCase().endsWith(".jar"),
                    new ScanDirectoryCase() {
                        @Override
                        public void pluginFileAdded(File newFile) {
                            Platform.runLater(() -> {
                                try {
                                    PluginParser.analyzeJar(newFile, pluginList, dataRepository);
                                    controller.addButton(pluginList);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }

                        @Override
                        public void pluginFileRemoved(File removedFile) {
                            Platform.runLater(() -> {
                                for(Map.Entry<String , PluginHolder> entry : pluginList.entrySet()) {
                                    String key = entry.getKey();
                                    PluginHolder value = entry.getValue();

                                    if (value.getJarFile().equals(removedFile.getName())) {
                                        pluginList.remove(key);
                                    }
                                }
                                controller.addButton(pluginList);
                            });
                        }

                        @Override
                        public void pluginFileUpdated(File updatedFile) {
                            Platform.runLater(() -> {
                                try {
                                    PluginParser.analyzeJar(updatedFile, pluginList, dataRepository);
                                    controller.addButton(pluginList);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
            });

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * launch the main layout (home page of the app)
      */
    public void showMainLayout() {
        try {
            this.isStart = false;

            // Load mainLayout.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/layouts/MainLayout.fxml"));
            AnchorPane mainLayout = loader.load();

            //Load mainMenu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("/views/menus/MainLeftMenuLayout.fxml"));
            AnchorPane menuLayout = menuLoader.load();

            // Set mainLayout into the center of root layout.
            rootLayout.setCenter(mainLayout);
            rootLayout.setLeft(menuLayout);
            rootLayout.setCenterShape(false);
            rootLayout.getStylesheets().add(currentCss.toURI().toString());
            
            // Give the controller access to controller
            MainLayoutController coreController = loader.getController();
            coreController.setMainApp(this);
            coreController.setFavouriteBasketTotal();
            coreController.setRecentGroup();
            coreController.setReminderTable();

	        //set the menu
            LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * launch the account layout
     */
    public void changeLayoutToAccount(){
    	try {
	    	// Load accountLayout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/views/layouts/AccountLayout.fxml"));
	        AnchorPane layout = loader.load();

	        //Load accountMenu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("/views/menus/AccountLeftMenuLayout.fxml"));
            AnchorPane menuLayout = menuLoader.load();
            
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

    /**
     * launch the basket management layout
      */
    public void changeLayoutToBasketManagement(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/views/layouts/BasketManagementLayout.fxml"));
	        AnchorPane layout = loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("/views/menus/BasketLeftMenuLayout.fxml"));
            AnchorPane menuLayout = menuLoader.load();
            
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

    /**
     * launch the preference management
      */
    public void changeLayoutToPreferenceManagement(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/views/layouts/PreferenceManagementLayout.fxml"));
	        AnchorPane layout = loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("/views/menus/PreferenceLeftMenuLayout.fxml"));
            AnchorPane menuLayout = menuLoader.load();
            
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

    /**
     * launch the product list layout
      */
    public void changeLayoutToProductList(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/views/layouts/ProductListLayout.fxml"));
	        AnchorPane layout = loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("/views/menus/ProductLeftMenuLayout.fxml"));
            AnchorPane menuLayout = menuLoader.load();
            
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

    /**
     * launch the group management layout
      */
    public void changeLayoutToGroupManagement(){
    	try {
	    	// Load Layout.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("/views/layouts/GroupManagementLayout.fxml"));
	        AnchorPane layout = loader.load();

	        //Load Menu
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(MainApp.class.getResource("/views/menus/GroupLeftMenuLayout.fxml"));
            AnchorPane menuLayout = menuLoader.load();
            
	        // Set Layout into the center of root layout.
	        rootLayout.setCenter(layout);
	        rootLayout.setLeft(menuLayout);
	        rootLayout.setCenterShape(false);
	        
	        // Give the controller access to the main app.
	        GroupManagementLayoutController coreController = loader.getController();
	        coreController.setMainApp(this);
	        coreController.setDataInTable();
	        coreController.setConnectedUser(connectedUser);

	        //set the menu
	        LeftMenuLayoutController menuController = menuLoader.getController();
            menuController.setMainApp(this);
            
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    /**
     * show the user edit dialog
      * @param user the user edited
     * @param type new or modified
     * @return if the user has been modified
     */
    public boolean showUserEditDialog(User user, String type) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/UserEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(type);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            // Set the user into the controller.
            UserEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setUser(user);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Show the Group Edit Dialog
     * @param group the group edited
     * @param isNew new or modified
     * @return if the group has been modified
     */
    public boolean showGroupEditDialog(Group group, boolean isNew) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/GroupEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (isNew) {
                dialogStage.setTitle("Creation d'un groupe");
            } else {
                dialogStage.setTitle("Modification d'un groupe");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            // Set the user into the controller.
            GroupEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGroupDetails(group);
            controller.setConnectedUser(connectedUser);
            controller.setIsNew(isNew);
            controller.setMainApp(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Show the Product Detail Dialog
     * @param product the product selected by the user
     * @param isPreference if the product is in preference
     */
    public void showProductDetailDialog(Product product, boolean isPreference) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/ProductDetailDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Detail produit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
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
    }

    /**
     * Show the Product Detail Dialog
     * @param product the product selected by the user
     * @param basket the basket witch the product come
     */
    public void showBasketProductDetailDialog(BasketProduct product, Basket basket) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/BasketProductDetailDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Detail produit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            // Set the user into the controller.
            BasketProductDetailDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setReferencedBasket(basket);
            controller.setProduct(product);
            controller.setNumberInChoiceBox();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the Plugin Management Dialog
     */
    public void showPluginManagement() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/PluginManagementDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gestion des plugins");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);
            
            PluginDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.setDataInTable();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the Basket Detail Dialog
     * @param selectedBasket the basket selected by the user
     */
    public void showBasketDetailDialog(Basket selectedBasket) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/BasketDetailDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Detail du Panier : "+selectedBasket.getName());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            BasketDetailDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setDataInTableByBasket(selectedBasket);
            if (selectedBasket.getIsFavourite()) {
                controller.setFavouriteDisable();
            }

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the Basket Edit Dialog
     * @param selectedBasket the basket selected by the user
     * @param isNew new or modified
     */
    public void showBasketEditDialog(Basket selectedBasket, boolean isNew) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/BasketEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (selectedBasket == null) {
                dialogStage.setTitle("Creation d'un panier");
            } else {
                dialogStage.setTitle("Modification du panier");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            BasketEditDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setChoiceBoxes();
            controller.setIsNew(isNew);

            if (selectedBasket != null) {
                controller.setBasketData(selectedBasket);
            } else {
                Basket newBasket = new Basket();
                newBasket.setGroup(defaultGroup);
                controller.setBasketData(newBasket);
            }

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the Member Management Dialog
     * @param selectedGroup the group selected by the user
     */
    public void showMemberManagementDialog(Group selectedGroup) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/MemberManagementDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Gestion des membres du groupe "+selectedGroup.getName());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            MemberManagementDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setDataInTableByGroup(selectedGroup);
            controller.setConnectedUser(connectedUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the User Search Dialog
     * @param selectedGroup the group selected by the user
     */
    public void showUserSearchDialog(Group selectedGroup) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/UserSearchDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajout utilisateur dans : "+selectedGroup.getName());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            UserSearchDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setReferencedGroup(selectedGroup);
            controller.setConnectedUser(connectedUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the User Detail Dialog
     * @param selectedUser the user selected by the the user
     */
    public void showUserDetailDialog(User selectedUser) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/UserDetailDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Detail Utilisateur");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            // Set the user into the controller.
            UserDetailDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUser(selectedUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * show the basket proof dialog
     * @param selectedBasket the basket selected by the user
     */
    public void showBasketProofDialog(Basket selectedBasket) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/views/dialogs/BasketProofDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Preuve d'achat du panier "+selectedBasket.getName());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(currentCss.toURI().toString());
            dialogStage.setScene(scene);

            // Set the basket into the controller.
            BasketProofDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.showProof(selectedBasket);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Set the data Repository given to the plugins
     */
    private void initDataRepository() {
        this.dataRepository = new DataRepository() {

            @Override
            public Stage getPrimaryStage() {
                return primaryStage;
            }

            @Override
            public File getDefaultCssFile() {
                return defaultCss;
            }

            @Override
            public void setNewCss(File newCss) {
                currentCss = newCss;
                if (!isStart) {
                    showMainLayout();
                }
            }

            @Override
            public void resetDefaultCss() {
                InputStream inputStream = getClass().getResourceAsStream("/style/MainTheme.css");
                try {
                    File tempDefaultCss = File.createTempFile("javafx_stylesheet", "");
                    tempDefaultCss.deleteOnExit();
                    Files.copy(inputStream, tempDefaultCss.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    currentCss = tempDefaultCss;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showMainLayout();
            }
        };
    }
    
    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the current Css.
     */
    public File getCurrentCss() {
        return currentCss;
    }

    /**
     * Set the connected User
     */
    public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
	}

    /**
     * The main
     * @param args given args
     */
	public static void main(String[] args) {
        launch(args);
    }

    /**
     * Explicit
     * @return the root layout
     */
    public BorderPane getRootLayout() {
        return rootLayout;
    }

    /**
     * Explicit
     * @return the plugin list
     */
    public Map<String, PluginHolder> getPluginList() {
        return pluginList;
    }

}
