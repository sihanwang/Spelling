package com.sihanwang.study.spelling.gui;

import java.awt.EventQueue;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.awt.event.ActionEvent;
import com.sihanwang.study.spelling.*;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EntryWindow extends JFrame {

	private JPanel contentPane;
	private Logger logger = LoggerFactory.getLogger(EntryWindow.class);
	private JButton btnLoad = new JButton("Load");
	public JButton btnReview = new JButton("Review");
	public JButton btnTest = new JButton("Test");
	private ReviewWindow RW = null;
	private SpellingTestWindow STW = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			EntryWindow frame = new EntryWindow();
			frame.setVisible(true);
		});

	}

	/**
	 * Create the frame.
	 */
	public EntryWindow() {
		initUI();
	}

	private void initUI() {
		setTitle("Spelling");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnLoad.setToolTipText("Load a word list");
		btnLoad.addActionListener((event) -> {

			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(Start.vocabulary_path));
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.showDialog(new JLabel(), "Select");
			File wordlist_file = chooser.getSelectedFile();
			
			if (wordlist_file==null)
			{
				return;
			}

			//////////
			String wordlist_filename = wordlist_file.getName();

			if (wordlist_file.getName().endsWith("progress")) {
				//continue previous test
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(wordlist_file));
					SpellingProgress oldProgress = (SpellingProgress) ois.readObject();
					Start.wordlist_name = wordlist_file.getName().substring(0, wordlist_filename.indexOf("."));
			

					STW = new SpellingTestWindow(oldProgress.getTotalwordnum(), oldProgress.getWordindex(),
							oldProgress.getWordQueue(), oldProgress.getErrorlist(), wordlist_file, oldProgress.getTesttype());
					STW.setVisible(true);
					
				} catch (Exception e) {
					logger.error("Error in reading progress", e);
				}
			} else {
				//////
				try {
					Start.wordlist_name = wordlist_filename;
					Start.wordlist = FileUtils.readLines(wordlist_file, "UTF-8");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					logger.error("Failed to load work list:", e1);
				}

				if (Start.wordlist != null && Start.wordlist.size() > 0) {
					btnReview.setText("Review " + Start.wordlist_name);
					btnReview.setEnabled(true);
					btnTest.setText("Test " + Start.wordlist_name);
					btnTest.setEnabled(true);
				}
			}
		});

		btnReview.setToolTipText("Review word list loaded in first step");
		btnReview.addActionListener((event) -> {
			Start.RW = new ReviewWindow();
			Start.RW.setVisible(true);
		});

		btnTest.addActionListener((event) -> {
			Start.STW = new SpellingTestWindow();
			Start.STW.setVisible(true);
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);

		GroupLayout.SequentialGroup hGroup = gl_contentPane.createSequentialGroup();
		hGroup.addGap(20, 100, 200);
		hGroup.addGroup(gl_contentPane.createParallelGroup().addComponent(btnLoad, 50, 100, 200)
				.addComponent(btnReview, 50, 100, 200).addComponent(btnTest, 50, 100, 200));
		hGroup.addGap(20, 100, 200);
		gl_contentPane.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup ssg = gl_contentPane.createSequentialGroup();
		ssg.addGap(10, 50, 100);
		ssg.addComponent(btnLoad, 25, 50, 100).addGap(10, 50, 100).addComponent(btnReview, 25, 50, 100)
				.addGap(10, 50, 100).addComponent(btnTest, 25, 50, 100);
		ssg.addGap(10, 50, 100);
		gl_contentPane.setVerticalGroup(ssg);

		pack();

		setSize(1024, 768);

		if (Start.wordlist == null) {
			btnReview.setEnabled(false);
			btnTest.setEnabled(false);
		} else {
			btnReview.setEnabled(true);
			btnTest.setEnabled(true);
		}
	}
}
