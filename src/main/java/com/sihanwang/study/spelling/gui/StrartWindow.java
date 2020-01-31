package com.sihanwang.study.spelling.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StrartWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StrartWindow window = new StrartWindow();
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
	public StrartWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLoadWordList = new JButton("Load Word List");
		btnLoadWordList.setBounds(144, 23, 151, 56);
		frame.getContentPane().add(btnLoadWordList);
		
		JButton btnReview = new JButton("Review");
		btnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReview.setBounds(144, 100, 151, 56);
		frame.getContentPane().add(btnReview);
		
		JButton btnSpellingTest = new JButton("Spelling Test");
		btnSpellingTest.setBounds(144, 184, 151, 56);
		frame.getContentPane().add(btnSpellingTest);
	}

}
