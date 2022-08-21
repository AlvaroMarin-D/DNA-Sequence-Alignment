package Sequencing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * The DPM Class is used to create and do operation to the Matrixes for DNA Sequence Alignment
 * @author Alvaro Marin
 * @version 4.20.0
 * 
 */
public class DPM {
	
	/**
	 * Initialize the frame and jtextarea that will display a matrix 
	 * Initialize the variables for the size of the matrix
	 */
	static JTextArea MATRIX = new JTextArea();
	static JFrame frame = new JFrame("");
	static int M;
	static int N;

	//Default Constructor for the DPM Class
	public DPM() {
		super();
	}
	
	/**
	 * Method to create a initial Matrix between the two DNA Sequences, with parameters taken from the OptionMenu Class 
	 * @param Seq1 : represents the First DNA Sequence 
	 * @param Seq2 : represents  the  second DNA Sequence 
	 * @param Match : represents the Match score that the user wishes to use 
	 * @param MisMatch : represents the mismatch score that the user wishes to use
	 * @param Gap : represents the gap penalty that the user wishes to use 
	 * @return : returns a int matrix containing the initialization 
	 */
	public static int[][] Intialization_Step(String Seq1, String Seq2, int Match, int MisMatch, int Gap) {
		
		 // Assign the size of the matrix to the variables
		 M = Seq1.length() + 1;
		 N = Seq2.length() + 1;
		 
		// Initialize a matrix with the correct size 
		int[][] Matrix = new int[M][N];
		
		// Initialize the first Row With Gap Penalty Equal To i*Gap
		for (int i = 1; i < M; i++) {
			Matrix[i][0] = i*Gap;		
		}
		
		// Initialize the first Column With Gap Penalty Equal To i*Gap
		for (int i = 1; i < N; i++) {
			Matrix[0][i] =  i * Gap;	
		}
		// set frame to be labeled as initial 
		frame = new JFrame("Initial");
		return Matrix;
	}
	
	/**
	 * This Method is used to get the max value for each slot of the matrix 
	 * @param i : size of the matrix (columns)
	 * @param j : size of the matrix (rows)
	 * @param Seq1 : DNA Sequence 1 that will be used to compare 
	 * @param Seq2 : DNA Sequence 2 that will be used to compare 
	 * @param Matrix : The Initial matrix that be will manipulated 
	 * @param Match  : The Match Score inputed by the user previously 
	 * @param MisMatch : The MisMatch Score inputed by the user previously 
	 * @param GapPenality : The Gap penalty inputed by the user previously 
	 * @return : Returns the new matrix with updated values 
	 */
	public static int[][] Get_Max(int i, int j, String Seq1, String Seq2, int[][] Matrix, int Match, int MisMatch,
			int GapPenality) {

		int[][] Temp = new int [i+1][j+1];

		int Sim;
		int Gap = GapPenality;

		if (Seq1.charAt(i - 1) == Seq2.charAt(j - 1))
			Sim = Match;
		else
		Sim = MisMatch;
		// Get values for cells around new cell
		int M1, M2, M3;
		// Diagonal
		M1 = Matrix[i - 1][j - 1] + Sim;
		// above
		M2 = Matrix[i][j - 1] + Gap;
		// left
		M3 = Matrix[i - 1][j] + Gap;
		// check which cell is the highest

		if (M1 >= M2 && M1 >= M3) {
			Temp [i][j]= M1;
		} else {
			if (M2 >= M1 && M2 >= M3) {
				Temp[i][j]= M2;
			} else {
				if (M3 >= M1 && M3 >= M2) {
					Temp[i][j] = M3;
				}
			}
		}

		return Temp;
	}

	/**
	 * Method to create the finalized matrix between the two DNA Sequences 
	 * 
	 * @param Matrix
	 * @param Seq1
	 * @param Seq2
	 * @param Match
	 * @param MisMatch
	 * @param Gap
	 * @return
	 */
	public static int[][] FinalMatrix(int[][] Matrix, String Seq1, String Seq2, int Match, int MisMatch, int Gap) {
		int M = Seq1.length() + 1;
		int N = Seq2.length() + 1;
		// Fill Matrix with each cell has a value result from method Get_Max
		for (int i = 1; i < M; i++) {
			for (int j = 1; j < N; j++) {
				Matrix[i][j] = Get_Max(i, j, Seq1, Seq2, Matrix, Match, MisMatch, Gap)[i][j];
			}
		}
		frame = new JFrame("Final");
		return Matrix;
	}
	
	/**
	 * This method is used to create the trace-back and to create the final alignment 
	 * @param dna1 : The first DNA Sequence that will be examined
	 * @param dna2 : The Second DNA Sequence that will examined 
	 * @return : The DNA Sequence Alignment between the two Sequences based on the inputed scoring system 
	 */
	public static DNA Traceback(String dna1, String dna2) {
		// Adds character depending on match and score
		if (dna1.length() == 0 && dna2.length() == 0) {
			return new DNA();

		} else if (dna1.length() == 0) {
			DNA result = Traceback(dna1, dna2.substring(1));
			result.addMatch('_', dna2.charAt(0));
			return result;

		} else if (dna2.length() == 0) {
			DNA result = Traceback(dna1.substring(1), dna2);
			result.addMatch(dna1.charAt(0), '_');
			return result;

		} else {
			DNA first = Traceback(dna1.substring(1), dna2);
			first.addMatch(dna1.charAt(0), '_');

			DNA second = Traceback(dna1, dna2.substring(1));
			second.addMatch('_', dna2.charAt(0));

			DNA both = Traceback(dna1.substring(1), dna2.substring(1));
			both.addMatch(dna1.charAt(0), dna2.charAt(0));

			if (first.score() >= second.score() && first.score() >= both.score()) {
				return first;

			} else if (second.score() >= first.score() && second.score() >= both.score()) {
				return second;

			} else {

				return both;
			}
		}
	}

	/**
	 * This Method is used to display a inputed matrix onto a new window
	 * @param Matrix : Matrix that will be displayed 
	 * @param Seq1 : Sequence that will be displayed on the top
	 * @param Seq2 : Sequence that will be displayed on the left
	 */
	public static void PrintMatrix(int[][] Matrix, String Seq1, String Seq2) {
		
		JPanel Matpanel = new JPanel(new GridLayout(0,1));
		MATRIX = new JTextArea(M,N);
		String test = new String();
		
		
		for (int[]row : Matrix) {
			test += Arrays.toString(row) + "\n";
		}
			MATRIX.setText(test);
			Matpanel.add(MATRIX);
			Matpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.add(Matpanel,BorderLayout.CENTER);
			frame.setTitle("Matrix");
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true); 
			
	}
	
}
