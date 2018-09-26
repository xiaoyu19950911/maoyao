/*
 * Copyright (c) 2015 上海极值信息技术有限公司 All Rights Reserved.
 */
package com.qjd.rry.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Base64 is a standard algorithm for converting binary content to text and
 * back again. It is used for MIME file attachments, SOAP and so on. See
 * RFC-2045 for more information. 
 * It is recommended you use {@link TextUtil#encode(byte[]) TextUtil.encode()}
 * and {@link TextUtil#decode(String) TextUtil.decode()} instead.
 * The encoded string can be stored in the database or text file.
 * @author <A HREF="mailto:ljunjie@qiujieda.com">JunJie.Lu</A>
 * @version 1.0, $Revision: 0$, $Date: Jun 15, 2015$
 * @since 1.0
 */
public class Base64
{
    /** Base64 encoding alphabet (specified in RFC-2045). */
    static final public byte[] encode =
    { (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K',
            (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
            (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
            (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o',
            (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y',
            (byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8',
            (byte) '9', (byte) '+', (byte) '/' };

    /** Base64 decoding alphabet (specified in RFC-2045). */
    static final public byte[] decode = new byte[128];
    static
    {
        for (int i = 0; i < encode.length; i++)
            decode[encode[i]] = (byte) i;
    }

    /**
     * This class isn't meant to be instantiated.
     */
    private Base64()
    {
    }

    /**
     * Converts a binary array to readable text using Base64 encoding.
     * 
     * @param input
     *            the byte array to be encode.
     * @return an encoded string.
     * @since 1.0
     */
    public static String encode(byte[] input)
    {
        return (input == null ? null : encode(input, 0, input.length));
    }

    /**
     * Converts a binary array to readable text using Base64 encoding. The
     * string can then be embedded in the database or text file.
     * 
     * @param input
     *            the byte array to be encode.
     * @param offset
     *            the starting offset to encode.
     * @param len
     *            the number of bytes to encode.
     * @return an encoded string.
     * @since 2.0
     */
    public static String encode(byte[] input, int offset, int len)
    {
        if (input == null)
            return null;

        // 3-byte to 4-byte conversion
        byte buf[] = new byte[((len + 2) / 3) * 4];
        int end = offset + len;
        int src, dest = 0;
        for (src = offset; src < end - 2; src += 3)
        {
            buf[dest++] = encode[(input[src] >>> 2) & 077];
            buf[dest++] = encode[(input[src + 1] >>> 4) & 017 | (input[src] << 4) & 077];
            buf[dest++] = encode[(input[src + 2] >>> 6) & 003 | (input[src + 1] << 2) & 077];
            buf[dest++] = encode[input[src + 2] & 077];
        }

        // Convert the last 1 or 2 bytes
        if (src < end)
        {
            buf[dest++] = encode[(input[src] >>> 2) & 077];
            if (src < end - 1)
            {
                buf[dest++] = encode[(input[src + 1] >>> 4) & 017 | (input[src] << 4) & 077];
                buf[dest++] = encode[(input[src + 1] << 2) & 077];
                buf[dest++] = '=';
            } else
            {
                buf[dest++] = encode[(input[src] << 4) & 077];
                buf[dest++] = '=';
                buf[dest++] = '=';
            }
        }
        return new String(buf);
    }

    /**
     * Decodes Base64-encoded string back to a byte array. This is the reverse
     * of Base64.encode().
     * 
     * @param input
     *            the Base64 encoded string to decode.
     * @return an decoded byte array.
     * @see Base64#encode(byte[])
     * @since 1.0
     */
    public static byte[] decode(String input)
    {
        if (input == null)
            return null;

        byte[] data = input.getBytes();
        if (data.length == 0)
            return data;

        // Skip padding from the end of encoded data
        int tail = data.length;
        for (; data[tail - 1] == '='; tail--)
            ;

        // 4-byte to 3-byte conversion
        byte buf[] = new byte[tail - data.length / 4];
        int src, dest;
        for (src = 0, dest = 0; dest < buf.length - 2;)
        {
            // ASCII-printable to 0-63 conversion
            byte b1 = decode[data[src++]];
            byte b2 = decode[data[src++]];
            byte b3 = decode[data[src++]];
            byte b4 = decode[data[src++]];
            buf[dest++] = (byte) (((b1 << 2) & 255) | ((b2 >>> 4) & 003));
            buf[dest++] = (byte) (((b2 << 4) & 255) | ((b3 >>> 2) & 017));
            buf[dest++] = (byte) (((b3 << 6) & 255) | (b4 & 077));
        }

        // Handle last 1 or 2 bytes (exception case)
        if (dest < buf.length && src + 1 < data.length)
        {
            byte b1 = decode[data[src++]];
            byte b2 = decode[data[src]];
            buf[dest] = (byte) (((b1 << 2) & 255) | ((b2 >>> 4) & 003));
        }

        if (++dest < buf.length && src + 1 < data.length)
        {
            byte b1 = decode[data[src++]];
            byte b2 = decode[data[src]];
            buf[dest] = (byte) (((b1 << 4) & 255) | ((b2 >>> 2) & 017));
        }
        return buf;
    }

    /**
     * This is to convert a serializable object to readable text that can then
     * be embedded in the database or an XML file.
     * 
     * @param input
     *            the byte array to be encode.
     * @return an encoded string.
     * @since 1.0
     */
    public static String encodeObject(Object input) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(input);
        oos.close();
        return encode(baos.toByteArray());
    }

    /**
     * Decodes Base64-encoded string back to a seriazable object. This is the
     * reverse of Base64.encode().
     * 
     * @param input the Base64 encoded string to decode.
     * @return an decoded byte array.
     * @see Base64#encode(byte[])
     * @since 1.0
     */
    public static Object decodeObject(String input) throws IOException, ClassNotFoundException
    {
        byte[] b = decode(input);
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }
}
