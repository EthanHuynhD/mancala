


import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {
	private Model model;
	private JLabel west;
	private JLabel east;
	private JLabel south;
	private JLabel north;

	public MainFrame(Model model) {
		this.model=model;
		west = new JLabel(vertStr("MACANLA B"));
		east = new JLabel(vertStr("MACANLA A"));
		north = new JLabel("<- Player B", SwingConstants.CENTER);
		south = new JLabel("Player A ->", SwingConstants.CENTER);
		west.setFont(new Font("Courier", Font.BOLD, 20));
		south.setFont(new Font("Courier", Font.BOLD, 20));
		east.setFont(new Font("Courier", Font.BOLD, 20));
		north.setFont(new Font("Courier", Font.BOLD, 20));
		setLayout(new BorderLayout(30,30));
		MainPanel mainPanel = new MainPanel(this.model);
		add(mainPanel, BorderLayout.CENTER);
		
		add(south, BorderLayout.SOUTH);
		add(north, BorderLayout.NORTH);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
	}

	public String vertStr(String s) {
		String[] in = s.split("");
		String out = "<html>";
		String br = "<br>";
		for (String a : in) {
			out += a + br;
		}
		out += "</html>";
		return out;
	}

}
