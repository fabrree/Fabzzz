package fabzzz.scripts.FabzzzTutorialIsland.tasks.Dungeon.Combat;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.Arrays;
import java.util.List;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.PlayerIsMoving;

public class DaggerHolding extends Task
{
    private static final int BRONZE_SWORD_ID = 1277;
    private static final int WOODEN_SHIELD_ID = 1171;
    private static final int EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON = 3;
    private static final int EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON_EXACT_LOCATION = 11;
    private static final List<String> CHATCONTAINS = Arrays.asList("You're now holding your dagger.","To unequip an item,","Tap on the flashing crossed swords icon","This is your combat interface.");

    @Override
    public boolean activate()
    {
        System.out.println("DaggerHolding -> activate");
        return Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()) && AnyOfMultipleChatsContains(CHATCONTAINS);
    }

    @Override
    public void execute()
    {
        System.out.println("DaggerHolding -> execute");
        if (ChatContains("You're now holding your dagger."))
        {
            System.out.println("Holding dagger..");

            if (Components.stream().widget(EQUIPMENT_FULL_SCREEN).textContains("Equip Your Character...").isNotEmpty())
            {
                System.out.println("Equipping screen is still open...");
                if (Widgets.widget(EQUIPMENT_FULL_SCREEN).component(EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON).component(EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON_EXACT_LOCATION).visible())
                {
                    System.out.println("Found the close button, going to close it.");
                    Widgets.widget(EQUIPMENT_FULL_SCREEN).component(EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON).component(EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON_EXACT_LOCATION).click();
                    Condition.wait(() -> Components.stream().widget(EQUIPMENT_FULL_SCREEN).textContains("Equip You Character...").isEmpty(), 50, 20);
                }
            }
            if (Areas.DUNGEON_COMBAT_INSTRUCTOR.contains(Players.local().tile()))
            {
                System.out.println("equipping screen is closed, going to talk to combat instructor");
                TalkToNpc("Combat Instructor");
                PlayerIsMoving(20);
                ContinueChat();
            }
            else
            {
                System.out.println("equipping screen is closed, but we are not in the area..");
                Movement.moveTo(Areas.DUNGEON_COMBAT_INSTRUCTOR.getRandomTile());
                PlayerIsMoving(60);
            }
        }
        else if (ChatContains("To unequip an item,"))
        {
            System.out.println("Going to equip the bronze sword and bronze shield");
            if (Inventory.opened())
            {
                if (Inventory.stream().id(BRONZE_SWORD_ID).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                {
                    Inventory.stream().id(BRONZE_SWORD_ID).first().click();
                    Condition.wait(() -> Inventory.stream().id(BRONZE_SWORD_ID).isEmpty(), 50, 10);
                }
                if (Inventory.stream().id(WOODEN_SHIELD_ID).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                {
                    Inventory.stream().id(WOODEN_SHIELD_ID).first().click();
                    Condition.wait(() -> Inventory.stream().id(WOODEN_SHIELD_ID).isEmpty(), 50, 10);
                }
            }
        }
        else if (ChatContains("Tap on the flashing crossed swords icon"))
        {
            OpenGameTab(Game.Tab.ATTACK);
        }
        else if (ChatContains("This is your combat interface."))
        {
            if (Areas.DUNGEON_OUTSIDE_RAT_DOOR.contains(Players.local().tile()))
            {
                System.out.println("We are at: dungeon outside rat door");
                Objects.stream().id(GATE_ID_RATS).nearest().first().interact("Open", "Gate");

                if (Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    System.out.println("Moving to the door and opening it");
                    Condition.wait(() -> Areas.DUNGEON_INSIDE_RAT_CAGE.contains(Players.local().tile()), 100, 40);
                }
            }
            else
            {
                Movement.moveTo(Areas.DUNGEON_OUTSIDE_RAT_DOOR.getRandomTile());
                System.out.println("Moving to: Dungeon outside rat door");
                PlayerIsMoving(60);
            }
        }
    }

    @Override
    public String status()
    {
        return null;
    }
}
