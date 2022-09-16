package UPO;

public abstract class Casella {
	protected int x;
	protected int y;
	
	public Casella(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void getPos(int[] pos) {
		pos[0] = this.x;
		pos[1] = this.y;
	}
}
