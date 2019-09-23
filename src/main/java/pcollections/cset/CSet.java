package pcollections.cset;

import pcollections.CCollection;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public interface CSet<E> extends Set<E>, CCollection<E>, List<E> {

    CSet<E> prepend(E e);

    CSet<E> prependAll(Collection<? extends E> list);

    CSet<E> delete(Object e);

    CSet<E> deleteAll(Collection<? extends E> list);

}
