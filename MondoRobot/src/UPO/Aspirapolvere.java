package UPO;

public class Aspirapolvere extends Casella{
	private int direction;
	
	public Aspirapolvere(int x, int y) {
		super(x,y);
		this.direction = 180;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public int nextPosX() {
		return super.getX()+(int) Math.cos(Math.toRadians(direction));
	}
	public int nextPosY() {
		return super.getY() +(int) Math.sin(Math.toRadians(direction));
	}
	
	public void turnLeft() {//90 180 270 360
		if(this.direction == 360)
			this.direction = 0;
		this.direction+=90;
	}
	public void turnRight() {//0 90 180 270
		if(this.direction == 0)
			this.direction = 360;
		this.direction-=90;
	}
	
	public void go() {
		this.setX(this.nextPosX());
		this.setY(this.nextPosY());
	}
	
	
}
