package ccollection.cset;

import ccollection.CCollection;
import ccollection.cmap.CMap;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;


public final class CHashSet<E> extends AbstractSet<E> implements CSet<E> {

    private static final Set<Object> EMPTY = new HashSet<>();
    private final CMap<E, Object> map;
    private final int hashCode;

    private CHashSet(final CMap<E, Object> map, final int hashCode) {
        this.map = map;
        this.hashCode = hashCode;
    }

    public static <E> CHashSet from(final CMap<E, ?> map) {
        return new CHashSet(map, map.keySet().hashCode());
    }

    public static <E> CHashSet<E> empty() {
        return (CHashSet<E>) EMPTY;
    }

    public static <E> CHashSet<E> getInstance(final E e) {
        return CHashSet.<E>empty().prepend(e);
    }

    public static <E> CHashSet<E> from(final Collection<? extends E> list) {
        return CHashSet.<E>empty().prependAll(list);
    }

    public static <E> CHashSet from(final CMap<E, ?> map, final Collection<? extends E> list) {
        return from(map).prependAll(list);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public int size() {
        return map.size();
    }


    @Override
    public boolean contains(final Object e) {
        return map.containsKey(e);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    public CHashSet prepend(final E e) {
        return new CHashSet(map.add(e, null), hashCode + e.hashCode());
    }

    public CHashSet delete(final Object e) {
        if (!contains(e)) return this;
        return new CHashSet(map.delete(e), hashCode - e.hashCode());
    }

    public CHashSet prependAll(final Collection<? extends E> list) {
        CMap<E, Object> map = this.map;
        for (E e : list)
            map = map.add(e, null);
        return from(map);
    }

    public CHashSet deleteAll(final Collection<? extends E> list) {
        CMap<E, Object> map = this.map.deleteAll(list);
        return from(map);
    }

    @Override
    public CCollection<E> subCList(int start, int end) {
        return null;
    }
}
