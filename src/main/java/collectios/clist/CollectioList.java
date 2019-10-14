package collectios.clist;

import collectios.exception.DoubleInitializationException;
import collectios.exception.EmptyObjectException;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import static collectios.enums.Constants.ONE;
import static collectios.enums.Constants.ZERO;


/**
 * This class is analogue for Java LinkedList, but immutable and consistent
 *
 * @author emahmudov
 */
public final class CollectioList<I> extends AbstractSequentialList<I> implements CList<I> {
    private static final CollectioList<Object> SINGLE_INSTANCE = new CollectioList<>();

    /**
     * First item in the list
     */
    private final I firstItem;

    /**
     * Rest items in the list
     */
    private final CollectioList<I> restItems;

    /**
     * Size of list
     */
    private final int length;

    /**
     * Private constructor which is used for empty singleton
     */
    private CollectioList() {
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
    private CollectioList(final I firstItem, final CollectioList<I> restItems) {
        length = restItems.length + ONE;

        this.firstItem = firstItem;
        this.restItems = restItems;
    }

    /**
     * Method for converting collections to collectio lists
     *
     * @param list
     * @param <I>
     * @return CollectioList
     */
    public static <I> CollectioList<I> of(final Collection<? extends I> list) {
        if (list instanceof CollectioList) {
            return (CollectioList<I>) list;
        }

        return of(list.iterator());
    }

    /**
     * Method for converting iterators to collectio lists
     *
     * @param i
     * @param <I>
     * @return CollectioList
     */
    private static <I> CollectioList<I> of(final Iterator<? extends I> i) {
        if (!i.hasNext()) {
            return empty();
        }
        I e = i.next();
        return CollectioList.<I>of(i).prepend(e);
    }

    /**
     * Primary method for getting the instance
     *
     * @param i
     * @param <I>
     * @return CollectioList
     */
    public static <I> CollectioList<I> getInstance(final I i) {
        return CollectioList.<I>empty().prepend(i);
    }

    /**
     * Method to get empty instance
     *
     * @param <E>
     * @return CollectioList
     */
    public static <E> CollectioList<E> empty() {
        return (CollectioList<E>) SINGLE_INSTANCE;
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
     * @return CollectioList
     */
    public CollectioList<I> getRestItems() {
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
     * @return CollectioList
     */
    @Override
    public CollectioList<I> prepend(I i) {
        return new CollectioList<>(i, this);
    }

    /**
     * Overridden method from CList to prepend multiple objects to the list
     *
     * @param list
     * @return CollectioList
     */
    @Override
    public CollectioList<I> prependAll(Collection<? extends I> list) {
        CollectioList<I> result = this;
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
     * @return CollectioList
     */
    @Override
    public CollectioList<I> prependTo(int index, I e) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return prepend(e);
        }
        return new CollectioList<>(firstItem, restItems.prependTo(index - 1, e));
    }

    /**
     * Overridden method from CList to prepend multiple objects to the index of the list
     *
     * @param i
     * @param list
     * @return CollectioList
     */
    @Override
    public CollectioList<I> prependAll(int i, Collection<? extends I> list) {
        if (restItems == null) {
            throw new EmptyObjectException();
        }
        if (i < 0 || i > length) {
            throw new IndexOutOfBoundsException();
        }
        if (i == 0) {
            return prependAll(list);
        }
        return new CollectioList<>(firstItem, restItems.prependAll(i - 1, list));
    }

    /**
     * Overridden method from CList to delete an object from the list
     *
     * @param i
     * @return CollectioList
     */
    @Override
    public CollectioList<I> delete(I i) {
        if (firstItem == null || restItems == null) {
            throw new EmptyObjectException();
        }
        if (firstItem.equals(i)) {
            return restItems;
        }

        CollectioList<I> newRest = restItems.delete(i);
        if (newRest == restItems) {
            return this;
        }
        return new CollectioList<>(firstItem, newRest);
    }

    /**
     * Overridden method to delete all objects from the list
     *
     * @param list
     * @return CollectioList
     */
    @Override
    public CollectioList<I> deleteAll(Collection<? extends I> list) {
        if (restItems == null) {
            throw new EmptyObjectException();
        }
        if (length == 0) {
            return this;
        }
        if (list.contains(firstItem)) {
            return restItems.deleteAll(list);
        }

        CollectioList<I> newRest = restItems.deleteAll(list);
        if (newRest == restItems) {
            return this;
        }
        return new CollectioList<>(firstItem, newRest);
    }

    /**
     * Overridden method to delete object by index
     *
     * @param i
     * @return CollectioList
     */
    @Override
    public CollectioList<I> delete(int i) {
        return delete(get(i));
    }

    /**
     * Overridden method from CList to create a sub list
     *
     * @param fromIndex
     * @param toIndex
     * @return CollectioList
     */
    @Override
    public CollectioList<I> subCList(int fromIndex, int toIndex) {
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
            return new CollectioList<>(firstItem, restItems.subCList(ZERO, toIndex - ONE));
        }

        return restItems.subCList(fromIndex - 1, toIndex - 1);
    }

    /**
     * Overridden method from CList to create a sublist starting from index
     *
     * @param fromIndex
     * @return CollectioList
     */
    @Override
    public CollectioList<I> subCList(int fromIndex) {
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

