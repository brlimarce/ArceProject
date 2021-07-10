/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'About' displays the developer's information as well as
* her resources and references during the design and
* development process.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-15 00:05
*
***********************************************************/
package beep.game;
import java.awt.Desktop;
import java.net.URI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

class About extends Screen {
	// -- Constructor
	About(GameStage stage) {
		super(stage, "moon-fishing.png");
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Initialize the about screen.
	public void init() {
		// To draw the background.
		this.root = new Group();
		this.root.getChildren().add(this.stage.createCanvas(this.bgPath));

		// To get the other uniformed components.
		Instructions instruct = new Instructions(this.stage);

		// To display the header.
		this.root.getChildren().addAll(instruct.createHeaderTitle("about me", this.stage.getLinearGradient(Color.web("#18BCFB"), Color.web("#04A7E6")), 435), instruct.createHeaderCaption("Learn more about my design + dev process!", 332));

		// To create the developer's bio.
		this.root.getChildren().add(this.stage.createImage(275, 239, "dialogue-bubble.png"));
		this.root.getChildren().add(this.stage.upgradeMultiline(this.stage.createLabel("“Hello! My name is Bianca Arce, but you can call me Bia! I love to code and design for fun. Sometimes, I play video games and read visual novels.”",
				this.stage.getFont(GameStage.PROXIMA_BOLD_PATH, 18), 338, 273, Color.web("#454479")), 540));

		// To display the labels.
		this.root.getChildren().add(this.createHeading("Design Resources", 407, 403, Color.web("#64A1FF")));
		this.root.getChildren().add(this.createHeading("Dev References", 407, 556, Color.web("#00FFAC")));
		this.root.getChildren().add(this.createHeading("Other References", 738, 403, GameStage.YELLOW));

		// To display the references.
		this.root.getChildren().add(this.createContent("1.\tFigma\n2.\tFlaticon\n3.\tColor Hunt", 452));
		this.root.getChildren().add(this.createContent("1.\tCMSC 22 Base Code\n2.\tEverwing FX", 605));

		// To display the hyperlinks.
		this.root.getChildren().add(this.createHyperlink("https://stackoverflow.com/questions/52750750/how-to-embed-font-in-javafx",
				"1.\tEmbedding Fonts", 452));
		this.root.getChildren().add(this.createHyperlink("http://www.java2s.com/Tutorials/Java/JavaFX/0110__JavaFX_Gradient_Color.htm",
				"2.\tGradient as Text Color", 476));
		this.root.getChildren().add(this.createHyperlink("https://docs.oracle.com/javafx/2/api/javafx/scene/paint/Color.html",
				"3.\tParsing Hex String to Color", 500));
		this.root.getChildren().add(this.createHyperlink("https://stackoverflow.com/questions/32005719/javafx8-how-to-shut-down-anti-aliasing-on-text",
				"4.\tFont Anti-aliasing", 524));
		this.root.getChildren().add(this.createHyperlink("https://www.javatpoint.com/java-int-to-string",
				"5.\tParsing int to String", 548));
		this.root.getChildren().add(this.createHyperlink("https://stackoverflow.com/questions/15977295/control-for-displaying-multiline-text",
				"6.\tMultiline Labels", 572));
		this.root.getChildren().add(this.createHyperlink("https://stackoverflow.com/questions/40866220/how-to-right-justify-wrapped-text-in-javafx",
				"7.\tJustify Text Alignment", 596));
		this.root.getChildren().add(this.createHyperlink("https://www.reddit.com/r/javahelp/comments/4bqcci/how_to_make_a_link_hyperlink_in_javafx/",
				"8.\tHyperlinks", 620));
		this.root.getChildren().add(this.createHyperlink("https://www.tutorialspoint.com/how-to-add-an-image-to-a-button-action-in-javafx",
				"9.\tImages as Buttons", 644));

		// To create the 'go back' button.
		Button btnPrev = this.stage.createBtnPrev("-fx-background-color:linear-gradient(to bottom,#18BCFB,#04A7E6)", "-fx-background-color:linear-gradient(to bottom,#FFC24C,#FFB425)");

		this.root.getChildren().add(btnPrev);
		this.scene = new Scene(this.root);
	}

	// Create the heading label.
	private Label createHeading(String name, int x, int y, Color color) {
		return this.stage.createLabel(name, this.stage.getFont(GameStage.PROXIMA_BOLD_PATH, 28), x, y, color);
	}

	// Create the content label.
	private Label createContent(String caption, int y) {
		return this.stage.upgradeMultiline(this.stage.createLabel(caption, this.stage.getFont(GameStage.PROXIMA_REG_PATH, 18), 407, y, GameStage.WHITE), 285);
	}

	// Create a hyperlink.
	private Hyperlink createHyperlink(String url, String title, int y) {
		Hyperlink link = new Hyperlink(title);
		link.setUnderline(true); // To put an underline to make it a 'link'.

		/*
		 * -- Event Handlers
		 * To change colors if the link is hovered.
		 */
		link.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				link.setTextFill(GameStage.WHITE);
			}
		});

		link.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				link.setTextFill(GameStage.HYPERLINK_BLUE);
			}
		});

		// To redirect to the webpage.
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// To redirect to the url using the desktop.
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (Exception e) { e.printStackTrace(); }
			}
		});

		// To style the hyperlink.
		link.setFont(this.stage.getFont(GameStage.PROXIMA_REG_PATH, 18));
		link.setTextFill(GameStage.HYPERLINK_BLUE);
		link.setBorder(null);
		link.relocate(738, y);

		return link;
	}
}
