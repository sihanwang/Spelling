package com.sihanwang.study.spelling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static File CreateOrReturnFolder(String path) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
			logger.info("Created folder:" + path);
		}

		return folder;
	}

	/**
	 * 传入txt路径读取txt文件
	 * 
	 * @param txtPath
	 * @return 返回读取到的内容
	 */
	public static String readTxt(String txtPath) {
		File file = new File(txtPath);
		if (file.isFile() && file.exists()) {
			FileInputStream fileInputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;

			try {

				fileInputStream = new FileInputStream(file);
				inputStreamReader = new InputStreamReader(fileInputStream);
				bufferedReader = new BufferedReader(inputStreamReader);

				StringBuffer sb = new StringBuffer();
				String text = null;
				while ((text = bufferedReader.readLine()) != null) {
					sb.append(text);
				}
				
				return sb.toString();
			} catch (Exception e) {
				logger.error(e.getMessage());
				
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}

				if (inputStreamReader != null) {
					try {
						inputStreamReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}

				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}
				}

			}
		}
		return null;
	}

	/**
	 * 使用FileOutputStream来写入txt文件
	 * 
	 * @param txtPath txt文件路径
	 * @param content 需要写入的文本
	 */
	public static void writeTxt(String txtPath, String content) {
		FileOutputStream fileOutputStream = null;
		File file = new File(txtPath);
		try {
			if (file.exists()) {
				// 判断文件是否存在，如果不存在就新建一个txt
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (fileOutputStream!=null)
			{
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
			}
		}
	}
}
