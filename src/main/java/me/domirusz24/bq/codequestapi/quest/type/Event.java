package me.domirusz24.bq.codequestapi.quest.type;

import me.domirusz24.bq.codequestapi.quest.Quest;

public class Event extends QuestConfigValue {

    public Event(Quest quest) {
        super(quest);
    }

    public String tag(String tag, TagAction action) {
        return "tag " + action.name().toLowerCase() + " " + tag;
    }

    @Override
    public String convertToEvent(String command) {
        return command;
    }

    @Override
    public String convertToCondition(String command) {
        return null;
    }

    @Override
    public String getName() {
        return "events";
    }

    public enum TagAction {
        ADD,
        REMOVE;
    }

}
