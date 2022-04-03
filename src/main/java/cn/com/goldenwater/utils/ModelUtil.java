package cn.com.goldenwater.utils;

import cn.com.goldenwater.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuxl.auxuan on 2021/2/2.
 */
public class ModelUtil {

    /**
     * 读取模型参数文件到Map对象中
     * @param filePath
     * @return
     */
    public static Map<String, String> readPar(String filePath) {
        // 从文件中读取参数
        Map<String, String> parMap = new HashMap<>(); //临时存参数与参数值
        ArrayList arrayList1 = FileUtils.executeList(filePath);
        for (Object lineStr : arrayList1) {
            String[] split = lineStr.toString().trim().split("="); // 按等号分割
            if (split.length >= 2) {
                parMap.put(split[0].trim(), split[1].trim()); // 必须是两个值
            }
        }
        return parMap;
    }

}
