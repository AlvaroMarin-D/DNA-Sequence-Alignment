package Sequencing;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * The OptionMenu class is used in order for the user to select what options they want the program to do after verifying
 *  all valid inputs from the GUI class. The option menu gives the user the options to display the initialized matrix, finalized matrix, the alignment 
 *  between the sequences and to input new DNA sequences. The alignment is calculated using the Needleman-Wunsch Algorithm.
 *  The OptionMenu class extends the GUI class in order to used the scoring system and the inputed DNA sequences as well as use the methods in the DPM class
 * 	Implements Action Listener in order to respond to the different button activations 
 * 
 * @author Alvaro Marin
 * @version 4.20.0
 * 
 */
public class OptionMenu extends GUI implements ActionListener {
	
	// Initialize all the variables used for calculations and displaying
	JTextField First = new JTextField();
	JTextField Second = new JTextField();
	JTextField Match = new JTextField();
	JTextField Mis = new JTextField();
	JTextField Gap = new JTextField();
	
	/**
	 * Constructor for the OptionMemu Class
	 * 
	 * @param First : Takes in the first JTextField from the GUI class, which would be the first DNA Sequence 
	 * @param Second : Takes in the Second JTextField from the GUI class, which would be the Second DNA Sequence 
	 * @param Match : Takes in a JTextField from the GUI class, which would the Match Score
	 * @param Mis : Takes in a JTextField from the GUI class, which would the Mis Match Score
	 * @param Gap : Takes in a JTextField from the GUI class, which would the Gap Penalty 
	 */
	public OptionMenu(JTextField First, JTextField Second, JTextField Match, JTextField Mis, JTextField Gap ) {		
		this.First = First;
		this.Second = Second;
		this.Match = Match;
		this.Mis = Mis;
		this.Gap = Gap;
	}

	// Initialize a frame and 4 buttons(each for the different options)
	JFrame frame = new JFrame();
	JButton button1 = new JButton();
	JButton button2 = new JButton();
	JButton button3 = new JButton();
	JButton button4 = new JButton();
	
	// Options() is a method to display the options window 
	  void Options() {
		  
		  // Create a frame called Options and 4 buttons for the different options
		  frame = new JFrame("Options");
		  button1 = new JButton("New");
		  button2 = new JButton("Initialized Matrix");
		  button3 = new JButton("Finalize Matrix");
		  button4 = new JButton("Alignment");
			
		  // Create Labels in order to identify which sequence is which
		  JLabel label1 = new JLabel("Sequence 1:");
		  JLabel label2 = new JLabel("Sequence 2:");
			
		  // Create the different panels to be added to the frame later
		  // label panel is used to display the labels for the Sequences 
		  JPanel Labelpanel = new JPanel(new GridLayout(0,1));
		  
		  // Text panel is used to display the DNA sequence used 
		  JPanel Textpanel = new JPanel(new GridLayout(0,1));
		  // button panel is ued to display all the buttons for the different options 
		  JPanel buttonPanel = new JPanel(new GridLayout(0,1));
			
		  // Add action listener to each button in order to perform different actions when the buttons are pressed
		  button1.addActionListener(this);
		  button2.addActionListener(this);
		  button3.addActionListener(this);
		  button4.addActionListener(this);
		
		  // Set the textfield that will display the two DNA Sequence to non-editable
		  // A textfield allows for the full sequence to be displayed while being compressed
		  First.setEditable(false);
		  First.setPreferredSize(new Dimension(125,25));
		  Second.setEditable(false);
		  Second.setPreferredSize(new Dimension(125,25));
			
		  // Set the label for each DNA Sequence
		  label1.setLabelFor(First);
		  label2.setLabelFor(Second);
			 
		  // add the labels to the label panel 
		  Labelpanel.add(label1);
		  Labelpanel.add(label2);
		
		  // Set the text for the DNA Sequence y
		  First.setText(First.getText().toUpperCase());
		  Second.setText(Second.getText().toUpperCase());
		  Textpanel.add(First);
		  Textpanel.add(Second); 
			
		  // Add all buttons to the button panel
		  buttonPanel.add(button2);
		  buttonPanel.add(button3);
		  buttonPanel.add(button4);
		  buttonPanel.add(button1);
	
		  // Format all the panel sizes 
		  Labelpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		  Textpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		  buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			
		  // Format the Frame and add all the panels to the frame 
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.add(Labelpanel,BorderLayout.CENTER);
		  frame.add(Textpanel, BorderLayout.LINE_END);
		  frame.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
		  frame.setTitle("Options");
		  frame.pack();
		  frame.setLocationRelativeTo(null);
		  frame.setVisible(true); 
		
		 }
		 
	  	 // Method to listen to which button is clicked
		 public void actionPerformed(ActionEvent e) {
			 // Check if button2(Initialization Matrix) is clicked
			 if(e.getSource()== button2) {
				 int match = Integer.parseInt(Match.getText());
				 int mis = Integer.parseInt(Mis.getText());
				 int gap = Integer.parseInt(Gap.getText());
				 int[][] Mat = DPM.Intialization_Step(First.getText(),Second.getText(),match,mis,gap);
				 DPM.frame.dispose();
				 PrintMatrix(Mat,First.getText(),Second.getText());
				 
			 }
			 // Check if button3(Finalized Matrix) is clicked 
			 if(e.getSource() == button3) {
				 int match = Integer.parseInt(Match.getText());
				 int mis = Integer.parseInt(Mis.getText());
				 int gap = Integer.parseInt(Gap.getText());
				 int[][] Mat = DPM.Intialization_Step(First.getText(),Second.getText(),match,mis,gap);
				 int[][] Fine = DPM.FinalMatrix(Mat, First.getText(), Second.getText(), match, match, gap);
				 DPM.frame.dispose();
				 PrintMatrix(Fine,First.getText(),Second.getText());
			 }
			 //Check if button4(Find Alignment) is clicked
			 if(e.getSource() == button4) {
				 DNA Align =Traceback(First.getText(),Second.getText());
				 Align.frame1.dispose();
				 Align.Alignment();
			
			 }
			 // Check if button1(New pair of Sequence) is clicked
			 if(e.getSource() == button1) {
				 
				 frame.dispose();
				 First.setText(null);
				 Second.setText(null);
				 Start();
			 }

		 }
	

}
