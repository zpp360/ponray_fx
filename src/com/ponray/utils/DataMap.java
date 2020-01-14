package com.ponray.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataMap extends HashMap implements Map{
    private static final long serialVersionUID = 1L;
    Map map = null;

    public DataMap(){
        map = new HashMap();
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    public String getString(Object key) {
        return (String) get(key);
    }

    public long getLong(Object key){
        return (long)get(key);
    }

    public int getInt(Object key){
        return (int)get(key);
    }

    public double getDouble(Object key){
        return (double)get(key);
    }

    public float getFloat(Object key){
        return (float)get(key);
    }



    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set entrySet() {
        return map.entrySet();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set keySet() {
        return map.keySet();
    }

    @SuppressWarnings("unchecked")
    public void putAll(Map t) {
        map.putAll(t);
    }

    public int size() {
        return map.size();
    }

    public Collection values() {
        return map.values();
    }
}
