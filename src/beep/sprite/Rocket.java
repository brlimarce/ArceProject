/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'Rocket' is the player in beep. It can move in any
* direction, blast stars (via the space bar), and collect
* power-ups to boost itself.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-11 00:18
*
***********************************************************/
package beep.sprite;
import java.util.ArrayList;
import java.util.Random;

import beep.game.GameStage;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Rocket extends Sprite {
	// -- Attributes
	private String name;
	private int strength;
	private int aliensKilled; // To check how many aliens have been blasted.
	private boolean isAlive;
	private ArrayList<Star> stars; // Serves as 'bullets'.

	public final static double ROCKET_HEIGHT = 174.5;

	// Distances
	public final static int POSITIVE_DISTANCE = 10;
	public final static int NEGATIVE_DISTANCE = -(Rocket.POSITIVE_DISTANCE);

	// Images
	private final static Image ROCKET_IMAGE = new Image(Sprite.IMAGE_DIR + "rocket.png");
	private final static Image ROCKET_HIT_IMAGE = new Image(Sprite.IMAGE_DIR + "rocket-hit.png");
	private final static Image ROCKET_BOOSTED_IMAGE = new Image(Sprite.IMAGE_DIR + "rocket-boosted.png");

	// -- Constructor
	public Rocket(String name, int x, int y) {
		super(x, y, Rocket.ROCKET_IMAGE); // To set the coordinates and load the image.
		this.name = name;

		// To generate the rocket's strength.
		Random r = new Random();
		this.strength = r.nextInt(51) + 100;

		this.isAlive = true;
		this.stars = new ArrayList<Star>();
	}

	/*
	 * -- Methods
	 * Below are the rocket's methods.
	 */

	// Move the rocket.
	public void move() {
		// If the rocket is within the screen, it is allowed to move.
		if ( (this.x + this.dx >= 0) && (this.x + this.dx <= GameStage.WINDOW_WIDTH - this.width) )
			this.x += this.dx;
		if ( (this.y + this.dy >= 0) && (this.y + this.dy <= GameStage.WINDOW_HEIGHT - this.height) )
			this.y += this.dy;
	}

	// Shoot stars.
	public void shoot() {
		// To add a star to the array list of stars.
		this.stars.add(new Star( (int)(this.x + (this.width / 2)), (int)this.y));
	}

	// Kill a UFO.
	void killUFO() {
		this.aliensKilled++; // To add the number of aliens killed.
		System.out.printf(">> %s killed %d UFOs!\n", this.name, this.aliensKilled);
	}

	// Decrease the rocket's strength.
	void getHit(UFO ufo) {
		this.strength -= ufo.getDamage();

		// To check if the rocket will die.
		if (this.strength <= 0) {
			this.strength = 0; // To reset the strength.
			this.die();
		}

		this.changeState(Rocket.ROCKET_HIT_IMAGE); // To show that the rocket is hit.
		System.out.printf(">> %s is hit! He has %d strength left!\n", this.name, this.strength);
	}

	// Collect a power-up.
	public void collectPowerup(PowerUp powerup) {
		// If the rocket collides with the power-up, boost the rocket.
		if (this.collidesWith(powerup)) {
			powerup.boost(this); // To provide the boost to the rocket.
			powerup.disappear(); // To hide the power-up.
			this.changeState(Rocket.ROCKET_BOOSTED_IMAGE);
		}
	}

	// Change the rocket's state.
	private void changeState(Image image) {
		// To load the rocket's state.
		this.load(image);

		// To revert back to its normal state via transition.
		PauseTransition transition = new PauseTransition(Duration.seconds(0.5));
		transition.play();

		transition.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				load(Rocket.ROCKET_IMAGE);
			}
		});
	}

	// The rocket dies.
	private void die() {
		this.isAlive = false;
		this.disappear();
	}

	/*
	 * -- Getters
	 * Below are some of the rocket's getters.
	 */

	// Get the rocket's name.
	public String getName() {
		return this.name;
	}

	// Get the rocket's life.
	public boolean getLife() {
		return this.isAlive;
	}

	// Get the rocket's stars.
	public ArrayList<Star> getStars() {
		return this.stars;
	}

	// Get the rocket's strength.
	public int getStrength() {
		return this.strength;
	}

	// Get the number of aliens killed.
	public int getAliensKilled() {
		return this.aliensKilled;
	}

	/*
	 * -- Setters
	 * Below are some of the rocket's setters.
	 */

	// Add the rocket's strength by boost.
	void boostStrength(int boost) {
		this.strength += boost;
	}
}
