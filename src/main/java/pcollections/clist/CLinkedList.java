package pcollections.clist;

import pcollections.exception.DoubleInitializationException;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ListIterator;

import static pcollections.enums.Constants.ZERO;


public final class CLinkedList<I> extends AbstractSequentialList<I> implements CList<I> {
    private static final CLinkedList<Object> SINGLE_INSTANCE = new CLinkedList<>();

    private final I firstItem;

    private final CLinkedList<I> restItems;

    private final int length;

    private CLinkedList() {
        if (SINGLE_INSTANCE != null) {
            throw new DoubleInitializationException();
        }
        firstItem = null;
        restItems = null;
        length = ZERO;
    }

    public static <E> CLinkedList<E> empty() {
        return (CLinkedList<E>) SINGLE_INSTANCE;
    }

    @Override
    public ListIterator<I> listIterator(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public CList<I> prepend(I i) {
        return null;
    }

    @Override
    public CList<I> prependAll(Collection<? extends I> list) {
        return null;
    }

    @Override
    public CList<I> prependTo(int i, I i2) {
        return null;
    }

    @Override
    public CList<I> prependAll(int i, Collection<? extends I> list) {
        return null;
    }

    @Override
    public CList<I> delete(I i) {
        return null;
    }

    @Override
    public CList<I> deleteAll(Collection<? extends I> list) {
        return null;
    }

    @Override
    public CList<I> delete(int i) {
        return null;
    }

    @Override
    public CList<I> subCList(int start, int end) {
        return null;
    }

    @Override
    public CList<I> subCList(int start) {
        return null;
    }
}

