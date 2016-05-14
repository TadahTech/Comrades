package com.tadahtech.pub.comrades.module;

import com.tadahtech.pub.comrades.Comrades;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by legop on 5/12/2016.
 */
public class ModuleLoader {

    /**
     * Loads the IComradesModule from the jar, instantiating the new instance with the argument of a javaplugin.
     * @param clazzbin - The path to the main class.
     * @return - The loaded module.
     * @throws Exception
     */
    private static IComradesModule instantiateModule(String clazzbin) throws Exception{
        ClassLoader loader = Comrades.class.getClass().getClassLoader();
        Class loadedClass = loader.loadClass(clazzbin);
        System.out.println("Loaded class: " + loadedClass.getName());
        Constructor con = loadedClass.getConstructor(JavaPlugin.class);
        IComradesModule cm = (IComradesModule) con.newInstance(Comrades.getInstance());

        return cm;
    }

    /**
     * Load all of the modules in the modules dir.
     * Loops through all files in the modules dir, every file that ends with '.jar' is checked to see if it contains a module.yml
     * If it does, it instantiates the module, adding it to the map in @link{Comrades.jar}
     * @throws Exception
     * @throws com.tadahtech.pub.comrades.module.ModuleDescription.InvalidModuleException - When the module.yml is not present, or is invalid.
     */
    public static void loadModules() throws Exception{
        File dir = new File(Comrades.getInstance().getDataFolder(), "modules");
        if (!dir.exists()){
            dir.mkdirs();
        }
        for (File f : dir.listFiles()){
            if (!f.getName().endsWith(".jar")){
                continue;
            }
            JarFile jar = new JarFile(f);
            JarEntry je = jar.getJarEntry("module.yml");
            if (je == null){
                System.out.println("Jar does not contain module.yml: " + jar.getName());
                continue;
            }
            InputStream is = jar.getInputStream(je);

            ModuleDescription md = new ModuleDescription(is);
            Comrades.getInstance().registerModule(instantiateModule(md.getPath()));

            jar.close();
            is.close();
        }
    }

    public static void loadModule(String path) throws Exception{
        Comrades.getInstance().registerModule(instantiateModule(path));

    }
}
