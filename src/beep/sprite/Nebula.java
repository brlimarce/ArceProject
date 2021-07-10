/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'Nebula' is a power-up, where it adds 50 strength to the
* rocket.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-10 23:08
*
***********************************************************/
package beep.sprite;
import javafx.scene.image.Image;

public class Nebula extends PowerUp {
	// -- Attributes
	private final static Image NEBULA_IMAGE = new Image(Sprite.IMAGE_DIR + "nebula.png");
	private final static int NEBULA_BOOST = 50;

	// -- Constructor
	public Nebula() {
		super(Nebula.NEBULA_IMAGE);
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Provide an additional 50 strength.
	@Override
	public void boost(Rocket rocket) {
		super.boost(rocket);
		rocket.boostStrength(Nebula.NEBULA_BOOST);
		System.out.printf(">> %s received %d strength! Strength: %d\n", rocket.getName(), Nebula.NEBULA_BOOST, rocket.getStrength());
	}
}
