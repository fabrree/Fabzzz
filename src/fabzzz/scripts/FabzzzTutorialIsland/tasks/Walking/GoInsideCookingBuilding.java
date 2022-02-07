package fabzzz.scripts.FabzzzTutorialIsland.tasks.Walking;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;


public class GoInsideCookingBuilding extends Task
{
    @Override
    public boolean activate()
    {
        return Areas.BETWEEN_FISH_GATE_COOKING_DOOR.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        if(!Areas.DOOR_IN_FRONT_OF_COOKING.contains(Players.local().tile()))
        {
            Movement.step(Areas.DOOR_IN_FRONT_OF_COOKING.getRandomTile());
            PlayerIsMoving(100);
        }
        if(Areas.DOOR_IN_FRONT_OF_COOKING.contains(Players.local().tile()))
        {
            int doorId = 9709;
            GameObject door = Objects.stream().id(doorId).nearest().first();
            if(door.inViewport())
            {
                door.interact("Open", "Door");
                Condition.wait(() -> Areas.COOKING_AREA.contains(Players.local().tile()), 50, 70);
            }
            else
            {
                TurnCamera();
            }
        }
    }
}
