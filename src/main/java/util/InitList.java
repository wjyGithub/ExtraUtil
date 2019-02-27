package util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 具备初始化功能的List
 * @author jianyuan.wei@hand-china.com
 * @date 2018/10/27 10:51
 */
public class InitList<E> extends AbstractList<E> {

    private List<E> list;

    private int length;

    private E initValue;

    public InitList(E initValue) {
        list = new ArrayList<>();
        length = list.size();
        this.initValue = initValue;
    }

    @Override
    public boolean add(E e) {
        boolean flag = list.add(e);
        length = list.size();
        return flag;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = getOldValue(index);
        int diff = (index+1) - length >= 0 ? (index+1) - length : 0;
        List<E> diffList = new ArrayList<>(diff);
        for(int i=0; i< diff; i++) {
            diffList.add(initValue);
        }
        list.addAll(diffList);
        list.set(index,element);
        length = list.size();
        return oldValue;
    }

    private void rangeCheck(int index) {
        if(index < 0) {
            throw new IllegalArgumentException("下标范围越界");
        }
    }

    private E getOldValue(int index) {
        if(index >= length) {
            return initValue;
        }else {
            return get(index);
        }
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
