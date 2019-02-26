package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 该类提供了表的数据结构
 * -------------------
 * key   mid   value
 * ------------------
 * 其中
 * 表的第一列为key值,不能重复,如果重复,会覆盖之前的数据,
 * 也是整条记录的标识,可通过该标识获取一条记录
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/2 16:56
 */
public class Table<K,T,V> {

    private Map<K,Map<T,V>> tableHashMap;

    public Table() {
        tableHashMap = new HashMap<>();
    }

    /**
     * 将数据放入表中
     * @param key
     * @param mid
     * @param value
     */
    public void put(K key,T mid,V value) {
        HashMap map = new HashMap();
        map.put(mid,value);
        tableHashMap.put(key,map);
    }

    /**
     * 从通过key值从表中取出数据,如果key值不存在 返回null
     * @param key
     * @return
     */
    public TableEntry get(K key) {
        TableEntry tableEntry = new TableEntry();
        Map<T, V> tvMap = tableHashMap.get(key);
        if(tvMap == null || tvMap.isEmpty()) {
            return null;
        }
        Set<Map.Entry<T, V>> entries = tvMap.entrySet();
        if(!entries.isEmpty()) {
            for(Map.Entry<T,V> entry : entries) {
               tableEntry.setKey(entry.getKey());
               tableEntry.setValue(entry.getValue());
            }
        }
        return tableEntry;
    }

    /**
     * 遍历表中的所有数据
     * @return
     */
    public Set<TableEntry<K,T,V>> entrySet() {
        Set<TableEntry<K,T,V>> tableEntrySet = new HashSet<>();
        Set<K> kSet = tableHashMap.keySet();
        for(K key : kSet) {
            TableEntry<K,T,V> tableEntry = new TableEntry<>();
            tableEntry.setKey(key);
            Map<T, V> tvMap = tableHashMap.get(key);
            Set<Map.Entry<T, V>> entries = tvMap.entrySet();
            for(Map.Entry<T,V> entry : entries) {
                tableEntry.setMid(entry.getKey());
                tableEntry.setValue(entry.getValue());
            }
            tableEntrySet.add(tableEntry);
        }
        return tableEntrySet;
    }

    @Override
    public String toString() {
        Set<TableEntry<K, T, V>> tableEntries = entrySet();
        StringBuilder sb = new StringBuilder();
        sb.append("Table{");
        for(TableEntry tableEntry : tableEntries) {
            sb.append(tableEntry);
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        String tableString = sb.toString();
        return  tableString.replaceAll("TableEntry","");
    }

    /**
     * 表的数据结构
     * @param <K>
     * @param <T>
     * @param <V>
     */
    public static class TableEntry<K,T,V> {

        private K key;

        private T mid;

        private V value;

        public TableEntry(){}

        public TableEntry(K key, T mid, V value) {
            this.key = key;
            this.mid = mid;
            this.value = value;
        }

        @Override
        public String toString() {
            return "TableEntry{" +
                    "key=" + key +
                    ", mid=" + mid +
                    ", value=" + value +
                    '}';
        }

        public K getKey() {
            return key;
        }

        private void setKey(K key) {
            this.key = key;
        }

        public T getMid() {
            return mid;
        }

        private void setMid(T mid) {
            this.mid = mid;
        }

        public V getValue() {
            return value;
        }

        private void setValue(V value) {
            this.value = value;
        }
    }

}
