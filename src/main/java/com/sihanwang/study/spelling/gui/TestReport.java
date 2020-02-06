package com.sihanwang.study.spelling.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.JTextArea;

import com.sihanwang.study.spelling.Start;

public class TestReport extends JFrame  {
	
	public HashMap<String,Integer> ErrorReport=new HashMap<String,Integer>();
	public int score;

	private JPanel contentPane;
	private JTextArea table= new JTextArea();
	private JLabel  lblScore = new JLabel("Your Score:");
	JButton okButton = new JButton("OK");


	/**
	 * Create the dialog.
	 */
	public TestReport() {
		
	}
	
	public void initUI()
	{
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (score==100)
				{
					Start.PlayEffect("ok");
				}
				else if (score <80)
				{
					Start.PlayEffect("cry");
				}				
			}}).start();

		
		setTitle("Test Report");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		lblScore.setFont(new Font("Arial Unicode MS", Font.BOLD, 56));
		
		table.setBackground(SystemColor.window);
		table.setFont(new Font("Arial Unicode MS", Font.PLAIN, 22));
		table.setLineWrap(true);
		table.setEditable(false);
		
		///////////////////////////
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		contentPane.setLayout(gl_contentPane);
		
		GroupLayout.SequentialGroup hGroup = gl_contentPane.createSequentialGroup();
		hGroup.addGap(10);
		hGroup.addGroup(gl_contentPane.createParallelGroup()
				.addComponent(lblScore, GroupLayout.Alignment.LEADING)
				.addComponent(table, GroupLayout.Alignment.LEADING)
				.addComponent(okButton,GroupLayout.Alignment.TRAILING));
		hGroup.addGap(10);
	
		gl_contentPane.setHorizontalGroup(hGroup);
		
		
		GroupLayout.SequentialGroup vGroup = gl_contentPane.createSequentialGroup();
		vGroup.addGap(10);
		vGroup.addComponent(lblScore,70,70,70);
		vGroup.addGap(10);
		vGroup.addComponent(table,100,300,	1024);
		vGroup.addGap(10);
		vGroup.addComponent(okButton);
		vGroup.addGap(10);

		gl_contentPane.setVerticalGroup(vGroup);
		
		pack();
		
		
		///////////////////////////
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		
		
		///////////////////////////
		setSize(800,600);
		
		//set score;
		
		lblScore.setText("Your Score:"+score);
		
		String errorList=new String();
		
		for (Map.Entry<String, Integer> errorWord :  ErrorReport.entrySet())
		{
			errorList=errorList+errorWord.getKey()+":"+errorWord.getValue()+Start.line_separator;
		}
		
		table.setText(errorList);
		
		
	}

}
