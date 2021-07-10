/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'Star' is the bullet in the game. This is blasted
* towards UFOs to kill them.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-11 00:12
*
***********************************************************/
package beep.sprite;
import beep.game.GameStage;
import javafx.scene.image.Image;

public class Star extends Sprite {
	// -- Attributes
	private final static Image STAR_IMAGE = new Image(Sprite.IMAGE_DIR + "star.png");
	private final static int STAR_SPEED = 10; // To determine the star's speed.

	// -- Constructor
	public Star(int x, int y) {
		super(x, y, Star.STAR_IMAGE);
		this.dx = Star.STAR_SPEED; // To set the star's horizontal distance.
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */
	public void move() {
		this.x += this.dx; // To move the star.

		// If the star reaches the end of the screen, disappear.
		if (this.x >= (GameStage.WINDOW_WIDTH - this.width))
			this.disappear();
	}
}
