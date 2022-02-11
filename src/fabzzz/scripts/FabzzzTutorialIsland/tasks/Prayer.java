package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.ContinueChat;

public class Prayer extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("Prayer -> activate");
        return Areas.PRAYER_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("Prayer -> execute");
        if(ChatContains("Follow the path to the chapel and enter it."))
        {
            if (Areas.PRAYER_AREA.contains(Players.local().tile()))
            {
                TalkToNpc("Brother Brace");
                ContinueChat();
            }
        }
        else if(ChatContains("Tap on the flashing icon to open the Prayer menu."))
        {
            OpenGameTab(Game.Tab.PRAYER);
        }
        else if(ChatContains("Talk with Brother Brace and he'll tell you about prayers."))
        {
            TalkToNpc("Brother Brace");
            ContinueChat();
        }
        else if(ChatContains("You should now see another new icon."))
        {
            OpenGameTab(Game.Tab.FRIENDS_LIST);
        }
        else if(ChatContains("These two lists can be very helpful for"))
        {
            TalkToNpc("Brother Brace");
            ContinueChat();
        }
        else if(ChatContains("You're almost finished on tutorial island."))
        {
            if(Areas.PRAYER_AREA_OUT_DOOR.contains(Players.local().tile()))
            {
                int doorId = 9723;
                GameObject door = Objects.stream().id(doorId).nearest().first();
                if(door.inViewport())
                {
                    door.interact("Open", "Door");
                    Condition.wait(() -> Areas.BETWEEN_PRAYER_MAGIC.contains(Players.local().tile()), 50, 70);
                }
                else
                {
                    TurnCamera();
                }
            }
            else
            {
                Movement.moveTo(Areas.PRAYER_AREA_OUT_DOOR.getRandomTile());
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> Areas.PRAYER_AREA_OUT_DOOR.contains(Players.local().tile()), 100, 50);
                }
            }
        }
    }
}
