package pcollections.cmap;

import pcollections.CCollection;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static pcollections.enums.Constants.ZERO;


public final class CHashMap<K, V> extends AbstractMap<K, V> implements CMap<K, V> {
    private CMap<Integer, CCollection<Entry<K, V>>> intMap;

    private int length, hashCode;

    private Set<Entry<K, V>> entrySet = null;

    public CHashMap() {
    }

    private CHashMap(final CMap<Integer, CCollection<Entry<K, V>>> intMap, final int length, final int hashCode) {
        this.intMap = intMap;
        this.length = length;
        this.hashCode = hashCode;
    }

    public static <K, V> CHashMap<K, V> empty() {
        return new CHashMap<>();
    }

    public static <K, V> CHashMap<K, V> clearAllValues(final CMap<Integer, CCollection<Entry<K, V>>> map) {
        return new CHashMap<>(map.deleteAll(map.keySet()), ZERO, ZERO);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public CMap<K, V> add(K key, V value) {
        return null;
    }

    @Override
    public CMap<K, V> addAll(Map<? extends K, ? extends V> map) {
        return null;
    }

    @Override
    public CMap<K, V> delete(K key) {
        return null;
    }

    @Override
    public CMap<K, V> deleteAll(Collection<? extends K> keys) {
        return null;
    }
}
