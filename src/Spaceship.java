import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Spaceship extends Sprite2D{
	private static String workingDirectory;
	protected Image bulletImg;
	protected List bulletsList;
	public Spaceship(Image i, int windowWidth) {
		super(i, windowWidth);
		// TODO Auto-generated constructor stub
		workingDirectory = System.getProperty("user.dir");
		ImageIcon playerBullet = new ImageIcon(workingDirectory + "/bullet.png");
		bulletImg = playerBullet.getImage();
		bulletsList = new ArrayList();
	}
	
	public void setXSpeed(double dx) {
		xSpeed = dx;
	}
	public void move() {
		x+= xSpeed;
		if (x<=0) {
			x = 0;
			xSpeed = 0;
		}
		else if (x >= winWidth - myImage1.getWidth(null)) {
			x = winWidth - myImage1.getWidth(null);
			xSpeed = 0;
		}
	
	}
	public Image getBullet() {
		return this.bulletImg;
	}
	public PlayerBullet shootBullet() {
		PlayerBullet b = new PlayerBullet(bulletImg, 600);
		b.setPosition(this.x+54/2, this.y);
		bulletsList.add(b);
		return b;
	}

}
