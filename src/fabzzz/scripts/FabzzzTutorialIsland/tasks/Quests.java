package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class Quests extends Task
{
    @Override
    public boolean activate()
    {
        return Areas.QUEST_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {

        if(ChatContains("learn about quests"))
        {
            TalkToNpc("Quest Guide");
            ContinueChat();
        }
        if(ChatContains("flashing icon to the right of your screen."))
        {
            Condition.wait(() -> Game.tab(Game.Tab.QUESTS), 25, 40);
            Condition.wait(() -> Game.tab() == Game.Tab.QUESTS, 100, 20);
        }

        if(ChatContains("This is your quest journal."))
        {
            TalkToNpc("Quest Guide");
            ContinueChat();
        }
        if(ChatContains("Moving on"))
        {
            System.out.println("Clicking on the door");
            int ladderId = 9726;
            Objects.stream().id(ladderId).nearest().first().interact("Climb-down");
            if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
            {
                Condition.wait(() -> Areas.DUNGEON_MINING_SMITHING_AREA.contains(Players.local().tile()), 100, 40);
            }

        }
    }
}
