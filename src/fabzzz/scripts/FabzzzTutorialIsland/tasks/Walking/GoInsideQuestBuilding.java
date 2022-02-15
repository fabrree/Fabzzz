package fabzzz.scripts.FabzzzTutorialIsland.tasks.Walking;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class GoInsideQuestBuilding extends Task
{
    private static int DOOR_ID = 9716;

    @Override
    public boolean activate()
    {
        System.out.println("GoInsideQuestBuilding -> activate");
        return Areas.BETWEEN_COOK_AND_QUEST.contains(Players.local().tile()) && !Areas.QUEST_AREA.contains(Players.local().tile()) ;
    }

    @Override
    public void execute()
    {
        System.out.println("GoInsideQuestBuilding -> execute");

        if(ChatContains("Moving on") || ChatContains("Fancy a run?"))
        {
            if(Areas.DOOR_IN_FRONT_OF_QUEST.contains(Players.local().tile()))
            {
                System.out.println("Clicking on the door");
                if(Objects.stream().id(DOOR_ID).nearest().first().interact("Open"))
                {
                    Condition.wait(() -> Areas.QUEST_AREA.contains(Players.local().tile()), 50, 60);
                }
            }
            else
            {
                System.out.println("Walking to quest building");
                Movement.moveTo(Areas.DOOR_IN_FRONT_OF_QUEST.getRandomTile());
            }
        }
    }
}
