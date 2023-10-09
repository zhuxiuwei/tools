package tools.bookTxt2Excel.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import tools.bookTxt2Excel.bean.ConvertableToExcel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析配置文件：BookTxt2ExcelConfig.yaml
 */
public class YamlConfigParser {

    public static ConvertConfig parseConfig(String filename) throws IOException, ClassNotFoundException {
        InputStream is = YamlConfigParser.class.getClassLoader().getResourceAsStream(filename);
        if (is == null) {
            throw new IOException("File not found: " + filename);
        }
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        String content = new String(bytes, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Map<String, Object> config = mapper.readValue(content, Map.class);
        String sourceTxtFilePath = (String) config.get("sourceTxtFilePath");
        String targetExcelPath = (String) config.get("targetExcelPath");
        String bottomClassName = (String) config.get("bottomClassName");
        String combineMultipleValuesToOneValueDelimiter = (String) config.get("combineMultipleValuesToOneValueDelimiter");
        if(Class.forName(bottomClassName) == null){
            System.err.println("bottomClassName对应的类不存在！");
            System.exit(0);
        }

        List<Map<String, Object>> printClassAndFields = (List<Map<String, Object>>) config.get("printClassAndFields");
        Map<String, List<String>> convertClassAndFieldsMap = new LinkedHashMap<>(); //key: className, value: printFields
        printClassAndFields.stream().forEach(kv -> {
            convertClassAndFieldsMap.put((String) kv.get("className"), (List<String>) kv.get("fields"));
        });

        List<Map<String, Object>> excludedClassFieldsInExcel = (List<Map<String, Object>>) config.get("excludedClassFieldsInExcel");
        Map<String, List<String>> excludedClassFieldsInExcelMap = new HashMap<>(); //key: className, value: printFields
        excludedClassFieldsInExcel.stream().forEach(kv -> {
            excludedClassFieldsInExcelMap.put((String) kv.get("className"), (List<String>) kv.get("fields"));
        });

        return new ConvertConfig(convertClassAndFieldsMap,
                (Class<ConvertableToExcel>) Class.forName(bottomClassName),
                targetExcelPath,
                sourceTxtFilePath,
                excludedClassFieldsInExcelMap,
                combineMultipleValuesToOneValueDelimiter);
    }

    //for debug
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(YamlConfigParser.parseConfig("tools/BookTxt2ExcelConfig.yaml"));
    }
}