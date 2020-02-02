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
		DownloadWordList("/Users/jing.wang/Desktop/word/u7.txt");
	}

	public static void DownloadWordList(String wordlistfilepath) throws Exception
	{
		File fileWordList = new File(wordlistfilepath);
		
		String filename = fileWordList.getName();
		String manifestfolder = vocabulary_path + Start.file_separator + filename;
		String explain_path = manifestfolder + Start.file_separator + "explain" ;
		String mp3_path = manifestfolder + Start.file_separator + "mp3" ;
		// create a folder
		FileUtils.forceMkdir(new File(manifestfolder));
		FileUtils.forceMkdir(new File(explain_path));
		FileUtils.forceMkdir(new File(mp3_path));
		File Manifestwordlist= new File(manifestfolder,filename);
		List<String> Wordlist = FileUtils.readLines(new File(wordlistfilepath), "UTF-8");
		// write word list to a manifest file
		Iterator<String> it = Wordlist.iterator();
		while (it.hasNext()) {
			String word = it.next();
			DownloadAWord(word,explain_path,mp3_path);
			FileUtils.writeStringToFile(Manifestwordlist, word+Start.line_separator, "UTF-8", true); 
		}
	}

	public static void DownloadAWord(String word, String explain_path, String mp3_path) throws Exception {

		FanyiV3Util fv3U = new FanyiV3Util();

		YoudaoDictResponse YDDR = fv3U.requestForHttp(fv3U.createParams(word));

		// write basic.explains into a text file named with word
		Basic basicResponse = YDDR.getBasic();

		String translation_result = "";
		
		String Ukphonetic=basicResponse.getUkphonetic();
		
		if (Ukphonetic!=null && Ukphonetic != null)
		{
			translation_result=translation_result+"\tUK phonetic: /"+Ukphonetic+"/";
		}
		
		String Usphonetic=basicResponse.getUsphonetic();

		if (Usphonetic!=null && Usphonetic != null)
		{
			translation_result=translation_result+"\tUS phonetic: /"+Usphonetic+"/";
		}
		
		if (translation_result != null)
		{
			translation_result="Pronunciation:" + Start.line_separator+ translation_result + Start.line_separator;
		}
		
		translation_result=translation_result+"Translation:"+Start.line_separator;
		
		
		for (String i : basicResponse.explains) {
			translation_result = translation_result + "\t"+i + Start.line_separator;
		}

		FileUtils.writeStringToFile(new File(explain_path + Start.file_separator + word + ".txt"), translation_result,
				"UTF-8", false);

		// download voice by speakUrl and write a mp3 file named with work
		String MP3_Url = YDDR.getSpeakUrl();

		FileUtils.writeByteArrayToFile(new File(mp3_path + Start.file_separator + word + ".mp3"), fv3U.requestForMp3(MP3_Url),
				false);
	}
	
	

}
