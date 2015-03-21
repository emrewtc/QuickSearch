package gui;

import java.text.*;

import qS.quickSearch;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
//import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Iterator;
import java.util.prefs.BackingStoreException;

public class QuickSearchApp {

	private int index = 0;
	private int matchCount = 0; 
	private int comparision = 0;
	private int g = 0;
	private int r = 0;
	private int j = 0;
	private boolean willShift = false;
	private boolean initialized = false;
	private char[] shiftedText;
	
	private JFrame frame;
	private DefaultHighlighter highlighter;
	private DefaultHighlightPainter hPainter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuickSearchApp window = new QuickSearchApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QuickSearchApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    this.highlighter = new DefaultHighlighter();
	    this.hPainter = new DefaultHighlightPainter(Color.LIGHT_GRAY);
		frame = new JFrame();
		frame.setBounds(100, 100, 646, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(85, 24, 340, 82);
		frame.getContentPane().add(textPane);
		textPane.setHighlighter(highlighter);
		
		JLabel lblText = new JLabel("Text:");
		lblText.setBounds(22, 49, 61, 16);
		frame.getContentPane().add(lblText);
		
		JLabel lblPattern = new JLabel("Pattern:");
		lblPattern.setBounds(22, 170, 61, 16);
		frame.getContentPane().add(lblPattern);
		
		final JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(85, 158, 130, 36);
		frame.getContentPane().add(textPane_1);
		
		final JLabel lblTime = new JLabel("Run Time: ");
		lblTime.setBounds(440, 251, 84, 16);
		frame.getContentPane().add(lblTime);
		
		JLabel lblComparision = new JLabel("Comparision:");
		lblComparision.setBounds(440, 279, 95, 16);
		frame.getContentPane().add(lblComparision);
		
		JLabel lblMatch = new JLabel("Match:");
		lblMatch.setBounds(440, 307, 61, 16);
		frame.getContentPane().add(lblMatch);
		
		final JLabel lblTimeResult = new JLabel("");
		lblTimeResult.setBounds(536, 251, 61, 16);
		frame.getContentPane().add(lblTimeResult);
		
		final JLabel lblComparisionResult = new JLabel("");
		lblComparisionResult.setBounds(536, 279, 61, 16);
		frame.getContentPane().add(lblComparisionResult);
		
		final JLabel lblMatchResult = new JLabel("");
		lblMatchResult.setBounds(536, 307, 61, 16);
		frame.getContentPane().add(lblMatchResult);
		
		
		JButton btnNewButton = new JButton("Quick Search");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{				
				quickSearch.qs(textPane.getText().toCharArray(),textPane_1.getText().toCharArray());
				lblTimeResult.setText(Long.toString(quickSearch.totalTime) + " ms");
				lblComparisionResult.setText(Integer.toString(quickSearch.comparision));
				lblMatchResult.setText(Integer.toString(quickSearch.matchCount));
				if(!quickSearch.hasFound)
					JOptionPane.showMessageDialog(null, "Pattern has not found!");
				else 
				{	
					for(int i = 0; i < quickSearch.matchIndex.size(); i++)
					{
						try {
							highlighter.addHighlight(quickSearch.matchIndex.get(i),quickSearch.matchIndex.get(i) + textPane_1.getText().length(), hPainter);
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
				}
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(493, 24, 117, 68);
		frame.getContentPane().add(btnNewButton);
		
		JButton buttonStepbyStep = new JButton("Step by Step");
		buttonStepbyStep.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				
				char[] text = textPane.getText().toCharArray();
				char[] pattern = textPane_1.getText().toCharArray();
				int textSize = text.length;
				int patternSize = pattern.length;
				int[] qsBc = new int[65536]; // size of an integer is 4 bytes = 16 bits = 2^16 = 65536
				
				if(!initialized)
				{
					shiftedText = Arrays.copyOfRange(text, 0, patternSize);
					initialized = true;					
				}
				
				
				// Pre-Processing
				quickSearch.preQsBc(pattern, patternSize, qsBc);
				
				// Searching 
				
				if(index <= textSize - patternSize)
				{		
		    			if (j < patternSize) 
						{	
							if(pattern[j] == shiftedText[j])
							{
								if(g == 0)
								{
									// PAINT IT GREEN
									try 
									{
										hPainter = new DefaultHighlightPainter(Color.GREEN);
										highlighter = new DefaultHighlighter();
										textPane.setHighlighter(highlighter);
										highlighter.addHighlight(index,index + 1, hPainter);
									}
									catch (BadLocationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									g++;
									j++;
									return;
									
								}
								
								else if(g > 0)
				    			{
					    			try 
					    			{
					    				hPainter = new DefaultHighlightPainter(Color.GREEN);
					    				highlighter = new DefaultHighlighter();
					    				textPane.setHighlighter(highlighter);
					    				highlighter.addHighlight(index + g,index + g + 1, hPainter);
					    			}
					    			catch (BadLocationException e) {
					    				// TODO Auto-generated catch block
					    				e.printStackTrace();
					    			}
					    			g++;
					    			willShift = false;
					    			comparision++;
				    			}
	
				    			j++;
												    			
					    	}
							
							else if(pattern[j] != shiftedText[j])
							{
									if(r == 0)
									{
										// PAINT IT RED
										try 
										{
											hPainter = new DefaultHighlightPainter(Color.RED);
											highlighter = new DefaultHighlighter();
											textPane.setHighlighter(highlighter);
											highlighter.addHighlight(index, index + 1, hPainter);
										}
										catch (BadLocationException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										r++;
										comparision++;
										return;
										
									}
									
									else if(r > 0)
									{
										// PAINT IT RED
										try 
										{
											hPainter = new DefaultHighlightPainter(Color.RED);
											highlighter = new DefaultHighlighter();
											textPane.setHighlighter(highlighter);
											highlighter.addHighlight(index, index + patternSize, hPainter);
										}
										catch (BadLocationException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										r = 0;
										j = 0;
										g = 0;
										willShift = true;
										comparision++;
									}
							}
									
						}
							
								
						}
						
						if(g >= patternSize)
						{
							System.out.println("Found at " + index);
							matchCount++;
							willShift = true;
							j = 0;
							g = 0;
							lblMatchResult.setText(Integer.toString(matchCount));
						}
					
					if(willShift)
					{				
						shiftedText = new char[patternSize];
						if(index + patternSize > textSize)
						{
							index += qsBc[text[textSize -1]];	
						}
						
						else
						{
							if(index+patternSize >= textSize)
								index += qsBc[text[textSize-1]];
							else
								index += qsBc[text[index + patternSize]];
						}
						
						if(index+patternSize >= textSize)
						{
							shiftedText = Arrays.copyOfRange(text, textSize - patternSize, textSize);
							
						}
						else
							shiftedText = Arrays.copyOfRange(text, index, index+patternSize);
						
			    		willShift = false;
						
					}

				}
				
				
				
				
			
		});
		buttonStepbyStep.setBounds(493, 96, 117, 68);
		frame.getContentPane().add(buttonStepbyStep);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				textPane.setText("");
				textPane_1.setText("");
				lblComparisionResult.setText("");
				lblMatchResult.setText("");
				lblTimeResult.setText("");
				index = 0;
		    	matchCount = 0;
		    	comparision = 0;
		    	g = 0;
		    	r = 0;
		    	j = 0;
		    	initialized = false;
		    	willShift = false;
			}
		});
		btnClear.setBounds(493, 170, 117, 42);
		frame.getContentPane().add(btnClear);
		
		
	}
}
