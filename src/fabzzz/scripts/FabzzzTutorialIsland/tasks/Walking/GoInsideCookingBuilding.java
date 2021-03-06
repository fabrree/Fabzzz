package fabzzz.scripts.FabzzzTutorialIsland.tasks.Walking;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;


public class GoInsideCookingBuilding extends Task
{
    private static final int DOOR_ID = 9709;
    @Override
    public boolean activate()
    {
        return Areas.BETWEEN_FISH_GATE_COOKING_DOOR.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        if(Areas.DOOR_IN_FRONT_OF_COOKING.contains(Players.local().tile()))
        {
            GameObject door = Objects.stream().id(DOOR_ID).nearest().first();
            if(door.inViewport())
            {
                door.interact("Open", "Door");
                Condition.wait(() -> Areas.COOKING_AREA.contains(Players.local().tile()), 50, 70);
            }
            else
            {
                Camera.turnTo(door);
            }
        }
        else
        {
            Movement.moveTo(Areas.DOOR_IN_FRONT_OF_COOKING.getRandomTile());
//            PlayerIsMoving(100);
        }
    }
    @Override
    public String status()
    {
        return "Going to cooking building";
    }
}
