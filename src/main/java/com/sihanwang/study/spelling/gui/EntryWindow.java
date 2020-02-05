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

import java.awt.event.ActionEvent;
import com.sihanwang.study.spelling.*;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class EntryWindow extends JFrame {

	private JPanel contentPane;
	private Logger logger = LoggerFactory.getLogger(EntryWindow.class);
	private JButton btnLoad = new JButton("Load");
	private JButton btnReview = new JButton("Review");
	private JButton btnTest = new JButton("Test");

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
		setSize(1024, 768);
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
			File file = chooser.getSelectedFile();

			try {
				File wordlist = new File(file.getAbsoluteFile().toString());
				Start.wordlist_name = wordlist.getName();
				Start.wordlist = FileUtils.readLines(wordlist, "UTF-8");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("Failed to load work list:", e1);
			}

			if (Start.wordlist != null && Start.wordlist.size() > 0) {
				btnReview.setEnabled(true);
				btnTest.setEnabled(true);
			}
		});

		btnReview.setToolTipText("Review word list loaded in first step");
		btnReview.addActionListener((event) -> {
			ReviewWindow RW = new ReviewWindow();
			RW.setVisible(true);
		});

		btnTest.addActionListener((event) -> {
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);
		
		GroupLayout.SequentialGroup hGroup = gl_contentPane.createSequentialGroup();
		hGroup.addGap(20,100,200);
		hGroup.addGroup(gl_contentPane.createParallelGroup()
				.addComponent(btnLoad, 50, 100, 200)
				.addComponent(btnReview, 50, 100, 200)
				.addComponent(btnTest, 50, 100, 200));
		hGroup.addGap(20,100,200);
		gl_contentPane.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup ssg = gl_contentPane.createSequentialGroup();
		ssg.addGap(10, 50, 100);
		ssg.addComponent(btnLoad, 25, 50, 100).addGap(10, 50, 100)
				.addComponent(btnReview, 25, 50, 100).addGap(10, 50, 100)
				.addComponent(btnTest, 25, 50, 100);
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
	}
}
