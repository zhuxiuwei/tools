package tools.photo_process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Get rid of duplicated wechat videos. How to judge duplicate:
 * If videos of the same day get the same file size, then consider them to be duplicate.
 * @author xiuzhu
 * 2015-10-3
 */
public class HandleDuplicate {

	public String path = "";
	private String NOTVIDEOPATH = "";
	private String DUPLICATEDPATH = "";	//duplicate files of same day
	private String DUPLICATEDPATH2 = "";	//duplicate files of different days
	private String DUPLICATEDPATH3 = "";	//duplicate files of similar size

	private String LOGPATH = "";
	private FileWriter fw = null;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	//NOTICE: Just need to match to date. No need to specify time(hour, minute, second)

	public static native long GetCompressedFileSize(String filename);

	//Constructor
	public HandleDuplicate(String filePath){
		//Create folder
		this.path = filePath;
		this.NOTVIDEOPATH = path + File.separator + "notvideo";
		this.DUPLICATEDPATH =  path + File.separator + "duplicated";
		this.DUPLICATEDPATH2 =  path + File.separator + "duplicated2";
		this.DUPLICATEDPATH3 =  path + File.separator + "duplicated3";
		this.LOGPATH = path + File.separator + "log";
		File notVideoFolder = new File(NOTVIDEOPATH);
		File dupeFolder = new File(DUPLICATEDPATH);
		File dupeFolder2 = new File(DUPLICATEDPATH2);
		File dupeFolder3 = new File(DUPLICATEDPATH3);
		File logFolder = new File(LOGPATH);
		if(!notVideoFolder.exists())
			notVideoFolder.mkdirs();
		if(!dupeFolder.exists())
			dupeFolder.mkdirs();
		if(!dupeFolder2.exists())
			dupeFolder2.mkdirs();
		if(!dupeFolder3.exists())
			dupeFolder3.mkdirs();
		if(!logFolder.exists())
			logFolder.mkdirs();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			fw = new FileWriter(new File(this.LOGPATH + File.separator + sdf.format(new Date()) + ".log"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Handle dupliacates of same day
	public void handle(){
		System.out.println("*** Reminder: Execute RenameByModifiedTime.java before execute this. ***");
		File sourceFolder = new File(path);
		if (sourceFolder.isDirectory()) {
			File files[] = sourceFolder.listFiles();
			Map<String, String> dupMap = new TreeMap<String, String>();

			//视频后缀
			Set<String> videoPostfixes = new HashSet<>();
			videoPostfixes.add(".mp4");
			videoPostfixes.add(".mov");

			for (File file : files) {
				if(file.isDirectory())
					continue;

				String name = file.getName().toLowerCase();
				String postfix = name.substring(name.lastIndexOf(".")).toLowerCase();
				//if not video, move to "notvideo" folder
				if(videoPostfixes.contains(postfix))
					moveFile(file, NOTVIDEOPATH);

				//handle duplicate videos
				String day = this.getDayFromFileName(file.getName());
				if(day.equals(""))	//Failed to extract date from file, skip this file.
					continue;

				String key = day + ":" + file.length();	//map key: day + file size, e.g. 20151002:202863
				if(!dupMap.containsKey(key))
					dupMap.put(key, name);
				else{	//duplicate file, move to duplicated folder
					String v = dupMap.get(key);
					v = v + "," + name;
					dupMap.put(key, v);
					moveFile(file, DUPLICATEDPATH);
				}
			}

			//log updates
			logDuplications(dupMap);

		}else{
			System.out.println(path + "is a file, skip");
		}
	}

	//Handle duplicates of different days
	public void handleLevel2(){
		System.out.println("*** Handle duplicates from different days ***");
		File sourceFolder = new File(path);
		File files[] = sourceFolder.listFiles();
		Map<Long, String> dupMap2 = new TreeMap<Long, String>();
		Set<File> firstDuplicatedNames  = new HashSet<File>();
		for (File file : files) {
			if(file.isDirectory())
				continue;

			String name = file.getName();

			//handle duplicate videos
			Long key = file.length();
			if(!dupMap2.containsKey(key)){
				dupMap2.put(key, name);
			}
			else{	//duplicate file, move to duplicated folder
				String v = dupMap2.get(key);
				if(!v.contains(","))
					firstDuplicatedNames.add(new File(this.path + File.separator + v));
				v = v + "," + name;
				dupMap2.put(key, v);
				moveFile(file, DUPLICATEDPATH2);
			}
		}

		//move files in firstDuplicatedNames to DUPLICATEDPATH2 to.
		//This is to facility confirmation if it's really duplicated later.
		for (File file : firstDuplicatedNames)
			moveFile(file, DUPLICATEDPATH2);

		//log updates
		logDuplications2(dupMap2);
	}

	//Handle files of similar size
	public void handleSimilarSize(){
		int sizeDeltaThreshold = 100;
		System.out.println("*** Handle files with similar size ***");
		File sourceFolder = new File(path);
		File files[] = sourceFolder.listFiles();
		List<File> similarSizeFilesToMove  = new ArrayList<>();
		List<File> fileList = new ArrayList<>();
		for (File file : files) {
			if(file.isDirectory())
				continue;
			fileList.add(file);
		}
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File file, File t1) {
				return (int)(file.length() - t1.length());
			}
		});
		for (int i = 1; i < fileList.size(); i++) {
			int delta = (int)Math.abs(fileList.get(i).length() - fileList.get(i - 1).length());
			if(delta <= sizeDeltaThreshold){
				similarSizeFilesToMove.add(fileList.get(i));
				similarSizeFilesToMove.add(fileList.get(i-1));
			}
		}
		//log updates
		logSimilarSizeFiles(similarSizeFilesToMove);

		//move files
		for (File file : similarSizeFilesToMove)
			moveFile(file, DUPLICATEDPATH3);
	}

	//Extract date from filename.
	private String getDayFromFileName(String Filename){
		Date date = null;
		try {
			date = sdf.parse(Filename);
		} catch (ParseException e) {
			System.out.println("Exception when convert date format: " + Filename);
			return "";
		}
		String year = (1900 + date.getYear()) + "";
		String month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1 + "";
		String day = date.getDate() < 10 ? "0" + date.getDate(): date.getDate() + "";
		return year + month + day;
	}

	//Move file to another folder
	private boolean moveFile(File file, String targetFolder){
		System.out.println("Moving '" + file + "' to folder '" + targetFolder + "'");
		File dest = new File(targetFolder + File.separator + file.getName());
		return file.renameTo(dest);
	}

	//Log contents in dupMap
	public void logDuplications(Map<String, String> dupMap){
		try {
			Set<String> keySet = dupMap.keySet();
			fw.write( "Date" + "\t\t\t" + "Size" + "\t\t" + "FileNames\r\n");
			for(String key: keySet){
				String v = dupMap.get(key);
				int size = v.split(",").length;
				if(size > 1)
					fw.write( key.split(":")[0] + "\t\t" + key.split(":")[1] + "\t\t" + dupMap.get(key) + "\r\n");
			}

			fw.flush();
		} catch (IOException e) {
			System.out.println("Exception when logging duplicated files:" + e.getMessage());
		}
	}

	//Log contents in dupMap2
	public void logDuplications2(Map<Long, String> dupMap){
		try {
			Set<Long> keySet = dupMap.keySet();
			fw.write( "\r\n----------------------------------------------------\r\n");
			fw.write( "Size" + "\t\t" + "FileNames\r\n");
			for(Long key: keySet){
				String v = dupMap.get(key);
				int size = v.split(",").length;
				String[]  sssss = v.split(",");
				if(size > 1)
					fw.write( key + "\t\t" + dupMap.get(key) + "\r\n");
			}

			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("Exception when logging duplicated files:" + e.getMessage());
		}
	}

	//Log contents in SimilarSizeFiles
	public void logSimilarSizeFiles(List<File> files){
		try {
			fw.write( "\r\n-------------------------SimilarSizeFiles---------------------------\r\n");
			fw.write( "Size" + "\t\t" + "FileNames\r\n");
			for(File file: files){
				fw.write( file.getName() + "\t\t" + file.length() + "\r\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("Exception when logging duplicated files:" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java HandleDuplicate <path>");
			return;
		}
		HandleDuplicate r = new HandleDuplicate(args[0]);
		r.handle();
		r.handleLevel2();	//seems this is not necessary now, since handleSimilarSize() on board. //2021-4-11
		r.handleSimilarSize();
	}

}