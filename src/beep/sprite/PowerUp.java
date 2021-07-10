/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'PowerUp' serves as the parent class for all of the
* game's power-ups.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-10 23:08
*
***********************************************************/
package beep.sprite;
import java.util.Random;

import beep.game.GameStage;
import javafx.scene.image.Image;

abstract class PowerUp extends Sprite {
	// -- Attributes
	protected boolean isCollected;

	// -- Constructor
	PowerUp(Image image) {
		super(0, 0, image);
		this.isCollected = false;
		this.getSpawn();
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Provide a boost to the rocket if collected.
	protected void boost(Rocket rocket) {
		this.isCollected = true;
	}

	// Spawn at random locations.
	public void getSpawn() {
		Random r = new Random();
		this.x = r.nextInt((int)(GameStage.WINDOW_WIDTH / 2));
		this.y = r.nextInt((int)(GameStage.WINDOW_HEIGHT - (2 * Rocket.ROCKET_HEIGHT)) + (int)this.height);
	}

	// Let the power-up disappear.
	public void vanish() {
		this.disappear();
	}

	/*
	 * -- Getters
	 * Below are some of the class's methods.
	 */
	public boolean getCollectState() {
		return this.isCollected;
	}
}
