package tools.xiaona.bookTxt2Excel.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import tools.xiaona.bookTxt2Excel.bean.ConvertableToExcel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析配置文件：BookTxt2ExcelConfig.json
 */
public class JsonConfigParser {

    //读取配置文件，打印指定类和字段的主逻辑代码
    public static ConvertConfig parseConfig(String filename) throws IOException, ClassNotFoundException {
        InputStream is = JsonConfigParser.class.getClassLoader().getResourceAsStream(filename);
        if (is == null) {
            throw new IOException("File not found: " + filename);
        }

        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        String content = new String(bytes, StandardCharsets.UTF_8);
        JSONObject json = JSON.parseObject(content);

        //convertClassAndFields
        Map<String, List<String>> convertClassAndFieldsMap = new LinkedHashMap<>(); //key: className, value: printFields
        JSONArray convertClassAndFields = json.getJSONArray("printClassAndFields");
        for (Object jsonObject : convertClassAndFields) {
            JSONObject o = (JSONObject) jsonObject;
            String className = (String) o.get("className");
            JSONArray fieldsArray = o.getJSONArray("fields");
            List<String> fields = fieldsArray.toJavaList(String.class);
            convertClassAndFieldsMap.put(className, fields);
        }

        //bottom_className
        String bottomClassName = (String) json.get("bottomClassName");
        System.out.println(bottomClassName);
        if(Class.forName(bottomClassName) == null){
            System.err.println("bottomClassName对应的类不存在！");
        }

        //targetExcelPath
        String targetExcelPath = (String) json.get("targetExcelPath");
        System.out.println(targetExcelPath);

        //sourceTxtFilePath
        String sourceTxtFilePath = (String) json.get("sourceTxtFilePath");
        System.out.println(sourceTxtFilePath);

        return new ConvertConfig(convertClassAndFieldsMap,
                (Class<ConvertableToExcel>) Class.forName(bottomClassName),
                targetExcelPath,
                sourceTxtFilePath);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(JsonConfigParser.parseConfig("tools/BookTxt2ExcelConfig.json"));
    }
}