package jobblett.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SuperClass for all List-classes in Jobblett.
 * Type "k" is the class-type used for in the identification.
 * Type "T" is the class-type which is stored in the list.
 */
public abstract class JobblettList<k,T>  extends JobblettPropertyChangeSupporter implements Iterable<T>, PropertyChangeListener {

    private List<T> list = new ArrayList<>();

    public T get(k key) {
        return stream()
                .filter(a -> identifier(a).equals(key))
                .findAny()
                .orElse(null);
    }

    public boolean add(T... objects) {
        for (T o : objects) {
            if (get(identifier(o)) != null)
                optionalAlreadyExists();
            if (o instanceof JobblettPropertyChangeSupporter)
                ((JobblettPropertyChangeSupporter) o).addListener(this);
        }
        boolean result = list.addAll(Arrays.asList(objects));
        firePropertyChange(simpleTypeName()+"List",list);
        if (optionalComparator()!=null) Collections.sort(list,optionalComparator());
        return result;
    }

    public boolean addAll(Iterable<T> jobblettList) {
        Collection<T> helperList = new ArrayList<>();
        jobblettList.forEach(helperList::add);
        return add((T[]) helperList.toArray());
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
    public boolean remove(T o) {
        boolean removed = list.remove(o);
        if (removed)
            if (o instanceof JobblettPropertyChangeSupporter)
                ((JobblettPropertyChangeSupporter) o).removeListener(this);
        return removed;
    }
    public boolean contains(T o) {
        return list.contains(o);
    }
    public Stream<T> stream() {
        return list.stream();
    }
    public List<T> filter(Predicate<T> predicate) {
        return stream().filter(predicate).collect(Collectors.toList());
    }
    public int size() {
        return list.size();
    }
    protected int indexOf(T type) {
        return list.indexOf(type);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return simpleTypeName()+"List=" + list;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof JobblettList) {
            JobblettList newList = (JobblettList) o;
            return list.equals(newList.list);
        }
        else return super.equals(o);
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        T source = (T) evt.getSource();
        String propertyName = simpleTypeName()+"{"+ identifier(source)+"}: "+evt.getPropertyName();
        firePropertyChange(propertyName, evt.getOldValue(),evt.getNewValue());
    }

    protected String simpleTypeName() {
        String fullTypeName = ((ParameterizedType)getClass().getGenericSuperclass())
                .getActualTypeArguments()[1]
                .getTypeName();
        String[] splitTypeName = fullTypeName.split("\\.");
        String simpleTypeName = splitTypeName[splitTypeName.length-1];
        return simpleTypeName;
    }

    protected abstract k identifier(T type);

    protected void optionalAlreadyExists() {

    };

    protected Comparator<T> optionalComparator() {
        return null;
    }


}