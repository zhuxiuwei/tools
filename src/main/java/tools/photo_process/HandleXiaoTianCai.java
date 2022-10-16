package tools.photo_process;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author xiuzhu 221016
 * 处理小天才z8照片。
 * 文件名是unix时间戳到毫秒级。
 */
public class HandleXiaoTianCai {
	private String path = "";	//file source folder path
	private HashMap<String, Integer> existedNames = new HashMap<String, Integer>();

	//constructor specify path and file type
	public HandleXiaoTianCai(String filePath){
		this.path = filePath;
	}
	
	//rename method
	public void rename() {
		File sourceFolder = new File(path);
		if (sourceFolder.isDirectory()) {
			String orginalNames[] = sourceFolder.list();
			for (String s : orginalNames) {
				File f = new File(sourceFolder.getAbsolutePath() + "\\" + s);
				if (f.isFile()) {
					String name = f.getName();
					String postfix = name.substring(name.lastIndexOf("."));
					String nameWithoutPostfix = name.substring(0, name.lastIndexOf("."));
					try {
						String pattern = "\\d*";
						boolean isMatch = Pattern.matches(pattern, nameWithoutPostfix);
						if(isMatch){
							String newName = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss", Locale.CHINA).format(new Date(Long.parseLong(nameWithoutPostfix)));
							System.out.println(sourceFolder.getAbsolutePath() + "\\" + newName + postfix);
							f.renameTo(new File(sourceFolder.getAbsolutePath() + "\\" + newName + postfix));
							System.out.println(name + " -> " + newName + postfix);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}else{
			System.out.println(sourceFolder + " is not a legal directory!");
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java HandleXiaoTianCai <path> ");
			return;
		}
		HandleXiaoTianCai r = new HandleXiaoTianCai(args[0]);
		r.rename();
	}
}