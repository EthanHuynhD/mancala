

import java.awt.Graphics2D;
import java.awt.Shape;

public interface Style{
	Shape createShape(int x,int y, int width, int height, int arch1, int arch2);
}
