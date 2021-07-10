package me.domirusz24.bq.codequestapi.quest.type;

import me.domirusz24.bq.codequestapi.quest.Quest;

public class Objective extends QuestConfigValue {
    public Objective(Quest quest) {
        super(quest);
    }

    public static final String START = "start";
    public static final String ADD = "add";

    public static final String DELETE = "delete";
    public static final String REMOVE = "remove";

    public static final String COMPLETE = "complete";
    public static final String FINISH = "finish";

    public String action(String objective, String action) {
        String id = quest.getModule(objective, Objective.class);
        if (id == null) return null;
        return "objective " + action + " " + id;
    }

    public String has(String objective) {
        return convertToCondition(objective);
    }

    @Override
    public String convertToEvent(String command) {
        String id = quest.getModule(command, Objective.class);
        if (id == null) return null;
        return "objective start " + id;
    }

    @Override
    public String convertToCondition(String command) {
        String id = quest.getModule(command, Objective.class);
        if (id == null) return null;
        return "objective " + id;
    }

    @Override
    public String getName() {
        return "objectives";
    }

}
