package com.elvinmahmudov.collectios.cset;

import com.elvinmahmudov.collectios.clist.Collectios;
import com.elvinmahmudov.collectios.cmap.CMap;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * The class is analogue for Java's HashSet
 *
 * @param <E>
 * @author emahmudov
 */
public final class CollectiosSet<E> extends AbstractSet<E> implements CSet<E> {

    private static final Set<Object> EMPTY = new HashSet<>();
    private final CMap<E, Object> map;
    private final int hashCode;

    private CollectiosSet(final CMap<E, Object> map, final int hashCode) {
        this.map = map;
        this.hashCode = hashCode;
    }

    public static <E> CollectiosSet from(final CMap<E, ?> map) {
        return new CollectiosSet(map, map.keySet().hashCode());
    }

    /**
     * Empty set
     *
     * @param <E>
     * @return CollectiosSet
     */
    public static <E> CollectiosSet<E> empty() {
        return (CollectiosSet<E>) EMPTY;
    }

    /**
     * Get instance
     *
     * @param e
     * @param <E>
     * @return CollectiosSet
     */
    public static <E> CollectiosSet<E> getInstance(final E e) {
        return CollectiosSet.<E>empty().prepend(e);
    }

    /**
     * Make CollectiosSet from any Java Collection
     *
     * @param list
     * @param <E>
     * @return CollectiosSet
     */
    public static <E> CollectiosSet<E> from(final Collection<? extends E> list) {
        return CollectiosSet.<E>empty().prependAll(list);
    }

    public static <E> CollectiosSet from(final CMap<E, ?> map, final Collection<? extends E> list) {
        return from(map).prependAll(list);
    }

    /**
     * Get CollectiosSet iterator
     *
     * @return CollectiosSet
     */
    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    /**
     * A
     *
     * @param list
     * @param index
     * @return CollectiosSet
     */
    public CollectiosSet addAllItems(int index, Collection<? extends E> list) {
        CMap<E, Object> map = this.map;
        for (E e : list) map = map.add(e, "YES");
        return from(map);
    }


    /**
     * Size of the Set
     *
     * @return int
     */
    @Override
    public int size() {
        return map.size();
    }


    /**
     * Whether the Set contains given object
     *
     * @param e
     * @return boolean
     */
    @Override
    public boolean contains(final Object e) {
        return map.containsKey(e);
    }


    /**
     * Hash code of the Set
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Prepend given elemnt to the set
     *
     * @param e
     * @return CollectiosSet
     */
    public CollectiosSet prepend(final E e) {
        return new CollectiosSet(map.add(e, null), hashCode + e.hashCode());
    }

    /**
     * Delete element
     *
     * @param e
     * @return CollectiosSet
     */
    public CollectiosSet delete(final Object e) {
        if (!contains(e)) return this;
        return new CollectiosSet(map.delete((E) e), hashCode - e.hashCode());
    }

    /**
     * Delete element
     *
     * @param e
     * @return CollectiosSet
     */
    public CollectiosSet delete(int e) {
        return null;
    }

    /**
     * Prepend all elements
     *
     * @param list
     * @return
     */
    public CollectiosSet prependAll(final Collection<? extends E> list) {
        CMap<E, Object> map = this.map;
        for (E e : list)
            map = map.add(e, null);
        return from(map);
    }

    /**
     * Delete all collection from given Set
     *
     * @param list
     * @return CollectiosSet
     */
    public CollectiosSet deleteAll(final Collection<? extends E> list) {
        CMap<E, Object> map = this.map.deleteAll(list);
        return from(map);
    }

    /**
     * Get sublist of elements from start to end
     *
     * @param start
     * @param end
     * @return Collectios
     */
    public Collectios<E> subCList(int start, int end) {
        return null;
    }
}
