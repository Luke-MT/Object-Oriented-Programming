package UPO;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.awt.BorderLayout;


/**
 * 
 * @author Luca Arborio (20038688)
 *
 */
public class CasaMainView extends JFrame implements PropertyChangeListener
{

	private static final long serialVersionUID = 1L;
	
	private int N;
	private final int SIZE = 75;
	
	private int[] robotPos = new int[2];
	
	private boolean kernel;
	
	private JRadioButton rb1;
	private JRadioButton rb2;
	private ButtonGroup G;
	private JButton ok;
	private JLabel l1;
	
	private JButton go;
  	private JButton turnLeft;
  	private JButton turnRight;
  	private JButton interact;
  	private JButton no1;
  	private JButton no2;
  	
  	private JLabel text1;
  	private JLabel text2;
  	
  	private JPanel grigliaCasa;
  	private JPanel panel;
  	private JPanel panelLabels;
  	private JPanel panelBottoni;
  	
  	private ChessButton[][] mapGUI; 
  	
  	private int[][] lastMap;
  	  	
   public CasaMainView(int [][] map) {
	   super("MondoRobot");
	   this.setDefaultCloseOperation(EXIT_ON_CLOSE);       
	   this.setLayout(null);
	   
	   this.N = map.length;
	   this.lastMap = new int[N][N];
	   this.lastMap = map;	   
	   	   
	   this.G = new ButtonGroup();
	   this.rb1 = new JRadioButton("Utente");
	   this.rb1.setBounds(85,40,100,50);	 
	   this.rb1.setSelected(true);
	   this.rb2 = new JRadioButton("Kernel");
	   this.rb2.setBounds(255,40,100,50);
	   this.G.add(rb1);
	   this.G.add(rb2);
	   
	   this.ok = new JButton("OK");
	   this.ok.setBounds(160,90,80,30);
	   
	   this.l1 = new JLabel("Scegliere la modalità utente");
	   this.l1.setBounds(20,0,250,50);
	   
	   go = new JButton("GO");
       turnLeft = new JButton("turnLeft");
       turnRight = new JButton("turnRight");
       interact = new JButton("interact");
       
       this.add(rb1);
       this.add(rb2);
       this.add(ok);
       this.add(l1);
       this.setSize(400,200);
       
       this.setVisible(true); 
	   		
   }
  	

   public void CasaView()
   {    
      this.setLayout(new BorderLayout());
      
      this.remove(rb1);
      this.remove(rb2);
      this.remove(ok);
      this.remove(l1);

      //sinistra
      grigliaCasa = new JPanel();
      grigliaCasa.setLayout(new GridLayout(N,N));
      grigliaCasa.setPreferredSize(new Dimension((10 * SIZE/N)*N, (10 * SIZE/N)*N));
      
      this.mapGUI = new ChessButton[N][N];
	  this.createMap(lastMap);
      
      //bordo destra
      panel = new JPanel();
      panel.setLayout(new BorderLayout());
      //destra sopra
      panelLabels = new JPanel();
      panelLabels.setLayout(new GridLayout(3,1)); 
      panelLabels.setPreferredSize(new Dimension(5 * SIZE, 4 * SIZE));
      //destra sotto
      panelBottoni = new JPanel();
      panelBottoni.setLayout(new GridLayout(2,3));
      panelBottoni.setPreferredSize(new Dimension(5 * SIZE, 4 * SIZE));
      
      //create useless buttons
      no1 = new JButton("");
      no2 = new JButton("");
      
      //add buttons 
      panelBottoni.add(no1);
      panelBottoni.add(interact);
      panelBottoni.add(no2);
      panelBottoni.add(turnLeft);
      panelBottoni.add(go);
      panelBottoni.add(turnRight);
      
      //add labels
      text1 = new JLabel("MondoRobot™");
      text2 = new JLabel("Secondi passati: ");
      text1.setFont(new Font("Titolo", Font.BOLD, 44));
      text2.setFont(new Font("Secondi", Font.BOLD, 24));
      panelLabels.add(text1);
      panelLabels.add(text2);
	  
      panel.add(panelLabels, BorderLayout.PAGE_START);//label in cima al panel
      panel.add(panelBottoni, BorderLayout.PAGE_END);//bottoni in fondo al panel
   
      this.add(grigliaCasa, BorderLayout.LINE_START);
      this.add(panel, BorderLayout.LINE_END);
      
      this.pack();
      this.setVisible(true);      
   }

   private class ChessButton extends JButton {
	   
	
	private static final long serialVersionUID = 1L;
	private boolean seen = kernel;

       public ChessButton(int x, int y,int t) {
    	   super(t+"");
           this.setOpaque(true);
           this.setBorderPainted(false);
           this.setColor(x, y, t);           	   
       }  
       
       public boolean isSeen() {
    	   return this.seen;
       }
       
       public void Seen() {
    	   this.seen = true;
       }
       
       public void setColor(int x, int y, int t) {
    	   if(t == 1) {
               this.setBackground(Color.gray);
    	   	   this.setForeground(Color.gray);
    	   }
    	   else if(t == 0) {
        	   this.setBackground(Color.lightGray);
        	   this.setForeground(Color.lightGray);
        	   this.setText("0");
           }
    	   else if(t == -1) {
        	   this.setBackground(Color.cyan);
        	   this.setText("-1");
        	   this.setForeground(Color.cyan);
           }
    	   else if(t == 2) {
        	   this.setBackground(Color.white);
        	   this.setForeground(Color.white);
    	   }
    	   else if(t == 4) {
        	   this.setBackground(Color.blue);
        	   this.setForeground(Color.blue);
    	   }
    	   else if(t == 6) {
        	   this.setBackground(Color.red);
        	   this.setForeground(Color.red);
    	   }
    	   else if(t == 3) {
        	   this.setText("3");
        	   this.setBackground(Color.black);
        	   this.setForeground(Color.black);
           }           
    	   else if(t == 8) {
        	   this.setBackground(Color.lightGray);
        	   this.setForeground(Color.lightGray);
        	   Icon up = null;
			try {
				up = ResizeImage(ImageIO.read(this.getClass().getResource("/Immagini/RobotUp.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
        	   this.setIcon(up);
        	   this.setText("up");
        	   robotPos[0] = x;
        	   robotPos[1] = y;        	   
           } 
    	   
    	   if(this.seen == false) { 
    		   this.setText(Integer.toString(t));
    		   this.setBackground(Color.black);
    	   	   this.setForeground(Color.black);
    	   }
       }
   }  
   
   public void setKernel(boolean kernel) {
		this.kernel = kernel;
	}
   public boolean isRb2Selected() {
	   return this.rb2.isSelected();
   }
   
   public void createMap(int [][]map) {
	   int len = map.length;
	   for (int i = 0; i < len * len; i++) {
		   int x = i/len;
		   int y = i%len;
		   this.mapGUI[x][y] = new ChessButton(x,y,map[x][y]);
           this.grigliaCasa.add(mapGUI[x][y]);
       }
	   this.lastMap = map;
	   this.explore();
   }
   
   public void updateMap(int [][]map) {
	   try {	   
		   int len = map.length;
		   this.lastMap = map;
		   
		   for(int i = 0; i<len; i++)
			   for(int j = 0; j<len; j++) {
				   if(this.mapGUI[i][j].isSeen() == false) {
					   this.mapGUI[i][j].setBackground(Color.black);
					   this.mapGUI[i][j].setForeground(Color.black);				   
				   }
				   else {
					   try {				   
						   if(map[i][j] != 8) {
							    int n_button = Integer.parseInt(this.mapGUI[i][j].getText());
						   		if(n_button != map[i][j])//no 8 per nome direction aspirapolvere string to int				   			
						   			this.mapGUI[i][j].setColor(i, j, map[i][j]);
						   }
						   else//mettere la casella grigia sotto il robot
						   		this.mapGUI[i][j].setBackground(Color.lightGray);
						   
					   }
					   catch(NumberFormatException ex){
					   } 
				   }
			   }
	   }	   
	   catch (NullPointerException ex){
		   
	   }
	  
   }
   
   private void go(int newX, int newY) {
	   int x = robotPos[0];
	   int y = robotPos[1];
	   
	   mapGUI[newX][newY].setText(mapGUI[x][y].getText());
	   mapGUI[x][y].setText("0");
	   
	   mapGUI[x][y].setBackground(Color.lightGray);
	   mapGUI[x][y].setForeground(Color.lightGray);
	   
	   mapGUI[newX][newY].setIcon(mapGUI[x][y].getIcon());
	   mapGUI[x][y].setIcon(null);
	   
	   robotPos[0] = newX;
	   robotPos[1] = newY;
	   
	   this.explore();
   }
   
   private ImageIcon ResizeImage(Image img) {
	   //Image img= new ImageIcon(url).getImage();	   
	   Image newimg = img.getScaledInstance( SIZE*10/N, SIZE*10/N,  java.awt.Image.SCALE_SMOOTH );  
	   return new ImageIcon(newimg);
   }
   
   public void turnImage(String dir) {
	   int x = robotPos[0];
	   int y = robotPos[1];
	   
	   Icon up = null;
	try {
		up = ResizeImage(ImageIO.read(this.getClass().getResource("/Immagini/RobotUp.png")));
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	   Icon right = null;
	try {
		right = ResizeImage(ImageIO.read(this.getClass().getResource("/Immagini/RobotRight.png")));
	} catch (IOException e) {
		e.printStackTrace();
	}  
	   Icon down = null;
	try {
		down = ResizeImage(ImageIO.read(this.getClass().getResource("/Immagini/RobotDown.png")));
	} catch (IOException e) {
		e.printStackTrace();
	}
	   Icon left = null;
	try {
		left = ResizeImage(ImageIO.read(this.getClass().getResource("/Immagini/RobotLeft.png")));
	} catch (IOException e) {
		e.printStackTrace();
	}
	   
	   if(mapGUI[x][y].getText().equals("up")) {
		   if(dir == "right") {
			   mapGUI[x][y].setIcon(right);
			   mapGUI[x][y].setText("right");
		   }
		   else if (dir == "left") {
			   mapGUI[x][y].setIcon(left);
			   mapGUI[x][y].setText("left");	
		   }
	   }
	   else if(mapGUI[x][y].getText().equals("right")) {
		   if(dir == "right") {
			   mapGUI[x][y].setIcon( down);
			   mapGUI[x][y].setText("down");
		   }
		   else if (dir == "left") {
			   mapGUI[x][y].setIcon( up);
			   mapGUI[x][y].setText("up");
		   }
	   }
	   else if(mapGUI[x][y].getText().equals("down")) {
		   if(dir == "right") {
			   mapGUI[x][y].setIcon(left);
			   mapGUI[x][y].setText("left");
		   }
		   else if (dir == "left") {
			   mapGUI[x][y].setIcon(right);
			   mapGUI[x][y].setText("right");
		   }
	   }
	   else if(mapGUI[x][y].getText().equals("left")) {
		   if(dir == "right") {
			   mapGUI[x][y].setIcon( up);
			   mapGUI[x][y].setText("up");
		   }
		   else if (dir == "left") {
			   mapGUI[x][y].setIcon(down);
			   mapGUI[x][y].setText("down");
		   }
	   }
   }
   
   public void explore() {
	   int x = this.robotPos[0];
	   int y = this.robotPos[1];
	   for(int i = -1; i < 2; i++)
		   for(int j = -1; j < 2; j++) {
			   ChessButton Casella = this.mapGUI[x+i][y+j];
			   if(Casella.isSeen() == false) {
				   Casella.Seen();  
				   Casella.setColor(x+i, y+j, this.lastMap[x+i][y+j]);
			   }
		   }
   }
   
   private void rompiAll(int [][] posCaselle) {
	   try {
		   for(int[] r : posCaselle) {
			   Icon esclamativo = ResizeImage(ImageIO.read(this.getClass().getResource("/Immagini/Punto_Esclamativo4.png")));
			   this.mapGUI[r[0]][r[1]].setIcon(esclamativo);
			}
	   }
	   catch (NullPointerException ex) {
		   
	   } 
	   catch (IOException e) {
		e.printStackTrace();
	}
   }
   
   private void interact(int [] pos) {
	   this.mapGUI[pos[0]][pos[1]].setIcon(null);	   
   }
   
   public void addListener(ActionListener controller) {
	    this.ok.addActionListener(controller);
		this.go.addActionListener(controller);
		this.turnLeft.addActionListener(controller);
		this.turnRight.addActionListener(controller);
		this.interact.addActionListener(controller);		
	}
   
   @Override
   public void propertyChange(PropertyChangeEvent evt) {
	   if(evt.getPropertyName().equals("go")) {
		   int [] newPos = (int []) evt.getNewValue();
		   this.go(newPos[0],newPos[1]);		   
	   }
	   if(evt.getPropertyName().equals("turnLeft")){
		   this.turnImage("left");
	   }
	   if(evt.getPropertyName().equals("turnRight")){
		   this.turnImage("right");
	   }
	   if(evt.getPropertyName().equals("Update")) {
		   this.updateMap((int[][]) evt.getNewValue());
	   }
	   if(evt.getPropertyName().equals("rompi")) {
		   this.rompiAll((int[][]) evt.getNewValue());
	   }
	   if(evt.getPropertyName().equals("Interact")) {
		   this.interact((int[]) evt.getNewValue());
	   }
	   if(evt.getPropertyName().equals("muro")) {
		   JOptionPane.showMessageDialog(this, "MURO");
	   }
	   if(evt.getPropertyName().equals("second")) {
		   try {
			   this.text2.setText("Secondi passati: "+(int) evt.getNewValue());
		   }
		   catch (NullPointerException ex) {
			   
		   }
	   }	   
   }
}






