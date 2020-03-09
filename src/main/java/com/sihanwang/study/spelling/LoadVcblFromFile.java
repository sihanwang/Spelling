package com.sihanwang.study.spelling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadVcblFromFile {

	private final static String spelling_cfg = "../cfg/spelling.properties";

	private static final String log4jcfg = "../cfg/log4j.properties";

	private static Logger logger = LoggerFactory.getLogger(LoadVcblFromFile.class);

	private static String vocabulary_path;

	static {
		Properties prop = new Properties();
		PropertyConfigurator.configure(log4jcfg);

		try {
			FileInputStream fis = new FileInputStream(spelling_cfg);
			prop.load(fis);
			vocabulary_path = prop.getProperty("vocabulary_folder");
		} catch (Exception e) {
			logger.error("Can't load Spelling configuration file!", e);
		}
	}

	public static void main(String[] args) throws Exception {
		// DownloadWordList(args[0]);
		
	;
		
		File file = new File("/Users/jing.wang/Desktop/word/WWW4A_DAY9.txt");
		
		DownloadWordList(file);
		logger.info("Done");
		System.exit(0);
	}

	public static void DownloadWordList(File fileWordList) throws Exception {
		
		String filename = fileWordList.getName().substring(0, fileWordList.getName().indexOf("."));
		String manifest_file = vocabulary_path + Start.file_separator + filename;
		String explain_path = vocabulary_path + Start.file_separator + "explain";
		String mp3_path = vocabulary_path + Start.file_separator + "mp3";
		// create a folder
		FileUtils.forceMkdir(new File(explain_path));
		FileUtils.forceMkdir(new File(mp3_path));
		File Manifestwordlist = new File(manifest_file);

		FileUtils.writeStringToFile(Manifestwordlist, "", false); // clear contents
		List<String> Wordlist = FileUtils.readLines(fileWordList, "UTF-8");
		// write word list to a manifest file
		Iterator<String> it = Wordlist.iterator();
		while (it.hasNext()) {
			String word = it.next();
			if (word.trim().equals("")) {
				continue;
			} else {

				DownloadAWord(word, explain_path, mp3_path);
				FileUtils.writeStringToFile(Manifestwordlist, word + Start.line_separator, "UTF-8", true);
			}
		}
	}

	public static void DownloadAWord(String word, String explain_path, String mp3_path) throws Exception {

		File explain_txt = new File(explain_path + Start.file_separator + word + ".txt");
		File mp3_file = new File(mp3_path + Start.file_separator + word + ".mp3");

		if (!explain_txt.exists() || !mp3_file.exists()) {

			FanyiV3Util fv3U = new FanyiV3Util();

			YoudaoDictResponse YDDR = fv3U.requestForHttp(fv3U.createParams(word));

			if (!YDDR.getErrorCode().equals("0")) {
				logger.error("#######################################################");
				logger.error("Can not download word \"" + word + "\" from Youdao! because of error code:"
						+ YDDR.getErrorCode());
				logger.error("#######################################################");
				return;
			}

			// write basic.explains into a text file named with word
			Basic basicResponse = YDDR.getBasic();

			String translation_result = "";

			String Ukphonetic = basicResponse.getUkphonetic();

			if (Ukphonetic != null && Ukphonetic != null) {
				translation_result = translation_result + "UK phonetic: /" + Ukphonetic + "/\t";
			}

			String Usphonetic = basicResponse.getUsphonetic();

			if (Usphonetic != null && Usphonetic != null) {
				translation_result = translation_result + "US phonetic: /" + Usphonetic + "/";
			}

			if (translation_result != null) {
				translation_result = "#Pronunciation#" + Start.line_separator + translation_result
						+ Start.line_separator + Start.line_separator;
			}

			translation_result = translation_result + "#Translation#" + Start.line_separator;

			for (String i : basicResponse.explains) {

				int pos = i.toLowerCase().indexOf(word.toLowerCase());
				while (pos != -1) {
					try {
						i = i.substring(0, pos) + i.substring(pos + word.length());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pos = i.toLowerCase().indexOf(word.toLowerCase());
				}

				translation_result = translation_result + i + Start.line_separator;
			}

			FileUtils.writeStringToFile(explain_txt, translation_result, "UTF-8", false);

			String MP3_Url = YDDR.getSpeakUrl();
			FileUtils.writeByteArrayToFile(mp3_file, fv3U.requestForMp3(MP3_Url), false);
		}
		else
		{
			logger.info("Word:"+word+" has been in existence, no need to download it again!");
		}

	}

}
