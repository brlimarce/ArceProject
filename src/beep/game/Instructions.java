/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'Instructions' displays the game's mechanics.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-14 10:50
*
***********************************************************/
package beep.game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;

class Instructions extends Screen {
	// -- Constructor
	Instructions(GameStage stage) {
		super(stage, "exploration-blurred.png");
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Initialize the instructions screen.
	@Override
	public void init() {
		// To draw the background.
		this.root = new Group();
		this.root.getChildren().add(this.stage.createCanvas(this.bgPath));

		// To display the header.
		this.root.getChildren().addAll(this.createHeaderTitle("how to play", this.stage.getLinearGradient(Color.web("#D26A9F"), Color.web("#C94C8C")), 396), this.createHeaderCaption("Help Junnie blast UFOs with cosmic stars!", 344));

		// To draw each step's image.
		this.root.getChildren().add(this.createImage(110, "step-1.gif"));
		this.root.getChildren().add(this.createImage(370, "step-2.gif"));
		this.root.getChildren().add(this.createImage(630, "step-3.gif"));
		this.root.getChildren().add(this.createImage(890, "step-4.gif"));

		// To create the labels.
		this.root.getChildren().add(this.createHeading("Maneuver!", 159));
		this.root.getChildren().add(this.createHeading("Blast Stars!", 415));
		this.root.getChildren().add(this.createHeading("Collect Nebulas!", 650));
		this.root.getChildren().add(this.createHeading("Avoid UFOs!", 930));

		// To create the description.
		this.root.getChildren().add(this.createContent("Move Junnie’s rocket by pressing any of the arrow keys to go UP, DOWN, LEFT, or RIGHT.",
				110));
		this.root.getChildren().add(this.createContent("Press the space bar to blast stars towards the UFOs!",
				370));
		this.root.getChildren().add(this.createContent("A nebula spawns every 10 seconds and lasts for 5 seconds. Collect them to gain 50 strength!",
				630));
		this.root.getChildren().add(this.createContent("Avoid dying from the UFOs by not getting hit by them or blasting stars towards those pesky things!",
				890));

		// To create the 'go back' button.
		Button btnPrev = this.stage.createBtnPrev("-fx-background-color:linear-gradient(to bottom,#D26A9F,#C94C8C)", "-fx-background-color:linear-gradient(to bottom,#F9E676,#F7DF4D)");

		this.root.getChildren().add(btnPrev);
		this.scene = new Scene(this.root);
	}

	// Create a step's image.
	private ImageView createImage(int x, String path) {
		return this.stage.createImage(x, 275, path);
	}

	// Create the uniformed label.
	private Label createHeading(String name, int x) {
		Label label = this.stage.createLabel(name, this.stage.getFont(GameStage.PROXIMA_BOLD_PATH, 20), x, 503, GameStage.YELLOW);
		label.setEffect(this.stage.getShadow(Color.web("rgba(0, 0, 0, 0.12)"), 2, 2)); // To set a shadow.
		return label;
	}

	// Create the description.
	private Label createContent(String caption, int x) {
		return this.stage.upgradeMultiline(this.stage.createLabel(caption, this.stage.getFont(GameStage.PROXIMA_REG_PATH, 16), x, 535, GameStage.WHITE), 200);
	}

	// Create the header label.
	Label createHeaderTitle(String caption, LinearGradient gradient, int x) {
		Label lblHeader = this.stage.createLabel(caption, this.stage.getFont(GameStage.GOTHAM_BLACK_PATH, 68), x, 88, gradient);
		lblHeader.setEffect(this.stage.getShadow(Color.web("rgba(0, 0, 0, 0.05)"), 4, 4));
		return lblHeader;
	}

	// Create the caption label for the header.
	Label createHeaderCaption(String caption, int x) {
		return this.stage.createLabel(caption, this.stage.getFont(GameStage.PROXIMA_REG_PATH, 28), x, 173, GameStage.WHITE);
	}
}
