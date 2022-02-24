package fabzzz.scripts.FabzzzTutorialIsland.tasks.Dungeon.Combat;
import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.rt4.*;

import java.util.Arrays;
import java.util.List;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;
public class AccessEquipment extends Task
{
    private static final int BRONZE_DAGGER_ID = 1205;
    private static final int EQUIPMENT_SCREEN_WIDGET = 387;
    private static final int EQUIPMENT_STATS_BUTTON_IN_EQUIPMENT = 2;
    private static final List<String> CHATCONTAINS = Arrays.asList("In this area you will find out about melee and ranged combat.","You now have access to a new interface.", "This is your worn inventory.","You can see what items you are wearing in the worn");

    @Override
    public boolean activate()
    {
        System.out.println("DungeonCombat -> activate");
        return Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()) && AnyOfMultipleChatsContains(CHATCONTAINS) ;
    }

    @Override
    public void execute()
    {
        System.out.println("DungeonCombat -> execute");
        //PREPARATION FOR RAT FIGHT
        if (ChatContains("In this area you will find out about melee and ranged combat."))
        {
            System.out.println("First time walking to combat instructor");
            if (Areas.DUNGEON_COMBAT_INSTRUCTOR.contains(Players.local().tile()))
            {
                TalkToNpc("Combat Instructor");
                PlayerIsMoving(20);
                ContinueChat();
            }
            else
            {
                Movement.moveTo(Areas.DUNGEON_COMBAT_INSTRUCTOR.getRandomTile());
                PlayerIsMoving(60);
            }
        }
        else if (ChatContains("You now have access to a new interface."))
        {
            OpenGameTab(Game.Tab.EQUIPMENT);
        }
        else if (ChatContains("This is your worn inventory."))
        {
            if (Game.tab(Game.Tab.EQUIPMENT))
            {
                if (Widgets.widget(EQUIPMENT_SCREEN_WIDGET).component(EQUIPMENT_STATS_BUTTON_IN_EQUIPMENT).visible()) //view equipment stats
                {
                    Widgets.widget(EQUIPMENT_SCREEN_WIDGET).component(EQUIPMENT_STATS_BUTTON_IN_EQUIPMENT).click();
                }
            }
        }
        else if (ChatContains("You can see what items you are wearing in the worn"))
        {
            System.out.println("Going to open equipment and hold dagger");
            if (Components.stream().widget(EQUIPMENT_FULL_SCREEN).textContains("Equip Your Character...").isNotEmpty())
            {
                System.out.println("Equip your character... screen is open -> clicking dagger");
                Inventory.stream().id(BRONZE_DAGGER_ID).first().click();
            }
        }




    }
    @Override
    public String status()
    {
        return "Dungeon combat part";
    }
}
