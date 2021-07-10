/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'Splash' serves as the splash screen.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-14 10:02
*
***********************************************************/
package beep.game;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

class Splash extends Screen {
	// -- Attributes
	private GameTimer timer;
	private Instructions instructScene;
	private About aboutScene;

	private static int BUTTON_WIDTH = 240;
	private static int BUTTON_HEIGHT = 60;

	// -- Constructor
	Splash(GameStage stage, GameTimer timer) {
		super(stage, "exploration.png");
		this.timer = timer;
		this.instructScene = new Instructions(this.stage);
		this.aboutScene = new About(this.stage);
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Initialize the splash screen.
	@Override
	public void init() {
		// To draw the background.
		this.root = new Group();
		this.root.getChildren().add(this.stage.createCanvas(this.bgPath));

		// To create the header label.
		Label lblTitle = this.stage.createLabel("beep", this.stage.getFont(GameStage.GOTHAM_BLACK_PATH, 84), 492, 96, this.stage.getLinearGradient(Color.web("#F9E676"), Color.web("#F7DF4D")));
		lblTitle.setEffect(this.stage.getShadow(Color.web("rgba(0, 0, 0, 0.05)"), 4, 4));

		// To create the description label.
		Label lblDesc = this.stage.createLabel("Blast or be blasted!", this.stage.getFont(GameStage.PROXIMA_REG_PATH, 28), 472, 192, GameStage.WHITE);

		/*
		 * -- Buttons
		 * To initialize the buttons in the splash screen.
		 */

		// To create and style the buttons.
		Button btnPlay = this.stage.createButton(480, 618, Splash.BUTTON_WIDTH, Splash.BUTTON_HEIGHT, GameStage.WHITE, "-fx-background-color:linear-gradient(to bottom,#F9E676,#F7DF4D)", "-fx-background-color:linear-gradient(to bottom,#FFC24C,#FFB425)");
		Button btnInstruct = this.stage.createButton(200, 618, Splash.BUTTON_WIDTH, Splash.BUTTON_HEIGHT, GameStage.WHITE, "-fx-background-color:linear-gradient(to bottom,#AAA2E2,#8D82D8)", "-fx-background-color:linear-gradient(to bottom,#8B7AFF,#6852FF)");
		Button btnAbout = this.stage.createButton(760, 618, Splash.BUTTON_WIDTH, Splash.BUTTON_HEIGHT, GameStage.WHITE, "-fx-background-color:linear-gradient(to bottom,#AAA2E2,#8D82D8)", "-fx-background-color:linear-gradient(to bottom,#8B7AFF,#6852FF)");

		btnPlay.setText("Play");
		btnInstruct.setText("Instructions");
		btnAbout.setText("About");

		/*
		 * -- Event Handlers
		 * To redirect to different screens.
		 */

		btnPlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
        	public void handle(MouseEvent arg0) {
				stage.getStage().setScene(stage.getScene());
				timer.start();
        	}
        });

		btnInstruct.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
        	public void handle(MouseEvent arg0) {
				stage.getStage().setScene(instructScene.getScene());
        	}
        });

		btnAbout.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
        	public void handle(MouseEvent arg0) {
				stage.getStage().setScene(aboutScene.getScene());
        	}
        });

		// To initialize the scene.
		this.root.getChildren().addAll(lblTitle, lblDesc, btnPlay, btnInstruct, btnAbout);
		this.scene = new Scene(this.root);
	}
}
