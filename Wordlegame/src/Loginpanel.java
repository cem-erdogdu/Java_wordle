
import javax.swing.*;



public class Loginpanel extends JPanel {

	ImageIcon icon;
	JLabel label;
	
	// Default constructor
	Loginpanel(){
		
		this.setBounds(0,0,1140,1000);// same as the frame size
		
		icon = new ImageIcon("C:/My_java_workspace/Wordlegame/Gold_and_marble.gif");
		label = new JLabel();
		
		label.setBounds(0,0,1140,1000);
		label.setIcon(icon);
		
		this.add(label);// adding label ,which contains the image,to our panel.
		
	}
	
	
	
}
