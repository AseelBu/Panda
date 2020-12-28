package View;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InfoPane extends Application {
	
	
	/**
	 * Root Parent
	 */
	private AnchorPane mainBorder;
	/**
	 * Primary of screen
	 */
	private static Stage primary;	
	/**
	 * ImageView
	 */
	@FXML // fx:id="info"
    private ImageView info; // Value injected by FXMLLoader
	
	
	
	@Override
	public void start(Stage primaryStage) {
		
	
		try {
			mainBorder = FXMLLoader.load(getClass().getResource("/View/info.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(mainBorder);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hamka");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/View/pictures/logo.png")));
		primaryStage.show();

		this.info = new ImageView();


		loadesign();


	}
	
	
	/**
	 * Get primary of the screen
	 * @return screen's primary
	 */
	public Stage getPrimary() {
		if(primary == null) {
			primary = new Stage();
		}
		return primary;
	}
	
	
	

	/**
	 * Loads the design of the help screen
	 */
	private void loadesign() {
		
		
		
		info.setImage(new Image(getClass().getResourceAsStream("pictures/info.png")));
		
		
		info.setFitWidth(mainBorder.getWidth() - 2);
		info.setFitHeight(mainBorder.getHeight() - 2);
		AnchorPane.setBottomAnchor(info, 0.0);
		AnchorPane.setLeftAnchor(info, 0.0);
		AnchorPane.setRightAnchor(info, 0.0);
		AnchorPane.setTopAnchor(info, 0.0);
		
		mainBorder.getChildren().add(info);
		
		mainBorder.getScene().setOnMouseMoved(e -> {
	        try {
				primary.hide();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    });
		
	}

	
	
	
	

}
