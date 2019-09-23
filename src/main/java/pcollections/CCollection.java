package pcollections;

import java.util.Collection;


public interface CCollection<E> extends Collection<E> {

    CCollection<E> prepend(E e);

    CCollection<E> prependAll(Collection<? extends E> list);

    CCollection<E> delete(E e);

    CCollection<E> deleteAll(Collection<? extends E> list);

    CCollection<E> subCList(int start, int end);
}
