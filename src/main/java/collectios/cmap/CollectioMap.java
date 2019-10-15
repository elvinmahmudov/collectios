package collectios.cmap;

import collectios.Collectios;
import collectios.clist.CollectioList;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static collectios.enums.Constants.ZERO;


/**
 * This class is analogue for Java HashMap, but immutable and consistent
 *
 * @param <K> Key
 * @param <V> Value
 */
public final class CollectioMap<K, V> extends AbstractMap<K, V> implements CMap<K, V> {

    /**
     * Internal integer map
     */
    private CMap<Integer, Collectios<Entry<K, V>>> integerMap;

    /**
     * Length of the map
     */
    private int length;

    /**
     * Set of entries
     */
    private Set<Entry<K, V>> entrySet = null;

    public CollectioMap() {
    }

    private CollectioMap(final CMap<Integer, Collectios<Entry<K, V>>> integerMap, final int length) {
        this.integerMap = integerMap;
        this.length = length;
    }

    /**
     * Method for getting empty map
     *
     * @param <K>
     * @param <V>
     * @return CollectioMap
     */
    public static <K, V> CollectioMap<K, V> empty() {
        return new CollectioMap<>();
    }

    /**
     * Method to clear all values from the map
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return CollectioMap with keys
     */
    public static <K, V> CollectioMap<K, V> clearAllValues(final CMap<Integer, Collectios<Entry<K, V>>> map) {
        return new CollectioMap<>(map.deleteAll(map.keySet()), ZERO);
    }

    /**
     * Private method to get key index in the entrie set
     *
     * @param entries
     * @param key
     * @param <K>
     * @param <V>
     * @return index of key int
     */
    private static <K, V> int getKeyIndex(final Collectios<Entry<K, V>> entries, final Object key) {
        int i = 0;
        for (Entry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Method to get size of the map
     *
     * @return int
     */
    public int size() {
        return length;
    }

    /**
     * Overridden method to get set of entries
     *
     * @return Set of entries
     */
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
                    return new CollectioMapIterator<>(integerMap.values().iterator());
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

    /**
     * Method to add value by key
     *
     * @param key
     * @param value
     * @return CollectioMap
     */
    @Override
    public CollectioMap<K, V> add(K key, V value) {
        Collectios<Entry<K, V>> entries = getEntries(key.hashCode());
        int localLength = entries.size();
        int i = getKeyIndex(entries, key);
        if (i != -1) {
            entries = entries.delete(i);
        }
        entries = entries.prepend(new SimpleImmutableEntry<>(key, value));
        return new CollectioMap<>(integerMap.add(key.hashCode(), entries),
                length - localLength + entries.size());
    }

    /**
     * Method to add multiple key values to the map
     *
     * @param map
     * @return CollectioMap
     */
    @Override
    public CollectioMap<K, V> addAll(Map<? extends K, ? extends V> map) {
        CollectioMap<K, V> result = this;
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            result = result.add(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * Method for checking whether there is an object for specified key
     *
     * @param key
     * @return boolean
     */
    public boolean contains(final Object key) {
        return getKeyIndex(getEntries(key.hashCode()), key) != -1;
    }

    /**
     * Overridden method to get value by key
     *
     * @param key
     * @return
     */
    @Override
    public V get(final Object key) {
        Collectios<Entry<K, V>> entries = getEntries(key.hashCode());

        for (Entry<K, V> entry : entries)
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }

        return null;
    }

    /**
     * Overridden method to delete specified object from map
     *
     * @param key
     * @return CollectioMap
     */
    @Override
    public CollectioMap<K, V> delete(Object key) {
        Collectios<Entry<K, V>> entries = getEntries(key.hashCode());
        int i = getKeyIndex(entries, key);
        if (i == -1) {
            return this;
        }
        entries = entries.delete(i);
        if (entries.size() == 0) {
            return new CollectioMap<>(integerMap.delete(key.hashCode()), length - 1);
        }

        return new CollectioMap<>(integerMap.add(key.hashCode(), entries),
                length - 1);
    }

    /**
     * Overridden method to delete multiple objects from the map
     *
     * @param keys
     * @return CollectioMap
     */
    @Override
    public CollectioMap<K, V> deleteAll(Collection<? extends K> keys) {
        CollectioMap<K, V> result = this;
        for (Object key : keys) {
            result = result.delete(key);
        }
        return result;
    }

    /**
     * Method to get entries
     *
     * @param hash
     * @return Collectios
     */
    private Collectios<Entry<K, V>> getEntries(final int hash) {
        Collectios<Entry<K, V>> entries = integerMap.get(hash);
        if (entries == null) {
            return CollectioList.empty();
        }
        return entries;
    }
}
