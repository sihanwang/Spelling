package com.sihanwang.study.spelling;

import java.io.Serializable;
import java.util.ArrayList;

public class DailyReviewProgress implements Serializable {
	
	int lastCompleteIndex;
	ArrayList<String> toBeReviewed=new ArrayList<String>();
	
}
