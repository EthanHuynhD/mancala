

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
/**
 * Strategy that is used by the application when user selects to change style of board.
 * This strategy creates a Circular Shape.
 * @author Tripod - Ethan Huynh, Raza Ahmad, Ching Tsoi
 */
public class Circular implements Style {
	/**
	 * Creates circular Shape when this method is called.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arch1
	 * @param arch2
	 * @return shape
	 * @return Shape
	 */
	@Override
	public Shape createShape(int x, int y, int width, int height, int arch1, int arch2) {
		Shape s = new Ellipse2D.Double(x, y, width, height);
		return s;
	}
}
