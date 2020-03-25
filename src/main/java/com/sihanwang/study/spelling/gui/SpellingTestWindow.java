package com.sihanwang.study.spelling.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sihanwang.study.spelling.Start;
import com.sihanwang.study.spelling.Daily50;
import com.sihanwang.study.spelling.DailyReviewProgress;
import com.sihanwang.study.spelling.SpellingProgress;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.JButton;

public class SpellingTestWindow extends JFrame {

	private int totalwordnum;
	private int wordindex ;
	private HashMap<String, Integer> errorlist ;
	private ArrayList<String> WordQueue ;
	private String TestType;
	
	private String word;
	private JPanel contentPane = new JPanel();
	private JTextField InputField = new JTextField();
	private JTextArea Meaning = new JTextArea();
	private JLabel Statusbar = new JLabel();
	private Logger logger = LoggerFactory.getLogger(SpellingTestWindow.class);
	private File progress_file=null;
	private JButton btnExit = new JButton("Exit");

	private void initUI(int twn,int wi, ArrayList<String> wq, HashMap<String, Integer> el, String tt) {
		totalwordnum=twn;
		wordindex=wi;
		WordQueue=wq;
		errorlist=el;
		TestType=tt;
		Start.LetterVoiceQueue.clear();
		setTitle("Test");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		btnExit.addActionListener((event) -> {

				if (wordindex != 0) {
					
					//delete previous progress file if any
					
					if (progress_file!=null)
					{
						try {
							FileUtils.forceDelete(progress_file);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							logger.error("Failed to delete progress file:"+progress_file.getName(),e1);
						}
					}

					
					// Serializw progress object into file
					SpellingProgress sp = new SpellingProgress(totalwordnum, wordindex, errorlist, WordQueue,Start.test_type,Start.DReviewProgress);

					String progressFolder = Start.progressFolder;
					Date dNow = new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");

					File SpellingTestProgress = new File(progressFolder,
							Start.wordlist_name + "." + ft.format(dNow) + ".progress");

					try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SpellingTestProgress));
						oos.writeObject(sp);
						oos.close();
					} catch (Exception e1) {
						logger.error("Can't write progress into file", e1);
					}
				}
			
				dispose();
				
				if (Start.EW!=null)
				{
					Start.setFullScreen(Start.EW);
				}
				else
				{
					System.exit(0);
				}
				
		});



		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);


		
		InputField.setEditable(true);
		InputField.enableInputMethods(false);
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
		
		GroupLayout.SequentialGroup hButtonGroup = gl_contentPane.createSequentialGroup();
		hButtonGroup.addGap(25, 50, 100).addComponent(btnExit,50,100,100).addGap(25, 50, 100);

		GroupLayout.ParallelGroup vButtonGroup = gl_contentPane.createParallelGroup();
		vButtonGroup.addComponent(btnExit, GroupLayout.Alignment.CENTER);
		
		GroupLayout.SequentialGroup hGroup = gl_contentPane.createSequentialGroup();
		hGroup.addGap(10);
		hGroup.addGroup(gl_contentPane.createParallelGroup().addComponent(InputField, GroupLayout.Alignment.LEADING)
				.addComponent(Meaning, GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.CENTER, hButtonGroup)
				.addComponent(Statusbar, GroupLayout.Alignment.LEADING));
				
		hGroup.addGap(10);

		gl_contentPane.setHorizontalGroup(hGroup);

		GroupLayout.SequentialGroup vGroup = gl_contentPane.createSequentialGroup();
		vGroup.addGap(10);
		vGroup.addComponent(InputField, 70, 70, 70);
		vGroup.addGap(10);
		vGroup.addComponent(Meaning, 100, 300, 1024);

		vGroup.addGap(10);
		vGroup.addGroup(vButtonGroup);
		vGroup.addGap(10);
		
		vGroup.addComponent(Statusbar, 15, 15, 15);
		gl_contentPane.setVerticalGroup(vGroup);

		pack();
		
		////////////////////////////
		InputField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					Start.LetterVoiceQueue.clear();
					if (Start.test_type.equals("voice")) {
						Start.LetterVoiceQueue.put(word.toLowerCase());
					}
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

			}
		});

		InputField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

				int code = e.getKeyChar();
				
				if (!((code > 64 && code < 91) || (code > 96 && code < 123) || (code == 32) || (code == 8))) {
					e.consume();
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				// int code=key3.getKeyCode();
				if (!InputField.isEditable()) {
					return;
				}
				int code = e.getKeyCode();
				if (code == 10) {

					if (word.toLowerCase().trim().equals(InputField.getText().trim().toLowerCase())) {
						// Currect
						try {
							Start.LetterVoiceQueue.put(word.toLowerCase());
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							logger.error("InterruptedException", e1);
						}

						wordindex = wordindex + 1;
						if (wordindex < WordQueue.size()) {
							showword();
						} else {
							
							//complete
							
							if (progress_file!=null)
							{
								try {
									FileUtils.forceDelete(progress_file);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									logger.error("Failed to delete progress file:"+progress_file.getName(),e);
								}
							}
							
							InputField.setEnabled(false);
							// reverse order by try times
							List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
									errorlist.entrySet());

							Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
								@Override
								public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
									return o2.getValue().compareTo(o1.getValue()) * -1;
								}
							});

							String reportFolder = Start.reportFolder;
							String reviewFolder = Start.reviewFolder;

							Date dNow = new Date();
							SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");

							File TestReport = new File(reportFolder,
									Start.wordlist_name + "." + ft.format(dNow) + ".report");
							File TestReview = new File(reviewFolder,
									Start.wordlist_name + "." + ft.format(dNow) + ".review");

							try {

								FileUtils.writeStringToFile(TestReport, "", false); // clear contentss
								TestReport tr = new TestReport();
								int TotalTimes = 0;

								//write incorrect tries
								for (Map.Entry<String, Integer> me : list) {
									tr.ErrorReport.put(me.getKey(), me.getValue());
									TotalTimes = TotalTimes + me.getValue();
									FileUtils.writeStringToFile(TestReport,
											me.getKey() + ":" + me.getValue() + Start.line_separator, "UTF-8", true);
									FileUtils.writeStringToFile(TestReview, me.getKey() + Start.line_separator, "UTF-8",
											true);
								}
								
								
								//Write daily review progress
								if (Start.DReviewProgress!=null)
								{
									
									int dailyIndex=1;
									
									List<File> allSourceFile = Daily50.searchFiles(new File(Start.dailyReviewListFolder, Start.dailyReviewListFolder));
									
									if(Start.DReviewProgress.getDailyIndex() <allSourceFile.size())
									{
										dailyIndex=Start.DReviewProgress.getDailyIndex()+1;
									}
									
									LinkedList<String> toBeReviewed=Start.DReviewProgress.getToBeReviewed();
									
									
									if (toBeReviewed.size()<Start.dailyReviewBackDays)
									{
										toBeReviewed.add(TestReview.getName());
									}
									
									if (toBeReviewed.size()>Start.dailyReviewBackDays)
									{
										toBeReviewed.removeFirst();
									}
									
									DailyReviewProgress DRP=new DailyReviewProgress(dailyIndex, toBeReviewed);
									
									File dailReviewProgressFile = new File(Start.progressFolder, Start.dailyReviewProgressFile);
									
									if (dailReviewProgressFile.exists())
									{
										try {
											FileUtils.forceDelete(dailReviewProgressFile);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											logger.error("Failed to delete old daily progress file:"+dailReviewProgressFile.getName(),e1);
										}
									}
									
									try {
										ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dailReviewProgressFile));
										oos.writeObject(DRP);
										oos.close();
									} catch (Exception e1) {
										logger.error("Can't write daily progress into file", e1);
									}
								}
								
								
								
								//write test type
								FileUtils.writeStringToFile(TestReport, "Test type:"+Start.test_type+ Start.line_separator, "UTF-8",true);

								//write score
								tr.score = (int) ((((float) totalwordnum)
										/ (TotalTimes + totalwordnum) * 100));
								tr.total = totalwordnum;
								tr.tries = TotalTimes + totalwordnum;
								tr.testtype=TestType;
								FileUtils.writeStringToFile(TestReport, "Score:" + tr.score + ", Total word:" + tr.total
										+ ",  Total tries:" + String.valueOf(tr.tries), "UTF-8", true);

								Start.LetterVoiceQueue.clear();
								dispose();
								tr.initUI();
								Start.setFullScreen(tr);

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					} else {
						if (InputField.getText().trim().equals("")) {
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
						Start.PlayEffect("bit");
						
						Start.PlaySpelling(word.toLowerCase());
						
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

			}
		});
		////////////////////////////

		if (WordQueue != null && WordQueue.size() > 0) {
			showword();
		}

	}

	/**
	 * Create the frame.
	 */
	public SpellingTestWindow() {
		
		Start.EW.setVisible(false);
		
		if (Start.RW!=null)
		{
			Start.RW.dispose();;
		}
		
		this.progress_file=null;
		
		ArrayList<String> tempWQ=new ArrayList<String>();
		
		for (String x : Start.wordlist) {
			tempWQ.add(x);
		}
		Collections.shuffle(tempWQ);
		
		initUI(Start.wordlist.size(),0,tempWQ,new HashMap<String, Integer>(),Start.test_type);
	}
	
	public SpellingTestWindow(int twn, int wi, ArrayList<String> wq, HashMap<String, Integer> el, File pf, String tt ) {
		
		if (Start.RW!=null)
		{
			Start.RW.dispose();;
		}
		
		initUI(twn, wi,wq,el,tt);
		this.progress_file=pf;
	}

	private void showword() {
		word = WordQueue.get(wordindex);
		InputField.setText("");

		String meaning_display=Start.ReadExplain(word);
		if (TestType.equals("translation"))
		{
			meaning_display=meaning_display.substring(meaning_display.indexOf("#Translation#"));
		}
		
		Meaning.setText(meaning_display);

		Integer times = errorlist.get(word);
		if (times == null) {
			times = 0;
		}

		Statusbar.setText(
				"Progress:" + String.valueOf(wordindex + 1) + "/" + WordQueue.size() + "          Try times:" + times);

		if (TestType.equals("voice")) {
			try {
				Start.LetterVoiceQueue.put(word.toLowerCase());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				logger.error("InterruptedException", e1);
			}
		}

	}

}
