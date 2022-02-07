package fabzzz.scripts.FabzzzTutorialIsland.tasks.Walking;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class GoInsideBankBuilding extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("GoInsideBankBuilding -> activate");
        return Areas.BETWEEN_CAVE_BANK.contains(Players.local().tile()) && !Areas.BANKING_AREA_INSIDE.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("GoInsideBankBuilding -> activate");

        if(ChatContains("Follow the path and you will come to the front of the building."))
        {
            System.out.println("Walking to bank");
            Movement.moveTo(Areas.BANK_INSIDE_TOWALK.getRandomTile());
            PlayerIsMoving(60);
        }
    }
}
