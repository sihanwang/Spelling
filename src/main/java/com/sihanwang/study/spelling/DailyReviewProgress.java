package com.sihanwang.study.spelling;

import java.io.Serializable;
import java.util.LinkedList;

public class DailyReviewProgress implements Serializable {
	

	public DailyReviewProgress(int dI, LinkedList<String> toBeReviewed) {
		super();
		this.dailyIndex = dI;
		this.toBeReviewed = toBeReviewed;
	}
	
	int dailyIndex;
	LinkedList <String> toBeReviewed=new LinkedList<String>();
	
	public int getDailyIndex() {
		return dailyIndex;
	}
	
	public LinkedList<String> getToBeReviewed() {
		return toBeReviewed;
	}
	
}
