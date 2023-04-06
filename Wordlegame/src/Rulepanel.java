
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import static javax.swing.SwingConstants.CENTER;

public class Rulepanel extends JPanel implements ActionListener {

	LinkedList<String> linkedlistl;
	String input = "";
	String name_surname = "";
	JTextArea textarea;//
	JButton textarea_submit;
	JLabel credentials;
	JLabel label;
	JButton button;
	JButton defaultwordle;
	JButton dragdropwordle;
	String line = "<html>";// This variable stores every line

	boolean validation = true;
	boolean start = false;// ın order to start user has to type his/her name

	File file1;
	File file2;
	BufferedWriter writer; // i will use it to write the users name into txt file
	BufferedReader reader; // i will use it to read the users name from that file

	Rulepanel() {

		linkedlistl = new LinkedList<String>();

		this.setBounds(220, 220, 700, 500);// bu panel ile alakalı
		this.setLayout(null);
		this.setBackground(new Color(21, 21, 21));

		file1 = new File("user1.txt");
		file2 = new File("user2.txt");

		textarea = new JTextArea(1, 1);
		textarea.setText("Enter your name:");
		textarea.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textarea.getText().equals("Enter your name:")) {
					textarea.setText("");
					textarea.setForeground(Color.WHITE);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textarea.getText().isEmpty()) {
					textarea.setForeground(Color.GRAY);
					textarea.setText("Enter your name:");
				}
			}
		});
		textarea.setBounds(180, 380, 300, 30);
		textarea.setFont(new Font("Arial", Font.PLAIN, 20));
		textarea.setForeground(Color.gray);
		textarea.setBackground(new Color(21, 21, 21));

		
		textarea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					linkedlistl.addLast(textarea.getText());
					linkedlistl.print();
					
				}
			}
		});

		/*
		 * credentials = new JLabel("");
		 * credentials.setBounds(180, 380, 200, 30);
		 * credentials.setFont(new Font("Arial",Font.PLAIN,20));
		 * credentials.setForeground(new Color(207,158,92));
		 * credentials.setBackground(new Color(21,21,21));
		 */

		dragdropwordle = new JButton("Drag and drop wordle");

		dragdropwordle.setBounds(350, 280, 200, 50);
		dragdropwordle.setBackground(new Color(226, 180, 103));
		dragdropwordle.setFocusable(false);
		dragdropwordle.addActionListener(this);

		defaultwordle = new JButton("Default Wordle");

		defaultwordle.setBounds(120, 280, 150, 50);
		defaultwordle.setBackground(new Color(226, 180, 103));
		defaultwordle.setFocusable(false);
		defaultwordle.addActionListener(this);

		// textarea_submit = new JButton("");

		label = new JLabel();
		label.setBounds(100, 10, 700, 300); // bu label ile alakalı
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		label.setForeground(new Color(207, 158, 92));
		label.setBackground(new Color(21, 21, 21));// background color of the label
		line += "Welcome to the Wordle game, above you can see the rules:<br>";// line break <br>
		line += "<br>";
		line += "1-You have to guess the Wordle in five chances or less.<br>";
		line += "2-Every word you enter must be in the word list.<br>";
		line += "3-You cannot enter random words<br>";
		line += "4-A correct letter turns green.<br>";
		line += "5-A correct letter in the wrong place turns yellow.<br>";
		line += "6-Letters can be used more than once.<br>";
		line += "7-Answers are never plurals.<br>";
		label.setText(line);

		label.setVerticalTextPosition(JLabel.TOP);
		label.setHorizontalTextPosition(CENTER);

		this.add(textarea);


		// this.add(credentials);
		this.add(dragdropwordle);
		this.add(defaultwordle);
		this.add(label);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == defaultwordle) {
			checkName();
			DefaultFrame defaultframe = new DefaultFrame();// we will be calling default wordle game frame
		} else if (e.getSource() == dragdropwordle) {
			checkName();
			Draganddrop chessBoard = new Draganddrop();
		}
	}

	private void checkName() {
		input = textarea.getText();

		if (input.equals("") || input.equals("Enter your name:")) {
			input = "Nameless";
		}

		name_surname = input;
		// name_surname = input.substring(16);

		try {
			boolean result = file1.createNewFile(); // creates a new file
			if (result) // test if successfully created a new file
			{
				writeName(name_surname);
			} else {
				PrintWriter writer = new PrintWriter(file1);
				writer.print("");
				writer.close();

				writeName(name_surname);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void writeName(String name_surname) {
		try {
			reader = new BufferedReader(new FileReader(file1));
			if (reader.readLine() == null) {
				// that means file1 is empty so we will simply write the users name into file
				// one
				try {
					writer = new BufferedWriter(new FileWriter(file1));
					writer.write(name_surname);
					writer.close();
				} catch (IOException b) {
					b.printStackTrace();
				}
			}
		} catch (IOException a) {
			a.printStackTrace();
		}
	}

}
