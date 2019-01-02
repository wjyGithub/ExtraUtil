package util;

import java.util.ArrayList;
import java.util.List;

/**
 * List分隔工具类,能够将给定的一个大list分隔成指定长度的小list
 * @author jianyuan.wei@hand-china.com
 * @date 2018/12/2 14:24
 */
public class ListSplit<T> {

    /**
     * 待处理的List
     */
    private List<T> datas;

    /**
     * 拆分的小数组个数
     */
    private int size;

    /**
     * 待处理的数据总量
     */
    private int totalNum;

    /**
     * 使用默认的长度分隔list
     * @param datas 待分隔的list
     */
    public ListSplit(List<T> datas) {
        this.datas = datas;
        this.size = 30;
        this.totalNum = datas.size();
    }

    /**
     * 指定分隔的长度进行分隔
     * @param datas  待分隔的list
     * @param size 分隔长度
     */
    public ListSplit(List<T> datas, int size) {
        this.datas = datas;
        this.size = size;
        this.totalNum = datas.size();
    }


    /**
     * 分隔给定的list
     * @return
     */
    public List<List<T>> split() {

        List<List<T>> smailList = new ArrayList<>();
        int num = totalNum % size == 0 ? totalNum / size : (totalNum / size) +1;
        for(int i=0; i<num; i++) {
            smailList.add(datas.subList(i*size,(i+1) * size >= totalNum ? totalNum :(i+1)*size));
        }
        return smailList;
    }
}
