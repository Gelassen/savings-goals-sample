package com.example.qapitalinterview;


import android.support.test.runner.AndroidJUnit4;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TestJava {

    @Test
    public void testFirst() throws Exception {
        List<String> result = (List<String>) parse(" [ 10, 20, 30.1 ] ");
        assertEquals("10", result.get(0));
        assertEquals("20", result.get(1));
        assertEquals("30.1", result.get(2));
    }

    @Test
    public void testSecond() throws Exception {
        List<String> result = (List<String>) parse(" [ 10 , 20, \"hello\", 30.1 ] ");
        assertEquals("10", result.get(0));
        assertEquals("20", result.get(1));
        assertEquals("\"hello\"", result.get(2));
        assertEquals("30.1", result.get(3));
    }

    /**
     * result = jsonParser.parse("{" +
     " \"hello\": \"world\"," +
     " \"key1\": 20," +
     " \"key2\": 20.3," +
     " \"foo\": \"bar\"" +
     "}");
     if (result != null) {
     Map<String, Object> map = (Map<String, Object>) result;
     System.out.println(map.get("hello"));
     System.out.println(map.get("key1"));
     System.out.println(map.get("key2"));
     System.out.println(map.get("foo"));
     }

     System.out.println("Fourth Step");
     result = jsonParser.parse("{" +
     " \"hello\" : \"world\"," +
     " \"key1\" : 20, " +
     " \"key2\": 20.3, " +
     " \"foo\": {" +
     "             \"hello1\": \"world1\"," +
     "             \"key3\": [200, 300]" +
     "          }" +
     "}");
     *
     *
     * */

    @Test
    public void testThird() throws Exception {
        Object result = parse("{" +
                " \"hello\": \"world\"," +
                " \"key1\": 20," +
                " \"key2\": 20.3," +
                " \"foo\": \"bar\"" +
                "}");

    }

    public Object parse(String input) {
        // IMPLEMENT ME
        List result = new ArrayList();
//        input = input.replaceAll("\\s+","");
//
//        final int startIdx = input.indexOf("[");
//        final int endIdx = input.indexOf("]");
//
//        input = input.substring(startIdx + 1, endIdx);
//
//        String[] tokens = input.split(",");
//        for (String token : tokens) {
//            result.add(preprocessToken(token));
//        }
        return result;
    }
//
//    private boolean isObject(String token) {
//
//        return false;
//    }
//
//    private Object preprocessToken(final String token) {
//        String result = token;
//        boolean isPair = token.contains(":");
//        if (isPair) {
//            token.split();
//        }
//        return result;
//    }
//
//    private Object processSegment(final String token) {
//        String result = token;
//        boolean isObject = token.contains("\\");
//        if (isObject) {
//            int start = result.indexOf("\\");
//            int end = result.lastIndexOf("\\");
//            result = token.substring(start + 1, end);
//        }
//        return result;
//    }

}
