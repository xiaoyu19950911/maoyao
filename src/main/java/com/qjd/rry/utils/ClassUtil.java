/*
 * Copyright (c) 2015 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.qjd.rry.utils;

import java.lang.reflect.Array;

/**
 * DateTimeUtil.
 * @author <A HREF="mailto:ljunjie@qiujieda.com">JunJie.Lu</A>
 * @version 1.0, $Revision: 0$, $Date: Jun 15, 2015$
 * @since 1.0
 */
public class ClassUtil
{
    /**
     * Returns the long class name with package prefix.
     * For Object array, adds '[]' to the end of the class name.
     * @param clazz the class.
     * @return the class name.
     */
    public static String getName(Class<?> clazz)
    {
        StringBuffer buf = new StringBuffer();
        for (; clazz.isArray(); clazz = clazz.getComponentType())
            buf.append("[]");

        String name = clazz.getName();
        return buf.insert(0, name).toString();
    }
    
    /**
     * Resolves class name using this classes' class loader.
     * @param name the class name to resolve.
     * @return the class.
     * @see #resolveClass(String,ClassLoader)
     */
    public static Class<?> resolveClass(String name)
    {
        return resolveClass(name, null);
    }
    
    /**
     * Resolves class name.
     * This differs from Class.forName() in that it handles can Java primitives:
     * int, int[], double, float, etc. This form is more readable than the
     * default Java convention where LI;==int[].   
     * @param name the class name to resolve.
     * @param cl the class loader.
     * @return the class.
     */
    public static Class<?> resolveClass(String name, ClassLoader cl)
    {
        if ("int".equals(name))
            return Integer.TYPE;
        if ("long".equals(name))
            return Long.TYPE;
        if ("short".equals(name))
            return Short.TYPE;
        if ("double".equals(name))
            return Double.TYPE;
        if ("float".equals(name))
            return Float.TYPE;
        if ("boolean".equals(name))
            return Boolean.TYPE;
        if ("byte".equals(name))
            return Byte.TYPE;
        if ("char".equals(name))
            return Character.TYPE;
        
        if (name.endsWith("[]"))
        {
            name = name.substring(0, name.length() - 2);
            return Array.newInstance(resolveClass(name, cl), 0).getClass();
        }
        
        if (cl == null)
            cl = ClassUtil.class.getClassLoader();
        
        try
        {
            return Class.forName(name, true, cl);
        }
        catch (ClassNotFoundException ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Returns the hashCode of an object.
     * If the object is null, it will return zero.
     * @param obj the object.
     * @return the hashCode of the object.
     */
    public static int hashCode(Object obj)
    {
        return (obj == null ? 0 : obj.hashCode());
    }
    
    /**
     * Returns true if one value "equals" another value.
     * This will correctly handles null values.
     * @param value1 the first value.
     * @param value2 the second value.
     * @return true if one value "equals" another value.
     */
    public static boolean equals(Object value1, Object value2)
    {
        return (value1 == null ? value2 == null : value1.equals(value2));
    }
}
