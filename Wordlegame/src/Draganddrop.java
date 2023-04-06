
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;



public class Draganddrop extends JFrame implements MouseListener, MouseMotionListener{
    File file1;
    BufferedWriter writer; // i will use it to write the users name into txt file
    BufferedReader reader; // i will use it to read the users name from that file

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
    JLayeredPane layeredPane;
    JPanel mainPanel = new JPanel();

    JLabel labelarray[][] = new JLabel[5][5];
    JLabel keyboardArray[][] = new JLabel[5][6];

    JLabel dragLabel;
    JPanel panelbutton = new JPanel();// i will place buttons to this panel

    JPanel keyboardPanel = new JPanel();// i will place buttons to this panel

    JLabel label;
    WordList wordlist;
    boolean check_chance = true;
    boolean allowed_to_proceed = true;
    int letter = 0;// it represents column which means letters, it can be maximum 5
    int chance = 0;// it represents rows, if user gets out of chances then he/she will lose
    String word = "";
    Timer timer;

    int counter_green = 0; // this will count the green buttons in each row, if in one row there are 5 green jbuttons
    // this counter then will be five and when this counter will be five it means that user has won the game.
    int width;
    int screenWidth;

    // height will store the height of the screen
    int height;
    int screenHeight;

    // Default constructor
    Draganddrop(){
        this.setUndecorated(true);// title bar'ı kaldırıyor
        Dimension size
                = Toolkit.getDefaultToolkit().getScreenSize();

        // width will store the width of the screen


        screenWidth = (int)size.getWidth();
        screenHeight = (int)size.getHeight();

        width = (int) (screenWidth / 2);
        height = (int) (screenHeight / 2);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(width, height);
        this.setTitle("Default Wordle Game Frame");

        Dimension boardSize = new Dimension(width, height);

        //  Use a Layered Pane for this this application

        layeredPane = new JLayeredPane();
        layeredPane.setSize( boardSize );
        layeredPane.addMouseListener( this );// layeredpane'a mouse listener ekledik
        layeredPane.addMouseMotionListener( this );// aynı şekilde mousemotion listener ekledik

        file1 = new File("user1.txt");



        getContentPane().add(layeredPane);// layeredPane'ı content pane'a ekliyoruz


        wordlist = new WordList();// creating wordlist object


        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("My Boli",Font.BOLD,width/28));
        label.setForeground(Color.yellow);
        label.setOpaque(true);
        label.setText("WORDLE");
        label.setBackground(Color.black);


        mainPanel.setSize(width,height);
        mainPanel.setLayout(null);


        label.setSize(width, (height / 10));
        panelbutton.setSize(width, (int) ((height / 10) * 5.2));
        keyboardPanel.setSize(width, (int) ((height / 10) * 3.8));

        label.setLocation(0, 0);
        panelbutton.setLocation(0, label.getHeight());
        keyboardPanel.setLocation(0, label.getHeight() + panelbutton.getHeight());



        panelbutton.setVisible(true); // setting panel to visible
        panelbutton.setBackground(Color.white);
        panelbutton.setLayout(new GridLayout(5,5, width/200, width/200));// setting panel layout to grid

        keyboardPanel.setVisible(true); // setting panel to visible
        keyboardPanel.setBackground(Color.white);
        keyboardPanel.setLayout(new GridLayout(6,5, width/200, width/200));// setting panel layout to grid

        mainPanel.add(label);// putting JLabel at the top of the panel
        mainPanel.add(panelbutton);
        mainPanel.add(keyboardPanel);



        for(int i = 0; i<labelarray.length; i++)
        {

            for(int j = 0; j<labelarray[i].length;j++)
            {
                labelarray[i][j] = new JLabel();
                labelarray[i][j].setBackground((hex2Rgb("#D47000")));
                labelarray[i][j].setForeground(hex2Rgb("#004B5E"));// background of the buttons
                labelarray[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                labelarray[i][j].setOpaque(true);

                labelarray[i][j].setFont(new Font("My Boli",Font.BOLD,width/24));
                panelbutton.add(labelarray[i][j]);
            }

        }

        for(int i = 0; i<keyboardArray.length; i++)
        {

            for(int j = 0; j<keyboardArray[i].length;j++)
            {
                keyboardArray[i][j] = new JLabel();
                keyboardArray[i][j].setBackground(Color.white);// background of the buttons
                keyboardArray[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                keyboardArray[i][j].setOpaque(true);

                keyboardArray[i][j].setBackground((hex2Rgb("#004B5E")));
                keyboardArray[i][j].setForeground(Color.white);
                keyboardArray[i][j].setFont(new Font("My Boli",Font.BOLD,width/35));
                String value;
                char keycode = (char)(65 + i*keyboardArray[i].length + j);
                if(keycode == 91 || keycode == 92) {
                    value = "DELETE";
                } else if(keycode == 93 || keycode == 94) {
                    value = "ENTER";
                } else {
                    value = String.valueOf(keycode);
                }
                keyboardArray[i][j].setText(value);
                keyboardPanel.add(keyboardArray[i][j]);

            }

        }





        this.add(mainPanel);

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);


        this.repaint();// repaints component after they have been added
        this.revalidate();

        this.setResizable(false);
        this.setLocationRelativeTo(null);//places jframe in the middle of the screen

        mainPanel.addMouseListener(this);
        mainPanel.addMouseMotionListener(this);

        this.setVisible(true);
    }
    private int xAdjustment, yAdjustment;

    public void mousePressed(MouseEvent e)
    {
        if(!SwingUtilities.isLeftMouseButton(e) ){
            return;
        }
        Component c = keyboardPanel.findComponentAt(e.getX(), e.getY() - panelbutton.getHeight() - label.getHeight());// bastığım yerin koordinatını alıyor

        if (!(c instanceof JLabel)) return;


        //xAdjustment = parentLocation.x - e.getX();
        //yAdjustment = parentLocation.y - e.getPoint().y;



        dragLabel = new JLabel();

        dragLabel.setBackground((hex2Rgb("#004B5E")));
        dragLabel.setHorizontalAlignment(SwingConstants.CENTER);

        layeredPane.setVisible(true);
        layeredPane.setOpaque(true);

        dragLabel.setFont(new Font("My Boli",Font.BOLD,width/28));
        dragLabel.setText(((JLabel) c).getText());
        dragLabel.setForeground(Color.white);

        xAdjustment = c.getX() - e.getX();
        yAdjustment = c.getY() - e.getY();

        dragLabel.setSize(width/5, (int) (height*0.063333333333));
        dragLabel.setLocation(c.getX(), c.getY() + panelbutton.getHeight() + label.getHeight());


        layeredPane.add(dragLabel, JLayeredPane.DRAG_LAYER);
        layeredPane.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

        dragLabel.setVisible(true);
        dragLabel.setOpaque(true);





    }

    /*
     **  Move the chess piece around
     */
    public void mouseDragged(MouseEvent me)
    {
        if(!SwingUtilities.isLeftMouseButton(me) || dragLabel==null){
            return;
        }
        if (layeredPane.getComponentCount()==0) return;

        //System.out.println(me.getX() + " " + me.getY());
        Component c = (Component) layeredPane.getComponent(0);
        if (!(c instanceof JLabel)) return;
        //if (!(c instanceof JLabel)) return;



        dragLabel.setLocation(me.getX() + xAdjustment,  me.getY() + yAdjustment + panelbutton.getHeight() + label.getHeight());
        //System.out.println(keyboardPanel.getHeight());



        System.out.println(me.getPoint().x + " "  +me.getPoint().y);
    }

    /*
     **  Drop the chess piece back onto the chess board
     */
    public void mouseReleased(MouseEvent e)
    {
        if(!SwingUtilities.isLeftMouseButton(e) || dragLabel==null){
            return;
        }
        Component c = layeredPane.findComponentAt(e.getPoint().x, e.getPoint().y);// bastığım yerin koordinatını alıyor
        if (!(c instanceof JLabel) || dragLabel==null) return;

        if(!(e.getY()<0.1*height || e.getY()>0.62*height )){
            JLabel label = (JLabel)c;

            layeredPane.setCursor(null);

            checkWord(dragLabel.getText());
        }

        dragLabel = null;
        if(layeredPane.getComponentCount()==2){
            layeredPane.remove(0);
            layeredPane.repaint();
        }






        //  Make sure the chess piece is no longer painted on the layered pane

        //label.setVisible(false);
        //layeredPane.remove(label);

        //  The drop location should be within the bounds of the chess board
        /*
        int xMax = layeredPane.getWidth() - dragLabel.getWidth();
        int x = Math.min(e.getPoint().x, xMax);
        x = Math.max(x, 0);

        int yMax = layeredPane.getHeight() - dragLabel.getHeight();
        int y = Math.min(e.getPoint().y, yMax);
        y = Math.max(y, 0);
        */
        /*
        if (c instanceof JLabel)
        {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add( chessPiece );
            parent.validate();
        }
        else
        {
            Container parent = (Container)c;
            parent.add( chessPiece );
            parent.validate();
        }
         */

        //labelarray[chance][letter].setText(String.valueOf((char)keycode));



    }

    private void checkWord(String key){

        // I want to validate whether user run out of chance.
        if(check_chance) {

            if(chance < 5)
            {
                // keycode of a is 65, keycode of z is 90,so anything besides these values will be invalid.

                    // It means if letter exceeds number of five which means, the first row is completed
                    // user either has to hit the enter or delete( to fix his/her mistake)
                    if(allowed_to_proceed && !key.equals("ENTER") && !key.equals("DELETE")) {
                        if(letter < 5) {
                            // I am converting keycode to char code and, setting it inside buttons
                            labelarray[chance][letter].setText(key);
                            letter++;// increase the letter, go to the next column if its possible

                        }
                        else {
                            allowed_to_proceed = false; // next time it will not check whether letter is smaller than 5
                        }

                        // When user wishes to hit the enter button it will validate whether he/she entered five letter otherwise, it wont
                        // let user the hit the user enter, so first i need to validate whether the word has five letters

                    } else if(key.equals("ENTER")) {
                    // I have to check again whether adequately entered 5 letter, only then he/she can hit the enter
                        if(letter == 5) {
                            counter_green = 0;
                            // we will store the letters of the word in this string
                            String typedword = "";
                            for(int i = 0; i < 5; i++) {
                                // adds all letters one by one to the typedword string object
                                typedword = typedword + labelarray[chance][i].getText().toLowerCase();
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
                                            labelarray[chance][j].setEnabled(true);
                                            labelarray[chance][j].setBackground(Color.yellow);

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
                                        labelarray[chance][i].setEnabled(true);
                                        labelarray[chance][i].setBackground(Color.green);
                                        counter_green++;// i am increasing the counter by one
                                        repaint();
                                    }
                                }

                                if(counter_green == 5) {
                                    System.out.println("after validation" + counter_green);
                                    label.setForeground(Color.green);
                                    label.setText("You won");
                                    allowed_to_proceed = false;

                                    // user will not be permitted to enter any number because he/she has already won.

                                    try {
                                        reader = new BufferedReader(new FileReader(file1));
                                        try {
                                            String str = "";
                                            str = reader.readLine(); // storing the name of the user
                                            writer = new BufferedWriter(new FileWriter(file1));

                                            writer.write(str+" won at "+(chance+1)+" attempt! He/She was playing on the drag and drop mode.");
                                            writer.close();
                                        }catch(IOException b) {


                                        }

                                        // user will not be permitted to enter any number because he/she has already won.
                                    } catch (FileNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                } else if(chance == 4) {
                                    // If user failed to win the game in this row, we know that he/she has a chance to win the game
                                    // in a different row until the he/she run out of chances
                                    // but if chance is equal to 4 and user failed to find the correct word then, game over
                                    timer.start();
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
                                // It means user entered 5 letter random word such as; aaabc does not make any sense
                            } else {
                                label.setForeground(Color.red);
                                label.setText("Word Doesn't Exist");


                            }
                            // If user hits enter before completing typing 5 letter word it will be false
                        } else {
                            label.setForeground(Color.red);
                            label.setText("Less letters");
                        }
                } else if(key.equals("DELETE")) {

                    if(letter > 0) {

                        letter--;
                        allowed_to_proceed = true;
                        labelarray[chance][letter].setText("");
                    }
                }
            }
            // If chance is greater than 4 that means user can no longer continue
            else {

                check_chance = false;
            }
            // Below brace belongs to chance validater
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}