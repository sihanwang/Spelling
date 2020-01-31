package com.sihanwang.study.spelling.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Review {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Review window = new Review();
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
	public Review() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane DisplaySpelling = new JTextPane();
		DisplaySpelling.setBounds(0, 0, 476, 173);
		DisplaySpelling.setText("");
		frame.getContentPane().add(DisplaySpelling);
		
		JTextArea DisplayMeaning = new JTextArea();
		DisplayMeaning.setBounds(0, 175, 476, 293);
		frame.getContentPane().add(DisplayMeaning);
		
		JToolBar StatusBar = new JToolBar();
		StatusBar.setBounds(6, 544, 476, 26);
		frame.getContentPane().add(StatusBar);
		
		JButton btnNewButton = new JButton("Show spelling");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(188, 473, 117, 29);
		frame.getContentPane().add(btnNewButton);
	}
}
