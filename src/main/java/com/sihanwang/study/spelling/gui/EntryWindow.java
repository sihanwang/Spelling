package com.sihanwang.study.spelling.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import com.sihanwang.study.spelling.*;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EntryWindow extends JFrame {

	private JPanel contentPane;
	private Logger logger = LoggerFactory.getLogger(EntryWindow.class);

	public JButton btnReview = new JButton("Review");
	public JButton btnTest = new JButton("Test");
	public JButton btnExit = new JButton("Exit");

	/**
	 * Create the frame.
	 */
	public EntryWindow(File file) {
		initUI(file);
	}

	private void initUI(File file) {
		setTitle("Spelling");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		File wordlist_file = file;

		if (wordlist_file == null) {
			return;
		}

		try {
			Start.wordlist = FileUtils.readLines(wordlist_file, "UTF-8");
			
			if (Start.DReviewProgress!=null)
			{
				ArrayList<String> toBeReviewed=Start.DReviewProgress.getToBeReviewed();
				
				for (String tobereviewd : toBeReviewed)
				{
					Start.wordlist.addAll (FileUtils.readLines(new File(Start.reviewFolder,   tobereviewd), "UTF-8"));
				}
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("Failed to load work list:", e1);
		}

		if (Start.wordlist != null && Start.wordlist.size() > 0) {
			btnReview.setText("Review " + Start.wordlist_name);
			btnReview.setEnabled(true);
			btnTest.setText("Test " + Start.wordlist_name);
			
		}

		btnReview.setToolTipText("Review word list loaded in first step");
		btnReview.addActionListener((event) -> {
			Start.RW = new ReviewWindow();
			Start.setFullScreen(Start.RW);
			
		});

		btnTest.addActionListener((event) -> {
			Start.STW = new SpellingTestWindow();
			Start.setFullScreen(Start.STW);
			
		});
		
		btnExit.addActionListener((event) -> {
			this.dispose();
			System.exit(0);
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);

		GroupLayout.SequentialGroup hGroup = gl_contentPane.createSequentialGroup();
		hGroup.addGap(20, 100, 200);
		hGroup.addGroup(gl_contentPane.createParallelGroup().addComponent(btnReview, 50, 100, 200).addComponent(btnTest,
				50, 100, 200).addComponent(btnExit,50,100,200));
		hGroup.addGap(20, 100, 200);
		gl_contentPane.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup ssg = gl_contentPane.createSequentialGroup();
		ssg.addGap(10, 50, 100);
		ssg.addGap(10, 50, 100).addComponent(btnReview, 25, 50, 100).addGap(10, 50, 100).addComponent(btnTest, 25, 50,
				100).addGap(10, 50, 100).addComponent(btnExit,25,50,100);
		ssg.addGap(10, 50, 100);
		gl_contentPane.setVerticalGroup(ssg);

		pack();
		

		if (Start.wordlist == null) {
			btnReview.setEnabled(false);
			btnTest.setEnabled(false);
		} else {
			btnReview.setEnabled(true);
			btnTest.setEnabled(true);
		}
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}}
		);
	}
}
