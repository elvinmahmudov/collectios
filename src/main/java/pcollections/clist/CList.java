package pcollections.clist;

import pcollections.CCollection;

import java.util.Collection;


public interface CList<E> extends CCollection<E>, java.util.List<E> {

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
