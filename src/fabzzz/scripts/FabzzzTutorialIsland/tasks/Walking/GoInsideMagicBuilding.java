package fabzzz.scripts.FabzzzTutorialIsland.tasks.Walking;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.ChatContains;

public class GoInsideMagicBuilding extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("GoInsideMagicBuilding -> activate");
        return Areas.BETWEEN_PRAYER_MAGIC.contains(Players.local().tile()) && !Areas.MAGIC_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("GoInsideMagicBuilding -> execute");

        if(ChatContains("Follow the path to the wizard's house,"))
        {
            System.out.println("Walking into magic house...");
            Movement.moveTo(Areas.MAGIC_AREA.getRandomTile());
        }
    }
}
