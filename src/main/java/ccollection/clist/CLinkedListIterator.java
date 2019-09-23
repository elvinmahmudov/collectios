package ccollection.clist;

import java.util.ListIterator;

import static ccollection.enums.Constants.ZERO;

public class CLinkedListIterator<I> implements ListIterator<I> {

    private int index;
    private CLinkedList<I> restItems;

    public CLinkedListIterator(int index, CLinkedList<I> restItems) {
        this.index = index;
        this.restItems = restItems;
    }

    public boolean hasNext() {
        return this.restItems.size() > ZERO;
    }

    public boolean hasPrevious() {
        return this.index > ZERO;
    }

    public int nextIndex() {
        return this.index;
    }

    public int previousIndex() {
        return this.index - 1;
    }

    public I next() {
        I i = restItems.getFirstItem();
        restItems = restItems.getRestItems();
        return i;
    }

    public I previous() {
        restItems = restItems.subCList(index - 1);
        return restItems.getFirstItem();
    }

    public void add(final I o) {
        throw new UnsupportedOperationException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void set(final I o) {
        throw new UnsupportedOperationException();
    }
}
