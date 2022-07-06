import com.hj.Plugs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String path = "/Users/hejie/Desktop/Plug-1.0-SNAPSHOT.jar";
        JarFile jarFile = new JarFile(new File(path));
        URL url = new URL("file:" + path);
        ClassLoader loader = new URLClassLoader(new URL[]{url});//自己定义的classLoader类，
        Enumeration<JarEntry> es = jarFile.entries();
        while (es.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) es.nextElement();
            String name = jarEntry.getName();
            if (name != null && name.endsWith(".class")) {//只解析了.class文件，没有解析里面的jar包
                //默认去系统已经定义的路径查找对象，针对外部jar包不能用
                //Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(name.replace("/", ".").substring(0,name.length() - 6));
                Class<?> c = loader.loadClass(name.replace("/", ".").substring(0, name.length() - 6));
                Plugs plugs =(Plugs) c.newInstance();
                plugs.show();
            }
        }
    }

}
