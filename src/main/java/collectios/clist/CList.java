package collectios.clist;

import collectios.Collectios;

import java.util.Collection;


public interface CList<E> extends Collectios<E>, java.util.List<E> {

    CList<E> prepend(E e);

    CList<E> prependAll(Collection<? extends E> list);

    CList<E> prependTo(int i, E e);

    CList<E> prependAll(int i, Collection<? extends E> list);

    CList<E> delete(E e);

    CList<E> deleteAll(Collection<? extends E> list);

    CList<E> delete(int i);

    CList<E> subCList(int start, int end);

    CList<E> subCList(int start);
}
