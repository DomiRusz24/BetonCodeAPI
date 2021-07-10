package me.domirusz24.bq.codequestapi.quest;

import me.domirusz24.bq.codequestapi.quest.abstraction.Bubble;
import me.domirusz24.bq.codequestapi.quest.type.QuestConfigValue;

import java.util.HashSet;

public class NPCBubble extends Bubble {

    private final HashSet<String> OPTIONS = new HashSet<>();

    public NPCBubble(NPC npc, String text) {
        super(npc, text);
    }



    @Override
    public NPCBubble addEvent(String command) {
        super.addEvent(command);
        return this;
    }

    @Override
    public NPCBubble addEvent(String command, Class<? extends QuestConfigValue> clazz) {
        super.addEvent(command, clazz);
        return this;
    }

    @Override
    public NPCBubble addCondition(String command) {
        super.addCondition(command);
        return this;
    }

    @Override
    public NPCBubble addCondition(String command, Class<? extends QuestConfigValue> clazz) {
        super.addCondition(command, clazz);
        return this;
    }

    public NPCBubble add(PlayerBubble bubble) {
        OPTIONS.add(getNPC().getID(bubble));
        return this;
    }

    public HashSet<String> getOptions() {
        return new HashSet<>(OPTIONS);
    }

    @Override
    public Object[] extraHash() {
        return OPTIONS.toArray(new String[0]);
    }

    @Override
    public boolean extraEquals(Bubble b) {
        if (b instanceof NPCBubble) {
            return ((NPCBubble) b).OPTIONS.equals(OPTIONS);
        } else {
            return false;
        }
    }
}
