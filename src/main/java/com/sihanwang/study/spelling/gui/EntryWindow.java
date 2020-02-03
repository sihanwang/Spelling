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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 10, 30));

		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		contentPane.add(btnLoad);
		btnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReviewWindow RW = new ReviewWindow();
				RW.setVisible(true);
			}
		});
		contentPane.add(btnReview);
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		contentPane.add(btnTest);

		if (Start.wordlist == null) {
			btnReview.setEnabled(false);
			btnTest.setEnabled(false);
		} else {
			btnReview.setEnabled(true);
			btnTest.setEnabled(true);
		}
	}
}
