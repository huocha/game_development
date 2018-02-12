import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Sprite2D extends JFrame{
	protected double x,y;
	protected double xSpeed = 0;
	protected Image myImage1, myImage2;
	int winWidth;
	
	public Sprite2D(Image i, int windowWidth) {
		myImage1 = i;
		x = Math.random()*600;
		y = Math.random()*600;	
	}
	public Sprite2D(Image i, Image i2, int windowWidth) {
		myImage1 = i;
		myImage2 = i2;
		x = Math.random()*600;
		y = Math.random()*600;	
	}
	
	public void setPosition(double xx, double yy) {
		x=xx;
		y=yy;
	}
	
	public void movePlayer() {
		x+= xSpeed;
	}
	
	public void setXSpeed(double dx) {
		xSpeed = dx;
	}
	
	public void paint(Graphics g) {
		g.drawImage(myImage1, (int)x, (int)y, null);
	}
}
