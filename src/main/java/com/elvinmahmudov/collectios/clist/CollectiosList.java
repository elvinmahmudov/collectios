package com.elvinmahmudov.collectios.clist;

import com.elvinmahmudov.collectios.exception.DoubleInitializationException;
import com.elvinmahmudov.collectios.exception.EmptyObjectException;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import static com.elvinmahmudov.collectios.enums.Constants.ONE;
import static com.elvinmahmudov.collectios.enums.Constants.ZERO;


/**
 * This class is analogue for Java LinkedList, but immutable and consistent
 *
 * @author emahmudov
 */
public final class CollectiosList<I> extends AbstractSequentialList<I> implements CList<I> {
    private static final CollectiosList<Object> SINGLE_INSTANCE = new CollectiosList<>();

    /**
     * First item in the list
     */
    private final I firstItem;

    /**
     * Rest items in the list
     */
    private final CollectiosList<I> restItems;

    /**
     * Size of list
     */
    private final int length;

    /**
     * Private constructor which is used for empty singleton
     */
    private CollectiosList() {
        if (SINGLE_INSTANCE != null) {
            throw new DoubleInitializationException();
        }
        firstItem = null;
        restItems = null;
        length = ZERO;
    }

    /**
     * Private constructor for non empty singleton
     *
     * @param firstItem
     * @param restItems
     */
    private CollectiosList(final I firstItem, final CollectiosList<I> restItems) {
        length = restItems.length + ONE;

        this.firstItem = firstItem;
        this.restItems = restItems;
    }

    /**
     * Method for converting collections to collectio lists
     *
     * @param list
     * @param <I>
     * @return CollectiosList
     */
    public static <I> CollectiosList<I> of(final Collection<? extends I> list) {
        if (list instanceof CollectiosList) {
            return (CollectiosList<I>) list;
        }

        return of(list.iterator());
    }

    /**
     * Method for converting iterators to collectio lists
     *
     * @param i
     * @param <I>
     * @return CollectiosList
     */
    private static <I> CollectiosList<I> of(final Iterator<? extends I> i) {
        if (!i.hasNext()) {
            return empty();
        }
        I e = i.next();
        return CollectiosList.<I>of(i).prepend(e);
    }

    /**
     * Primary method for getting the instance
     *
     * @param i
     * @param <I>
     * @return CollectiosList
     */
    public static <I> CollectiosList<I> getInstance(final I i) {
        return CollectiosList.<I>empty().prepend(i);
    }

    /**
     * Method to get empty instance
     *
     * @param <E>
     * @return CollectiosList
     */
    public static <E> CollectiosList<E> empty() {
        return (CollectiosList<E>) SINGLE_INSTANCE;
    }

    /**
     * Method to get the first item of the list
     *
     * @return I
     */
    public I getFirstItem() {
        return firstItem;
    }

    /**
     * Method to get the rest items in the list
     *
     * @return CollectiosList
     */
    public CollectiosList<I> getRestItems() {
        return restItems;
    }

    /**
     * Overridden method from AbstractSequenceList to get list iterator
     *
     * @param index
     * @return ListIterator
     */
    @Override
    public ListIterator<I> listIterator(int index) {
        if (index < ZERO || index > length) {
            throw new IndexOutOfBoundsException();
        }

        return new CollectioListIterator<>(index, subCList(index));
    }

    /**
     * Overridden method from AbstractCollection to get size of the list
     *
     * @return int
     */
    @Override
    public int size() {
        return length;
    }

    /**
     * Overridden method from CList to prepend an object to the list
     *
     * @param i
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> prepend(I i) {
        return new CollectiosList<>(i, this);
    }

    /**
     * Overridden method from CList to prepend multiple objects to the list
     *
     * @param list
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> prependAll(Collection<? extends I> list) {
        CollectiosList<I> result = this;
        for (I i : list) {
            result = result.prepend(i);
        }
        return result;
    }

    /**
     * Overridden method from CList to prepend one object to the index of the list
     *
     * @param index
     * @param e
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> prependTo(int index, I e) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return prepend(e);
        }
        return new CollectiosList<>(firstItem, restItems.prependTo(index - 1, e));
    }

    /**
     * Overridden method from CList to prepend multiple objects to the index of the list
     *
     * @param i
     * @param list
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> prependAll(int i, Collection<? extends I> list) {
        if (restItems == null) {
            throw new EmptyObjectException();
        }
        if (i < 0 || i > length) {
            throw new IndexOutOfBoundsException();
        }
        if (i == 0) {
            return prependAll(list);
        }
        return new CollectiosList<>(firstItem, restItems.prependAll(i - 1, list));
    }

    /**
     * Overridden method from CList to delete an object from the list
     *
     * @param i
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> delete(I i) {
        if (firstItem == null || restItems == null) {
            throw new EmptyObjectException();
        }
        if (firstItem.equals(i)) {
            return restItems;
        }

        CollectiosList<I> newRest = restItems.delete(i);
        if (newRest == restItems) {
            return this;
        }
        return new CollectiosList<>(firstItem, newRest);
    }

    /**
     * Overridden method to delete all objects from the list
     *
     * @param list
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> deleteAll(Collection<? extends I> list) {
        if (restItems == null) {
            throw new EmptyObjectException();
        }
        if (length == 0) {
            return this;
        }
        if (list.contains(firstItem)) {
            return restItems.deleteAll(list);
        }

        CollectiosList<I> newRest = restItems.deleteAll(list);
        if (newRest == restItems) {
            return this;
        }
        return new CollectiosList<>(firstItem, newRest);
    }

    /**
     * Overridden method to delete object by index
     *
     * @param i
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> delete(int i) {
        return delete(get(i));
    }

    /**
     * Overridden method from CList to create a sub list
     *
     * @param fromIndex
     * @param toIndex
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> subCList(int fromIndex, int toIndex) {
        if (fromIndex < ZERO || toIndex > length || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        if (fromIndex == toIndex) {
            return empty();
        }

        if (toIndex == length) {
            return subCList(fromIndex);
        }

        if (restItems != null && fromIndex == ZERO) {
            return new CollectiosList<>(firstItem, restItems.subCList(ZERO, toIndex - ONE));
        }

        return restItems.subCList(fromIndex - 1, toIndex - 1);
    }

    /**
     * Overridden method from CList to create a sublist starting from index
     *
     * @param fromIndex
     * @return CollectiosList
     */
    @Override
    public CollectiosList<I> subCList(int fromIndex) {
        if (restItems == null) {
            throw new EmptyObjectException();
        }
        if (fromIndex < 0 || fromIndex > length) {
            throw new IndexOutOfBoundsException();
        }
        if (fromIndex == 0) {
            return this;
        }
        return restItems.subCList(fromIndex - 1);
    }
}

