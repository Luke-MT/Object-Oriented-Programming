package UPO;

public class MainClass {
	
	/**
	 * 
	 * @author Luca Arborio (20038688)
	 *
	 */
	private final static int[][] map = 
				{{1,1,1,1,1,1,1,1,1,1},
				 {1,0,6,4,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,3,0,0,0,1},
				 {1,0,0,0,0,0,1,0,0,1},
				 {1,0,0,0,0,0,1,0,0,1},
				 {1,0,0,0,0,0,1,0,0,1},
				 {1,8,0,0,0,0,1,0,4,1},
				 {1,0,0,0,0,4,1,0,2,1},
				 {1,1,1,1,1,1,1,1,1,1},
				};
	
	

	public static void main(String[] args) {
		
		Casa c = new Casa(map);
		CasaMainView v = new CasaMainView(map);
		new CasaController(c,v);

	}

}
