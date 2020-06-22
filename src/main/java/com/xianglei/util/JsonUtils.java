package com.xianglei.util;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonUtils
 * json与bean之间的相互转化类
 *
 * @author Ricky.Z
 * @date 2018/8/17 00:43
 */
public abstract class JsonUtils {

    private static final JsonConfig JSONCONFIG = new JsonConfig();
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 将对象组装成json数据，应用场景如后台往前台返回json数据
     * 对于普通bean转成类似于{'a'='4','b'='2009-07-12'}的json数据 对于List转成类似于[{'a'='4','b'=
     * '2009-07-12'},{'a'='4','b'='2009-07-12'}]的json数据
     * 对于Map转成类似于{'a'='4','b'='2009-07-12'}的json数据
     *
     * @param object Object
     * @return String
     */
    public static String toJson(final Object object) {
        String jsonString = null;
        if (object != null) {
            if (object instanceof Collection || object instanceof Object[]) {
                jsonString = JSONArray.fromObject(object, JSONCONFIG)
                        .toString();
            } else {
                jsonString = JSONObject.fromObject(object, JSONCONFIG)
                        .toString();
            }
        }
        return jsonString;
    }

    public static JSONObject toJsonObject(final Object object) {
        String jsonString = null;

        return JSONObject.fromObject(object, JSONCONFIG);
    }

    /**
     * 从json串转换成实体对象
     *
     * @param jsonObjStr e.g. {'name':'get','dateAttr':'2009-11-12'}
     * @param clazz      e.g. Person.class
     * @return
     */
    public static <T> T jsonToBean(final String jsonObjStr,
                                   final Class<T> clazz) {
        return jsonToBean(jsonObjStr, clazz, null);
    }

    /**
     * 从json串转换成实体对象，并且实体集合属性存有另外实体Bean
     *
     * @param jsonObjStr e.g. {'data':[{'name':'get'},{'name':'set'}]}
     * @param clazz      e.g. MyBean.class
     * @param classMap   e.g. classMap.put("data", Person.class)
     * @return Object
     */
    public static <T> T jsonToBean(final String jsonObjStr,
                                   final Class<T> clazz, final Map<String, Class<?>> classMap) {
        return jsonToBean(JSONObject.fromObject(jsonObjStr), clazz, classMap);
    }

    /**
     * 把一个json数组串转换成存放普通类型元素的集合
     *
     * @param jsonArrStr e.g. ['get',1,true,null]
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<Object> jsonArrayToList(final String jsonArrStr) {
        return JSONArray.fromObject(jsonArrStr);
    }

    /**
     * 把一个json数组串转换成集合，且集合里存放的为实例Bean
     *
     * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}]
     * @param clazz      e.g. Person.class
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonArrayToList(final String jsonArrStr,
                                              final Class<T> clazz) {
        if (clazz == null) {
            return JSONArray.fromObject(jsonArrStr);
        }
        if (jsonArrStr == null) {
            return null;
        } else if ("[]".equals(jsonArrStr)) {
            return new ArrayList<T>();
        } else {
            List<T> beans = new ArrayList<T>();
            for (Iterator<JSONObject> it = JSONArray.fromObject(jsonArrStr)
                    .iterator(); it.hasNext(); ) {
                beans.add(jsonToBean(it.next(), clazz, null));
            }
            return beans;
        }
    }

    /**
     * 把json对象串转换成map对象
     *
     * @param jsonObjStr e.g. {'name':'get','int':1,'double',1.1,'null':null}
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(final String jsonObjStr) {
        return JSONObject.fromObject(jsonObjStr);
    }

    /**
     * 把json对象串转换成map对象，且map对象里存放的为其他实体Bean
     *
     * @param jsonObjStr e.g. {'data1':{'name':'get'},'data2':{'name':'set'}}
     * @param clazz      e.g. Person.class
     * @return Map
     */
    public static <T> Map<String, T> jsonToMap(final String jsonObjStr,
                                               final Class<T> clazz) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

        Map<String, T> map = new HashMap<String, T>(16);
        for (Iterator<?> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            map.put(key, jsonToBean(jsonObject.getJSONObject(key), clazz, null));
        }
        return map;
    }

    /**
     * 把json对象串转换成map对象，且map对象里存放的其他实体Bean还含有另外实体Bean
     *
     * @param jsonObjStr e.g. {'mybean':{'data':[{'name':'get'}]}}
     * @param clazz      e.g. MyBean.class
     * @param classMap   e.g. classMap.put("data", Person.class)
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> jsonToMap(final String jsonObjStr,
                                               final Class<T> clazz, final Map<String, Class<?>> classMap) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
        Map<String, T> map = new HashMap<>(16);
        for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = iter.next();
            map.put(key,
                    jsonToBean(jsonObject.getJSONObject(key), clazz, classMap));
        }
        return map;
    }

    /**
     * 把一个json数组串转换成集合，且集合里的对象的属性含有另外实例Bean
     *
     * @param jsonArrStr e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param clazz      e.g. MyBean.class
     * @param classMap   e.g. classMap.put("data", Person.class)
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonArrayToList(final String jsonArrStr,
                                              final Class<T> clazz, final Map<String, Class<?>> classMap) {
        if (jsonArrStr == null) {
            return null;
        } else if ("[]".equals(jsonArrStr)) {
            return new ArrayList<T>();
        } else {
            JSONArray jsonArray = JSONArray.fromObject(jsonArrStr);
            List<T> beans = new ArrayList<T>();
            for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext(); ) {
                beans.add(jsonToBean(it.next(), clazz, classMap));
            }
            return beans;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T jsonToBean(final JSONObject jsonObject,
                                    final Class<T> clazz, final Map<?, ?> classMap) {
        return (T) JSONObject.toBean(jsonObject, clazz, classMap);
    }

    static Pattern p = Pattern.compile("[\\\\|'|\\r|\\n]");

    /**
     * 将字符串中的\和'转义为\\和\'
     *
     * @param str
     * @return
     */
    public static String escape(final String str) {
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            if ("\\".equals(m.group())) {
                m.appendReplacement(sb, "\\\\\\\\");
            } else if ("'".equals(m.group())) {
                m.appendReplacement(sb, "\\\\'");
            } else if ("\r".equals(m.group())) {
                m.appendReplacement(sb, "\\\\r");
            } else if ("\n".equals(m.group())) {
                m.appendReplacement(sb, "\\\\n");
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将Json转换成List
     *
     * @param jsonObjStr e.g. {'deviceList':[{'agent':'','grantId':-1,'keyValue':'11111111','nt':'2222','resetCount':2,'saleType':1,'stateOld':1,'pid':1,'mac':'mac1','endUser':'','processId':-1,'id':1,'isReset':0,'sn':'1111','state':1,'account':'2','fromProxy':'JPN','remarks':''},{'agent':'','grantId':-1,'keyValue':'111123232','nt':'4444','resetCount':2,'saleType':1,'stateOld':1,'pid':1,'mac':'mac2','endUser':'','processId':-1,'id':2,'isReset':0,'sn':'3333','state':1,'account':'2','fromProxy':'JPN','remarks':''}],'taskList':[{'processTypeId':1,'handleUser':'','receiveRole':'1','sendUser':'zhangshan','receiveDate':'2017-08-04 15:28:21','readUser':'','processId':1,'handleLong':0,'handleDate':'','id':1,'taskNumber':1,'isPass':1,'remarks':'','taskConfId':1}],'order':{'endDate':'','applicantSource':1,'applicant':'','number':'NBSP000001','processId':1,'name':'TestOrder','id':1,'state':1,'orderDate':'2017-08-04 15:14:40','account':'','fromProxy':'JPN','ind':'000001','remarks':'1123123','taskId':1}}
     * @param key        e.g. 'deviceList'
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonArrayToList(final String jsonObjStr, final String key,
                                              final Class<T> clazz) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
            JSONArray array = jsonObject.getJSONArray(key);
            String jsonArrStr = array.toString();
            return jsonArrayToList(jsonArrStr, clazz);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 从json串转换成实体对象
     *
     * @param jsonObjStr e.g. {'deviceList':[{'agent':'','grantId':-1,'keyValue':'11111111','nt':'2222','resetCount':2,'saleType':1,'stateOld':1,'pid':1,'mac':'mac1','endUser':'','processId':-1,'id':1,'isReset':0,'sn':'1111','state':1,'account':'2','fromProxy':'JPN','remarks':''},{'agent':'','grantId':-1,'keyValue':'111123232','nt':'4444','resetCount':2,'saleType':1,'stateOld':1,'pid':1,'mac':'mac2','endUser':'','processId':-1,'id':2,'isReset':0,'sn':'3333','state':1,'account':'2','fromProxy':'JPN','remarks':''}],'taskList':[{'processTypeId':1,'handleUser':'','receiveRole':'1','sendUser':'zhangshan','receiveDate':'2017-08-04 15:28:21','readUser':'','processId':1,'handleLong':0,'handleDate':'','id':1,'taskNumber':1,'isPass':1,'remarks':'','taskConfId':1}],'order':{'endDate':'','applicantSource':1,'applicant':'','number':'NBSP000001','processId':1,'name':'TestOrder','id':1,'state':1,'orderDate':'2017-08-04 15:14:40','account':'','fromProxy':'JPN','ind':'000001','remarks':'1123123','taskId':1}}
     * @param key        e.g. 'order'
     * @param clazz
     * @return
     */
    public static <T> T jsonToBean(final String jsonObjStr, final String key,
                                   final Class<T> clazz) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
            Object obj = jsonObject.get(key);
            String objStr = obj.toString();
            return jsonToBean(objStr, clazz, null);
        } catch (Exception ex) {
            return null;
        }
    }

}
