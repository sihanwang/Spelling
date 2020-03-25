package com.sihanwang.study.spelling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;

public class FakeProgress {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LinkedList<String> toBeReviewd=new LinkedList<String>();
		
		toBeReviewd.add("Daily_0006.txt.20200324030900.review");
		toBeReviewd.add("Daily_0005.txt.20200323101507.review");
		toBeReviewd.add("Daily_0001.txt.20200318112642.review");
		toBeReviewd.add("Daily_0004.txt.20200322011715.review");
		toBeReviewd.add("Daily_0003.20200321080129.review");
		toBeReviewd.add("Daily_0002.txt.20200320114727.review");
		
		
		DailyReviewProgress DRP=new DailyReviewProgress(7, toBeReviewd);
		
		File dailReviewProgressFile = new File("/Users/jing.wang/github/Spelling/progress", "dailyreview.progress");
		
		if (dailReviewProgressFile.exists())
		{
			try {
				FileUtils.forceDelete(dailReviewProgressFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.print("Failed to delete old daily progress file:"+dailReviewProgressFile.getName());
			}
		}
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dailReviewProgressFile));
			oos.writeObject(DRP);
			oos.close();
		} catch (Exception e1) {
			System.out.print("Can't write daily progress into file");
		}
		
	}

}
