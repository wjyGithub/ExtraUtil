package util;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *  jar包测试
 *  先执行compiler下面的compiler:compiler进行编译
 *  在通过Plugins->jar:jar进行打jar包
 *  执行java -cp xxx.jar util.ClassLoaderUtil(包含main类的全权限定名(包名+类名))
 * @author jianyuan.wei@hand-china.com
 * @date 2019/2/26 16:24
 */
public class ClassLoaderUtil {

    /**
     * 获取classLoader
     *
     * 获取资源的相对路径统一是以classes/为当前路径进行检索的
     * getResource/getResourceAsStream/getResource
     *
     * 如果在test目录下进行测试,那么相对路径统一以test-classes为当前路径进行检索
     * @return
     */
    public static ClassLoader getDefaultClassLoader(){

        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        }catch(Throwable e) {}
        if(classLoader == null) {
            classLoader = ClassLoaderUtil.class.getClassLoader();
            if(classLoader == null) {
                try {
                    classLoader = classLoader.getSystemClassLoader();
                }catch(Throwable e){}
            }
        }
        return classLoader;
    }

    /**
     * 从jar包中读取文件指定包下的文件全限定名
     * URL resource = classLoader.getResource(包名.replace('.','/'));
     * ((JarURLConnection)resource.openConnection()).getJarFile().entries();
     * 该方式会获取到jar包中的全部文件(会自动迭代文件夹下的文件夹,并统一获取到),
     * 并非是我们指定的包名下的所有文件,所以想要获取我们自己指定的包路径下的文件,需要添加判断
     * 如:
     * META-INF
     * META-INF/MANIFEST.MF
     * util/
     * util/BeanUtil.class
     * util/ClassLoaderUtil.class
     * util/ListSplit.class
     * util/ListWrap.class
     * util/ObjectConvert.class
     * util/Table$TableEntry.class
     * util/Table.class
     * META-INF/maven/
     * META-INF/maven/com.hand/
     * META-INF/maven/com.hand/ListWrap/
     * META-INF/maven/com.hand/ListWrap/pom.xml
     * META-INF/maven/com.hand/ListWrap/pom.properties
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String PACKAGE_TYPE_NAME = "util";
        // 不需要迭代子包的正则表达式
        String REG_EXP = "^" + PACKAGE_TYPE_NAME +"/[a-zA-Z]+\\.class$";
        //是否迭代包下的子包
        boolean isIter = true;
        //类的全限定名
        List<String> fullClassNames = new ArrayList<>();

        ClassLoader classLoader = getDefaultClassLoader();
        //获取util包下的全部文件
        URL resource = classLoader.getResource(PACKAGE_TYPE_NAME);
        System.out.println("资源类型:" + resource.getProtocol());
        if(resource.getProtocol().equalsIgnoreCase("jar")) {
            JarFile jarFile = ((JarURLConnection) resource.openConnection()).getJarFile();
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                //fileName: util/ListWrap.class
                String fileTypeName = jarEntry.getName();
                System.out.println(fileTypeName);
                String fileFullName = fileTypeName.replace("/",".");
                if(isIter && fileTypeName.startsWith(PACKAGE_TYPE_NAME) && fileTypeName.endsWith(".class") && !fileTypeName.contains("$")) {
                    fullClassNames.add(fileFullName.substring(0,fileFullName.lastIndexOf(".")));
                }else if(fileTypeName.matches(REG_EXP)) {
                    fullClassNames.add(fileFullName.substring(0,fileFullName.lastIndexOf(".")));
                }
            }
        }
        System.out.println(fullClassNames);
    }
}
