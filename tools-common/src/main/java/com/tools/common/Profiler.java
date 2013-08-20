package com.tools.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public final class Profiler {

    private static final ThreadLocal<Pool> PROFILER = new ThreadLocal<Pool>();

    public static void start(String name) {
        Pool pool = PROFILER.get();
        if (pool == null) {
            pool = new Pool();
            PROFILER.set(pool);
        }

        pool.push(name);
    }

    public static void release() {
        Pool pool = PROFILER.get();
        if (pool == null || pool.getStartStack().isEmpty()) {
            return;
        }

        pool.release();

    }

    public static void dump() {
        Pool pool = PROFILER.get();

        if (pool == null || pool.getRelease().isEmpty()) {
            P.print("empty");
            return;
        }

        P.each(pool.getRelease(), new CallBack<Entry>() {
            @Override
            public void call(Entry entry) {
                System.out.println(entry);
            }
        });

        PROFILER.set(null);
    }

    static class Pool {
        private Stack<Entry> startStack;
        private List<Entry> release;

        Pool() {
            startStack = new Stack<Entry>();
            release = new ArrayList<Entry>();
        }

        Stack<Entry> getStartStack() {
            return startStack;
        }

        List<Entry> getRelease() {
            return release;
        }

        public void push(String name) {
            Entry entry = new Entry();
            entry.setStart(System.nanoTime());
            entry.setName(name);

            startStack.push(entry);
        }

        public void release() {
            Entry entry = startStack.pop();
            if (entry == null) {
                return;
            }

            entry.setEnd(System.nanoTime());

            release.add(entry);
        }

    }

    static class Entry {
        private String name;
        private long start;
        private long end;

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        long getStart() {
            return start;
        }

        void setStart(long start) {
            this.start = start;
        }

        long getEnd() {
            return end;
        }

        void setEnd(long end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return name + " start : " + start + " --> end :" + end + "  cost: " + (end - start) + " ms";
        }
    }
}
