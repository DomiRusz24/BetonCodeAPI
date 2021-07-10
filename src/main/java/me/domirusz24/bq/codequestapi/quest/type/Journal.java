package me.domirusz24.bq.codequestapi.quest.type;

import me.domirusz24.bq.codequestapi.quest.Quest;

public class Journal extends QuestConfigValue {
    public Journal(Quest quest) {
        super(quest);
    }

    public String add(String text) {
        return convertToEvent(text);
    }

    public String remove(String text) {
        String id = quest.getModule(text, Journal.class);
        if (id == null) return null;
        return "journal remove " + id;
    }

    public String has(String text) {
        return convertToEvent(text);
    }

    public String update() {
        return "journal update";
    }

    @Override
    public String convertToEvent(String command) {
        String id = quest.getModule(command, Journal.class);
        if (id == null) return null;
        return "journal add " + id;
    }

    @Override
    public String convertToCondition(String command) {
        String id = quest.getModule(command, Journal.class);
        if (id == null) return null;
        return "journal " + id;
    }

    @Override
    public String getName() {
        return "journal";
    }
}
