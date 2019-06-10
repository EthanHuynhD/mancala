
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Tester {
	public static void main(String[] args) {
		int pebbles = 0;
		String input = null;
		boolean valid = true;

		while (valid) {
			input = JOptionPane.showInputDialog("Please enter the number of pebbles (1-4)");
			try {
				pebbles = Integer.parseInt(input);
				if (pebbles < 1 || pebbles > 4) {
					JOptionPane.showMessageDialog(null, "Invalid range");
				} else {
					valid = false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid number input");
			}
		}

		Model model = new Model(pebbles);
		if (model.getTurn() % 2 == 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Player A gets to start first!");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Player B gets to start first!");
		}
		// model.setStyle(new Rectangular());
		MainFrame frame1 = new MainFrame(model);
		// model.attach(frame);

		frame1.setVisible(true);
		frame1.pack();
		// frame1.setSize(1000, 700);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
