
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InnerPanel extends JPanel implements ChangeListener {
	private Model model;
	private ArrayList<Shape> pits = new ArrayList<Shape>();
	private ArrayList<Integer> cirX = new ArrayList<Integer>();
	private ArrayList<Integer> cirY = new ArrayList<Integer>();
	private Style style;
	private ArrayList<Integer> data;

	public InnerPanel(Model model) {
		this.model = model;
		this.data = this.model.getData();
		style = this.model.getStyle();
		setCir();
		// setSize(1000, 300);
		setPreferredSize(new Dimension(1000, 350));
		setLayout(new BorderLayout());
		drawShapes(style);
		// undo button
		String s = "      A1               A2               A3               A4             A5              A6     ";
		JLabel south = new JLabel(s, SwingConstants.CENTER);
		south.setFont(new Font(s, Font.BOLD, 20));
		add(south, BorderLayout.SOUTH);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				for (int i = 1; i < 13; i++) {
					if (pits.get(i).contains(e.getPoint())) {
						// check player's turn
						if (model.getTurn() % 2 != 0 && i < 7)
							JOptionPane.showMessageDialog(new JFrame(), "not player B turn");
						else if (model.getTurn() % 2 == 0 && i >= 7)
							JOptionPane.showMessageDialog(new JFrame(), "not player A turn");
						else
							model.update(i);
						if (!model.getWin().equals("N")) {

							JOptionPane.showConfirmDialog(null, model.getWin() + " !!!\nPress OK to quit.",
									"Congratulations!", JOptionPane.DEFAULT_OPTION);
							System.exit(0);

						}

					}
				}
			}
		});

	}

	public void drawShapes(Style style) {
		this.style = style;
		pits.add(this.style.createShape(20, 20, 100, 260, 50, 50));
		// pits.add(new RoundRectangle2D.Double(20, 20, 100, 260, 50, 50));
		for (int i = 0; i < 12; i++) {
			if (i < 6)
				pits.add(this.style.createShape((20 + (20 * i) + (100 * i)) + 125, 20, 100, 120, 0, 0));
			// pits.add(new Ellipse2D.Double((20 + (20 * i) + (100 * i))+125, 20, 100,
			// 120));
			else
				pits.add(this.style.createShape((20 + (20 * (i - 6)) + (100 * (i - 6))) + 125, 160, 100, 120, 0, 0));
			// pits.add(new Ellipse2D.Double((20 + (20 * (i - 6)) + (100 * (i - 6)))+125,
			// 160, 100, 120));
		}
		pits.add(this.style.createShape(860, 20, 100, 260, 50, 50));
		// pits.add(new RoundRectangle2D.Double(860, 20, 100, 260, 50, 50));
	}

	@Override
	protected void paintComponent(Graphics grphcs) {
		super.paintComponent(grphcs);
		Graphics2D g2 = (Graphics2D) grphcs;
		for (int i = 0; i < data.size(); i++) {
			g2.draw(pits.get(i));
			for (int x = 0; x < model.getStone(i); x++) // where check pit array in model to draw no. of stone
			{
				if (i < 7)
					g2.fillOval(20 + 20 * i + (100 * i) + cirX.get(x), 20 + cirY.get(x), 15, 15);
				else
					g2.fillOval((20 + (20 * (i - 7)) + (100 * (i - 7)) + cirX.get(x) + 125), 155 + cirY.get(x), 15, 15);
			}
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		this.data = model.getData();
		this.style = model.getStyle();
		pits = new ArrayList<>();
		drawShapes(style);
		repaint();
	}
	public void setCir() {
		cirX.add(50);
		cirX.add(35);
		cirX.add(35);
		cirX.add(65);
		cirX.add(65);
		cirX.add(50);
		cirX.add(50);
		cirX.add(20);
		cirX.add(20);
		cirX.add(20);
		cirX.add(20);
		cirX.add(35);
		cirX.add(65);
		cirX.add(35);
		cirX.add(65);
		cirX.add(80);
		cirX.add(5);
		cirX.add(5);
		cirX.add(78);
		cirX.add(47);

		cirY.add(50);
		cirY.add(35);
		cirY.add(65);
		cirY.add(65);
		cirY.add(35);
		cirY.add(25);
		cirY.add(75);
		cirY.add(50);
		cirY.add(20);
		cirY.add(75);
		cirY.add(75);
		cirY.add(90);
		cirY.add(90);
		cirY.add(10);
		cirY.add(10);
		cirY.add(50);
		cirY.add(65);
		cirY.add(35);
		cirY.add(75);
		cirY.add(100);
	}
}
