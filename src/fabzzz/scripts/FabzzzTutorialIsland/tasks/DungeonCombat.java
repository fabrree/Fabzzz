package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class DungeonCombat extends Task
{
    static boolean checkForEquipment = false;

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
            } else
            {
                Movement.moveTo(Areas.DUNGEON_COMBAT_INSTRUCTOR.getRandomTile());
                PlayerIsMoving(60);
            }
        }

        if (ChatContains("You now have access to a new interface."))
        {
            Condition.wait(() -> Game.tab(Game.Tab.EQUIPMENT), 25, 40);
            Condition.wait(() -> Game.tab() == Game.Tab.EQUIPMENT, 100, 20);
        }

        if (ChatContains("This is your worn inventory."))
        {
            if (Game.tab(Game.Tab.EQUIPMENT))
            {
                if (Widgets.widget(387).component(2).visible()) //view equipment stats
                {
                    Widgets.widget(387).component(2).click();
                }
            }
        }

        if (ChatContains("You can see what items you are wearing in the worn"))
        {
            System.out.println("Going to open equipment and hold dagger");
            int bronzeDaggerId = 1205;
            int equipmentStatScreenWidget = 84;
            System.out.println("a " + Components.stream().widget(equipmentStatScreenWidget).textContains("Equip You Character...").isNotEmpty());
            System.out.println("122 " + Components.stream().widget(122).textContains("Equip You Character...").isNotEmpty());
            System.out.println("614 " + Components.stream().widget(614).textContains("Equip You Character...").isNotEmpty());
            System.out.println("651 " + Components.stream().widget(651).textContains("Equip You Character...").isNotEmpty());
            System.out.println("708 " + Components.stream().widget(708).textContains("Equip You Character...").isNotEmpty());

            if (Components.stream().widget(equipmentStatScreenWidget).textContains("Equip Your Character...").isNotEmpty())
            {
                System.out.println("Equip your character... screen is open -> clicking dagger");
                Inventory.stream().id(bronzeDaggerId).first().click();
            }
        }
        if (ChatContains("You're now holding your dagger."))
        {
            System.out.println("Holding dagger..");
            int equipmentStatScreenWidget = 84;
            int closewidget1 = 3;
            int closewidget2 = 11;
            if (Components.stream().widget(equipmentStatScreenWidget).textContains("Equip Your Character...").isNotEmpty())
            {
                System.out.println("Equipping screen is still open..." + Widgets.widget(equipmentStatScreenWidget).component(closewidget1).component(closewidget2).visible());
                if (Widgets.widget(equipmentStatScreenWidget).component(closewidget1).component(closewidget2).visible())
                {
                    System.out.println("Found the close button, going to close it.");
                    Widgets.widget(equipmentStatScreenWidget).component(closewidget1).component(closewidget2).click();
                    Condition.wait(() -> Components.stream().widget(equipmentStatScreenWidget).textContains("Equip You Character...").isEmpty(), 50, 20);
                }
            }

            if (Areas.DUNGEON_COMBAT_INSTRUCTOR.contains(Players.local().tile()))
            {
                System.out.println("equipping screen is closed, going to talk to combat instructor");
                TalkToNpc("Combat Instructor");
                PlayerIsMoving(20);
                ContinueChat();
            } else
            {
                System.out.println("equipping screen is closed, but we are not in the area..");
                Movement.moveTo(Areas.DUNGEON_COMBAT_INSTRUCTOR.getRandomTile());
                PlayerIsMoving(60);
            }
        }
        if (ChatContains("To unequip an item,"))
        {
            System.out.println("Going to equip the bronze sword and bronze shield");
            int bronzeSwordId = 1277;
            int woodenShieldId = 1171;
            if (Inventory.opened())
            {
                if (Inventory.stream().id(bronzeSwordId).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                {
                    Inventory.stream().id(bronzeSwordId).first().click();
                    Condition.wait(() -> Inventory.stream().id(bronzeSwordId).isEmpty(), 50, 10);
                }
                if (Inventory.stream().id(woodenShieldId).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                {
                    Inventory.stream().id(woodenShieldId).first().click();
                    Condition.wait(() -> Inventory.stream().id(woodenShieldId).isEmpty(), 50, 10);
                }
            }

        }

        if (ChatContains("Tap on the flashing crossed swords icon"))
        {
            Condition.wait(() -> Game.tab(Game.Tab.ATTACK), 25, 40);
            Condition.wait(() -> Game.tab() == Game.Tab.ATTACK, 100, 20);
            System.out.println("Do we have tab open? : " + (Game.tab() == Game.Tab.ATTACK));
        }

        if (ChatContains("This is your combat interface."))
        {
            if (Areas.DUNGEON_OUTSIDE_RAT_DOOR.contains(Players.local().tile()))
            {
                System.out.println("We are at: dungeon outside rat door");
                int gateId = 9719; //also 9720

                Objects.stream().id(gateId).nearest().first().interact("Open", "Gate");

                if (Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    System.out.println("Moving to the door and opening it");
                    Condition.wait(() -> Areas.DUNGEON_INSIDE_RAT_CAGE.contains(Players.local().tile()), 100, 40);
                }

            } else
            {
                Movement.moveTo(Areas.DUNGEON_OUTSIDE_RAT_DOOR.getRandomTile());
                System.out.println("Moving to: Dungeon outside rat door");
                PlayerIsMoving(60);
            }
        }
        if (ChatContains("It's time to slay some rats!") ||ChatContains("While you are fighting you will see a bar over your head.") )
        {
            if (Areas.DUNGEON_INSIDE_RAT_CAGE.contains(Players.local().tile()))
            {
                int ratId = 3313;
                System.out.println("Inside cage! going to attack a rat!");
                if (Npcs.stream().id(ratId).isNotEmpty())
                {
                    Npcs.stream().id(ratId).nearest().first().interact("Attack", "Giant rat");
                    PlayerIsMoving(40);
                    System.out.println("In combat with the rat!");
                    Condition.wait(() -> !Players.local().interacting().healthBarVisible(), 200, 50); // in combat with rat
                }
            }
        }


        if (ChatContains("Pass through the gate and talk to the combat instructor"))
        {
            if (Areas.DUNGEON_COMBAT_INSTRUCTOR.contains(Players.local().tile()))
            {
                if (Areas.DUNGEON_COMBAT_INSTRUCTOR.contains(Players.local().tile()))
                {
                    TalkToNpc("Combat Instructor");
                    PlayerIsMoving(20);
                    ContinueChat();
                } else
                {
                    Movement.moveTo(Areas.DUNGEON_COMBAT_INSTRUCTOR.getRandomTile());
                    PlayerIsMoving(60);
                }
            } else if (Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()))
            {
                Movement.moveTo(Areas.DUNGEON_COMBAT_INSTRUCTOR.getRandomTile());
                PlayerIsMoving(50);
            } else if (Areas.DUNGEON_INSIDE_RAT_CAGE.contains(Players.local().tile()))
            {
                if (Areas.DUNGEON_INSIDE_RAT_DOOR.contains(Players.local().tile()))
                {
                    System.out.println("We are at: dungeon outside rat door");
                    int gateId = 9719; //also 9720

                    Objects.stream().id(gateId).nearest().first().interact("Open", "Gate");

                    if (Condition.wait(() -> Players.local().inMotion(), 15, 20))
                    {
                        System.out.println("Moving to the door and opening it");
                        Condition.wait(() -> Areas.DUNGEON_INSIDE_RAT_CAGE.contains(Players.local().tile()), 100, 40);
                    }
                } else
                {
                    System.out.println("Moving to the cage door");
                    Movement.moveTo(Areas.DUNGEON_INSIDE_RAT_DOOR.getRandomTile());
                    PlayerIsMoving(50);
                }
            }
        }

        if (ChatContains("I can't reach that!"))
        {
            ContinueChat();
        }
        if (ChatContains("Now you have a bow and some arrows."))
        {
            int shortbowId = 841;
            int bronzeArrowId = 882;

            if (checkForEquipment)
            {
                Game.tab(Game.Tab.EQUIPMENT);
                Condition.wait(() -> Game.tab() == Game.Tab.EQUIPMENT, 100, 20);
                System.out.println("Do we have  equipment tab open? : " + (Game.tab() == Game.Tab.EQUIPMENT));
            }
            checkForEquipment = true;

            if (Equipment.itemAt(Equipment.Slot.MAIN_HAND).id() != shortbowId || Equipment.itemAt(Equipment.Slot.QUIVER).id() != bronzeArrowId)
            {
                System.out.println("We are not wearing our range items yet..");
                if (Inventory.open())
                {
                    System.out.println("Going to equip the shortbow and/or arrows");
                    if (Inventory.stream().id(shortbowId).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                    {
                        Inventory.stream().id(shortbowId).first().click();
                        Condition.wait(() -> Inventory.stream().id(shortbowId).isEmpty(), 50, 10);
                    }
                    if (Inventory.stream().id(bronzeArrowId).isNotEmpty() && Game.tab(Game.Tab.INVENTORY))
                    {
                        Inventory.stream().id(bronzeArrowId).first().click();
                        Condition.wait(() -> Inventory.stream().id(bronzeArrowId).isEmpty(), 50, 10);
                    }
                }
            } else if (Areas.DUNGEON_RANGING_AREA.contains(Players.local().tile()))
            {
                int ratId = 3313;
                System.out.println("Inside range spot going to attack a rat!");
                if (Npcs.stream().id(ratId).isNotEmpty() && Npcs.stream().id(ratId).nearest().first().inViewport())
                {
                    Npcs.stream().id(3313).nearest().first().interact("Attack", "Giant rat");

                    PlayerIsMoving(40);
                    System.out.println("In combat with the rat!");
                    if (Condition.wait(() -> Players.local().interacting().healthBarVisible(), 15, 20))
                    {
                        Condition.wait(() -> !Players.local().interacting().healthBarVisible(), 100, 150); // in combat with rat
                    }
                } else
                {
                    TurnCamera();
                }
            } else
            {
                Movement.moveTo(Areas.DUNGEON_RANGING_AREA.getRandomTile());
                PlayerIsMoving(50);
            }
        }

        if (ChatContains("Moving on"))
        {
            if (Areas.DUNGEON_EXIT_LADDER.contains(Players.local().tile()))
            {
                int ladderId = 9727;
                GameObject ladder = Objects.stream().id(ladderId).nearest().first();

                if (ladder.inViewport())
                {
                    ladder.click();
                    Condition.wait(() -> Areas.BETWEEN_CAVE_BANK.contains(Players.local().tile()), 100, 50);
                } else
                {
                    TurnCamera();
                }
            } else
            {
                Movement.moveTo(Areas.DUNGEON_EXIT_LADDER.getRandomTile());
                PlayerIsMoving(70);
            }
        }

    }
}
