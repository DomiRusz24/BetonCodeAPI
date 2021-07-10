package me.domirusz24.bq.codequestapi.quest;

import me.domirusz24.bq.codequestapi.YamlConfig;
import me.domirusz24.bq.codequestapi.quest.type.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Quest {

    public static final HashSet<Class<? extends QuestConfigValue>> MANDATORY = new HashSet<>();

    static {
        MANDATORY.add(Condition.class);
        MANDATORY.add(Event.class);
        MANDATORY.add(Journal.class);
        MANDATORY.add(Objective.class);
    }

    private final HashMap<String, NPC> NPC = new HashMap<>();

    private final HashMap<Class<? extends QuestConfigValue>, HashMap<String, String>> MODULES = new HashMap<>();
    private final HashMap<Class<? extends QuestConfigValue>, QuestConfigValue> OBJECT_BY_CLASS = new HashMap<>();

    private final String name;

    public Quest(String name) {
        this.name = name;
        for (Class<? extends QuestConfigValue> clazz : MANDATORY) {
            createIfNotExist(clazz);
        }
    }


    private boolean addModule(String command, Class<? extends QuestConfigValue> clazz) {
        if (!createIfNotExist(clazz)) return false;
        if (!MODULES.get(clazz).containsKey(command)) {
            MODULES.get(clazz).put(command, "AUTO_" + OBJECT_BY_CLASS.get(clazz).getNewID());
        }
        return true;
    }

    public String getModule(String command, Class<? extends QuestConfigValue> clazz) {
        if (!addModule(command, clazz)) return null;
        return MODULES.get(clazz).get(command);
    }

    public<T extends QuestConfigValue> T get(Class<T> clazz) {
        if (!createIfNotExist(clazz)) return null;
        return (T) OBJECT_BY_CLASS.get(clazz);
    }

    public NPC getNPC(String name) {
        if (!NPC.containsKey(name)) NPC.put(name, new NPC(name, this));
        return NPC.get(name);
    }

    public void saveTo(File file) {
        if (file.isFile()) file = file.getParentFile();
        file = new File(file, getName());
        if (!file.exists()) file.mkdirs();
        for (Class<? extends QuestConfigValue> clazz : MODULES.keySet()) {
            QuestConfigValue obj = OBJECT_BY_CLASS.get(clazz);
            YamlConfig config = new YamlConfig(new File(file, obj.getName() + ".yml"));
            for (String command : MODULES.get(clazz).keySet()) {
                config.add(MODULES.get(clazz).get(command), command);
            }
            config.save();
        }
        {
            YamlConfig main = new YamlConfig(new File(file, "main.yml"));
            main.add("variables", "");
            int i = 0;
            for (String name : NPC.keySet()) {
                main.add("npcs." + i, name);
                i++;
            }
            main.save();
        }
        File conv = new File(file, "conversations");
        conv.mkdir();
        for (String name : NPC.keySet()) {
            YamlConfig config = new YamlConfig(new File(conv, name + ".yml"));
            config.add("quester", name);
            NPC npc = NPC.get(name);
            config.add("first", arrayToList(npc.getStarting()));
            for (NPCBubble bubble : npc.getNpcBubbles()) {
                String prefix = "NPC_options." + npc.getID(bubble) + ".";
                config.add(prefix + "text",
                                bubble.getText());
                config.add(prefix + "pointers", arrayToList(bubble.getOptions()));
                Collection<String> temp;
                temp = bubble.getConditions();
                if (!temp.isEmpty()) config.add(prefix + "conditions", arrayToList(temp));
                temp = bubble.getEvents();
                if (!temp.isEmpty()) config.add(prefix + "events", arrayToList(temp));
            }
            for (PlayerBubble bubble : npc.getPlayerBubbles()) {
                String prefix = "player_options." + npc.getID(bubble) + ".";
                config.add(prefix + "text", bubble.getText());
                if (bubble.getNext() != null) config.add(prefix + "pointers", bubble.getNext());
                Collection<String> temp;
                temp = bubble.getConditions();
                if (!temp.isEmpty()) config.add(prefix + "conditions", arrayToList(temp));
                temp = bubble.getEvents();
                if (!temp.isEmpty()) config.add(prefix + "events", arrayToList(temp));
            }
            config.save();
        }
    }
    private boolean createIfNotExist(Class<? extends QuestConfigValue> clazz) {
        if (OBJECT_BY_CLASS.containsKey(clazz)) return true;
        QuestConfigValue value = null;
        try {
            value = clazz.getConstructor(Quest.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (value == null) {
            System.out.printf("ERROR: Class %s couldn't be instanced by Reflection!", clazz);
            return false;
        }
        OBJECT_BY_CLASS.put(clazz, value);
        MODULES.put(clazz, new HashMap<>());
        return true;
    }

    public String getName() {
        return name;
    }

    public static String arrayToList(String[] list) {
        StringBuilder b = new StringBuilder();
        for (String s : list) {
            b.append(s).append(",");
        }
        b.deleteCharAt(b.length() - 1);
        return b.toString();
    }

    public static String arrayToList(Collection<String> list) {
        return arrayToList(list.toArray(new String[0]));
    }


}


