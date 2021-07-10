package me.domirusz24.bq.codequestapi.quest.type;

import me.domirusz24.bq.codequestapi.quest.Quest;

public abstract class QuestConfigValue {

    protected final Quest quest;

    public QuestConfigValue(Quest quest) {
        this.quest = quest;
    }

    public abstract String convertToEvent(String command);

    public abstract String convertToCondition(String command);

    private int LATEST_ID = 0;

    public int getNewID() {
        LATEST_ID++;
        return LATEST_ID - 1;
    }

    public abstract String getName();


}

