

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
public class Circular implements Style {
	@Override
	public Shape createShape(int x, int y, int width, int height, int arch1, int arch2) {
		Shape s = new Ellipse2D.Double(x, y, width, height);
		return s;
	}
}
