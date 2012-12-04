import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

//bug: food can appear under the snake

@SuppressWarnings("serial")
public class SnakePanel extends JPanel implements ActionListener, KeyListener{
	private int WIDTH = 200; //WIDTH and HEIGHT must be evenly divisible by blockSize
	private int HEIGHT = 200;
	private int speed = 100;
	List<int[]> snakePos = new ArrayList<int[]>();
	private int[] foodPos = new int[2];
	int snakeDir;
	int blockSize = 10;
	Random rand = new Random();
	Timer timer;
	
	private int points;
	private JLabel pointsPanel;
	
	public SnakePanel(JLabel label){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(this);
		timer = new Timer(speed, this);
		timer.setInitialDelay(speed);
		timer.start();
		int[] initialPosition = {WIDTH / 2, HEIGHT / 2};
		snakePos.add(initialPosition);
		foodPos[0] = rand.nextInt(WIDTH / blockSize) * blockSize;
		foodPos[1] = rand.nextInt(HEIGHT / blockSize) * blockSize;
		snakeDir = 3;
		points = 0;
		pointsPanel = label;
		pointsPanel.setText("Points: " + points);
	}
	
	private boolean gameOver(){
		int[] headPos = snakePos.get(0);
		if(headPos[0] < 0 || headPos[0] > WIDTH - blockSize){
			return true;
		}
		if(headPos[1] < 0 || headPos[1] > HEIGHT - blockSize){
			return true;
		}
		for(int i = 1; i < snakePos.size(); i++){
			int[] blockPos = snakePos.get(i);
			if(headPos[0] == blockPos[0] && headPos[1] == blockPos[1]){
				return true;
			}
		}
		return false;
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		g.fillRect(foodPos[0], foodPos[1], blockSize, blockSize);
		g.setColor(Color.BLACK);
		for(int[] block : snakePos){
			g.fillRect(block[0], block[1], blockSize, blockSize);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(gameOver()){
			snakePos.clear();
			int[] initialPosition = {WIDTH / 2, HEIGHT / 2};
			snakePos.add(initialPosition);
			foodPos[0] = rand.nextInt(WIDTH / blockSize) * blockSize;
			foodPos[1] = rand.nextInt(HEIGHT / blockSize) * blockSize;
			snakeDir = 3;
			points = 0;
			pointsPanel.setText("Points: " + points);
		}else{
			int[] headPos = snakePos.get(0);
			int[] newHead = new int[2];
			newHead[0] = headPos[0];
			newHead[1] = headPos[1];
			switch(snakeDir){
			case 1:	newHead[1] -= blockSize;
					break;
			case 2:	newHead[1] += blockSize;
					break;
			case 3: newHead[0] -= blockSize;
					break;
			case 4:	newHead[0] += blockSize;
					break;
			}
			snakePos.add(0, newHead);
			if(foodPos[0] == headPos[0] && foodPos[1] == headPos[1]){
				foodPos[0] = rand.nextInt(WIDTH / blockSize) * blockSize;
				foodPos[1] = rand.nextInt(HEIGHT / blockSize) * blockSize;
				points += 100;
				pointsPanel.setText("Points: " + points);
			}else{
				snakePos.remove(snakePos.size() - 1);
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode == KeyEvent.VK_UP && snakeDir != 2){
			snakeDir = 1;
		}else if(keyCode == KeyEvent.VK_DOWN && snakeDir != 1){
			snakeDir = 2;
		}else if(keyCode == KeyEvent.VK_LEFT && snakeDir != 4){
			snakeDir = 3;
		}else if(keyCode == KeyEvent.VK_RIGHT && snakeDir != 3){
			snakeDir = 4;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//unused
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//unused
	}
}
