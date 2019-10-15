package collectios.cset;

import collectios.Collectios;

import java.util.Collection;
import java.util.Set;


public interface CSet<E> extends Set<E>, Collectios<E> {

    CSet<E> prepend(E e);

    CSet<E> prependAll(Collection<? extends E> list);

    CSet<E> delete(Object e);

    CSet<E> deleteAll(Collection<? extends E> list);

}
