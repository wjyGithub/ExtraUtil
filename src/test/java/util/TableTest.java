package util;


import org.junit.Test;

import java.util.Set;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/1/2 18:04
 */
public class TableTest {

    @Test
    public void table() {
        Table<String,Integer,String> table = new Table<>();
        table.put("wjy1",21,"物联网工程师");
        table.put("wjy2",21,"物联网工程师");
        table.put("wjy3",22,"物联网工程师");
        table.put("wjy4",23,"物联网工程师");
        table.put("wjy5",24,"物联网工程师");
        System.out.println(table);

        //记录存在
        System.out.println("--------记录存在------------");
        Table.TableEntry wjy1 = table.get("wjy1");
        System.out.println(wjy1);

        //记录不存在
        System.out.println("--------记录不存在------------");
        Table.TableEntry wjy = table.get("wjy");
        System.out.println("记录不存在的返回值:" + wjy);

        //遍历表中全部记录
        Set<Table.TableEntry<String,Integer,String>> entries = table.entrySet();
        System.out.println("---------遍历表中的全部记录-----------------");
        for(Table.TableEntry<String,Integer,String> tableEntry : entries) {
            System.out.println(tableEntry);
        }
    }
}