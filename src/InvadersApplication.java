import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.*;
public class InvadersApplication extends JFrame implements Runnable, KeyListener{
	private static final Dimension WindowSize = new Dimension(800,600);
	private static boolean isGraphicsInitialised = false;
	private static final int NUMALIENS = 30;
	private Alien [] AlienArray = new Alien[NUMALIENS];
	private Spaceship PlayerShip;
	private PlayerBullet bullet;
	private static String workingDirectory;
	private BufferStrategy strategy;
	private Image bulletImg;
	private ImageIcon alien1, alien2, playerShip, playerBullet;
	private ArrayList bulletList = new ArrayList();
	private double h1, h2, h3, w1, w2, w3;
	private boolean isGameInProgress=false;
	private int score=0;
	
	//constructor
	public InvadersApplication() {
		workingDirectory = System.getProperty("user.dir");
		System.out.println("Working Directory = " + workingDirectory);
		this.setTitle("Invader application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		alien1 = new ImageIcon(workingDirectory + "/alien_ship_1.png");
		alien2 = new ImageIcon(workingDirectory + "/alien_ship_2.png");
		playerShip = new ImageIcon(workingDirectory + "/player_ship.png");
		playerBullet = new ImageIcon(workingDirectory + "/bullet.png");
		
		bulletImg = playerBullet.getImage();
		
		w2 = bulletImg.getWidth(null);
		h2 = bulletImg.getHeight(null);
		
		w1 = alien1.getImage().getWidth(null);
		h1 = alien1.getImage().getHeight(null);
		
		w3 = playerShip.getImage().getWidth(null);
		h3 = playerShip.getImage().getHeight(null);
		
		//create and initialize some aliens
		startNewGame();
		
		// Display the window, centered on the screen
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width/2 - WindowSize.width/2;
		int y = screensize.height/2 - WindowSize.height/2;
		setBounds(x,y, WindowSize.width, WindowSize.height);
		setVisible(true);
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		Thread t = new Thread(this);
		t.start();
		addKeyListener(this);
		
		isGraphicsInitialised = true;
	}
	
	public void run() {
		double x0, x1, x2, x3, y0, y1, y2, y3;
		
		isGameInProgress = true;
		while (1==1) {
			try {
				Thread.sleep(20);
			}
			catch (InterruptedException e) {  
			}
			
			boolean  moveAlien = false;
			
			if (isGameInProgress) {
				for(int i = 0; i< NUMALIENS; i++) {
					moveAlien = AlienArray[i].move();
				}
				if(moveAlien == true) {
					for(int i = 0; i<NUMALIENS;i++) {
						AlienArray[i].reverseDirection();
					}
				}
				
				PlayerShip.movePlayer();
				
				x3 = PlayerShip.x;
				y3 = PlayerShip.y;
				
				for (int i = 0; i< NUMALIENS; i++) {
					if (AlienArray[i].isAlive) {
						x0 = AlienArray[i].x;
						y0 = AlienArray[i].y;
						
					if (((x0<x3 && x0+w1>x3) || (x3<x0 && x3+w3 >x0)) 
								&& ((y0 <y3 && y0+ h1 > y3) || (y3<y0 && y3+h3>y0))) {
							isGameInProgress = false;
						}
					}
				}
				
				Iterator iterator = bulletList.iterator();
				
				while (iterator.hasNext()) {
					PlayerBullet b = (PlayerBullet) iterator.next();
					
					if (b.move() == true) {
						iterator.remove();
					}
					else
					{
						x2 = b.x;
						y2 = b.y;
						for (int i = 0; i< NUMALIENS; i++) {
							if (AlienArray[i].isAlive) {
								x1 = AlienArray[i].x;
								y1 = AlienArray[i].y;
								
								// Collision detection
								if (((x1<x2 && x1+w1>x2) || (x2<x1 && x2+w2 >x1)) 
										&& ((y1 <y2 && y1+ h1 > y2) || (y2<y1 && y2+h2>y1))) {
									score++;
									AlienArray[i].isAlive = false;
									//iterator.remove();
								}
							}	
						}
						
						if (allAlienKilled()) {
							startNewWave(Math.random()*10+4);
						}
					}
				}
				this.repaint();
			}
		}
	}
	
	public void startNewGame() {
		score = 0;
		isGameInProgress = true;
		startNewWave(4);
		PlayerShip = new Spaceship(playerShip.getImage(),600);
		PlayerShip.setPosition(300, 530);
	}
	public void startNewWave(double speed) {
		for(int i = 0; i< NUMALIENS; i++) {
			double xx = (i/5)*80 +70;
			double yy = (i%5)*40 +50;
			AlienArray[i] = new Alien (alien1.getImage(), alien2.getImage(), 300);
			AlienArray[i].setPosition(xx, yy);
			AlienArray[i].setFleetXSpeed(speed);
		}	
	}
	public boolean allAlienKilled() {
		for (int i = 0; i< NUMALIENS; i++) {
			if (AlienArray[i].isAlive) {
				return false;
			}
		}
		return true;
	}
	public void keyPressed(KeyEvent e) {
		if (isGameInProgress) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				PlayerShip.setXSpeed(-4);
			}
			else if (e.getKeyCode()== KeyEvent.VK_RIGHT) {
				PlayerShip.setXSpeed(4);
			}	
			else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				bulletList.add(PlayerShip.shootBullet());
			}
		}
		else {
			startNewGame();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_ENTER )
			PlayerShip.setXSpeed(0);
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void paint(Graphics g) {
		if (!isGraphicsInitialised) return;
		g = strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);
		String result = "Score: "+score;
		if (!isGameInProgress) {
			String gameOver = "Game Over";			
			g.setColor(Color.WHITE);
			g.setFont(new Font("SansSerif", Font.BOLD, 52));
			g.drawString(gameOver, 270, 120);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("SansSerif", Font.PLAIN, 22));
			g.drawString("Score: "+score , 320, 200);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("SansSerif", Font.PLAIN, 22));
			g.drawString("Press any key to restart" , 280, 230);
		}
		else {
			g.setColor(Color.WHITE);
			g.setFont(new Font("SansSerif", Font.BOLD, 16));
			g.drawString(result, 300, 50);
			
			Iterator iterator = PlayerShip.bulletsList.iterator();
			while (iterator.hasNext()) {
				PlayerBullet b = (PlayerBullet) iterator.next();
				b.paint(g);
			}
			
			for(int i = 0; i< NUMALIENS; i++) {
				if(AlienArray[i].isAlive)
					AlienArray[i].paint(g);
			}
			PlayerShip.paint(g);	
		}
		g.dispose();
		strategy.show();
	}
	
	public static void main (String [] arg) {
		InvadersApplication i = new InvadersApplication();
	}
}
