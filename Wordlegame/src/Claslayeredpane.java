import java.awt.event.*;
import javax.swing.*;


public class Claslayeredpane extends JLayeredPane implements MouseMotionListener, MouseListener, ActionListener {

	
	Rulepanel rulepanel;
	Loginpanel loginpanel;
	
	// Default constructor
	Claslayeredpane(){
		loginpanel = new Loginpanel();// creating loginpanel
		
		rulepanel = new Rulepanel();// creating panel which contains the rules of the game
		
		this.setBounds(0, 0, 1140, 1000);// same as the jframe
		
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.add(rulepanel);
		this.add(loginpanel);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
	}
	
	
	
	
}
