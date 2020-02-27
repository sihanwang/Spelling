package com.sihanwang.study.spelling;


import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


public class daily50 {
	
	static String sourcePath="";
	static String targetPath="";
	static String lineSeparator = System.getProperty("line.separator");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<File> allSourceFile=searchFiles(new File(sourcePath));

	}


    public static List<File> searchFiles(File folder) {
        List<File> result = new ArrayList<File>();
        if (folder.isFile())
            result.add(folder);
 
        File[] subFolders = folder.listFiles();
        
        if (subFolders != null) {
            for (File file : subFolders) {
                if (file.isFile()) {
                    // 如果是文件则将文件添加到结果列表中
                    result.add(file);
                } else {
                    // 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                    result.addAll(searchFiles(file));
                }
            }
        }
        return result;
    }


}
