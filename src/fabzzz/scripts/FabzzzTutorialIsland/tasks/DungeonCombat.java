package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class DungeonCombat extends Task
{
    private static boolean CHECK_FOR_EQUIPMENT = false;
    private static final int SHORTBOW_ID = 841;
    private static final int BRONZE_DAGGER_ID = 1205;
    private static final int BRONZE_SWORD_ID = 1277;
    private static final int WOODEN_SHIELD_ID = 1171;
    private static final int GATE_ID_RATS = 9719;
    private static final int RAT_ID = 3313;
    private static final int BRONZE_ARROW_ID = 882;
    private static final int LADDER_ID = 9727;
    private static final String GIANT_RAT = "Giant rat";

    private static final int EQUIPMENT_SCREEN_WIDGET = 387;
    private static final int EQUIPMENT_FULL_SCREEN = 84;
    private static final int EQUIPMENT_STATS_BUTTON_IN_EQUIPMENT = 2;
    private static final int EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON = 3;
    private static final int EQUIPMENT_FULL_SCREEN_CLOSE_BUTTON_EXACT_LOCATION = 11;


    @Override
    public boolean activate()
    {
        System.out.println("DungeonCombat -> activate");
        return Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("DungeonCombat -> execute");
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
        else if (ChatContains("You're now holding your dagger."))
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
        else if (ChatContains("It's time to slay some rats!") ||ChatContains("While you are fighting you will see a bar over your head.") )
        {
            if (Areas.DUNGEON_INSIDE_RAT_CAGE.contains(Players.local().tile()))
            {
                System.out.println("Inside cage! going to attack a rat!");
                Npc giantRat = Npcs.stream().name(GIANT_RAT).nearest().first();
                if(!Players.local().interacting().valid() && giantRat.inViewport() && giantRat.healthPercent() > 0)
                {
                    System.out.println("attacking!");
                    if(giantRat.interact("Attack", GIANT_RAT))
                    {
                        System.out.println("in motion..");
                        if(Condition.wait(() -> Players.local().interacting().valid(), 15, 40))
                        {
                            System.out.println("in motion done.. interacting...");
                            Condition.wait(() -> !Players.local().interacting().valid(), 200, 100);
                        }

                    }
                }
                else
                {
                    Camera.turnTo(giantRat);
                }
            }
        }
        else if (ChatContains("Pass through the gate and talk to the combat instructor"))
        {
            if (Areas.DUNGEON_COMBAT_INSTRUCTOR.contains(Players.local().tile()))
            {
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
            else if (Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()))
            {
                Movement.moveTo(Areas.DUNGEON_COMBAT_INSTRUCTOR.getRandomTile());
                PlayerIsMoving(50);
            }
            else if (Areas.DUNGEON_INSIDE_RAT_CAGE.contains(Players.local().tile()))
            {
                if (Areas.DUNGEON_INSIDE_RAT_DOOR.contains(Players.local().tile()))
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
                    System.out.println("Moving to the cage door");
                    Movement.moveTo(Areas.DUNGEON_INSIDE_RAT_DOOR.getRandomTile());
                    PlayerIsMoving(50);
                }
            }
        }
        else if (ChatContains("I can't reach that!"))
        {
            ContinueChat();
        }
        else if (ChatContains("Now you have a bow and some arrows."))
        {
            if (CHECK_FOR_EQUIPMENT)
            {
                OpenGameTab(Game.Tab.EQUIPMENT);
                System.out.println("Do we have  equipment tab open? : " + (Game.tab() == Game.Tab.EQUIPMENT));
            }
            CHECK_FOR_EQUIPMENT = true;

            if (Equipment.itemAt(Equipment.Slot.MAIN_HAND).id() != SHORTBOW_ID || Equipment.itemAt(Equipment.Slot.QUIVER).id() != BRONZE_ARROW_ID)
            {
                System.out.println("We are not wearing our range items yet..");
                if (Inventory.open())
                {
                    System.out.println("Going to equip the shortbow and/or arrows");
                    if (Inventory.stream().id(SHORTBOW_ID).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                    {
                        Inventory.stream().id(SHORTBOW_ID).first().click();
                        Condition.wait(() -> Inventory.stream().id(SHORTBOW_ID).isEmpty(), 50, 10);
                    }
                    if (Inventory.stream().id(BRONZE_ARROW_ID).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                    {
                        Inventory.stream().id(BRONZE_ARROW_ID).first().click();
                        Condition.wait(() -> Inventory.stream().id(BRONZE_ARROW_ID).isEmpty(), 50, 10);
                    }
                }
            }
            else if (Areas.DUNGEON_RANGING_AREA.contains(Players.local().tile()))
            {
                System.out.println("Inside cage! going to attack a rat!");
                Npc giantRat = Npcs.stream().name(GIANT_RAT).nearest().first();
                if(!Players.local().interacting().valid() && giantRat.inViewport() && giantRat.healthPercent() > 0)
                {
                    System.out.println("attacking!");
                    if(giantRat.interact("Attack", GIANT_RAT))
                    {
                        System.out.println("in motion..");
                        if(Condition.wait(() -> Players.local().interacting().valid(), 15, 40))
                        {
                            System.out.println("in motion done.. interacting...");
                            Condition.wait(() -> !Players.local().interacting().valid(), 200, 100);
                        }
                    }
                }
                else
                {
                    Camera.turnTo(giantRat);
                }
            } else
            {
                Movement.moveTo(Areas.DUNGEON_RANGING_AREA.getRandomTile());
                PlayerIsMoving(50);
            }
        }
        else if (ChatContains("Moving on"))
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

    }
    @Override
    public String status()
    {
        return "Dungeon combat part";
    }
}
