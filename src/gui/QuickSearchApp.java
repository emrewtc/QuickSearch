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
import java.util.Iterator;
import java.util.prefs.BackingStoreException;

public class QuickSearchApp {

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
		frame.setBounds(100, 100, 493, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(85, 24, 217, 82);
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
		lblTime.setBounds(301, 251, 84, 16);
		frame.getContentPane().add(lblTime);
		
		JLabel lblComparision = new JLabel("Comparision:");
		lblComparision.setBounds(301, 279, 95, 16);
		frame.getContentPane().add(lblComparision);
		
		JLabel lblMatch = new JLabel("Match:");
		lblMatch.setBounds(301, 307, 61, 16);
		frame.getContentPane().add(lblMatch);
		
		final JLabel lblTimeResult = new JLabel("");
		lblTimeResult.setBounds(397, 251, 61, 16);
		frame.getContentPane().add(lblTimeResult);
		
		final JLabel lblComparisionResult = new JLabel("");
		lblComparisionResult.setBounds(397, 279, 61, 16);
		frame.getContentPane().add(lblComparisionResult);
		
		final JLabel lblMatchResult = new JLabel("");
		lblMatchResult.setBounds(397, 307, 61, 16);
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
		btnNewButton.setBounds(345, 24, 117, 68);
		frame.getContentPane().add(btnNewButton);
		
		
	}
}
