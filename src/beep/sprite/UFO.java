/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'UFO' is the rocket's enemy. It tries to damage the rocket
* by hitting it. 3 UFOs spawn every 5 seconds.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-11 00:18
*
***********************************************************/
package beep.sprite;
import java.util.Random;

import beep.game.GameStage;
import javafx.scene.image.Image;

public class UFO extends Sprite {
	// -- Attributes
	private int damage;
	private int speed;
	private boolean isLeft; // To determine the UFO's direction.
	private boolean isAlive;

	private final static Image UFO_IMAGE = new Image(Sprite.IMAGE_DIR + "ufo.png");
	private final static int UFO_DAMAGE = 30;
	public final static int UFO_SPAWN = 5;

	// -- Constructor
	public UFO() {
		super(0, 0, UFO.UFO_IMAGE);
		this.getSpawn();
		this.isLeft = true;
		this.isAlive = true;
		this.damage = UFO.UFO_DAMAGE;
		this.initSpeed(); // To initialize the UFO's speed.
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Move the UFO.
	public void move() {
		// To determine if the UFO goes left or right.
		if (this.isLeft)
			this.x -= this.speed;
		else
			this.x += this.speed;

		// To check if the UFO had hit the screen's boundaries.
		if (this.x <= 0)
			this.isLeft = false;
		else if (this.x >= (GameStage.WINDOW_WIDTH - this.width))
			this.isLeft = true;
	}

	// The UFO dies.
	private void die() {
		this.isAlive = false;
		this.disappear();
	}

	// Hit the rocket.
	private void hit(Rocket rocket) {
		rocket.getHit(this);
		this.die();
	}

	// Check the UFO's collision with the rocket or star.
	public void detectCollision(Rocket rocket) {
		// To check if the UFO collided with the rocket.
		if (this.collidesWith(rocket))
			this.hit(rocket);

		// To check if the UFO collided with the stars.
		for (int i = 0; i < rocket.getStars().size(); i++) {
			if (this.collidesWith(rocket.getStars().get(i))) {
				rocket.killUFO();
				this.die();
				rocket.getStars().get(i).disappear(); // To make the star vanish.
			}
		}
	}

	// Spawn at a random location.
	private void getSpawn() {
		Random r = new Random();
		this.x = GameStage.WINDOW_WIDTH - this.width;
		this.y = r.nextInt( (int)(GameStage.WINDOW_HEIGHT - (2 * Rocket.ROCKET_HEIGHT)) + (int)this.height );
	}

	/*
	 * -- Getters
	 * Below are some of the class's getters.
	 */

	// Get the UFO's life.
	public boolean getLife() {
		return this.isAlive;
	}

	// Get the UFO's damage.
	public int getDamage() {
		return this.damage;
	}

	/*
	 * -- Setters
	 * Below are some of the class's setters.
	 */
	// Initialize the UFO's speed.
	private void initSpeed() {
		Random r = new Random();
		this.speed = r.nextInt(6) + 1;
	}
}
