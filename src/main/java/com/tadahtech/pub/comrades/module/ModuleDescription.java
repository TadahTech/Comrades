package com.tadahtech.pub.comrades.module;

import org.bukkit.plugin.PluginAwareness;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by legop on 5/12/2016.
 * MODULE JAR MUST CONTAIN A YML FILE FORMATED AS SUCH:
 *     main: path.to.main.class
 */
public class ModuleDescription {
    //The YAML
    private static final ThreadLocal<Yaml> YAML = new ThreadLocal<Yaml>() {
        @Override
        protected Yaml initialValue() {
            return new Yaml(new SafeConstructor() {
                {
                    yamlConstructors.put(null, new AbstractConstruct() {
                        @Override
                        public Object construct(final Node node) {
                            if (!node.getTag().startsWith("!@")) {
                                // Unknown tag - will fail
                                return SafeConstructor.undefinedConstructor.construct(node);
                            }
                            // Unknown awareness - provide a graceful substitution
                            return new PluginAwareness() {
                                @Override
                                public String toString() {
                                    return node.toString();
                                }
                            };
                        }
                    });
                    for (final PluginAwareness.Flags flag : PluginAwareness.Flags.values()) {
                        yamlConstructors.put(new Tag("!@" + flag.name()), new AbstractConstruct() {
                            @Override
                            public PluginAwareness.Flags construct(final Node node) {
                                return flag;
                            }
                        });
                    }
                }
            });
        }
    };

    //The path
    private String path;

    /**
     * Contructs the ModuleDescription for the module.
     * @param is - The module.yml file, as loaded by JarEntry.
     * @throws InvalidModuleException
     */
    public ModuleDescription(InputStream is) throws InvalidModuleException{
        loadMap(asMap(YAML.get().load(is)));
    }

    public String getPath(){
        return path;
    }

    private void setPath(String path){
        this.path = path;
    }

    /**
     *
     * @param object - The InputStream.
     * @return The InputStream as a map.
     * @throws InvalidModuleException
     */
    private Map<?,?> asMap(Object object) throws InvalidModuleException{
        if (object instanceof Map) {
            return (Map<?, ?>) object;
        }
        throw new InvalidModuleException("Invalid Module: jar does not contain a module.yml");
    }

    /**
     * Reads the map and parses it into the description object.
     * @param map
     * @throws InvalidModuleException
     */
    private void loadMap(Map<?,?> map) throws InvalidModuleException{
        Object o = map.get("main");
        if (o == null){
            throw new InvalidModuleException("module.yml does not contain a main classpath.");
        }
        String p = o.toString();
        setPath(p);
    }

    /**
     * Thrown when the description is invalid.
     */
    public static class InvalidModuleException extends Exception {

        public InvalidModuleException(String s){
            super(s);
        }
    }
}
