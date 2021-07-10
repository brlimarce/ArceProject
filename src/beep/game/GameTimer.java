/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'GameTimer' holds the game's main flow.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-11 00:34
*
***********************************************************/
package beep.game;
import java.util.ArrayList;
import beep.sprite.Nebula;
import beep.sprite.Rocket;
import beep.sprite.Sprite;
import beep.sprite.Star;
import beep.sprite.UFO;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;

public class GameTimer extends AnimationTimer {
	// -- Attributes
	private GraphicsContext gc;
	private Scene scene;
	private Rocket rocket;
	private ArrayList<UFO> aliens;
	private Nebula nebula;
	private int startStrength;
	private boolean isWin;
	private boolean isAlienSpawn; // To see if 7 aliens had spawned.
	private double shootElapsedTime; // For the shooting cooldown.

	// For the delay of spawn and shoot.
	private long startSpawn;
	private long startShoot;
	private long startPowerup;
	private long startGame;

	private final static Image GAME_BACKDROP = new Image(GameStage.BACKGROUND_DIR + "unknown.png");
	private final static Image HEALTH_BAR = new Image(Sprite.IMAGE_DIR + "health-bar.png");
	private final static Image ALIEN_BAR = new Image(Sprite.IMAGE_DIR + "alien-bar.png");
	private final static int GAME_TIME = 60;
	private final static int ALIEN_SPAWN = 3;
	private final static int ALIEN_START_SPAWN = 7;
	private final static double SPAWN_DELAY = 5;
	private final static double SHOOT_DELAY = 0.07;
	private final static int POWERUP_DURATION = 5;
	private final static int POWERUP_SPAWN = 10;

	// -- Constructor
	GameTimer(GraphicsContext gc, Scene scene) {
		this.gc = gc;
		this.scene = scene;
		this.rocket = new Rocket("Junnie", 150, 250);
		this.startStrength = this.rocket.getStrength();
		this.aliens = new ArrayList<UFO>();
		this.nebula = new Nebula();
		this.isAlienSpawn = true;
		this.isWin = true;
		this.startGame = this.startShoot = this.startSpawn = this.startPowerup = System.nanoTime();
		this.handleKeyEvent();

		// To style the bar's components.
		this.gc.setFont(Font.loadFont(getClass().getResourceAsStream(GameStage.PROXIMA_BOLD_PATH), 20));
		this.gc.setFill(GameStage.WHITE);
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Handle the game's flow.
	@Override
	public void handle(long currentNanoTime) {
		// To get the elapsed time.
		double gameElapsedTime = (currentNanoTime - this.startGame) / 1000000000.0;
		double spawnElapsedTime = (currentNanoTime - this.startSpawn) / 1000000000.0;
		double powerupElapsedTime = (currentNanoTime - this.startPowerup) / 1000000000.0;
		this.shootElapsedTime = (currentNanoTime - this.startShoot) / 1000000000.0;

		// To initialize the game stage.
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc.drawImage(GameTimer.GAME_BACKDROP, 0, 0);

		// To initialize the rocket.
		this.rocket.move();
		this.rocket.render(this.gc);

		/*
		 * -- Nebula
		 * The timer resets if the rocket collects a nebula OR the nebula disappears.
		 */

		// If it reaches duration, it'll disappear.
		if (powerupElapsedTime > GameTimer.POWERUP_DURATION && this.nebula.getVisibility()) {
			this.nebula.vanish();
			this.startPowerup = System.nanoTime();
		}

		// To spawn a new nebula after cooldown.
		if (powerupElapsedTime > GameTimer.POWERUP_SPAWN)
			this.nebula = new Nebula();

		// While the nebula is visible, initialize some properties.
		if (this.nebula.getVisibility()) {
			// If the nebula is collected, reset the timer.
			this.rocket.collectPowerup(this.nebula);
			if (this.nebula.getCollectState())
				this.startPowerup = System.nanoTime();
		}

		/*
		 * -- Stars
		 * To shoot stars at an interval.
		 */

		// To set the stars' properties.
		ArrayList<Star> stars = this.rocket.getStars();
		for (int i = 0; i < stars.size(); i++) {
			// If the star is still visible, it can still move at a certain speed.
			Star star = stars.get(i);
			if (star.getVisibility())
				star.move();
			else
				stars.remove(i);
		}

		/*
		 * -- UFOs
		 * To spawn UFOs at an interval.
		 */

		// To check if the game will do a start spawn or not.
		if (this.isAlienSpawn) {
			// To reset the game timer.
			this.startGame = System.nanoTime(); // To start the game.
			gameElapsedTime = (currentNanoTime - this.startGame) / 1000000000.0;

			// To spawn 7 UFOs.
			for (int i = 0; i < GameTimer.ALIEN_START_SPAWN; i++)
				this.aliens.add(new UFO());
			this.isAlienSpawn = false;
			this.startSpawn = System.nanoTime();
		} else if (!this.isAlienSpawn && spawnElapsedTime > GameTimer.SPAWN_DELAY) {
			// To spawn 3 UFOs.
			for (int i = 0; i < GameTimer.ALIEN_SPAWN; i++)
				this.aliens.add(new UFO());
			this.startSpawn = System.nanoTime();
		}

		// To let the UFOs move.
		for (int i = 0; i < this.aliens.size(); i++) {
			// To let the UFOs continue moving if it's alive.
			UFO ufo = this.aliens.get(i);
			if (ufo.getLife()) {
				ufo.move();
				ufo.detectCollision(this.rocket); // To check collision with the rocket.
				if (!this.rocket.getLife()) this.isWin = false; // If the rocket dies, the player loses.
			} else this.aliens.remove(i);
		}

		// If the game isn't over, continue rendering the assets.
		if (this.rocket.getLife() && gameElapsedTime <= GameTimer.GAME_TIME) {
			// To render the stars, UFOs, and nebulae.
			for (Star star : this.rocket.getStars())
				star.render(this.gc);
			for (UFO ufo : this.aliens)
				ufo.render(this.gc);
			if (this.nebula.getVisibility())
				this.nebula.render(this.gc);

			// To display the health and alien bar.
			this.renderBar(GameTimer.HEALTH_BAR, 102, this.rocket.getStrength() + " / " + this.startStrength, 224);
			this.renderBar(GameTimer.ALIEN_BAR, 422, "Killed: " + this.rocket.getAliensKilled(), 543);
		} else {
			System.out.println(">> GAME ENDED!");
			this.stop();
			this.initGameOver(this.isWin); // To redirect to the GameOver screen.
		}
	}

	// Handle the key press events.
	private void handleKeyEvent() {
		/*
		 * -- Event Handlers
		 * To check if the rocket will move or stop.
		 */

		// To move once a key has been pressed.
		this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();

				// If the keys are up, down, left, and right, move the rocket.
				if (code == KeyCode.UP || code == KeyCode.LEFT || code == KeyCode.DOWN || code == KeyCode.RIGHT)
					moveRocket(code);
				else if (code == KeyCode.SPACE) {
					// If the elapsed time passes the delay, the rocket shoots.
					if (shootElapsedTime > GameTimer.SHOOT_DELAY) {
						rocket.shoot();
						startShoot = System.nanoTime();
					}
				}
			}
		});

		// To stop once a key has been released.
		this.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				stopRocket(code);
			}
		});
	}

	// Move the rocket. Direction is dependent on the key pressed.
	private void moveRocket(KeyCode e) {
		// To check which key has been pressed.
		if (e == KeyCode.UP)
			this.rocket.setDY(Rocket.NEGATIVE_DISTANCE);
		if (e == KeyCode.LEFT)
			this.rocket.setDX(Rocket.NEGATIVE_DISTANCE);
		if (e == KeyCode.DOWN)
			this.rocket.setDY(Rocket.POSITIVE_DISTANCE);
		if (e == KeyCode.RIGHT)
			this.rocket.setDX(Rocket.POSITIVE_DISTANCE);
	}

	// Stop the rocket; DX and DY turns into 0.
	private void stopRocket(KeyCode e) {
		this.rocket.setDX(0);
		this.rocket.setDY(0);
	}

	// Render the health bar.
	private void renderBar(Image bar, int barX, String text, int textX) {
		// To render the assets.
		this.gc.drawImage(bar, barX, 52); // To draw the bar.
		this.gc.fillText(text, textX, 120); // To put the caption.
	}

	// Initialize the game over screen.
	private void initGameOver(boolean isWin) {
		GameStage stage = new GameStage(); // To get some properties.
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT); // To erase the scene.
		this.gc.drawImage(new Image(GameStage.BACKGROUND_DIR + "unknown-blurred.png"), 0, 0); // To draw the background.

		/*
		 * -- Screen Customization
		 * To set the popup's properties.
		 */
		Image popup;
		String event, caption;
		Color primary, secondary;
		LinearGradient gradient;

		// To customize according to the event.
		if (isWin) {
			popup = new Image(Sprite.IMAGE_DIR + "victory.png");
			event = "victory";
			caption = "Your blasts are out of this world!";
			primary = GameStage.PURPLE;
			secondary = GameStage.BLACK;
			gradient = stage.getLinearGradient(Color.web("#5F45A6"), Color.web("#4F398A"));
		} else {
			popup = new Image(Sprite.IMAGE_DIR + "defeat.png");
			event = "defeat";
			caption = "Your rocket has been blasted! Try again!";
			primary = GameStage.YELLOW;
			secondary = GameStage.WHITE;
			gradient = stage.getLinearGradient(Color.web("#F9E676"), GameStage.YELLOW);
		}

		/*
		 * -- Popup
		 * To create and style the popup.
		 */
		this.gc.drawImage(popup, 200, 93); // To draw the 'modal'.

		// To draw the event (W/L).
		this.gc.setFill(gradient);
		this.gc.setFont(stage.getFont(GameStage.GOTHAM_BLACK_PATH, 64));
		this.gc.setEffect(stage.getShadow(Color.web("rgba(0, 0, 0, 0.1)"), 2, 2));
		this.gc.fillText(event, 557, 335);

		// To draw the caption.
		this.gc.setFill(secondary);
		this.gc.setFont(stage.getFont(GameStage.PROXIMA_REG_PATH, 20));
		this.gc.fillText(caption, 557, 370);

		// To draw the number of aliens killed and rocket's strength.
		this.gc.setFont(stage.getFont(GameStage.PROXIMA_REG_PATH, 24));
		this.gc.fillText(String.valueOf(this.rocket.getAliensKilled()), 702, 416);
		this.gc.fillText(String.valueOf(this.rocket.getStrength()) + " / " + String.valueOf(this.startStrength), 744, 445);

		// To draw their respective labels.
		this.gc.setFill(primary);
		this.gc.setFont(stage.getFont(GameStage.PROXIMA_BOLD_PATH, 24));
		this.gc.fillText("Aliens Killed:", 557, 416);
		this.gc.fillText("Rocket Strength:", 557, 445);

		/*
		 * -- Event Handler
		 * To exit the game.
		 */

		// To customize the label.
		this.gc.setFill(GameStage.WHITE);
		this.gc.setFont(stage.getFont(GameStage.PROXIMA_BOLD_PATH, 28));
		this.gc.fillText("Press the ENTER key to exit", 437, 612);

		this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				if (code == KeyCode.ENTER) System.exit(0);
			}
		});
	}
}
