import java.awt.*;
import javax.swing.*;


public class Login extends JFrame {

	//Loginpanel loginpanel;
	
	Claslayeredpane claslayeredpane;
	
	//Default constructor
	Login(){
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(1140,1000);
		
		claslayeredpane = new Claslayeredpane ();
		
		this.add(claslayeredpane);
	
		
		
		this.repaint();// repaints component after they have been added
		this.revalidate();
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);//places jframe in the middle of the screen
	}

	
	
	
	
}