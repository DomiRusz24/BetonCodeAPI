package me.domirusz24.bq.codequestapi;

import me.domirusz24.bq.codequestapi.quest.NPC;
import me.domirusz24.bq.codequestapi.quest.NPCBubble;
import me.domirusz24.bq.codequestapi.quest.PlayerBubble;
import me.domirusz24.bq.codequestapi.quest.Quest;
import me.domirusz24.bq.codequestapi.quest.type.*;

import java.io.File;

public final class CodeQuestAPI {

    public static void main(String[] arg) {
        Quest quest = new Quest("Test");
        NPC jack = quest.getNPC("Jack");

        jack.addStartingConv(2, new NPCBubble(jack, "Hello!")
                .addEvent("You said hello to Jack!", Journal.class)
                .add(new PlayerBubble(jack, "Hey!")
                        .addEvent(quest.get(Event.class).tag("tag1", Event.TagAction.ADD))
                        .addEvent("You said hello to jack!", Journal.class)
                )
        );

        jack.addStartingConv(1, new NPCBubble(jack, "Oh, it's you again!")
                .addCondition(quest.get(Condition.class).tag("tag1"))
                .add(new PlayerBubble(jack, "But of course!", new NPCBubble(jack, "Hmm...")
                                .add(new PlayerBubble(jack, "What?")
                                        .addEvent(quest.get(Event.class).tag("tag1", Event.TagAction.ADD))
                                        .addEvent(quest.get(Journal.class).remove("You said hello to jack!"))
                                )
                        )
                )
        );


        quest.saveTo(new File(quest.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile());
    }
}
