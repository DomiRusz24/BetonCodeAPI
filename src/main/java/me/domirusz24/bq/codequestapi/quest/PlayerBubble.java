package me.domirusz24.bq.codequestapi.quest;

import me.domirusz24.bq.codequestapi.quest.abstraction.Bubble;
import me.domirusz24.bq.codequestapi.quest.type.QuestConfigValue;

import java.util.HashSet;
import java.util.Objects;

public class PlayerBubble extends Bubble {

    private final String NEXT;


    public PlayerBubble(NPC npc, String text) {
        this(npc, text, null);
    }

    public PlayerBubble(NPC npc, String text, NPCBubble next) {
        super(npc, text);
        if (next != null) {
            this.NEXT = npc.getID(next);
        } else {
            this.NEXT = null;
        }
    }

    @Override
    public PlayerBubble addEvent(String command) {
        super.addEvent(command);
        return this;
    }

    @Override
    public PlayerBubble addEvent(String command, Class<? extends QuestConfigValue> clazz) {
        super.addEvent(command, clazz);
        return this;
    }

    @Override
    public PlayerBubble addCondition(String command) {
        super.addCondition(command);
        return this;
    }

    @Override
    public PlayerBubble addCondition(String command, Class<? extends QuestConfigValue> clazz) {
        super.addCondition(command, clazz);
        return this;
    }

    public String getNext() {
        return NEXT;
    }

    @Override
    public Object[] extraHash() {
        return new Object[]{NEXT};
    }

    @Override
    public boolean extraEquals(Bubble b) {
        if (b instanceof PlayerBubble) {
            return Objects.equals(((PlayerBubble) b).NEXT, NEXT);
        } else {
            return false;
        }
    }
}
