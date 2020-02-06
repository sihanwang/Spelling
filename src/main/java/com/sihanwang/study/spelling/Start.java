package com.sihanwang.study.spelling;

import java.awt.EventQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sihanwang.study.spelling.gui.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Start {

	private final static String spelling_cfg = "../cfg/spelling.properties";

	private static final String log4jcfg = "../cfg/log4j.properties";

	public static final String line_separator = System.getProperty("line.separator");

	public static final String file_separator = System.getProperty("file.separator");

	private static Logger logger = LoggerFactory.getLogger(Start.class);

	public static String vocabulary_path = null;

	public static List<String> wordlist = null;

	public static String wordlist_name = null;

	public static boolean review_spelling = true;

	private static HashMap<String, byte[]> Spelling_Voice = new HashMap<String, byte[]>();
	
	public static ArrayBlockingQueue<String> LetterVoiceQueue = new ArrayBlockingQueue<String>(30);
	
	private static EntryWindow EW;

	static {
		Properties prop = new Properties();
		PropertyConfigurator.configure(log4jcfg);

		try {
			FileInputStream fis = new FileInputStream(spelling_cfg);
			prop.load(fis);
			vocabulary_path = prop.getProperty("vocabulary_folder");
			if (prop.getProperty("review_spelling").equals("true")) {
				review_spelling = true;
			} else {
				review_spelling = false;
			}
			String voice_path = prop.getProperty("voice_folder");

			Spelling_Voice.put("a", FileUtils.readFileToByteArray(new File(voice_path, "a.mp3")));
			Spelling_Voice.put("b", FileUtils.readFileToByteArray(new File(voice_path, "b.mp3")));
			Spelling_Voice.put("c", FileUtils.readFileToByteArray(new File(voice_path, "c.mp3")));
			Spelling_Voice.put("d", FileUtils.readFileToByteArray(new File(voice_path, "d.mp3")));
			Spelling_Voice.put("e", FileUtils.readFileToByteArray(new File(voice_path, "e.mp3")));
			Spelling_Voice.put("f", FileUtils.readFileToByteArray(new File(voice_path, "f.mp3")));
			Spelling_Voice.put("g", FileUtils.readFileToByteArray(new File(voice_path, "g.mp3")));
			Spelling_Voice.put("h", FileUtils.readFileToByteArray(new File(voice_path, "h.mp3")));
			Spelling_Voice.put("i", FileUtils.readFileToByteArray(new File(voice_path, "i.mp3")));
			Spelling_Voice.put("j", FileUtils.readFileToByteArray(new File(voice_path, "j.mp3")));
			Spelling_Voice.put("k", FileUtils.readFileToByteArray(new File(voice_path, "k.mp3")));
			Spelling_Voice.put("l", FileUtils.readFileToByteArray(new File(voice_path, "l.mp3")));
			Spelling_Voice.put("m", FileUtils.readFileToByteArray(new File(voice_path, "m.mp3")));
			Spelling_Voice.put("n", FileUtils.readFileToByteArray(new File(voice_path, "n.mp3")));
			Spelling_Voice.put("o", FileUtils.readFileToByteArray(new File(voice_path, "o.mp3")));
			Spelling_Voice.put("p", FileUtils.readFileToByteArray(new File(voice_path, "p.mp3")));
			Spelling_Voice.put("q", FileUtils.readFileToByteArray(new File(voice_path, "q.mp3")));
			Spelling_Voice.put("r", FileUtils.readFileToByteArray(new File(voice_path, "r.mp3")));
			Spelling_Voice.put("s", FileUtils.readFileToByteArray(new File(voice_path, "s.mp3")));
			Spelling_Voice.put("t", FileUtils.readFileToByteArray(new File(voice_path, "t.mp3")));
			Spelling_Voice.put("u", FileUtils.readFileToByteArray(new File(voice_path, "u.mp3")));
			Spelling_Voice.put("v", FileUtils.readFileToByteArray(new File(voice_path, "v.mp3")));
			Spelling_Voice.put("w", FileUtils.readFileToByteArray(new File(voice_path, "w.mp3")));
			Spelling_Voice.put("x", FileUtils.readFileToByteArray(new File(voice_path, "x.mp3")));
			Spelling_Voice.put("y", FileUtils.readFileToByteArray(new File(voice_path, "y.mp3")));
			Spelling_Voice.put("z", FileUtils.readFileToByteArray(new File(voice_path, "z.mp3")));

		} catch (Exception e) {
			logger.error("Can't load Spelling configuration file!", e);
		}
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true)
				{
					try {
						String Letter=LetterVoiceQueue.take();
						if(Letter.length()==1)
						{
							PlayLetter(Letter);
						}
						else
						{
							PlayMp3(Letter);
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error("InterruptedException", e);
					}
				}
			}}).start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EW = new EntryWindow();
					EW.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static String ReadExplain(String word) {
		String manifestfolder = vocabulary_path + file_separator + wordlist_name;
		String explain_path = manifestfolder + file_separator + "explain";

		String Result = null;
		try {
			Result = FileUtils.readFileToString(new File(explain_path, word + ".txt"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error reading explanation", e);
		}

		return Result;
	}

	public static void PlayLetter(String letter)
	{
		byte[] voice=Spelling_Voice.get(letter);
		if (voice==null)
		{
			return;
		}
		
		ByteArrayInputStream BAIS = new ByteArrayInputStream(voice);
		Player player=null;
		try {
			player = new Player(BAIS);
			player.play();
			player.close();
		} catch (JavaLayerException e1) {
			// TODO Auto-generated catch block
			logger.error("Cannot play Mp3", e1);
		}
		try {
			BAIS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Can't close inputstream", e);
		}
		
	}

	public static void PlaySpelling(String word) {
		word=word.toLowerCase();
		try {
			for (int i = 0; i < word.length(); i++) {
				String subStr = word.substring(i, i + 1);
				Start.LetterVoiceQueue.put(subStr);
			}
			Start.LetterVoiceQueue.put(word);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			logger.error("InterruptedException", e1);
		}
	}

	public static void PlayMp3(String word) {
		String manifestfolder = vocabulary_path + file_separator + wordlist_name;
		String mp3_path = manifestfolder + file_separator + "mp3" + file_separator + word + ".mp3";

		try {
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(mp3_path));
			Player player = new Player(stream);
			player.play();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Mp3 file not found!", e);
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			logger.error("Cannot play Mp3", e);
		}

	}
}
