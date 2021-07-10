/***********************************************************
*
* == beep ==
* 'Blast or be blasted!'
* ----------------------------------------------------------
* == Class Description ==
* 'Sprite' serves as the parent class for all of the game's
* assets, such as the power-ups, characters, and stars.
*
* @author Bianca Raianne L. Arce
* @created_date 2021-06-10 22:38
*
***********************************************************/
package beep.sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
	// -- Attributes
	protected Image image;
	protected double x, y; // For the sprite's coordinates.
	protected int dx, dy; // For the character's vert. and horiz. distances.
	protected boolean isVisible;
	protected double width, height; // For the sprite's size dimensions.

	public final static String IMAGE_DIR = "assets/image/edited/";

	// -- Constructor
	Sprite(int x, int y, Image image) {
		// To set the sprite's coordinates.
		this.x = x;
		this.y = y;

		this.load(image); // To load the image.
		this.isVisible = true;
	}

	/*
	 * -- Methods
	 * Below are the class's methods.
	 */

	// Load the sprite.
	protected void load(Image image) {
		try {
			this.image = image; // To set the sprite's image.
			this.setSize(); // To set the sprite's dimensions.
		} catch (Exception e) { e.printStackTrace(); }
	}

	// Render the sprite.
	public void render(GraphicsContext gc) {
		gc.drawImage(this.image, this.x, this.y);
	}

	// Set the sprite's dimensions.
	private void setSize() {
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}

	// Check the collision between two sprites.
	public boolean collidesWith(Sprite sprite) {
		// To get the bounds of the two colliding sprites.
		Rectangle2D collider = this.getBounds();
		Rectangle2D collided = sprite.getBounds();

		return collider.intersects(collided);
	}

	// Make the sprite disappear.
	protected void disappear() {
		this.isVisible = false;
	}

	/*
	 * -- Getters
	 * Below are some of this class's getters.
	 */

	// Get the sprite's bounds.
	private Rectangle2D getBounds() {
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}

	// Get the sprite's image.
	Image getImage() {
		return this.image;
	}

	/*
	 * -- Coordinates
	 * Get the sprite's x and y coordinates.
	 */

	// Get the sprite's x-coordinate.
	public double getX() {
		return this.x;
	}

	// Get the sprite's y-coordinate.
	public double getY() {
		return this.y;
	}

	/*
	 * -- Distances
	 * Get the sprite's dx and dy (distances).
	 */

	// Get the sprite's dx.
	public int getDX() {
		return this.dx;
	}

	// Get the sprite's dy.
	public int getDY() {
		return this.dy;
	}

	// Get the sprite's visibility.
	public boolean getVisibility() {
		return this.isVisible;
	}

	/*
	 * -- Dimensions
	 * Get the sprite's dimensions (width and height).
	 */

	// Get the sprite's width.
	public double getWidth() {
		return this.width;
	}

	// Get the sprite's height.
	public double getHeight() {
		return this.height;
	}

	/*
	 * -- Setters
	 * Below are some of this class's setters.
	 */

	// Set the sprite's dx.
	public void setDX(int dx) {
		this.dx = dx;
	}

	// Set the sprite's dy.
	public void setDY(int dy) {
		this.dy = dy;
	}
}