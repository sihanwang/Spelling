package com.sihanwang.study.spelling.gui;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sihanwang.study.spelling.Start;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout;

public class SpellingTestWindow extends JFrame {

	private int wordindex = 0;
	private String word;
	
	private JPanel contentPane= new JPanel();
	private JTextField InputField= new JTextField();
	private JTextArea Meaning = new JTextArea();
	private JLabel Statusbar = new JLabel();
	private Logger logger = LoggerFactory.getLogger(SpellingTestWindow.class);
	
	private HashMap<String,Integer> result=new HashMap<String,Integer>();
	
	private ArrayList<String> WordQueue=new ArrayList<String>();
	
	
	private void initUI()
	{
		for(String x : Start.wordlist)
		{
			WordQueue.add(x);
		}
		Collections.shuffle(WordQueue);
		
		setTitle("Test");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		InputField.setEditable(true);
		InputField.setFont(new Font("Arial Unicode MS", Font.BOLD, 56));
		InputField.setForeground(Color.black);
		
		Meaning.setBackground(SystemColor.window);
		Meaning.setFont(new Font("Arial Unicode MS", Font.PLAIN, 22));
		Meaning.setLineWrap(true);
		Meaning.setEditable(false);
		Statusbar.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));

		
		////////////////////////////
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);
		
		GroupLayout.SequentialGroup hGroup = gl_contentPane.createSequentialGroup();
		hGroup.addGap(10);
		hGroup.addGroup(gl_contentPane.createParallelGroup()
				.addComponent(InputField, GroupLayout.Alignment.LEADING)
				.addComponent(Meaning, GroupLayout.Alignment.LEADING)
				.addComponent(Statusbar,GroupLayout.Alignment.LEADING));
		hGroup.addGap(10);
	
		gl_contentPane.setHorizontalGroup(hGroup);
		
		
		GroupLayout.SequentialGroup vGroup = gl_contentPane.createSequentialGroup();
		vGroup.addGap(10);
		vGroup.addComponent(InputField,70,70,70);
		vGroup.addGap(10);
		vGroup.addComponent(Meaning,100,300,	1024);

		vGroup.addGap(10);
		vGroup.addComponent(Statusbar, 15, 15, 15);
		gl_contentPane.setVerticalGroup(vGroup);
		
		pack();
		setSize(1024,768);
		
		
		////////////////////////////
		InputField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				//int code=key3.getKeyCode();
				if (!InputField.isEditable())
				{
					return;
				}
				int code=e.getKeyCode();
				if (code==10)
				{
					if (word.toLowerCase().trim().equals(InputField.getText().trim().toLowerCase()))
					{
						//Currect
						try {
							Start.LetterVoiceQueue.put(word.toLowerCase());
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							logger.error("InterruptedException", e1);
						}
						
						Integer times=result.get(word);
						if (times==null)
						{
							result.put(word, 1);
						}
						else
						{
							result.put(word, times+1);
						}
						
						
						wordindex=wordindex+1;
						if (wordindex<WordQueue.size())
						{
							showword();
						}
						else
						{
							//complete
							System.out.println("Complete");
						}
					}
					else
					{
						//wrong
						if (WordQueue.get(WordQueue.size()-1)!=word) //dedup
						{
							WordQueue.add(word);
						}
						
						Start.LetterVoiceQueue.clear();
						InputField.setEditable(false);
						InputField.setForeground(Color.red);
						InputField.setText(word);
						new Timer().schedule(new TimerTask() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								InputField.setEditable(true);
								InputField.setForeground(Color.black);
								InputField.setText("");
							}}, 2000);
						
					}
					
					return;
					
				}
				
				
				try {
					Start.LetterVoiceQueue.put(String.valueOf(e.getKeyChar()).toLowerCase());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					logger.error("InterruptedException", e);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}});
		////////////////////////////

		if (WordQueue!=null && WordQueue.size()>0)
		{
			showword();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public SpellingTestWindow() {
		 initUI();
	}
	
	private void showword()
	{
		word = WordQueue.get(wordindex);
		InputField.setText("");
		
		Meaning.setText( Start.ReadExplain(word));
		Statusbar.setText("Progress:\t"+String.valueOf(wordindex+1)+"/"+WordQueue.size());
		

		try {
			Start.LetterVoiceQueue.put(word.toLowerCase());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			logger.error("InterruptedException", e1);
		}
		
		
	}

}
