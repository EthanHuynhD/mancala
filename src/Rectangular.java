

import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

public class Rectangular implements Style {
	
	@Override
	public Shape createShape(int x, int y, int width, int height, int arch1, int arch2) {
		Shape s = new RoundRectangle2D.Double(x, y, width, height, arch1, arch2);
		return s;
	}
	
}
