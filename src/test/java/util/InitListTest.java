package util;

import org.junit.Test;

import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/2/27 10:24
 */
public class InitListTest {

    @Test
    public void test(){
        List<Integer> list = new InitList<>(0);
        list.add(1);
        list.add(3);
        System.out.println("list[0]:" + list.get(0));
        System.out.println("list[1]:" + list.get(1));
        System.out.println("list的长度:" + list.size() + ",list:" +list);
        list.set(4,8);
        list.set(0,5);
        System.out.println("list[0]:" + list.get(0));
        System.out.println("list[1]:" + list.get(1));
        System.out.println("list[2]:" + list.get(2));
        System.out.println("list[3]:" + list.get(3));
        System.out.println("list[4]:" + list.get(4));
        //System.out.println("list[5]:" + list.get(5));
        System.out.println("list的长度:" + list.size() + ",list:" +list);
    }

}