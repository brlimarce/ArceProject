/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Game Description ==
* beep is a rocket-shooting game. Its objective is to let
* Junnie's rocket survive a one-minute round from UFOs by
* blasting stars towards them. His rocket can also collect
* nebulae to gain 50 strength.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-10 20:56
*
***********************************************************/
package main;
import beep.game.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		// To force anti-aliasing on the fonts.
		System.setProperty("prism.lcdtext", "false");
	    System.setProperty("prism.subpixeltext", "false");

	    launch(args);
	}

	@Override
    public void start(Stage stage) {
       GameStage game = new GameStage();
       game.setStage(stage);
    }
}
