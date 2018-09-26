/*
 * Copyright (c) 2015 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.qjd.rry.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * TextUtil.
 * @author <A HREF="mailto:ljunjie@qiujieda.com">JunJie.Lu</A>
 * @version 1.0, $Revision: 0$, $Date: Jun 15, 2015$
 * @since 1.0
 */
public class TextUtil
{
    /**
     * Converts a binary array to readable text using Bas64 encoding.
     * @param input the byte array to be encode.
     * @return an encoded string.
     * @see Base64#encode(byte[], int, int)
     * @since 1.0
     */
    public static String encode(byte[] input)
    {
        return (input == null ? null : Base64.encode(input, 0, input.length));
    }
    
    /**
     * Decodes Base64-encoded string back to a byte array.
     * This is the reverse of Base64.encode(). Note that if you are decoding
     * MIME objects, the string may contain carriage returns. Make sure all
     * carriage returns are stripped before calling this method. You can do
     * TextUtil.replace(s, "\n", "").
     * @param input the Base64 encoded string to decode.
     * @return an decoded byte array.
     * @see Base64#decode(String)
     * @since 1.0
     */
    public static byte[] decode(String input)
    {
        return Base64.decode(input);
    }
    
    /**
     * Tests to see if a string is empty.
     * @param s the string to test.
     * @return true if string is null or blank.
     */
    public static boolean isEmpty(String s)
    {
        return (s == null || s.length() == 0 || s.trim().length() == 0);
    }
    
    /**
     * Expands tokenized string.
     * It will use System.getProperty() if it cannot find a match in the map.
     * @param s the string to expand.
     * @param map the property map.
     * @return the expanded string.
     */
    public static String expand(String s, Map<String, String> map)
    {
        return TextUtil.expand(s, "${", "}", map);
    }
    
    /**
     * Expands tokenized string.
     * It will use System.getProperty() if it cannot find a match in the map.
     * @param s the string to expand.
     * @param map the property map.
     * @return the expanded string.
     */
    public static String expand(String s, String begin, String end, Map<String, String> map)
    {
        return expand(s, begin, end, map, false);
    }
    
    /**
     * Expands tokenized string.
     * It will use System.getProperty() if it cannot find a match in the map.
     * @param s the string to expand.
     * @param map the property map.
     * @param emptyIfMissing replace the variable with empty if missing.
     * @return the expanded string.
     */
    public static String expand(String s, String begin, String end, Map<String, String> map, boolean emptyIfMissing)
    {
        if (s == null || s.length() == 0)
            return s;

        StringBuffer buf = new StringBuffer();
        int k1 = 0;
        int k2;
        while ((k2 = s.indexOf(begin, k1)) >= 0)
        {
            buf.append(s.substring(k1, k2));
            if ((k1 = s.indexOf(end, k2 + begin.length())) < 0)
            {
                buf.append(s.substring(k2));
                break;
            }

            String f = s.substring(k2 + begin.length(), k1);
            Object v = (map == null ? null : map.get(f));
            if (v == null)
            {
                if (!emptyIfMissing)
                    buf.append(begin).append(f).append(end);
            }
            else
            {
                buf.append(v);
            }
            k1++;
        }

        if (k1 >= 0 && k1 < s.length())
            buf.append(s.substring(k1));
        
        return buf.toString();
    }
    
    /**
     * substring between two symbol
     * @param str
     * @param firstSymbol
     * @param lastSymbol
     * @return
     */
    public static String subStringLastIndex(String str, String firstSymbol, String lastSymbol)
    {
        if(TextUtil.isEmpty(str))
            return str;
        int first = 0;
        int last = str.length();
        if(!TextUtil.isEmpty(firstSymbol) && str.lastIndexOf(firstSymbol) > 0)
            first = str.lastIndexOf(firstSymbol) + firstSymbol.length();
        if(!TextUtil.isEmpty(lastSymbol) && str.lastIndexOf(lastSymbol) > 0)
            last = str.lastIndexOf(lastSymbol);
        if(first <= last)
            return str.substring(first, last);
        return null;
    }
    
    /**
     * Generates a UUID.
     * @return a UUID.
     */
    public static String createUUID()
    {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Replaces strings with another string.
     * This method will work for JRE versions before 1.4.
     * @param s the original string.
     * @param replace the string to replace.
     * @param with the string to replace with.
     * @return the replaced string.
     */
    public static String replace(String s, String replace, String with)
    {
        if (s == null || s.length() == 0 || replace == null || replace.length() == 0 || with == null || replace.equals(with))
            return s;
        
        StringBuffer buf = new StringBuffer();
        int k1 = 0;
        int k2;
        while ((k2 = s.indexOf(replace, k1)) >= 0)
        {
            buf.append(s.substring(k1, k2));
            buf.append(with);
            k1 = k2 + replace.length();
        }

        if (k1 < s.length())
            buf.append(s.substring(k1));
        
        return buf.toString();
    }
    
    /**
     * Trims the string to length.
     * @param str the string
     * @return the length to keep
     */
    public static String substring(String str, int length)
    {
        if (str == null || str.length() <= length)
            return str;
        return str.substring(0, length);
    }
    
    /**
     * Gets the left part of the string, the returned string's length will not exceed length parameter, and the omitted parts will be represented as "...".
     */
    public static String omitString(String oriStr, int length)
    {
        if (oriStr == null || oriStr.length() <= length)
            return oriStr;
        String rtn = oriStr.substring(0,length - 3);
        return rtn + "...";
    }
    
    /**
     * expandNum(12, "0", 4) = "0012"
     * @param tenantId
     * @param prefix
     * @param totalLength
     * @return
     */
    public static String expandNum(int tenantId, String prefix, int totalLength)
    {
        return String.format("%" + prefix + totalLength + "d", tenantId);
    }
    
    public static List<String> split(String value, int len)
    {
        List<String> result = new ArrayList<String>();
        if (isEmpty(value))
            return result;
        
        int length = value.length();
        for (int i = 0; (i * len) <= length; i++)
        {
            int end = ((i + 1) * len) <= length ? ((i + 1) * len) : length;
            if ((i * len) < end)
                result.add(value.substring(i * len, end));
        }
        return result;
    }
    
    public static List<byte[]> split(byte[] data, int len)
    {
        List<byte[]> result = new ArrayList<byte[]>();
        if (data == null)
            return result;
        
        int length = data.length;
        for (int i = 0; (i * len) <= length; i++)
        {
            int end = ((i + 1) * len) <= length ? len : length - (i * len);
            if ((i * len) < end)
            {
                byte[] arr = new byte[len];
                System.arraycopy(data, i * len, arr, 0, end);
                result.add(arr);    
            }
        }
        
        return result;
    }
    
    public static String bcd2Str(byte[] bytes)
    {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++)
        {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }
    
    public static byte[] ascii2bcd(byte[] ascii, int asc_len)
    {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++)
        {
            bcd[i] = asc2bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc2bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }
    
    public static byte asc2bcd(byte asc)
    {
        byte bcd;
        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }
    
}
