package com.hero.mvcdemo.tools;

import android.content.Context;

import com.hero.mvcdemo.cons.AppConfig;
import com.hero.mvcdemo.tools.httprequest.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * asset文件中json数据
 * </pre>
 */
public class JsonRawReadUtils {
    /**
     * 获取asset中json文件 解析成 JSONObject数据
     *
     * @param fileName
     * @return
     */
    public static JSONObject getRawJsonObject(Context context, String fileName) {
        try {
            String json = FileUtils.getRawFlieString(context, FileUtils.getResIdFromName(context, fileName, AppConfig.RESTYPE_RAW));
            return new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取asset中json文件 解析成 list 数据
     *
     * @param fileName
     * @param t        bean类型
     * @return
     */
    public static List getRawJSONArray(Context context,String fileName, Class t) {
        List list = new ArrayList<>();

        try {
            String json = FileUtils.getRawFlieString(context,FileUtils.getResIdFromName(context, fileName, AppConfig.RESTYPE_RAW));
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jSONObject = (JSONObject) jsonArray.get(i);
                list.add(JsonUtils.Json2Object(jSONObject.toString(), t));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
