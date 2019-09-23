package pcollections.cmap;

import pcollections.CCollection;
import pcollections.clist.CLinkedList;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
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
        if (entrySet == null) {
            entrySet = new AbstractSet<>() {

                @Override
                public int size() {
                    return length;
                }

                @Override
                public Iterator<Entry<K, V>> iterator() {
                    return new CMapIterator<>(intMap.values().iterator());
                }

                @Override
                public int hashCode() {
                    return hashCode;
                }

                @Override
                public boolean contains(final Object e) {
                    if (!(e instanceof Entry)) {
                        return false;
                    }
                    V value = get(((Entry<?, ?>) e).getKey());
                    return value != null && value.equals(((Entry<?, ?>) e).getValue());
                }
            };
        }
        return entrySet;
    }

    @Override
    public CHashMap<K, V> add(K key, V value) {
        CCollection<Entry<K, V>> entries = getEntries(key.hashCode());
        int size0 = entries.size(), hashCode0 = hashCode(entries),
                i = getKeyIndex(entries, key);
        if (i != -1) {
            entries = entries.delete(i);
        }
        entries = entries.prepend(new SimpleImmutableEntry<>(key, value));
        return new CHashMap<>(intMap.add(key.hashCode(), entries),
                length - size0 + entries.size(), hashCode - hashCode0 + hashCode(entries));
    }

    @Override
    public CHashMap<K, V> addAll(Map<? extends K, ? extends V> map) {
        CHashMap<K, V> result = this;
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            result = result.add(entry.getKey(), entry.getValue());
        }
        return result;
    }

    @Override
    public CMap<K, V> delete(K key) {
        CCollection<Entry<K, V>> entries = getEntries(key.hashCode());
        int i = getKeyIndex(entries, key);
        if (i == -1) {
            return this;
        }
        int hashCode0 = hashCode(entries);
        entries = entries.delete(i);
        if (entries.size() == 0) {
            return new CHashMap<>(intMap.delete(key.hashCode()),
                    length - 1, hashCode - hashCode0);
        }

        return new CHashMap<>(intMap.add(key.hashCode(), entries),
                length - 1, hashCode - hashCode0 + hashCode(entries));
    }

    @Override
    public CHashMap<K, V> deleteAll(Collection<? extends K> keys) {
        CHashMap<K, V> result = this;
        for (Object key : keys) {
            result = result.delete(key);
        }
        return result;
    }

    private static <K, V> int getKeyIndex(final CCollection<Entry<K, V>> entries, final Object key) {
        int i = 0;
        for (Entry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private CCollection<Entry<K, V>> getEntries(final int hash) {
        CCollection<Entry<K, V>> entries = intMap.get(hash);
        if (entries == null) {
            return CLinkedList.empty();
        }
        return entries;
    }
}
