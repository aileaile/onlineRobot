package ll.config;

import ll.pojo.PosPojo;

import java.util.HashMap;
import java.util.Map;

public class
AppConfig {

    public static Map<String,Object> map = new HashMap<>();


    public static String getStr(String key){

        return (String)map.get(key);

    }

    public static int getInt(String key){

        return (int)map.get(key);

    }

    public static PosPojo getPos(String key){

        return (PosPojo)map.get(key);

    }

    public static Double getDouble(String key){

        return (Double)map.get(key);

    }



}
