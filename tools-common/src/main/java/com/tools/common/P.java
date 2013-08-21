package com.tools.common;

import java.util.List;

/**
 * User: liqiang
 * Date: 13-8-12
 * Time: 下午4:06
 */
public final class P {

    public static void print(Object ... objects) {
        if (objects == null || objects.length == 0) {
            return;
        }

        StringBuffer buffer = new StringBuffer();
        for (Object o : objects) {
            buffer.append(o.toString());
        }

        System.out.println(buffer);
    }

    public static <T> void each(List<T> list, CallBack<T> callBack) {
        if (list == null || list.isEmpty()) {
            print("EMPTY");
            return;
        }

        for (T t : list) {
            callBack.call(t);
        }
    }

    public static void each(int end, int start, int step, CallBack<Integer> callBack) {
        for (int i = start; i < end; i += step) {
            callBack.call(i);
        }
    }

    public static void each(int end, int start, CallBack<Integer> callBack) {
        each(end, start, 1, callBack);
    }

    public static void each(int end, CallBack<Integer> callBack) {
        each(end ,0, 1, callBack);
    }
}
