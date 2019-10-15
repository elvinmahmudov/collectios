package collectios.cmap;

import collectios.Collectios;
import collectios.clist.CollectioList;

import java.util.Iterator;

public class CollectioMapIterator<E> implements Iterator<E> {
    private final Iterator<Collectios<E>> i;
    private Collectios<E> seq = CollectioList.empty();

    CollectioMapIterator(Iterator<Collectios<E>> i) {
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
