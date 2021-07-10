package me.domirusz24.bq.codequestapi;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class YamlConfig extends Yaml {

    private final HashMap<String, String> CONTENTS = new LinkedHashMap<>();

    private final File file;

    public YamlConfig(File file) {
        this.file = toYaml(file);
    }

    public void add(String path, String value) {
        CONTENTS.put(path, value);
    }

    public String get(String path) {
        return CONTENTS.getOrDefault(path, null);
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(file);
            dump(CONTENTS, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            FileInputStream input = new FileInputStream(file);
            CONTENTS.clear();
            CONTENTS.putAll(load(input));
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File toYaml(File file) {
        File newFile;
        if (!file.exists()) {
            if (!file.getParentFile().exists()) file.mkdirs();
            if (!isYamlFile(file)) {
                String name = file.getName();
                if (hasExtension(file)) name = removeExtension(name);
                name = name + ".yml";
                newFile = new File(file.getParentFile(), name);
            } else {
                newFile = file;
            }
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!isYamlFile(file)) {
            throw new IllegalArgumentException("File must have a .yml extension!");
        } else {
            newFile = file;
        }
        return  newFile;
    }

    public static boolean isYamlFile(File file) {
        if (!hasExtension(file.getAbsoluteFile().getName())) {
            System.out.println(file.getAbsoluteFile().getName());
            return false;
        }
        String[] split = file.getName().split("\\.");
        String ext = split[split.length - 1];
        return ext.equals("yml") || ext.equals("yaml");
    }

    public static boolean hasExtension(File file) {
        return hasExtension(file.getName());
    }

    public static boolean hasExtension(String name) {
        return name.split("\\.").length >= 2;
    }

    public static String removeExtension(String name) {
        if (!hasExtension(name)) return name;
        String[] split = name.split(".");
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            b.append(split[i]);
        }
        return b.toString();
    }


}
