package me.domirusz24.bq.codequestapi.quest;

import java.util.*;

public class NPC {

    private int LATEST_ID = 0;

    private final Quest quest;
    private final String name;

    private final TreeMap<Integer, String> FIRST = new TreeMap<>();

    private final HashMap<NPCBubble, String> NPC_BUBBLES = new HashMap<>();
    private final HashMap<PlayerBubble, String> PLAYER_BUBBLES = new HashMap<>();

    public NPC(String name, Quest quest) {
        this.name = name;
        this.quest = quest;
    }

    public void addStartingConv(int index, NPCBubble bubble) {
        addStartingConv(index, getID(bubble));
    }

    public void addStartingConv(NPCBubble bubble) {
        addStartingConv(getID(bubble));
    }

    public void addStartingConv(int index, String bubble) {
        FIRST.put(index, bubble);
    }

    public void addStartingConv(String bubble) {
        int last = 0;
        for (Integer i : FIRST.keySet()) {
            last = Math.max(last, i);
        }
        FIRST.put(last + 1, bubble);
    }

    public LinkedList<String> getStarting() {
        LinkedList<String> list = new LinkedList<>();
        for (Integer i : FIRST.keySet()) {
            list.add(FIRST.get(i));
        }
        return list;
    }

    public NPCBubble get(NPCBubble bubble) {
        if (!NPC_BUBBLES.containsKey(bubble)) {
            NPC_BUBBLES.put(bubble, "AUTO_" + getNewID());
        }
        return bubble;
    }

    public PlayerBubble get(PlayerBubble bubble) {
        if (!PLAYER_BUBBLES.containsKey(bubble)) {
            PLAYER_BUBBLES.put(bubble, "AUTO_" + getNewID());
        }
        return bubble;
    }

    public String getID(PlayerBubble bubble) {
        get(bubble);
        return PLAYER_BUBBLES.get(bubble);
    }

    public String getID(NPCBubble bubble) {
        get(bubble);
        return NPC_BUBBLES.get(bubble);
    }


    public HashSet<NPCBubble> getNpcBubbles() {
        return new HashSet<>(NPC_BUBBLES.keySet());
    }

    public HashSet<PlayerBubble> getPlayerBubbles() {
        return new HashSet<>(PLAYER_BUBBLES.keySet());
    }

    public Quest getQuest() {
        return quest;
    }

    public String getName() {
        return name;
    }

    public int getNewID() {
        LATEST_ID++;
        return LATEST_ID - 1;
    }
}
