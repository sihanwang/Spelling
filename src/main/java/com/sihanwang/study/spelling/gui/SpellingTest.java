package com.sihanwang.study.spelling.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class SpellingTest {

	private JFrame frmSpelling;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpellingTest window = new SpellingTest();
					window.frmSpelling.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SpellingTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSpelling = new JFrame();
		frmSpelling.setTitle("Spelling");
		frmSpelling.setBounds(100, 100, 806, 640);
		frmSpelling.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSpelling.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(0, 0, 657, 338);
		frmSpelling.getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("pronunciation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(681, 67, 105, 103);
		frmSpelling.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(0, 345, 800, 133);
		frmSpelling.getContentPane().add(textField);
		textField.setColumns(10);
	}
}
