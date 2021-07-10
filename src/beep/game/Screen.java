/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'Screen' serves as the parent class for the game's
* other screens.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-14 09:28
*
***********************************************************/
package beep.game;
import javafx.scene.Group;
import javafx.scene.Scene;

abstract class Screen {
	// -- Attributes
	protected GameStage stage;
	protected Scene scene;
	protected Group root;
	protected String bgPath;

	// -- Constructor
	Screen(GameStage stage, String bgPath) {
		this.stage = stage;
		this.bgPath = bgPath;
		this.init(); // To display the screen.
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// To initialize the screen.
	abstract protected void init();

	/*
	 * -- Getters
	 * Below are some of the class's getters.
	 */
	Scene getScene() {
		return this.scene;
	}
}