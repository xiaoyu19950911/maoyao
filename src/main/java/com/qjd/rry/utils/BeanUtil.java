/*
 * Copyright (c) 2015 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.qjd.rry.utils;

import lombok.extern.slf4j.Slf4j;

import java.beans.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class BeanUtil
{
    private static Pattern timePattern = Pattern.compile("(\\d\\d:\\d\\d:\\d\\d)");
    private static DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat defaultDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Converts a property value to the desired type.
     * This is equivalent to
     * {@link #valueOf(Object, Class, Locale) valueOf(value, type, null)}.
     * @param value the value to convert.
     * @param type the type to convert to.
     * @return the converted value.
     */
    public static Object valueOf(Object value, Class<?> type)
    {
        return valueOf(value, type, null);
    }
    
    /**
     * Converts a property value to the desired type.
     * It handles numbers, strings and dates.
     * @param value the value to convert.
     * @param type the type to convert to.
     * @param locale the locale used for type conversion (null=locale neutral)
     * @return the converted value.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object valueOf(Object value, Class<?> type, Locale locale)
    {
        if (value == null || type == null || type.isAssignableFrom(value.getClass()))
            return value;
        
        // Convert to a number
        if (Number.class.isAssignableFrom(type) || (type.isPrimitive()&&type!=Boolean.TYPE))
        {
            if (value instanceof Number)
            {
                if (type == Integer.class || type == Integer.TYPE)
                    return new Integer(((Number) value).intValue());
                if (type == Long.class || type == Long.TYPE)
                    return new Long(((Number) value).longValue());
                if (type == Float.class || type == Float.TYPE)
                    return new Float(((Number) value).floatValue());
                if (type == Double.class || type == Double.TYPE)
                    return new Double(((Number) value).doubleValue());
                if (type == Short.class || type == Short.TYPE)
                    return new Short(((Number) value).shortValue());
                if (type == Byte.class || type == Byte.TYPE)
                    return new Byte(((Number) value).byteValue());
            }
            if (type == char.class)
            {
                if (Character.class == value.getClass())
                {
                    return value;
                }
                else
                {
                    String str = (String) value;
                    if (str.length() != 1)
                        throw new IllegalArgumentException("Cannot convert " + str + " to a char");
                    return new Character(str.charAt(0));
                }
            }
            if (type == BigInteger.class)
                return new BigInteger(value.toString());
            
            // Parse text number
            if (locale != null)
            {
                try
                {
                    value = NumberFormat.getInstance(locale)
                                        .parse(value.toString());
                }
                catch (ParseException ex)
                {
                    throw new RuntimeException(ex);
                }                
            }
            
            if (type == BigDecimal.class)
                return new BigDecimal(value.toString());
            
            if(type == Integer.class)
            {
                try
                {
                    return new Integer(value.toString());
                }
                catch (Exception ex)
                {
                }
            }
        }
        else if (type == String.class)
        {
            if (value instanceof Date)
            {
                if (locale == null)
                    return new java.sql.Date(((Date) value).getTime()).toString();
                
                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
                if (df instanceof SimpleDateFormat)
                {
                    SimpleDateFormat sdf = (SimpleDateFormat) df;
                    String pattern = sdf.toPattern();
                    // Force pattern to yyyy year long-format 
                    if (pattern.indexOf("yyyy") < 0)
                    {
                        pattern = TextUtil.replace(pattern, "yy", "yyyy");
                        df = new SimpleDateFormat(pattern, locale);
                    }
                }
                return df.format((Date) value);
            }
            else
            {
                return value.toString();
            }
        }
        else if (type == Date.class)
        {
            // Treat "" as null dates
            String s = value.toString().trim();
            if (s.length() == 0)
                return null;
            
            try
            {
                Date date = DateFormat.getDateInstance(DateFormat.SHORT, locale)
                                 .parse(s);
                Matcher m = timePattern.matcher(s);
                if(m.find()){
                    String timeString = m.group(1);
                    String dateString = defaultDateFormat.format(date);
                    date = defaultDateTimeFormat.parse(dateString+" "+timeString);
                }
                    
                return date;
            }
            catch (ParseException ex)
            {
                Date date = DateTimeUtil.parseDateCalDate(s);
                if (date == null)
                    throw new RuntimeException(ex);
                return date;
            }
        }
        else if (Collection.class.isAssignableFrom(type))
        {
            Collection col;
            if (type.isInterface())
            {
                if (Set.class.isAssignableFrom(type))
                    col = new HashSet();
                else
                    col = new ArrayList();
            }
            else
            {
                try
                {
                    col = (Collection) type.newInstance();
                }
                catch (Exception ex)
                {
                    throw new RuntimeException(ex);
                }
            }
            
            if (value.getClass().isArray())
                ArrayUtil.addAll(col, value);
            else if (value instanceof Collection)
                col.addAll((Collection) value);
            else
                col.add(value);

            return col;
        }
        else if (type == Class.class)
        {
            return ClassUtil.resolveClass(value.toString());
        }
        else if (type.isArray())
        {
            Class ctype = type.getComponentType();
            if (value.getClass().isArray())
            {
                int len = Array.getLength(value); 
                Object obj = Array.newInstance(ctype, len);
                for (int n = 0; n < len; n++)
                    Array.set(obj, n, BeanUtil.valueOf(Array.get(value, n),
                                                       ctype));
                return obj;
            }
            else if (value instanceof Collection)
            {
                Collection cols = (Collection) value;
                Object obj = Array.newInstance(ctype, cols.size()); 
                return ArrayUtil.copy(obj, cols);
            }
            else
            {
                Object obj = Array.newInstance(ctype, 1);
                Array.set(obj, 0, value);
                return obj;
            }
        }

        // Try PropertyEditor
        PropertyEditor pe = PropertyEditorManager.findEditor(type);
        if (pe != null)
        {
            pe.setAsText(value.toString());
            return pe.getValue();
        }
        
        // Try constructor (e.g. URL)
        try
        {
            Constructor c = type.getConstructor(new Class[]{value.getClass()});
            return c.newInstance(new Object[]{value});
        }
        catch (Exception ex)
        {
            
        }
        
        // We tried...
        throw new IllegalArgumentException("Failed to convert " + value + " to " + type);
    }
    
    /**
     * Copies from one bean/Map to another bean/Map.
     * This is equivalent to
     * {@link #copy(Object, Object, Locale) copy(from, to, null)}.
     * @param from the bean/Map to copy from.
     * @param to the bean/Map to copy to.
     * @return the to object.
     */
    public static Object copy(Object from, Object to)
    {
        return copy(from, to, null);
    }

    /**
     * Copies from one bean/Map to another bean/Map.
     * <P><P>When copying bean to bean:
     * <LI>Use {@link Introspector Intropsector} to get a list of fields.</LI>
     * <LI>Use getter method to get the value</LI>
     * <LI>If getter not defined, try accessing the field directly.</LI>
     * <LI>If field not defined, it will try the field with m_ prefix.</LI>
     * <LI>Once value is fetched, use setter methods to set the value.</LI> 
     * @param from the bean/Map to copy from.
     * @param to the bean/Map to copy to.
     * @param locale the locale used for type conversion (null=locale neutral).
     * @return the to object.
     */
    public static Object copy(Object from, Object to, Locale locale)
    {
        return copy(from, to, locale, false);
    }

    /**
     * Copies from one bean/Map to another bean/Map.
     * <P><P>When copying bean to bean:
     * <LI>Use {@link Introspector Intropsector} to get a list of fields.</LI>
     * <LI>Use getter method to get the value</LI>
     * <LI>If getter not defined, try accessing the field directly.</LI>
     * <LI>If field not defined, it will try the field with m_ prefix.</LI>
     * <LI>Once value is fetched, use setter methods to set the value.</LI> 
     * @param from the bean/Map to copy from.
     * @param to the bean/Map to copy to.
     * @param locale the locale used for type conversion (null=locale neutral).
     * @param emptyNull true to convert empty string to null
     * @return the to object.
     */
    @SuppressWarnings("rawtypes")
    public static Object copy(Object from, Object to, Locale locale, boolean emptyNull)
    {
        if (to == null || from == null)
            return to;
        
        if (from instanceof Map)
        {
            copy((Map) from, to, locale, emptyNull);
        }
        else if (to instanceof Map)
        {
            copy(from, (Map) to, locale, emptyNull);
        }
        else
        {
            // Get Bean Info
            PropertyDescriptor[] pdf = getPropertyDescriptors(from.getClass());
            PropertyDescriptor[] pdt = getPropertyDescriptors(to.getClass());

            // Copy from Bean to Bean
            Object[] arg = new Object[1];
            for (int n = 0; n < pdt.length; n++)
            {
                Method mt = pdt[n].getWriteMethod();
                if (mt != null)
                {
                    String tname = pdt[n].getName();
                    Class<?> type = pdt[n].getPropertyType();
                    for (int k = 0; k < pdf.length; k++)
                    {
                        if (!pdf[k].getName().equals(tname))
                            continue;
                        
                        // No getXYZ method so try getting field
                        Method mf = pdf[k].getReadMethod();
                        Field f = null;
                        Object val = null;
                        if (mf == null)
                        {
                            Class<?> clazz = from.getClass();
                            try
                            {
                                f = clazz.getDeclaredField(tname);
                            }
                            catch (NoSuchFieldException ex)
                            {
                            }
                            
                            // Try with m_ prefix
                            if (f == null)
                            {
                                try
                                {
                                    f = clazz.getDeclaredField("m_" + tname);
                                }
                                catch (NoSuchFieldException ex)
                                {
                                }
                            }
                            
                            // We tried...
                            if (f == null)
                                continue;

                            // Make sure we can access private fields
                            if (!f.isAccessible())
                                f.setAccessible(true);
                        }

                        try
                        {
                            if (mf == null)
                                val = f.get(from);
                            else
                                val = mf.invoke(from, new Object[]{});
                            
                            val = valueOf(val, type, locale);
                            arg[0] = valueOf(val, type, locale);
                            mt.invoke(to, arg);
                        }
                        catch (IllegalAccessException ex)
                        {
                        }
                        catch (InvocationTargetException ex)
                        {
                            log.error(ex.getMessage(), ex);
                        }
                        catch (RuntimeException re)
                        {
                            log.error("Failed to convert value [" + val + "] to type " + type.getName());
                            throw re;
                        }
                        break;
                    }
                }
            }
        }
        return to;
    }
    
    /**
     * Get the property descriptors of a class.
     * @param type the class.
     * @return the property descriptors.
     */
    @SuppressWarnings("rawtypes")
    public static PropertyDescriptor[] getPropertyDescriptors(Class type)
    {
        PropertyDescriptor[] pd;
        try
        {
            BeanInfo bi = Introspector.getBeanInfo(type);
            pd = bi.getPropertyDescriptors();
        }
        catch (IntrospectionException ex)
        {
            log.error(ex.getMessage(), ex);
            throw new IllegalArgumentException(ex);
        }
        return pd;
    }
}
