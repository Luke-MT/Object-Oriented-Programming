package UPO;

public abstract class Elettrodomestico extends NonAttraversabile{
	
	private boolean rotto;
	
	public Elettrodomestico(int x, int y) {
		super(x,y);
		this.rotto = false;
	}
	
	public boolean isRotto() {
		return this.rotto;
	}
	
	public void rompi() {
		this.rotto = true;
	}
	public void aggiusta() {
		this.rotto = false;
	}

}
