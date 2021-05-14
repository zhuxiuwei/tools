package tools.mprocess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;


public class Process {
    private String logFilePath;
    private String rootPath;
    private String target;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    public Process(){
        logFilePath = String.format("D:\\res-%s.csv", sdf.format(new Date()));
        try {
            rootPath = new String(Base64.getDecoder().decode("RDpcZWFtZGF0YVxkYlw="), "UTF-8") ;
            System.out.println(rootPath);
            target = new String(Base64.getDecoder().decode("c2NyZWVu"), "UTF-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void doIt(){
        CopyOnWriteArrayList<String> records = new CopyOnWriteArrayList();
        File files[] = new File(rootPath).listFiles();
        Arrays.stream(files).filter(f -> f.isDirectory()).parallel().forEach(f -> {
            if(f != null) {
                System.out.println("Process " + f.getName() + " Start.");
                File files2[] = f.listFiles();
                Arrays.stream(files2).parallel().filter(f2 -> targetExists(f2.getAbsolutePath())).forEach(f2 -> {
                    List<File> dayFs = Arrays.stream(new File(f2.getAbsolutePath() + File.separator + target).listFiles()).parallel().
                            filter(f3 -> f3.isDirectory() && Pattern.matches("\\d{8}", f3.getName()))
                            .collect(toList());
                    if (dayFs.size() > 0) {
                        int dirNum = dayFs.size();
                        Collections.sort(dayFs);
                        String lastDir = dayFs.get(dayFs.size() - 1).getName();
                        int total = 0;
//                        for (File dayF : dayFs) {     //too slow
//                            total += dayF.listFiles().length;
//                        }
                        records.add(f.getName() + "," + f2.getName() + "," + dirNum + "," + lastDir + ","
                                + (double) total / (double) dirNum + ","
                                + rootPath + f.getName() + File.separator + f2.getName() + File.separator + target);
                    }

                });
            }
            System.out.println("Process " + f.getName() + " Done." );
        });
        System.out.println("Done. Records: " + records.size());
        try( FileWriter fw = new FileWriter(logFilePath)) {
            for(String r: records){
                fw.write(r + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean targetExists(String path){
        File file = new File(path + File.separator + target);
        return file.exists();
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        new Process().doIt();
    }
}
