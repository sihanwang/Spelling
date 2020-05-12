package com.sihanwang.study.spelling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SpellingProgress implements Serializable {
	
	private int totalwordnum;
	private int wordindex ;
	private HashMap<String, Integer> errorlist ;
	private ArrayList<String> WordQueue;
	private String testtype;
	private DailyReviewProgress DReviewProgress=null;

	public SpellingProgress(int twn, int wi, HashMap<String, Integer> el, ArrayList<String> wq, String tt, DailyReviewProgress DRP) {
		super();
		this.totalwordnum = twn;
		this.wordindex = wi;
		this.errorlist = el;
		this.WordQueue = wq;
		this.testtype=tt;
		this.DReviewProgress=DRP;
	}
	
	public int getTotalwordnum()
	{
		return totalwordnum;
	}

	public int getWordindex() {
		return wordindex;
	}

	public HashMap<String, Integer> getErrorlist() {
		return errorlist;
	}

	public ArrayList<String> getWordQueue() {
		return WordQueue;
	}
	
	public String getTesttype() {
		return testtype;
	}

	public DailyReviewProgress getDReviewProgress() {
		return DReviewProgress;
	}
	
}
