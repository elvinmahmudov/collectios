package collectios;

import java.util.Collection;


public interface Collectios<E> extends Collection<E> {

    Collectios<E> prepend(E e);

    Collectios<E> prependAll(Collection<? extends E> list);

    Collectios<E> delete(E e);

    Collectios<E> delete(int i);

    Collectios<E> deleteAll(Collection<? extends E> list);

    Collectios<E> subCList(int start, int end);
}
