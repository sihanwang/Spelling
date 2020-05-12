package com.sihanwang.study.spelling.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.sihanwang.study.spelling.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.util.Timer;
import java.util.TimerTask;

import java.awt.SystemColor;

public class ReviewWindow extends JFrame {

	private int wordindex = 0;
	private String word;

	private JPanel contentPane;
	private JLabel TextLabel = new JLabel();
	private JTextArea Meaning = new JTextArea();

	private JButton btnPrevious = new JButton("Previous");
	private JButton btnNext = new JButton("Next");
	private JButton btnExit = new JButton("Exit");
	private JLabel Statusbar = new JLabel();

	private Logger logger = LoggerFactory.getLogger(ReviewWindow.class);

	/**
	 * Create the frame.
	 */
	public ReviewWindow() {
		Start.EW.setVisible(false);
		
		if (Start.STW!=null)
		{
			Start.STW.dispose();;
		}
		
		initUI();
	}

	private void initUI() {
		setTitle("Review");
		

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		TextLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 56));

		Meaning.setBackground(SystemColor.window);
		Meaning.setFont(new Font("Arial Unicode MS", Font.PLAIN, 22));
		Meaning.setLineWrap(true);
		Meaning.setEditable(false);
		Statusbar.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		////////////////////////////
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);

		GroupLayout.SequentialGroup hButtonGroup = gl_contentPane.createSequentialGroup();
		hButtonGroup.addGap(25, 50, 100).addComponent(btnPrevious, 50, 100, 100).addGap(25, 50, 100).addComponent(btnExit,50,100,100).addGap(25, 50, 100)
				.addComponent(btnNext, 50, 100, 100).addGap(25, 50, 100);

		GroupLayout.ParallelGroup vButtonGroup = gl_contentPane.createParallelGroup();
		vButtonGroup.addComponent(btnPrevious, GroupLayout.Alignment.CENTER).addComponent(btnExit, GroupLayout.Alignment.CENTER).addComponent(btnNext,
				GroupLayout.Alignment.CENTER);

		GroupLayout.SequentialGroup hGroup = gl_contentPane.createSequentialGroup();
		hGroup.addGap(10);
		hGroup.addGroup(gl_contentPane.createParallelGroup().addComponent(TextLabel, GroupLayout.Alignment.LEADING)
				.addComponent(Meaning, GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.CENTER, hButtonGroup)
				.addComponent(Statusbar, GroupLayout.Alignment.LEADING));
		hGroup.addGap(10);

		gl_contentPane.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = gl_contentPane.createSequentialGroup();
		vGroup.addGap(10);
		vGroup.addComponent(TextLabel, 70, 70, 70);
		vGroup.addGap(10);
		vGroup.addComponent(Meaning, 100, 300, 1024);
		vGroup.addGap(10);
		vGroup.addGroup(vButtonGroup);
		vGroup.addGap(10);
		vGroup.addComponent(Statusbar, 15, 15, 15);
		gl_contentPane.setVerticalGroup(vGroup);

		pack();

		//setSize(1024, 768);

		////////////////////////////
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordindex = wordindex - 1;
				Start.LetterVoiceQueue.clear();
				showword();
			}
		});
		
		btnExit.addActionListener((event) -> {
			Start.setFullScreen(Start.EW);
			dispose();
		});

		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordindex = wordindex + 1;
				Start.LetterVoiceQueue.clear();
				showword();
			}
		});

		if (Start.wordlist != null && Start.wordlist.size() > 0) {
			showword();
		}

	}

	private void showword() {
		word = Start.wordlist.get(wordindex);
		TextLabel.setText(word);

		Meaning.setText(Start.ReadExplain(word));
		Statusbar.setText("Progress:\t" + String.valueOf(wordindex + 1) + "/" + Start.wordlist.size());

		if (wordindex == 0) {
			btnPrevious.setEnabled(false);
		} else {
			btnPrevious.setEnabled(true);
		}

		if (wordindex == (Start.wordlist.size() - 1)) {
			btnNext.setEnabled(false);
		} else {
			btnNext.setEnabled(true);
		}

		try {
			Start.LetterVoiceQueue.put(word.toLowerCase());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			logger.error("InterruptedException", e1);
		}

	}

}
