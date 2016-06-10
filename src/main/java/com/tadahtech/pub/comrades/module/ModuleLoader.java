package com.tadahtech.pub.comrades.module;

import com.tadahtech.pub.comrades.Comrades;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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
    private static IComradesModule instantiateModule(File file, String clazzbin) throws Exception{
        ModuleClassLoader loader;
        if (file != null){
            loader = new ModuleClassLoader(file, Comrades.getInstance().getClass().getClassLoader(), clazzbin);
        } else {
            loader = new ModuleClassLoader(Comrades.getInstance().getClass().getClassLoader(), clazzbin);
        }

        Class loadedClass = null;
        try {
            loadedClass = Class.forName(clazzbin);
        } catch (ClassNotFoundException | NoClassDefFoundError e){
            loadedClass = loader.loadClass(clazzbin);
        }
        System.out.println("Loaded class: " + loadedClass.getName());
        Constructor con = loadedClass.getConstructor(JavaPlugin.class);
        if (con == null){
            throw new ModuleDescription.InvalidModuleException("Main file does not have a default constructor with arguments of JavaPlugin.");
        }
        Object cm = con.newInstance(Comrades.getInstance());
        if (!(cm instanceof IComradesModule)){
            throw new ModuleDescription.InvalidModuleException("Main file does not extend ComradesModule.");
        }

        return (IComradesModule) cm;
    }

    /**
     * Load all of the modules in the modules dir.
     * Loops through all files in the modules dir, every file that ends with '.jar' is checked to see if it contains a module.yml
     * If it does, it instantiates the module, adding it to the map in @link{Comrades.java}
     * @throws Exception
     * @throws com.tadahtech.pub.comrades.module.ModuleDescription.InvalidModuleException - When the module.yml is not present, or is invalid.
     */
//    public static void loadModules() throws Exception{
//        File dir = new File(Comrades.getInstance().getDataFolder(), "modules");
//        if (!dir.exists()){
//            dir.mkdirs();
//        }
//        for (File f : dir.listFiles()){
//            if (f.isDirectory()){
//                continue;
//            }
//            if (!f.getName().endsWith(".jar")){
//                continue;
//            }
//            JarFile jar = new JarFile(f);
//            JarEntry je = jar.getJarEntry("module.yml");
//            if (je == null){
//                throw new ModuleDescription.InvalidModuleException("Jar does not contain module.yml");
//            }
//            InputStream is = jar.getInputStream(je);
//
//            ModuleDescription md = new ModuleDescription(is);
//            Comrades.getInstance().registerModule(instantiateModule(f, md.getPath()));
//
//            jar.close();
//            is.close();
//        }
//    }
    /**
     * Load all of the modules in the modules dir.
     * Loops through all files in the modules dir, every file that ends with '.jar' is checked to see if it contains a module.yml
     * If it does, it instantiates the module, adding it to the map in @link{Comrades.java}
     * @throws Exception
     */
    public static void loadModules() throws Exception{
        File dir = new File(Comrades.getInstance().getDataFolder(), "modules");
        if (!dir.exists()){
            dir.mkdirs();
        }
        for (File f : dir.listFiles()){
            if (f.isDirectory()){
                continue;
            }
            if (!f.getName().endsWith(".jar")){
                continue;
            }
            JarInputStream jFile = new JarInputStream(new FileInputStream(f));
            JarEntry je;
            while (true){
                je = jFile.getNextJarEntry();
                if (je == null){
                    break;
                }
                if (je.getName().endsWith(".class")){
                    String className = je.getName().replaceAll("/", "\\.");
                    className = className.replace(".class", "");
                    Comrades.getInstance().registerModule(instantiateModule(f, className));
                }
            }

        }


    }

    public static void loadModule(String path) throws Exception{
        Comrades.getInstance().registerModule(instantiateModule(null, path));

    }


    public static final class ModuleClassLoader extends URLClassLoader {

        public ModuleClassLoader(File f, ClassLoader parent, String clazz) throws Exception {

            super(new URL[]{f.toURI().toURL()}, parent);

            try {
                Class<?> jarClass;
                try {
                    jarClass = Class.forName(clazz, false, this);
                } catch (ClassNotFoundException ex) {
                    throw new InvalidPluginException("Cannot find main class `" + clazz + "'", ex);
                }

                Class<? extends ComradesModule> comClass;
                try {
                    comClass = jarClass.asSubclass(ComradesModule.class);
                } catch (ClassCastException ex) {
                    throw new InvalidPluginException("main class `" + clazz + "' does not extend JavaPlugin", ex);
                }


            } catch (Exception e){
                e.printStackTrace();
            }
        }
        public ModuleClassLoader(ClassLoader parent, String clazz) throws Exception {

            super(new URL[]{}, parent);

            try {
                Class<?> jarClass;
                try {
                    jarClass = Class.forName(clazz, true, this);
                } catch (ClassNotFoundException ex) {
                    throw new InvalidPluginException("Cannot find main class `" + clazz + "'", ex);
                }

                Class<? extends ComradesModule> comClass;
                try {
                    comClass = jarClass.asSubclass(ComradesModule.class);
                } catch (ClassCastException ex) {
                    throw new InvalidPluginException("main class `" + clazz + "' does not extend JavaPlugin", ex);
                }


            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
