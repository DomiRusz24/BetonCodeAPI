package me.domirusz24.bq.codequestapi.quest.abstraction;

import me.domirusz24.bq.codequestapi.quest.NPC;
import me.domirusz24.bq.codequestapi.quest.Quest;
import me.domirusz24.bq.codequestapi.quest.type.Condition;
import me.domirusz24.bq.codequestapi.quest.type.Event;
import me.domirusz24.bq.codequestapi.quest.type.QuestConfigValue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Bubble implements Cloneable {

    private final String text;

    private final Quest quest;
    private final NPC npc;

    protected final HashSet<String> EVENTS;
    protected final HashSet<String> CONDITIONS;

    public Bubble(NPC npc, String text) {
        this(npc, text, new HashSet<>(), new HashSet<>());
    }

    private Bubble(NPC npc, String text, HashSet<String> EVENTS, HashSet<String> CONDITIONS) {
        this.text = text;
        this.npc = npc;
        this.quest = npc.getQuest();
        this.EVENTS = EVENTS;
        this.CONDITIONS = CONDITIONS;
    }

    public HashSet<String> getConditions() {
        return new HashSet<>(CONDITIONS);
    }

    public HashSet<String> getEvents() {
        return new HashSet<>(EVENTS);
    }

    public Bubble addEvent(String command) {
        return addEvent(command, Event.class);
    }

    public Bubble addEvent(String command, Class<? extends QuestConfigValue> clazz) {
        QuestConfigValue value = quest.get(clazz);
        if (value == null) return this;
        add(value.convertToEvent(command), Event.class, EVENTS);
        return this;
    }

    public Bubble addCondition(String command) {
        return addCondition(command, Condition.class);
    }

    public Bubble addCondition(String command, Class<? extends QuestConfigValue> clazz) {
        QuestConfigValue value = quest.get(clazz);
        if (value == null) return this;
        add(value.convertToCondition(command), Condition.class, CONDITIONS);
        return this;
    }

    private String add(String command, Class<? extends QuestConfigValue> clazz, HashSet<String> set) {
        if (command != null) {
            String module = quest.getModule(command, clazz);
            if (module != null) {
                set.add(module);
                return module;
            }
        }
        return null;
    }

    public Quest getQuest() {
        return quest;
    }

    public NPC getNPC() {
        return npc;
    }

    public String getText() {
        return text;
    }


    @Override
    public int hashCode() {
        return text.hashCode() + npc.hashCode() + Arrays.hashCode(EVENTS.toArray(new String[0])) + Arrays.hashCode(CONDITIONS.toArray(new String[0])) + Arrays.hashCode(extraHash());
    }



    public Object[] extraHash() {
        return new Object[0];
    }

    public boolean extraEquals(Bubble b) {
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Bubble) {
            Bubble b = (Bubble) obj;
            return b.text.equals(text) && b.npc.equals(npc) && b.EVENTS.equals(EVENTS) && b.CONDITIONS.equals(CONDITIONS) && extraEquals(b);
        }
        return super.equals(obj);
    }



    @Override
    public Bubble clone() {
        return new Bubble(npc, text, new HashSet<>(EVENTS), new HashSet<>(CONDITIONS));
    }

    public Bubble clone(NPC npc) {
        return new Bubble(npc, text, new HashSet<>(EVENTS), new HashSet<>(CONDITIONS));
    }
}
