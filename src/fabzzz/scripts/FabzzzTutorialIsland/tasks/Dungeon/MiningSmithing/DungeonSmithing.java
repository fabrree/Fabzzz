package fabzzz.scripts.FabzzzTutorialIsland.tasks.Dungeon.MiningSmithing;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.AnyOfMultipleChatsContains;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.ChatContains;

public class DungeonSmithing extends Task
{
    private static final int GATE_ID = 9717; //can also be 9719 (random choose 1? )
    private static final int SMITHING_MENU_ID = 312;
    private static final int BRONZE_DAGGER_ID = 1205;
    private static final List<String> CHATCONTAINS = Arrays.asList("To smith you'll need a hammer and enough metal bars", "Use an anvil to open the smithing menu,", "Now you have the smithing menu open,","Congratulations, you've made your first weapon");
    @Override
    public boolean activate()
    {
        System.out.println("DungeonSmithing -> activate");
        return Areas.DUNGEON_MINING_SMITHING_AREA.contains(Players.local().tile()) && AnyOfMultipleChatsContains(CHATCONTAINS);
    }

    @Override
    public void execute()
    {
        System.out.println("DungeonSmithing -> execute");
        if(ChatContains("To smith you'll need a hammer and enough metal bars") || ChatContains("Use an anvil to open the smithing menu,"))
        {
            GameObject anvil = Objects.stream().name("Anvil").nearest().first();
            if(anvil.inViewport() && anvil.interact("Smith", "Anvil"))
            {
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20)) // check if walking to anvil
                {
                    Condition.wait(() -> Components.stream(SMITHING_MENU_ID).textContains("Dagger").viewable().isNotEmpty(), 100, 60); // we are walking to anvil
                }
            }
            else
            {
                Camera.turnTo(anvil);
            }
        }
        else if(ChatContains("Now you have the smithing menu open,"))
        {
            if(Components.stream(SMITHING_MENU_ID).textContains("Dagger").viewable().isNotEmpty())
            {

                System.out.println("Text contains dagger found! clicking dagger");
                Components.stream().widget(SMITHING_MENU_ID).textContains("Dagger").first().click();
                Condition.wait(() -> Inventory.stream().id(BRONZE_DAGGER_ID).isNotEmpty(), 100, 100);
            }
            else
            {
                GameObject anvil = Objects.stream().name("Anvil").nearest().first();
                if(anvil.inViewport() && anvil.interact("Smith", "Anvil"))
                {
                    if(Condition.wait(() -> Players.local().inMotion(), 15, 20)) // check if walking to anvil
                    {
                        Condition.wait(() -> Components.stream(SMITHING_MENU_ID).textContains("Dagger").viewable().isNotEmpty(), 100, 60); // we are walking to anvil
                    }
                }
                else
                {
                    Camera.turnTo(anvil);
                }
            }
        }
        else if(ChatContains("Congratulations, you've made your first weapon"))
        {
            if(Areas.DUNGEON_DOOR.contains(Players.local().tile()))
            {
                System.out.println("Clicking on the gate");
                var ladder = Objects.stream().id(GATE_ID).nearest().first();
                if(ladder.interact("Open", "Gate") && ladder.inViewport())
                {
                    if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                    {
                        Condition.wait(() -> Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()), 100, 20);
                    }
                }
                else
                {
                    Camera.turnTo(ladder);
                }

            }
            else
            {
                Movement.moveTo(Areas.DUNGEON_DOOR.getRandomTile());
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> Areas.DUNGEON_DOOR.contains(Players.local().tile()), 100, 10);
                }
            }
        }

    }

    @Override
    public String status()
    {
        return "Dungeon smithing part";
    }
}
