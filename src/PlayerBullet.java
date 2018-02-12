import java.awt.Image;

public class PlayerBullet extends Sprite2D{

	public PlayerBullet(Image i, int windowWidth) {
		super(i, windowWidth);
	}
	
	public boolean move() {
		y -= 4;
		if (y < 0) return true;
		else return false;
	}

}