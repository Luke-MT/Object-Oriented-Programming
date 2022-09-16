package UPO;

public class AnimaleDomestico extends NonAttraversabile{
	
	public AnimaleDomestico(int x, int y) {
		super(x,y);
	}
	
	public void go(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

}
