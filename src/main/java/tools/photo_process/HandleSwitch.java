package tools.photo_process;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author xiuzhu 231203
 * 处理switch照片。
 * 源文件名字示例：2023111917214800.jpg
 */
public class HandleSwitch {
	private String path = "";	//file source folder path
	private HashMap<String, Integer> existedNames = new HashMap<String, Integer>();

	//constructor specify path and file type
	public HandleSwitch(String filePath){
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
						String pattern = "\\d{16}";
						boolean isMatch = Pattern.matches(pattern, nameWithoutPostfix);
						if(isMatch){
							//2023111917214800
							String newName = nameWithoutPostfix.substring(0,4)
									+ "-" + nameWithoutPostfix.substring(4,6) + "-" + nameWithoutPostfix.substring(6,8)
									+ "-" + nameWithoutPostfix.substring(8,10) + "." + nameWithoutPostfix.substring(10,12)
									+ "." + nameWithoutPostfix.substring(12,14);
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
		HandleSwitch r = new HandleSwitch("E:\\rename");
		r.rename();
	}
}