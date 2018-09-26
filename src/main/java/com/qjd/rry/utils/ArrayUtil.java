/*
 * Copyright (c) 2015 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.qjd.rry.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * ArrayUtil.
 * @author <A HREF="mailto:ljunjie@qiujieda.com">JunJie.Lu</A>
 * @version 1.0, $Revision: 0$, $Date: Sep 9, 2015$
 * @since 1.0
 */
public class ArrayUtil
{
    /**
     * Adds an array to a collection.
     * @param c the collection.
     * @param array the array to add.
     */
    public static Collection<Object> addAll(Collection<Object> c, Object array)
    {
        if (array != null && array.getClass().isArray())
        {
            int len = Array.getLength(array);
            for (int n = 0; n < len; n++)
                c.add(Array.get(array, n));
        }
        return c;
    }
    
    /**
     * Copies items in a collection to an array.
     * @param to the array to copy to.
     * @param from the collection to copy from.
     * @return the array.
     */
    public static Object copy(Object to, Collection<Object> from)
    {
        if (to == null)
            return to;
        
        Class<?> ctype = to.getClass().getComponentType();
        int len = Array.getLength(to);
        if (from instanceof List)
        {
            // More efficient
            List<Object> list = (List<Object>) from;
            for (int n = 0; n < list.size() && n < len; n++)
                Array.set(to, n, BeanUtil.valueOf(list.get(n), ctype));
        }
        else
        {
            Iterator<Object> i = from.iterator();
            for (int n = 0; i.hasNext() && n < len; n++)
                Array.set(to, n, BeanUtil.valueOf(i.next(), ctype));
        }
        return to;
    }
    
    /**
     * convert array to list
     * @param values
     * @return
     */
    public static <T> List<T> asList(T[] values)
    {
        if (values == null)
            return null;
        ArrayList<T> result = new ArrayList<T>();
        for (T value : values)
            result.add(value);
        return result;
    }
}
