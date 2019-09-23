package ccollection.cmap;

import ccollection.CCollection;
import ccollection.clist.CLinkedList;

import java.util.Iterator;

public class CMapIterator<E> implements Iterator<E> {
    private final Iterator<CCollection<E>> i;
    private CCollection<E> seq = CLinkedList.empty();

    CMapIterator(Iterator<CCollection<E>> i) {
        this.i = i;
    }

    public boolean hasNext() {
        return seq.size() > 0 || i.hasNext();
    }

    public E next() {
        if (seq.size() == 0) {
            seq = i.next();
        }
        final E result = seq.stream().findFirst().get();
        seq = seq.subCList(1, seq.size());
        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
