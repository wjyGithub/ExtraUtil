import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * list的包装类,具备初始化功能
 * @author jianyuan.wei@hand-china.com
 * @date 2018/10/27 10:51
 */
public class ListWrap<E> extends AbstractList<E> {

    private List<E> list;

    private int length;

    private E initValue;

    public ListWrap(E initValue) {
        list = new ArrayList<E>();
        length = list.size();
        this.initValue = initValue;
    }


    @Override
    public void add(int index, E element) {
        int diff = (index+1) - length >= 0 ? (index+1) - length : 0;
        List<E> diffList = new ArrayList<E>(diff);
        for(int i=0; i< diff; i++) {
            diffList.add(initValue);
        }
        list.addAll(diffList);
        list.set(index,element);
        length = list.size();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}
