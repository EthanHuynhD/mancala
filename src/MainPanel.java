

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;
import java.awt.geom.*;
import javax.swing.event.*;

public class MainPanel extends JPanel{
	private Model dataModel;


	//private ArrayList<Shape> stones;
	private int fontSize=20;
	public MainPanel(Model model) {
		//stones =  new ArrayList<>();
		this.dataModel = model;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(1250, 400));
		String n = "        B6               B5               B4              B3             B2              B1     ";
		InnerPanel innerPanel = new InnerPanel(dataModel); 

		add(innerPanel,BorderLayout.CENTER);
		
		JButton undo = new JButton("UNDO");
		undo.setPreferredSize(new Dimension(200,20));
		undo.setBackground(Color.BLACK);
	    undo.setForeground(Color.WHITE);

		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(model.getUndoCount()>=3)
					JOptionPane.showMessageDialog(new JFrame(), "You can only undo 3 times on every turn.");
				else
					model.undo();
				
				}}
			);
		add(undo,BorderLayout.SOUTH);
		//Controller
		JButton east = new JButton("Circular Style");
		//Context program written in terms of the interface
		east.addActionListener((e)->{dataModel.setStyle(new Circular());});
		JButton west = new JButton("Rectangular Style");
		west.addActionListener((e)->{dataModel.setStyle(new Rectangular());});
		add(BorderLayout.EAST,east);
		add(BorderLayout.WEST,west);
		
		dataModel.attach(innerPanel);
		JLabel north = new JLabel(n,SwingConstants.CENTER);
		north.setFont(new Font(n,Font.BOLD,fontSize));
		
		add(north,BorderLayout.NORTH);

		
	}
	@Override
	protected void paintComponent(Graphics grphcs) {
		Shape border=new RoundRectangle2D.Double(0, 0, 1250, 399, 50, 50);
		super.paintComponent(grphcs);
		Graphics2D g2 = (Graphics2D) grphcs;
		g2.draw(border);
	}
}