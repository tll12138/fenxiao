package cn.iocoder.yudao.module.fx.utils;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tll
 * @date 2024-12-30 10:28:12
 */
public final class MapUtils {

    public static <K, V> Map<K, V> newMap(K key, V val) {
        return kv(key, val).newMap();
    }

    public static <K, V> KV<K, V> newMap(Map<K, V> map) {
        return new KV<K, V>(map);
    }

    public static <K, V> KV<K, V> kv(K key, V val) {
        return new KV<K, V>(key, val);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static int nullSafeSizeOf(Map<?, ?> map) {
        return map != null ? map.size() : 0;
    }

    public static <K, V> List<V> getValueList(Map<K, V> map) {
        return isNotEmpty(map) ? Lists.newArrayList(map.values()) : Lists.newArrayList();
    }


    //~ private methods ---------------------------------------------------------------------------------------------
    private MapUtils() {
        throw new UnsupportedOperationException();
    }

    //~ inner classes --------------------------------------------------------------------------------------------------

    public static class KV<K, V> {
        private final Map<K, V> map;

        public KV<K, V> kv(K key, V val) {
            this.map.put(key, val);
            return this;
        }

        public Map<K, V> newMap() {
            return this.map;
        }

        private KV() {
            this.map = new HashMap<K, V>();
        }

        private KV(K key, V val) {
            this();
            this.map.put(key, val);
        }

        private KV(Map<K, V> map) {
            this.map = map;
        }
    }
}
