package com.sihanwang.study.spelling.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextPane;

import com.sihanwang.study.spelling.*;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.util.Timer;
import java.util.TimerTask;

public class ReviewWindow extends JFrame {

	private int wordindex = 0;
	private String word;

	private JPanel contentPane;
	private JTextPane TextPanel = new JTextPane();
	private JTextArea Meaning = new JTextArea();
	private JPanel Buttonpanel = new JPanel();
	private JButton btnPrevious = new JButton("Previous");
	private JButton btnNext = new JButton("Next");

	/**
	 * Create the frame.
	 */
	public ReviewWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 1, 10, 30));
		TextPanel.setFont(new Font("Arial Unicode MS", Font.BOLD, 33));
		

	

		contentPane.add(TextPanel);
		Meaning.setFont(new Font("Arial Unicode MS", Font.PLAIN, 22));
		
		contentPane.add(Meaning);

		contentPane.add(Buttonpanel);

		Buttonpanel.setLayout(new GridLayout(1, 0, 10, 30));
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordindex=wordindex-1;
				showword();
			}
		});

		Buttonpanel.add(btnPrevious);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wordindex=wordindex+1;
				showword();
			}
		});

		Buttonpanel.add(btnNext);
		
		if (Start.wordlist!=null && Start.wordlist.size()>0)
		{
			showword();
		}
	}
	
	private void showword()
	{
		word = Start.wordlist.get(wordindex);
		TextPanel.setText(word);
		
		Meaning.setText( Start.ReadExplain(word));
		
		if (wordindex==0)
		{
			btnPrevious.setEnabled(false);
		}
		else
		{
			btnPrevious.setEnabled(true);
		}
		
		if (wordindex==(Start.wordlist.size()-1))
		{
			btnNext.setEnabled(false);
		}
		else
		{
			btnNext.setEnabled(true);
		}
		
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Start.PlaySpelling(word);
			}}, 100);	
		
		
	}

}
