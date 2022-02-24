package fabzzz.scripts.FabzzzTutorialIsland.tasks.Dungeon.Combat;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.ChatContains;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.PlayerIsMoving;

public class MoveAndClimbUpLadder extends Task
{
    private static final int LADDER_ID = 9727;
    @Override
    public boolean activate()
    {
        return Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()) && ChatContains("Moving on");
    }

    @Override
    public void execute()
    {
        if (Areas.DUNGEON_EXIT_LADDER.contains(Players.local().tile()))
        {
            GameObject ladder = Objects.stream().id(LADDER_ID).nearest().first();

            if (ladder.inViewport())
            {
                ladder.click();
                Condition.wait(() -> Areas.BETWEEN_CAVE_BANK.contains(Players.local().tile()), 100, 50);
            }
            else
            {
                Camera.turnTo(ladder);
            }
        }
        else
        {
            Movement.moveTo(Areas.DUNGEON_EXIT_LADDER.getRandomTile());
            PlayerIsMoving(70);
        }
    }

    @Override
    public String status()
    {
        return "To ladder -> climb up";
    }
}
