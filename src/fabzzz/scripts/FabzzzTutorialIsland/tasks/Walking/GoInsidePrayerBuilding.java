package fabzzz.scripts.FabzzzTutorialIsland.tasks.Walking;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class GoInsidePrayerBuilding extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("GoInsidePrayerBuilding -> activate");
        return Areas.BETWEEN_BANK_PRAYER.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("GoInsidePrayerBuilding -> execute");
        if(ChatContains("Follow the path to the chapel and enter it."))
        {
            System.out.println("Walking into prayer house...");
            Movement.moveTo(Areas.PRAYER_AREA_SMALL.getRandomTile());
        }

    }
    @Override
    public String status()
    {
        return "Going to prayer building";
    }
}
