package org.sotatek;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Helper {
    public static String encodeString(String str)
    {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(str);
        String encodedString = StandardCharsets.UTF_8.decode(buffer).toString();
        return encodedString;
    }
}
