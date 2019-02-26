package util;

import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jianyuan.wei@hand-china.com
 * @date 2019/2/26 16:49
 */
public class ClassLoaderUtilTest {

    @Test
    public void test() {

        ClassLoader classLoader = ClassLoaderUtil.getDefaultClassLoader();
        /**
         * 在测试目录下,资源的
         */
        URL resource = classLoader.getResource("./../classes/util/ListSplit.class");
        InputStream is = classLoader.getResourceAsStream("util/ListSplit.class");
        URL url = classLoader.getResource("./../classes/util");
        List<String> listFileNames = new ArrayList<>();
        String file = url.getFile();
        System.out.println(file);
        listFileName(listFileNames,true,new File(file));
        System.out.println(listFileNames);

    }

    private void listFileName(List<String> fileNames,Boolean isIter,File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tf : files) {
                if (isIter && tf.isDirectory()) {
                    listFileName(fileNames, isIter, tf);
                } else {
                    fileNames.add(file.getPath() + "/" + tf.getName());
                }
            }
        }
    }

}