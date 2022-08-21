package Sequencing;
import javax.swing.*;
/**
 * 
 * The GUI Class is used at the beginning of the program and any time a new DNA sequence alignment is required.
 * The class is used to store the text input from the user and determines if it is valid and to send to option menu class
 * The GUI class extends to the DPM class in order to do calculations later on 
 * @author Alvaro Marin
 * @version 4.20.0
 * 
 */
public class GUI extends DPM{
	
	 // Initialize the two input fields for the DNA sequences 
	 static JTextField Sequence1 = new JTextField();
	 static JTextField Sequence2 = new JTextField();

	 public static void main(String[] args) {
		 Start();
	   } 
	 
	 // Method that gets executed at the start of the program and where the initial DNA sequences are asked  
	 public static void Start() {
		 
		 // Make sure the Text fields are Editable 
		 Sequence1.setEditable(true);
		 Sequence2.setEditable(true);
		 
		 // Create the visual panel on the frame with a label for each text field
		 Object[] message = {
		     "DNA Sequence 1:", Sequence1,
		     "DNA Sequence 2:", Sequence2
		 };
		 
		 // Create the frame called DNA Sequencing with OK and Cancel options
		 int option = JOptionPane.showConfirmDialog(null, message, "DNA Sequencing", JOptionPane.OK_CANCEL_OPTION);
		 
		 // Check if the user clicks the cancel option or if the user clicks the close option. If the user clicks one of these options, closes program/window 
		 if(option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
			 System.exit(0);
		 }
		 
		 // Create a object of type DNA that will be used to call the validCheck method, to check if the sequences the user inputed are valid
		 DNA seq = new DNA();
		 
		 // Check if both inputed fields are valid. If the user inputed invalid text for both, display error message
		 if((Sequence1.getText().length() <=0 && Sequence2.getText().length()<=0) ||(!seq.ValidCheck(Sequence1) && !seq.ValidCheck(Sequence2))){
			 JOptionPane.showMessageDialog(null, "Invalid Sequence 1 and 2 Input","Failure",JOptionPane.ERROR_MESSAGE );
			 Start(); 
			 
		 // Check if only the first text field is valid. If only the first text field is invalid, display error message	 
		 }else if(Sequence1.getText().length() <=0 || !seq.ValidCheck(Sequence1)) {
			 JOptionPane.showMessageDialog(null, "Invalid Sequence 1 Input","Failure",JOptionPane.ERROR_MESSAGE );
			 Start();
		 
		 // Check if only the Second text filed is valid. If only the second text field is invalid, display error message 
		 }else if(Sequence2.getText().length()<=0 || !seq.ValidCheck(Sequence2)){
			 JOptionPane.showMessageDialog(null, "Invalid Sequence 2 Input","Failure",JOptionPane.ERROR_MESSAGE );
			 Start();
		 
		 // If the both text fields are valid and user clicks ok option then display the info window, 
		 // which asks for the matchscore, mismatchscore and gapPenality used for DNA alignment 
		 }else if(option ==JOptionPane.OK_OPTION) {
			 Info();
		 }  
		 
	 }
	 // Method used to setup scoring system for DNA Sequence alignment
	 public static void Info() {
		 // Setup the text fields for the scoring system 
		 // Match Score is the amount given if a character in the sequences matches with each other
		 JTextField MatchScore = new JTextField();
		 
		 // Mis Match Score is the taken away if the sequences have different character 
		 JTextField MisMatchScore = new JTextField();
		 
		 // Gap penalty is the penalty when there is a gap between the sequences
		 JTextField GapPenalty = new JTextField();
		 
		 // Create the visual panel on the frame with a label for each text field
		 Object[] message = {
		     "MatchScore:", MatchScore,
		     "MisMatchScore:", MisMatchScore,
		     "GapPenalty: ", GapPenalty
		     };
		 
		 // Create the frame called DNA Sequencing with OK and Cancel options
		 int option = JOptionPane.showConfirmDialog(null, message, "DNA Sequencing", JOptionPane.OK_CANCEL_OPTION);
		 
		 // Checks if user clicks the cancel option. If the user selects cancel option then return to the start menu
		 if(option == JOptionPane.CANCEL_OPTION) {
			 Start();
		 }
		 
		 // Checks if the user clicks the close option. If the user selects close option then closes program/ window 
		 if(option == JOptionPane.CLOSED_OPTION) {
			 System.exit(0);
		 }
		 
		 // Checks if the user inputed any inputs. If the user did not input and inputs then display error message and return to info menu
		 if(MatchScore.getText().length() <= 0||MisMatchScore.getText().length() <= 0|| GapPenalty.getText().length() <= 0) {
			 JOptionPane.showMessageDialog(null, "Invalid Inputs","Failure",JOptionPane.ERROR_MESSAGE );
			 Info();
	
		 // Check if the user inputed positive integers. If user did not input positive integers display error message
		 }else if(!MatchScore.getText().matches("^[0-9]*$")||!MisMatchScore.getText().matches("^[0-9]*$")||!GapPenalty.getText().matches("^[0-9]*$")) {
			 JOptionPane.showMessageDialog(null, "Only Positive integers Allowed","Failure",JOptionPane.ERROR_MESSAGE );
			 Info();
			 
		// If the user clicks ok option and inputs are valid then go to the option menu 	
		 }else if(option == JOptionPane.OK_OPTION) {
			 OptionMenu men = new OptionMenu(Sequence1, Sequence2, MatchScore,MisMatchScore,GapPenalty);
			 men.Options();
		 }
	 }
	 
}
