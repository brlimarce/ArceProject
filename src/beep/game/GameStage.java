/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'GameStage' serves as the game's main stage.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-11 07:08
*
***********************************************************/
package beep.game;
import beep.sprite.Sprite;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GameStage {
	// -- Attributes
	private Stage stage;
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer timer;

	// Window Dimensions
	public final static int WINDOW_WIDTH = 1200;
	public final static int WINDOW_HEIGHT = 750;

	// Screens
	private Splash splash;

	/*
	 * -- Paths
	 * Below are the paths for some of the game's assets.
	 */

	// Directories
	public final static String BACKGROUND_DIR = "assets/background/";
	private final static String FONT_DIR = "/assets/font/";

	// Fonts
	public final static String GOTHAM_BLACK_PATH = GameStage.FONT_DIR + "Gotham-Black.otf";
	public final static String PROXIMA_BOLD_PATH = GameStage.FONT_DIR + "Proxima Nova Bold.otf";
	public final static String PROXIMA_REG_PATH = GameStage.FONT_DIR + "ProximaNova-Regular.otf";

	// Colors
	public final static Color WHITE = Color.web("#FEFEFE");
	public final static Color BLACK = Color.web("#202020");
	public final static Color YELLOW = Color.web("#F7DF4D");
	public final static Color HYPERLINK_BLUE = Color.web("#BAD7DF");
	public final static Color PURPLE = Color.web("#4F398A");

	// -- Constructor
	public GameStage() {
		this.root = new Group();
		this.scene = new Scene(this.root);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.timer = new GameTimer(this.gc, this.scene);
		this.splash = new Splash(this, this.timer);
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Set the stage.
	public void setStage(Stage stage) {
		// To initialize the stage.
		this.stage = stage;

		// To set the stage's elements.
		this.root.getChildren().add(this.canvas);
		this.stage.setTitle("beep");
		this.stage.getIcons().add(new Image(Sprite.IMAGE_DIR + "rocket.png"));

		// To initialize the splash scene.
		this.stage.setScene(this.splash.getScene());

		this.stage.setResizable(false); // To prevent the user from resizing the window.
		this.stage.show(); // To show the stage.
	}

	/*
	 * -- Component Creation
	 * Below are methods to create UI components.
	 */

	// Create a label.
	Label createLabel(String caption, Font font, int x, int y, Paint fill) {
		Label lblText = new Label(caption);
		lblText.setFont(font); // To embed the font.
		lblText.relocate(x, y); // To set the label's location.
		lblText.setTextFill(fill); // To set the label's color (i.e. hue, gradient).

		return lblText;
	}

	// Create a multiline label.
	Label upgradeMultiline(Label label, int width) {
		Label multiline = label;
		multiline.setMaxWidth(width);
		multiline.setWrapText(true); // To make the label multiline.
		multiline.setTextAlignment(TextAlignment.JUSTIFY); // To justify the text.

		return multiline;
	}

	// Create a button. (All buttons in this stage are similar with variations.)
	Button createButton(int x, int y, int width, int height, Color textFill, String normalStyle, String activeStyle) {
		String radiusStyle = "-fx-background-radius: 8px"; // For the button's border radius.
		Button button = new Button();

		// To style the button.
		button.setFont(this.getFont(GameStage.PROXIMA_BOLD_PATH, 24)); // Font
		button.setPrefSize(width, height); // Dimensions
		button.setEffect(this.getShadow(Color.web("rgba(0, 0, 0, 0.1)"), 0, 0)); // Shadow
		button.setCursor(Cursor.HAND); // Cursor
		button.setTextFill(textFill); // Text color
		button.relocate(x, y); // Button Position
		button.setStyle(radiusStyle + ";" + normalStyle);

		/*
		 * -- Event Handlers
		 * To change colors if the button is hovered.
		 */
		button.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				button.setStyle(radiusStyle + ";" + activeStyle);
			}
		});

		button.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				button.setStyle(radiusStyle + ";" + normalStyle);
			}
		});

		return button;
	}

	// Create a 'previous' button. (For the Instructions and About screens)
	Button createBtnPrev(String normalStyle, String activeStyle) {
		Button button = this.createButton(200, 96, 48, 48, GameStage.WHITE, normalStyle, activeStyle);
		Image image = new Image(Sprite.IMAGE_DIR + "arrow.png");
		ImageView view = new ImageView(image);
		button.setGraphic(view); // To style the image button.

		/*
		 * -- Event Handler
		 * To go back to the splash screen.
		 */
		button.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				stage.setScene(splash.getScene());
			}
		});

		return button;
	}

	// Create a canvas.
	Canvas createCanvas(String bgPath) {
		Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);

		// To draw the background.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(new Image(GameStage.BACKGROUND_DIR + bgPath), 0, 0);

		return canvas;
	}

	// Create an ImageView.
	ImageView createImage(int x, int y, String path) {
		ImageView view = new ImageView();
		view.setX(x);
		view.setY(y);
		view.setEffect(this.getShadow(Color.web("rgba(0, 0, 0, 0.1)"), 4, 4));
		view.setImage(new Image(Sprite.IMAGE_DIR + path));

		return view;
	}

	/*
	 * -- Getters
	 * Below are some of the class's getters.
	 */

	// Get the stage.
	Stage getStage() {
		return this.stage;
	}

	// Get the scene.
	Scene getScene() {
		return this.scene;
	}

	// Get the font.
	Font getFont(String path, int size) {
		return Font.loadFont(getClass().getResourceAsStream(path), size);
	}

	// Get a linear gradient.
	LinearGradient getLinearGradient(Color primary, Color secondary) {
		Stop[] stops = new Stop[] { new Stop(0, primary), new Stop(1, secondary) }; // To initialize the gradient's stops.
		return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
	}

	// Get a shadow.
	DropShadow getShadow(Color color, int x, int y) {
		DropShadow shadow = new DropShadow();
		shadow.setBlurType(BlurType.GAUSSIAN);
		shadow.setColor(color);
		shadow.setOffsetX(x);
		shadow.setOffsetY(y);

		return shadow;
	}
}
