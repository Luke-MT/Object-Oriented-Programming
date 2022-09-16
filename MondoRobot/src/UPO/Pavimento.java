package UPO;

public class Pavimento extends Attraversabile{
	
	private boolean isBagnato;
	
	public Pavimento(int x, int y) {
		super(x, y);
		this.isBagnato = false;
	}
	
	public boolean isBagnato() {
		return this.isBagnato;
	}
	public void setBagnato(boolean b) {
		this.isBagnato = b;
	}
	

}
