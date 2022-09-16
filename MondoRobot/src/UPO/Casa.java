package UPO;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author Luca Arborio (20038688)
 *
 */
public class Casa {
	
	/**
	 * VARIABILI
	 */
	
	private Casella[][] casa;
	private int[][]posLavatrici;	
	private int[][]posRubinetti;	
	private int[][]posFornelli;	
	
	private Aspirapolvere robot;
	private AnimaleDomestico cane;
	
	private PropertyChangeSupport support;
	
	private int[][] map_n;

	private int len;
	
	private Timer timer;
	private int seconds = 0;
	
	/**
	 * COSTRUTTORE
	 */
	public Casa(int[][] map) {
		super();
		if(map == null) throw new NullPointerException("Map nullo");
		
		len = map.length;
		for(int i = 0; i < len; i++)
			if(len != map[i].length) throw new IllegalArgumentException("La casa deve essere di dimensione nxn");
		map_n = map;
		
		this.support = new PropertyChangeSupport(this);
		this.timer = new Timer();
			
		
		
		
		int countlavatrici = contaTipoCasella(map,2);
		posLavatrici = new int[countlavatrici][2];
		countlavatrici = 0;
		
		int countrubinetti = contaTipoCasella(map,4);
		posRubinetti = new int[countrubinetti][2];
		countrubinetti = 0;
		
		int countfornelli = contaTipoCasella(map,6);
		posFornelli = new int[countfornelli][2];
		countfornelli = 0;
		
		this.casa = new Casella[len][len];
		
		for(int i = 0;i<len;i++)
			for(int j = 0;j<len;j++) {
				if(map[i][j] == 0)
					this.casa[i][j] = new Pavimento(i,j);
				else if(map[i][j] == 1)
					this.casa[i][j] = new Muro(i,j);
				else if(map[i][j] == 2) {
					this.casa[i][j] = new Lavatrice(i,j);
					this.casa[i][j].getPos(posLavatrici[countlavatrici]);
					countlavatrici++;
				}
				else if(map[i][j] == 4) {
					this.casa[i][j] = new Rubinetto(i,j);
					this.casa[i][j].getPos(posRubinetti[countrubinetti]);
					countrubinetti++;
				}
				else if(map[i][j] == 6) {
					this.casa[i][j] = new Fornelli(i,j);
					this.casa[i][j].getPos(posFornelli[countfornelli]);
					countfornelli++;
				}	
				else if(map[i][j] == 8) {
					this.robot = new Aspirapolvere(i,j); 
					this.casa[i][j] = new Pavimento(i,j);
				}
				else if(map[i][j] == 3) {
					this.cane = new AnimaleDomestico(i,j);
					this.casa[i][j] = new Pavimento(i,j);
				}
			}		
	}

	/**
	 * METODI
	 */
	
	//GET & SET
	private Casella getCasella(int x, int y){
		return casa[x][y];		
	}
	
	public boolean isCasellaAttraversabile(int x, int y) {
		if(this.getCasella(x, y) instanceof Attraversabile)
			return true;
		
		return false;
	}
	
	public boolean isCasellaNonAttraversabile(int x, int y) {
		if(this.getCasella(x, y) instanceof NonAttraversabile)
			return true;
		
		return false;
	}
	
	
	public int[] getRobotPos() {
		int [] pos = {this.robot.getX(), this.robot.getY()};
		return pos;		
	}
	
	public int[] getRobotNextPos() {
		int [] pos = {this.robot.nextPosX(), this.robot.nextPosY()};
		return pos;		
	}
	
	public int getRobotDir() {
		return this.robot.getDirection();
	}
	
	public int[] getCanePos() {
		int [] pos = {this.cane.getX(), this.cane.getY()};
		return pos;		
	}
	
	public int getSeconds() {
		return seconds;
	}

	private void IncrementSeconds() {
		this.seconds++;
	}
		
	//FUNZIONI CASA
	/**
	 * Crea una stringa che rappresenta la matrice di casa tramite numeri e caratteri.
	 * @return text
	 */
	public String toStringCasa() {
		String text = ""; 
		for(Casella[] row : casa) {
			for(Casella el : row) {
				if(el instanceof Muro)
					text+="1";
				else if(el instanceof Lavatrice)
					text+="2";
				else if(el instanceof Rubinetto)
					text+="4";
				else if(el instanceof Aspirapolvere)
					text+="8";
				else if(el instanceof Fornelli)
					if(((Fornelli) el).isRotto() == true)
						text+="9";
					else	
						text+="6";
				else if(el instanceof Pavimento) 
					if(((Pavimento) el).isBagnato() == true)
						text+="X";
					else	
						text+="0";	
				else if(el instanceof AnimaleDomestico)
					text+="3";
			}
			text+="\n";
		}	
		return text;
	}
	
	private int contaTipoCasella(int[][] mat, int nType) {
		int count = 0;
		for(int[] r : mat)
			for(int el : r)
				if(el == nType)
					count++;
		return count;				
	}
	
	private int[][] concatenateArray(int [][]a1, int [][]a2){
		int len = a1.length + a2.length;
		int [][] posTot = new int[len][2];
		for(int i = 0; i<len; i++) {
			for(int[]r : a1) {
				posTot[i][0] = r[0];
				posTot[i][1] = r[1];
				i++;
			}
			for(int[]r : a2) {
				posTot[i][0] = r[0];
				posTot[i][1] = r[1];
				i++;
			}
		}
		return posTot;
	}
	
	private void rompiAll(int [][] posCaselle) {
		for(int[] r : posCaselle) {
			Elettrodomestico el = ((Elettrodomestico) casa[r[0]][r[1]]);
			if(el.isRotto() == false)
				el.rompi();
		}
		this.support.firePropertyChange("rompi", null, posCaselle);
	}
	
	public void startTimer() {
		this.timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				IncrementSeconds();
				controlSeconds();
			}
			
		}, 1000, 1000);	
	}
	/**
	 * Aumenta di una casella le chiazze d'acqua collegate ad un elettrodomestico rotto.
	 * L'acqua aumenta dove possibile nei 4 punti cardinali di ogni casella già bagnata.
	 */
		
	private void aumenta_bagnato() {			
		//scorro tutte le posizioni del tipo delle caselle presenti nei vettori
		int [][] posTot = this.concatenateArray(posLavatrici, posRubinetti);
		for(int[] r : posTot) { 
			//l'acqua aumenta solo se l'elettrodomestico è rotto
			if(((Elettrodomestico)casa[r[0]][r[1]]).isRotto() == false)
				continue;
			for(int i = -1; i<2; i++) 
				for(int j = -1; j<2; j++) {
					if(i+j!=1 && i+j!=-1) continue;//bagna solo le caselle adiacenti nei 4 punti cardinali					
					
						int posx = r[0]+i; 
						int posy = r[1]+j;
						//controllo che posx e posy non vadano fuori dalle dimensioni della matrice n x n
						if(0<=posx && posx<len && 0<=posy && posy<len) {
							int pos = map_n[posx][posy];
							
							//bagno le caselle nei 4 punti cardinali dove è presente un pavimento asciutto
							if(pos == 0)
								map_n[posx][posy] = -2;
							//se la casella è già bagnata invoco il metodo aumenta_acqua
							if(pos == -1)
								aumenta_acqua(posx,posy);
						}
					}
				}
		//ora che la lettura del map_n è terminata posso segnare tutte le caselle bagnate come -1
		//e bagnare le caselle di tipo pavimento
		for(int x=0; x<len; x++) 
			for(int y=0; y<len; y++) 	
				if(map_n[x][y] == -2) {
					map_n[x][y] = -1;		
					((Pavimento)this.getCasella(x, y)).setBagnato(true);					
				}		
	}
	
	private void aumenta_acqua(int posX, int posY) {
		for(int i = -1; i<2; i++) 
			for(int j = -1; j<2; j++) {
				if(i+j!=1 && i+j!=-1) continue;//bagna solo le caselle adiacenti nei 4 punti cardinali
				int posx = posX+i; 
				int posy = posY+j;
				//controllo che posx e posy non vadano fuori dalle dimensioni della matrice n x n
				if(0<=posx && posx<len && 0<=posy && posy<len) {
					int pos = map_n[posx][posy];
					if(pos != 0 && pos != -1) continue;
					//bagno le caselle e le segno con -2 la posto di -1 per evitare che il metodo ricorsivo ritornando 
					//sui suoi passi estenda l'acqua in caselle che sono appena state bagnate 
					if(pos == 0) {
						map_n[posx][posy] = -2;
					}
					if(pos == -1) {
						map_n[posx][posy] = -2;
						aumenta_acqua(posx,posy);
					}
				}				
			}	
	}
	
	/**
	 * Ogni movimento del robot fa partire un turno e successivamente il cane si muove di una casella.
	 * @param action: determina che azione compirà il robot.
	 */
	
	public void turno(Action action) {
		
		if(action == Action.GO)
			this.goRobot();
		else if(action == Action.LEFT)
			this.turnLeft();
		else if(action == Action.RIGHT)
			this.turnRight();
		
		this.goCane();
		this.support.firePropertyChange("Update", -1, map_n);
		
	}
	/**
	 * Il Robot si muove di una casella nella direzione che sta puntando.
	 * Può spostari solo su altre caselle attraversabili.
	 */
	private void goRobot() throws ArrayIndexOutOfBoundsException{
		Aspirapolvere rob = this.robot;
		AnimaleDomestico cane = this.cane;
		if(rob.nextPosX()<len && 0<=rob.nextPosX() && rob.nextPosY()<len && 0<=rob.nextPosY()) {//se è compreso nella matrice
			
			Casella nextCasella = this.getCasella(rob.nextPosX(),rob.nextPosY());
			//se la casella è attraversabile e il cane non si trova lì
			if(nextCasella instanceof Attraversabile && (cane.getX() != rob.nextPosX() || cane.getY() != rob.nextPosY())) {//se è davanti ad un pavimento
				
			    //pulisce la casella
				if(nextCasella instanceof Pavimento && ((Pavimento) nextCasella).isBagnato() == true) 
					((Pavimento) nextCasella).setBagnato(false);
				
				map_n[rob.getX()][rob.getY()] = 0;
				rob.go();
				map_n[rob.getX()][rob.getY()] = 8;	
				
				int [] pos = {rob.getX(), rob.getY()};
				this.support.firePropertyChange("go", null, pos);							
				}
			else
				this.support.firePropertyChange("muro", null, null);
		}
		else//se dovesse andare fuori dai bordi	
			throw new ArrayIndexOutOfBoundsException("Robot fuori dall mappa");
			
		
	}
	/**
	 * il cane si muove di una casella intorno a lui oppure resta fermo,
	 * la casella viene scelta in modo randomico, può spostarsi solo su altre caselle attraversabili.
	 * 
	 */
	private void goCane() {
		Random rand = new Random();
		
		int nextX;
		int nextY;
		
		Casella nextCasella;
		Aspirapolvere rob = this.robot;
		AnimaleDomestico cane = this.cane;
		
		//cerca una posizione possibile (intorno a lui o rimane fermo)
		do {
			nextX = cane.getX() + rand.nextInt(3)-1;
			nextY = cane.getY() + rand.nextInt(3)-1;
			nextCasella = this.getCasella(nextX, nextY);
			
		}while(!((nextX<len && 0<=nextX && nextY<len && 0<=nextY) && 
				  nextCasella instanceof Attraversabile &&
				  (rob.getX() != nextX || rob.getY() != nextY))
			  );
		//se la casella su cui è posizionato il cane era bagnata la reimposto bagnata in map_n
		//quando il cane si sposta
		if(this.getCasella(cane.getX(), cane.getY()) instanceof Pavimento)
			if(((Pavimento) this.getCasella(cane.getX(), cane.getY())).isBagnato() == true)
				map_n[cane.getX()][cane.getY()] = -1;
			else
				if(map_n[cane.getX()][cane.getY()] != 8)
					map_n[cane.getX()][cane.getY()] = 0;

		cane.go(nextX, nextY);
		map_n[cane.getX()][cane.getY()] = 3;
				
	}
	/**
	 * attiva le varie funzioni a seconda dei secondi passati:
	 * ogni 10s aumenta il bagnato
	 * ogni 15s si rompono le lavatrici
	 * ogni 20s si rompono i rubinetti
	 * ogni 50s si rompono i fornelli
	 */
	private void controlSeconds() {
		if(this.seconds % 15 == 0)
			this.rompiAll(posLavatrici);
		if(this.seconds % 10 == 0) 
			this.aumenta_bagnato();
		if(this.seconds % 20 == 0)
			this.rompiAll(posRubinetti);
		if(this.seconds % 50 == 0)
			this.rompiAll(posFornelli);
		
		
		this.support.firePropertyChange("Update", -1, map_n);
		this.support.firePropertyChange("second",null, this.seconds);
	}
	
	private void turnLeft() {
		this.robot.turnLeft();
		this.support.firePropertyChange("turnLeft", null, this.robot.getDirection());	
		
	}
	private void turnRight() {
		this.robot.turnRight();
		this.support.firePropertyChange("turnRight", null, this.robot.getDirection());	
	}
	
	/**
	 * Il robot può interagire con gli elettrodomestici che si trovano davanti a lui,
	 * aggiusta l'elettrodomestico se è rotto.
	 * @return true se l'elettrodomestico viene aggiustato, false se l'elettrodomestico non è rotto 
	 * 		   oppure se la casella davanti al robot non è un elettrodomestico
	 */
	
	public boolean interact() {

		int x = this.robot.nextPosX();
		int y = this.robot.nextPosY();
		Casella cas = this.getCasella(x, y);
		if(cas instanceof Elettrodomestico) 
			if(((Elettrodomestico) cas).isRotto() == true){
				((Elettrodomestico) cas).aggiusta();	
				int [] pos = {x,y};
				this.support.firePropertyChange("Interact", null, pos);
				return true;
			}
		return false;
	}
	
	
	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
	}

}
