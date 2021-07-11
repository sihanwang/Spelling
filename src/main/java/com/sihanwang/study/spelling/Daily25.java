package com.sihanwang.study.spelling;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.HashSet;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Daily25 {
	
	private static final String log4jcfg = "../cfg/log4j.properties";

	final static int WORD_NUM = 18;
	static String inputPath = "/Users/jing.wang/github/Spelling/daily25/backlog";
	//static String inputPath = "/Users/jing.wang/temp/reviewinput";
	static String vocabularyPath = "/Users/jing.wang/github/Spelling/daily25";
	//static String vocabularyPath = "/Users/jing.wang/temp/review";
	static String lineSeparator = System.getProperty("line.separator");
	static HashSet<String> VocabularyLib = new HashSet<String>();
	static LinkedList<String> NewLib = new LinkedList<String>();
	static LinkedList<File> AvailableFiles = new LinkedList<File>();
	static Logger logger = LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure(log4jcfg);
		List<File> allSourceFile = searchFiles(new File(vocabularyPath));

		// load existing words into vocabulary library
		for (File thisFile : allSourceFile) {
			List<String> allWordsInFile = FileUtils.readLines(thisFile);
			for (String word : allWordsInFile) {
				if (!VocabularyLib.add(word.trim())) {
					logger.info(word + " has existed in vocabulary library");
				}
			}

			if (allWordsInFile.size() < WORD_NUM) {
				AvailableFiles.add(thisFile);
			}
		}

		// load new words into new library

		List<File> newWordFile = searchFiles(new File(inputPath));

		for (File thisFile : newWordFile) {
			List<String> allNewWordsInFile = FileUtils.readLines(thisFile);
			for (String word : allNewWordsInFile) {
				NewLib.add(word.trim());
			}
		}

		for (String word : NewLib) {
			if (!VocabularyLib.contains(word.trim())) {
				if (!AvailableFiles.isEmpty()) {
					File theFirstFile = AvailableFiles.getFirst();
					FileUtils.writeStringToFile(theFirstFile, word + Start.line_separator, "UTF-8", true);

					List<String> wordsInFile = FileUtils.readLines(theFirstFile);
					if (wordsInFile.size() >= WORD_NUM) {
						AvailableFiles.removeFirst();
					}
				} else {
					// create new word file
					File NewDailyFile = new File(vocabularyPath, "Daily" + "_" + String.format("%04d", allSourceFile.size()+1) + ".txt");
					FileUtils.writeStringToFile(NewDailyFile, word + Start.line_separator, "UTF-8", false);
					allSourceFile.add(NewDailyFile);
					logger.info("Created new daily file:"+NewDailyFile.getAbsolutePath());
					
					if (WORD_NUM > 1) {
						AvailableFiles.add(NewDailyFile);
					}
				}
				VocabularyLib.add(word);
			} else {
				logger.info(word+" has been added before");
				continue;
			}
		}
		
		System.out.println("Done");
	}

	public static List<File> searchFiles(File folder) {
		List<File> result = new LinkedList<File>();
		if (folder.isFile() && !folder.getName().startsWith(".")) {
			result.add(folder);
		} else {
			File[] fileOrFolder = folder.listFiles();

			if (fileOrFolder != null) {
				for (File file : fileOrFolder) {
					if (file.isFile()&& !file.getName().startsWith(".")) {
						// 如果是文件则将文件添加到结果列表中
						result.add(file);
						/*
						 * } else { // 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中 result.addAll(searchFiles(file));
						 * }
						 */
					}
				}
			}
		}
		return result;
	}

}
