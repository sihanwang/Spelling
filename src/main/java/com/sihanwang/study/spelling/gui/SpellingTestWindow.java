package com.sihanwang.study.spelling.gui;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
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
	
	private HashMap<String,Integer> errorlist=new HashMap<String,Integer>();
	
	private ArrayList<String> WordQueue=new ArrayList<String>();
	
	
	private void initUI()
	{
		Start.LetterVoiceQueue.clear();
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
		InputField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					Start.LetterVoiceQueue.clear();
					Start.LetterVoiceQueue.put(word.toLowerCase());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					logger.error("InterruptedException", e1);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		
		
		
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
						
						
						wordindex=wordindex+1;
						if (wordindex<WordQueue.size())
						{
							showword();
						}
						else
						{
							
							//complete
							InputField.setEnabled(false);
							//reverse order by try times
							List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(errorlist.entrySet());
							
							Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
						           @Override
						           public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
						               return o2.getValue().compareTo(o1.getValue())*-1;
						           }
						       });

							String manifestfolder = Start.vocabulary_path ;

							Date dNow = new Date( );
						    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");
							
						    File TestReport= new File(manifestfolder,Start.wordlist_name+"."+ft.format(dNow)+".report");
						    File TestReview= new File(manifestfolder,Start.wordlist_name+"."+ft.format(dNow)+".review");
							
							try {
								
								FileUtils.writeStringToFile(TestReport,"",false); //clear contentss
								TestReport tr = new TestReport();
								int TotalTimes = 0;
								
								for (Map.Entry<String, Integer> me : list)
								{
									tr.ErrorReport.put(me.getKey(),me.getValue());
									TotalTimes=TotalTimes+me.getValue();
									FileUtils.writeStringToFile(TestReport, me.getKey()+":"+me.getValue()+Start.line_separator, "UTF-8", true); 
									FileUtils.writeStringToFile(TestReview, me.getKey()+Start.line_separator, "UTF-8", true); 
								}
								
								tr.score=(int)((((float)Start.wordlist.size())/(TotalTimes+Start.wordlist.size())*100));
								tr.total=Start.wordlist.size();
								tr.tries=TotalTimes+Start.wordlist.size();
								FileUtils.writeStringToFile(TestReport, "Score:"+tr.score +", Total word:"+tr.total+",  Total tries:"+String.valueOf(tr.tries), "UTF-8", true); 
								
								Start.LetterVoiceQueue.clear();
								dispose();
								tr.initUI();
								tr.setVisible(true);
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
					}
					else
					{
						if (InputField.getText().trim().equals(""))
						{
							return;
						}
						
						Integer times = errorlist.get(word);
						if (times != null) {
							times = times + 1;
						} else {
							times = 1;
						}

						errorlist.put(word, times);

						// wrong
						// put wrong word to the end of queue
						if (WordQueue.get(WordQueue.size() - 1) != word) // dedup
						{
							WordQueue.add(word);
						}

						Statusbar.setText("Progress:" + String.valueOf(wordindex + 1) + "/" + WordQueue.size()
								+ "          Try times:" + times);

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
							}
						}, 3000);

						Start.PlayEffect("bit");
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
		
		Integer times=errorlist.get(word);
		if (times==null)
		{
			times=0;
		}
		
		Statusbar.setText("Progress:"+String.valueOf(wordindex+1)+"/"+WordQueue.size()+"          Try times:"+times);
		

		try {
			Start.LetterVoiceQueue.put(word.toLowerCase());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			logger.error("InterruptedException", e1);
		}
		
		
	}

}
