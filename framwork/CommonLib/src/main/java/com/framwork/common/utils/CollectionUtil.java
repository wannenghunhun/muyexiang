package com.framwork.common.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class CollectionUtil {
    
    public static boolean isListMoreOne(Collection mArrayList) {
        return mArrayList != null && mArrayList.size() > 0;
    }



    public static boolean isEmpty(Collection mArrayList) {
        return !isListMoreOne(mArrayList);
    }


    public static <T> List<T> array2List(T... objects) {
        return Arrays.asList(objects);
    }
    
    public static <T> ArrayList<T> array2ArrayList(T... objects) {
        return new ArrayList<>(Arrays.asList(objects));
    }
    
    public static String[] strlist2Array(ArrayList<String> list) {
        return list2Array(list, String.class);
    }
    
    
    @SuppressWarnings("unchecked")
    public static <T> T[] list2Array(List<T> list, Class<T> clazz) {
        return list.toArray((T[]) Array.newInstance(clazz, list.size()));
    }




}
