package com.sihanwang.study.spelling;

import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
	
	public static String reportFolder= null;
	
	public static String progressFolder=null;
	
	public static String reviewFolder=null;
	
	public static String test_type=null;
	
	public static String dailyReviewProgressFile=null;
	
	public static String dailyReviewListFolder=null;
	
	public static int dailyReviewBackDays=0;

	public static List<String> wordlist = null;

	public static String wordlist_name = null;
	
	public static GraphicsDevice device;
	
	public static DailyReviewProgress DReviewProgress=null;

	private static HashMap<String, byte[]> Spelling_Voice = new HashMap<String, byte[]>();
	private static HashMap<String, byte[]> SoundEffect = new HashMap<String, byte[]>();
	
	
	public static ArrayBlockingQueue<String> LetterVoiceQueue = new ArrayBlockingQueue<String>(30);
	
	public static EntryWindow EW;
	public static ReviewWindow RW;
	public static SpellingTestWindow STW;

	static {
		Properties prop = new Properties();
		PropertyConfigurator.configure(log4jcfg);
		device=GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		try {
			FileInputStream fis = new FileInputStream(spelling_cfg);
			prop.load(fis);
			
			/////////////////////////////////////////////////
			//Load configurations for daily review
			String dailyReviewCfg=prop.getProperty("daily_review_cfg").trim();
			FileInputStream fisDailyReviewCfg = new FileInputStream(dailyReviewCfg);
			
			Properties propDailyReviewProperties = new Properties();
			
			propDailyReviewProperties.load(fisDailyReviewCfg);
			
			dailyReviewProgressFile=propDailyReviewProperties.getProperty("dailyreview_progress_file").trim();
			
			dailyReviewListFolder=propDailyReviewProperties.getProperty("dailyreview_list_folder").trim();
			
			dailyReviewBackDays=Integer.valueOf(propDailyReviewProperties.getProperty("review_back_days").trim());
			
			
			/////////////////////////////////////////////////
			
			test_type = prop.getProperty("test_type").trim();
			
			vocabulary_path = prop.getProperty("vocabulary_folder").trim();
			
			reportFolder=prop.getProperty("report_folder").trim();
			
			reviewFolder=prop.getProperty("review_folder").trim();
			
			progressFolder=prop.getProperty("progress_folder").trim();
			
			String voice_path = prop.getProperty("voice_folder").trim();

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
			
			SoundEffect.put("bit", FileUtils.readFileToByteArray(new File(voice_path+"/effect", "bit.mp3")));
			SoundEffect.put("ok", FileUtils.readFileToByteArray(new File(voice_path+"/effect", "ok.mp3")));
			SoundEffect.put("cry", FileUtils.readFileToByteArray(new File(voice_path+"/effect", "cry.mp3")));
			SoundEffect.put("lok", FileUtils.readFileToByteArray(new File(voice_path+"/effect", "lok.mp3")));
			SoundEffect.put("soso", FileUtils.readFileToByteArray(new File(voice_path+"/effect", "soso.mp3")));
			
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
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(Start.vocabulary_path));
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.showDialog(new JLabel(), "Select");
					
					File file=chooser.getSelectedFile();
					if (file == null)
					{
						//Check if DailyReviewProgress exists
						File dailyReviewProgress = new File(progressFolder,dailyReviewProgressFile);
						
						if (dailyReviewProgress.exists())
						{
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dailyReviewProgress));
							DReviewProgress = (DailyReviewProgress) ois.readObject();
						}
						else
						{
							DReviewProgress = new DailyReviewProgress(1,new LinkedList<String>());
						}
						
						int dailyIndex=DReviewProgress.getDailyIndex();
						
						Start.wordlist_name="Daily" + "_" + String.format("%04d", dailyIndex) + ".txt";
						
						EW = new EntryWindow(new File(dailyReviewListFolder,Start.wordlist_name));
						setFullScreen(EW);
						
					}
					else if (file.getName().endsWith("progress")) {
						//continue previous test
						try {
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
							SpellingProgress oldProgress = (SpellingProgress) ois.readObject();
							
							DReviewProgress = oldProgress.getDReviewProgress();
							
							Start.wordlist_name = file.getName().substring(0, file.getName().indexOf("."));
					
							Start.STW = new SpellingTestWindow(oldProgress.getTotalwordnum(), oldProgress.getWordindex(),
									oldProgress.getWordQueue(), oldProgress.getErrorlist(), file, oldProgress.getTesttype());
							setFullScreen(Start.STW);
							
						} catch (Exception e) {
							logger.error("Error in reading progress", e);
						}
					}
					else
					{
						Start.wordlist_name = file.getName();
						EW = new EntryWindow(file);
						setFullScreen(EW);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static String ReadExplain(String word) {
		String manifestfolder = vocabulary_path;
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
	
	public static void PlayEffect(String effectname) {
		byte[] voice=SoundEffect.get(effectname);
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

	public static void PlayMp3(String word) {
		String manifestfolder = vocabulary_path ;
		String mp3_path = manifestfolder + file_separator + "mp3" + file_separator + word.toLowerCase() + ".mp3";

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
	
	public static void setFullScreen(JFrame window) {
		window.dispose();
		window.setUndecorated(true);
		window.setResizable(false);
		device.setFullScreenWindow(window);
		//window.setVisible(true);
	}
	
	public void restoreScreen() {
		Window window = device.getFullScreenWindow();
		if (window != null) {
			window.dispose();
		}
		device.setFullScreenWindow(null);
	}

}
