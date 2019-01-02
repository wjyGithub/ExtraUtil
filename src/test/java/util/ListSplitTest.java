package util;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/2 20:29
 */
public class ListSplitTest {

    @Test
    public void split() {
        List<Integer>  list = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
        ListSplit<Integer> listSplit = new ListSplit<>(list, 3);
        List<List<Integer>> split = listSplit.split();
        System.out.println(split);
    }
}