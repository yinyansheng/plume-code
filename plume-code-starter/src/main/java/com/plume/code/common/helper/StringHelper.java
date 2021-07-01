package com.plume.code.common.helper;

import org.apache.commons.lang3.StringUtils;

public class StringHelper {
    public static String removePrefix(String value, String... prefix) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }

        if (null != prefix) {
            for (int i = 0; i < prefix.length; ++i) {
                String pf = prefix[i];
                if (value.toLowerCase().matches("^" + pf.toLowerCase() + ".*")) {
                    //截取前缀后面的字符串
                    return value.substring(pf.length());
                }
            }
        }

        return value;
    }

    public static String removeUnderline(String value) {
        if (value.contains("_")) {
            String[] keyArray = value.split("_");
            StringBuilder sb = new StringBuilder();
            boolean flag = false;
            for (String key : keyArray) {
                if (flag) {
                    //下划线后的首字母大写
                    sb.append(StringUtils.capitalize(key));
                } else {
                    flag = true;
                    sb.append(key);
                }
            }
            value = sb.toString();
        }
        return value;
    }

    public static String lowerFirstCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        char[] chars = str.toCharArray();
        //首字母小写方法，大写会变成小写，如果小写首字母会消失
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public static String upperFirstCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        char[] chars = str.toCharArray();
        //首字母小写方法，大写会变成小写，如果小写首字母会消失
        chars[0] -= 32;
        return String.valueOf(chars);
    }
}
