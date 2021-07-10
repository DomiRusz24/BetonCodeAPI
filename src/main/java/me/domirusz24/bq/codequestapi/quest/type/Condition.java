package me.domirusz24.bq.codequestapi.quest.type;

import me.domirusz24.bq.codequestapi.quest.Quest;

public class Condition extends QuestConfigValue {


    public Condition(Quest quest) {
        super(quest);
    }

    public String tag(String tag) {
        return "tag " + tag;
    }

    @Override
    public String convertToEvent(String command) {
        return null;
    }

    @Override
    public String convertToCondition(String command) {
        return command;
    }

    @Override
    public String getName() {
        return "conditions";
    }
}
