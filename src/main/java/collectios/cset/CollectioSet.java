package collectios.cset;

import collectios.Collectios;
import collectios.cmap.CMap;

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
public final class CollectioSet<E> extends AbstractSet<E> implements CSet<E> {

    private static final Set<Object> EMPTY = new HashSet<>();
    private final CMap<E, Object> map;
    private final int hashCode;

    private CollectioSet(final CMap<E, Object> map, final int hashCode) {
        this.map = map;
        this.hashCode = hashCode;
    }

    public static <E> CollectioSet from(final CMap<E, ?> map) {
        return new CollectioSet(map, map.keySet().hashCode());
    }

    /**
     * Empty set
     *
     * @param <E>
     * @return CollectioSet
     */
    public static <E> CollectioSet<E> empty() {
        return (CollectioSet<E>) EMPTY;
    }

    /**
     * Get instance
     *
     * @param e
     * @param <E>
     * @return CollectioSet
     */
    public static <E> CollectioSet<E> getInstance(final E e) {
        return CollectioSet.<E>empty().prepend(e);
    }

    /**
     * Make CollectioSet from any Java Collection
     *
     * @param list
     * @param <E>
     * @return CollectioSet
     */
    public static <E> CollectioSet<E> from(final Collection<? extends E> list) {
        return CollectioSet.<E>empty().prependAll(list);
    }

    public static <E> CollectioSet from(final CMap<E, ?> map, final Collection<? extends E> list) {
        return from(map).prependAll(list);
    }

    /**
     * Get CollectioSet iterator
     *
     * @return CollectioSet
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
     * @return CollectioSet
     */
    public CollectioSet addAllItems(int index, Collection<? extends E> list) {
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
     * @return CollectioSet
     */
    public CollectioSet prepend(final E e) {
        return new CollectioSet(map.add(e, null), hashCode + e.hashCode());
    }

    /**
     * Delete element
     *
     * @param e
     * @return CollectioSet
     */
    public CollectioSet delete(final Object e) {
        if (!contains(e)) return this;
        return new CollectioSet(map.delete((E) e), hashCode - e.hashCode());
    }

    /**
     * Delete element
     *
     * @param e
     * @return CollectioSet
     */
    public CollectioSet delete(int e) {
        return null;
    }

    /**
     * Prepend all elements
     *
     * @param list
     * @return
     */
    public CollectioSet prependAll(final Collection<? extends E> list) {
        CMap<E, Object> map = this.map;
        for (E e : list)
            map = map.add(e, null);
        return from(map);
    }

    /**
     * Delete all collection from given Set
     *
     * @param list
     * @return CollectioSet
     */
    public CollectioSet deleteAll(final Collection<? extends E> list) {
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
    @Override
    public Collectios<E> subCList(int start, int end) {
        return null;
    }
}
