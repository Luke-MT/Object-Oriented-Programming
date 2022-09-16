package UPO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CasaController implements ActionListener{
	
	private Casa c;
	private CasaMainView v;
	
	public CasaController(Casa c, CasaMainView v) {
		super();
		this.c = c;
		this.v = v;
		v.addListener(this);
		c.addListener(v);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText().equals("OK")) {
			if(v.isRb2Selected() == true) {
				v.setKernel(true);
			}				
			else
				v.setKernel(false);
			c.startTimer();
			v.CasaView();
		}
		if(((JButton)e.getSource()).getText().equals("GO")) 
			c.turno(Action.GO);
		if(((JButton)e.getSource()).getText().equals("turnLeft"))
			c.turno(Action.LEFT);
		if(((JButton)e.getSource()).getText().equals("turnRight"))
			c.turno(Action.RIGHT);
		if(((JButton)e.getSource()).getText().equals("interact")) {
			c.interact();
		}
		
	}
	

}
