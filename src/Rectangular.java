

import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
/**
 * Strategy that is used by the application when user selects to change style of board.
 * This strategy creates a Rectangular Shape.
 * @author Tripod - Ethan Huynh, Raza Ahmad, Ching Tsoi
 */
public class Rectangular implements Style {
	/**
	 * Creates Rectangular Shape when this method is called.
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
		Shape s = new RoundRectangle2D.Double(x, y, width, height, arch1, arch2);
		return s;
	}
	
}
