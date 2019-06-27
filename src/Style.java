

import java.awt.Graphics2D;
import java.awt.Shape;
/**
 * Style interface is the interface of the Strategy pattern.
 * @author Tripod - Ethan Huynh, Raza Ahmad, Ching Tsoi
 */
public interface Style{
	/**
	 * Create shape of the board depending on the metrics. This method is to 
	 * be overriden by the strategies.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arch1
	 * @param arch2
	 * @return shape
	 */
	Shape createShape(int x,int y, int width, int height, int arch1, int arch2);
}
