package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.Players;
import org.powbot.mobile.script.ScriptManager;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Areas.LUMBRIDGE;

public class LumbridgeLogout extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("LumbridgeLogout -> activate");
        return LUMBRIDGE.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("LumbridgeLogout -> execute");
        System.out.println("Logging out");
        Game.logout();
        ScriptManager.INSTANCE.stop();
    }
}
