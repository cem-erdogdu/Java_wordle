import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class DefaultFrame extends JFrame implements ActionListener,KeyListener{

	File file1;
	BufferedWriter writer; // i will use it to write the users name into txt file
	BufferedReader reader; // i will use it to read the users name from that file

	JLabel buttonarray[][] = new JLabel[5][5];
	JPanel panelbutton = new JPanel();// i will place buttons to this panel

	JLabel label;
	WordList wordlist;
	boolean validation = true;
	boolean check_chance = true;
	boolean allowed_to_proceed = true;
	int letter = 0;// it represents column which means letters, it can be maximum 5
	int chance = 0;// it represents rows, if user gets out of chances then he/she will lose
	String word = "";


	int counter_green = 0; // this will count the green buttons in each row, if in one row there are 5 green jbuttons 
	// this counter then will be five and when this counter will be five it means that user has won the game.


	// Default constructor
	DefaultFrame(){

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(1140,1000);
		this.setTitle("Default Wordle Game Frame");

		file1 = new File("C:/My_java_workspace/Wordlegame/user1.txt");

	

		wordlist = new WordList();// creating wordlist object


		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("My Boli",Font.BOLD,50));
		label.setForeground(Color.yellow);
		label.setOpaque(true);
		label.setText("WORDLE");
		label.setBackground(Color.black);

		panelbutton.setVisible(true); // setting panel to visible
		panelbutton.setBackground(Color.black);
		panelbutton.setLayout(new GridLayout(5,5, 10, 10));// setting panel layout to grid

		// array yerine linked list kullan
		for(int i = 0; i<buttonarray.length; i++)
		{

			for(int j = 0; j<buttonarray[0].length;j++)
			{
				buttonarray[i][j] = new JLabel();
				buttonarray[i][j].setBackground((Draganddrop.hex2Rgb("#D47000")));
				buttonarray[i][j].setForeground(Draganddrop.hex2Rgb("#004B5E"));
				buttonarray[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				buttonarray[i][j].setOpaque(true);

				panelbutton.add(buttonarray[i][j]);

				buttonarray[i][j].setFont(new Font("My Boli",Font.BOLD,60));
				panelbutton.add(buttonarray[i][j]);
			}

		}
		

		this.add(label,BorderLayout.NORTH);// putting JLabel at the top of the panel


		this.add(panelbutton);
		this.addKeyListener(this);// so i am not adding key listener for every button, adding for panel


		this.repaint();// repaints component after they have been added
		this.revalidate();

		this.setResizable(false);
		this.setLocationRelativeTo(null);//places jframe in the middle of the screen

	}







	@Override
	public void keyTyped(KeyEvent e) {

	}

	// *******REMEMBER WHENEVER I PRESSED THE KEY THIS METHOD WILL BE INVOKED*******

	// I need key code of the character because it means that whether i prompt word with capital letter or with upper letter
	// they will have the exact same key code.
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode;
		// I only wish to take characters between (A to Z) so, i need to define a boundry for that
		keycode = e.getKeyCode();

		// I want to validate whether user run out of chance.
		if(check_chance) {

			if(chance < 5)
			{


				// keycode of a is 65, keycode of z is 90,so anything besides these values will be invalid.
				if(keycode>=65 && keycode <=90) {

					// It means if letter exceeds number of five which means, the first row is completed
					// user either has to hit the enter or delete( to fix his/her mistake)
					if(allowed_to_proceed) {
						if(letter < 5) {

							// I am converting keycode to char code and, setting it inside buttons
							buttonarray[chance][letter].setText(String.valueOf((char)keycode));
							letter++;// increase the letter, go to the next column if its possible

						}
						else {
							allowed_to_proceed = false; // next time it will not check whether letter is smaller than 5
						}

					}

				}
				// When user wishes to hit the enter button it will validate whether he/she entered five letter otherwise, it wont
				// let user the hit the user enter, so first i need to validate whether the word has five letters
				else if( keycode == KeyEvent.VK_ENTER) {
					// I have to check again whether adequately entered 5 letter, only then he/she can hit the enter
					if(letter ==5) {
						counter_green = 0;
						// we will store the letters of the word in this string
						String typedword = "";
						for(int i = 0; i < 5; i++) {
							// adds all letters one by one to the typedword string object
							typedword = typedword + buttonarray[chance][i].getText().toLowerCase();
						}
						// we have to check whether the word user prompted is valid or not
						// user may have entered random word such as; aabcd

						// this will return true if the word user entered valid

						if(new WordList().wordExists(typedword)) {
							System.out.println(wordlist.getWord());

							// User entered valid 5 letter word and now we have to check whether the 5 letter word 
							// which user has entered satisfies the criteria of the game 


							// First for loop will spot whether the word that user entered matches with any of the letters
							// of the wordle word
							for(int i = 0; i < 5; i++) {
								String c = String.valueOf(wordlist.getWord().charAt(i));

								for(int j = 0; j < 5; j++) {

									if(j != i && c.equals(String.valueOf(typedword.charAt(j))) ) {
										buttonarray[chance][j].setEnabled(true);
										buttonarray[chance][j].setBackground(Color.yellow);

										repaint();
									}

								}
								// End of the second for loop, below bracket belongs to the outer for loop
							}


							// Second loop will find whether the word which user entered, has a correct spot or not
							for(int i = 0; i < 5; i++) {
								//System.out.println(String.valueOf(typedword.charAt(i)));
								//System.out.println(String.valueOf(wordlist.getWord().charAt(i)));

								if(String.valueOf(typedword.charAt(i)).equals(String.valueOf(wordlist.getWord().charAt(i))) ) {
									// It means we have found the correct sport
									buttonarray[chance][i].setEnabled(true);
									buttonarray[chance][i].setBackground(Color.green);
									counter_green++;// i am increasing the counter by one

									repaint();
								}

							}



							if(counter_green == 5) {

								label.setForeground(Color.green);
								label.setText("You won");
								allowed_to_proceed = false;

								try {
									reader = new BufferedReader(new FileReader(file1));
									try {
										String str = "";
										str = reader.readLine(); // storing the name of the user
										writer = new BufferedWriter(new FileWriter(file1));

										writer.write(str+" won at "+(chance+1)+" attempt! He/She was playing on the default mode.");
										writer.close();
									}catch(IOException b) {


									}

								// user will not be permitted to enter any number because he/she has already won.
								} catch (FileNotFoundException ex) {
									throw new RuntimeException(ex);
								}
							}

								// If user failed to win the game in this row, we know that he/she has a chance to win the game
							// in a different row until the he/she run out of chances
							// but if chance is equal to 4 and user failed to find the correct word then, game over
							else if(chance == 4) {
								
								label.setForeground(Color.red);
								label.setText("You lost");

								try {
									reader = new BufferedReader(new FileReader(file1));
									try {
										String str = "";
										str = reader.readLine(); // storing the name of the user
										writer = new BufferedWriter(new FileWriter(file1));

										writer.write(str+" lost!");
										writer.close();
									}catch(IOException b) {


									}

									// user will not be permitted to enter any number because he/she has already won.
								} catch (FileNotFoundException ex) {
									throw new RuntimeException(ex);
								}
							} else {
								letter = 0;
								chance++;
								allowed_to_proceed = true;
							}

							// below bracket belongs to the statement which validates whether the word is on the list or not

						}

						// It means user entered 5 letter random word such as; aaabc does not make any sense
						else {
							label.setForeground(Color.red);
							label.setText("Word Doesn't Exist");


						}


					}

					// If user hits enter before completing typing 5 letter word it will be false
					else {
						label.setForeground(Color.red);
						label.setText("Less letters");

					}



				}
				// if user wishes to delete the letter he entered, it will detect it
				else if( keycode == KeyEvent.VK_BACK_SPACE) {

					if(letter > 0) {

						letter--;
						allowed_to_proceed = true;
						buttonarray[chance][letter].setText("");
					}





				}
			}
			// If chance is greater than 4 that means user can no longer continue
			else {

				check_chance = false;
			}
			// Below brace belongs to chance validater
		}

		// End of key pressed method
	}

	@Override
	public void keyReleased(KeyEvent e) {
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

}