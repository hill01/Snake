import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Snake {

	public static JLabel pointsPanel = new JLabel();
	public static SnakePanel snake = new SnakePanel(pointsPanel);
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(snake, BorderLayout.PAGE_END);
		frame.add(pointsPanel, BorderLayout.LINE_START);
		frame.pack();
		frame.requestFocusInWindow();
		frame.setVisible(true);
	}
}
