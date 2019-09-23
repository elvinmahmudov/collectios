package pcollections.cmap;

import java.util.Collection;
import java.util.Map;

public interface CMap<K, V> extends Map<K, V> {

    CMap<K, V> add(K key, V value);

    CMap<K, V> addAll(Map<? extends K, ? extends V> map);

    CMap<K, V> delete(K key);

    CMap<K, V> deleteAll(Collection<? extends K> keys);
}
