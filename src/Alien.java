import java.awt.Graphics;
import java.awt.Image;

public class Alien extends Sprite2D{
	public boolean isAlive = true; 
	private double xSpeed = 0;
	int framesDrawn =0;
	public Alien(Image i, Image i2, int windowWidth) {
		super(i,i2, windowWidth);
	}
	public double getSpeed() {
		return this.xSpeed;
	}
	public boolean move() {
		this.x+= this.xSpeed;
		if (x<=0 || x>=800-this.myImage1.getWidth(null)) {
			return true;
		}
		else return false;
	}
	public void setFleetXSpeed(double dx) {
		xSpeed = dx;
	}
	public void reverseDirection() {
			xSpeed = -xSpeed;
			y+=20;
	}
	public void jumpDownwards() {
		y+=20;
	}
	public void paint(Graphics g) {
		framesDrawn++;
		if (framesDrawn%100<50) {
			g.drawImage(myImage1, (int)x, (int)y, null);
		}
		else 
			g.drawImage(myImage2, (int)x, (int)y, null);
	}
}
