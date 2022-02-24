package fabzzz.scripts.FabzzzTutorialIsland.tasks.Dungeon.Combat;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class RatFightRange extends Task
{
    private static boolean CHECK_FOR_EQUIPMENT = false;
    private static final int SHORTBOW_ID = 841;
    private static final int BRONZE_ARROW_ID = 882;

    @Override
    public boolean activate()
    {
        return Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()) && ChatContains("Now you have a bow and some arrows.");
    }

    @Override
    public void execute()
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
                            Condition.wait(() -> !Players.local().interacting().valid(), 200, 200);
                        }
                    }
                }
                else
                {
                    Camera.turnTo(giantRat);
                }
            }
            else
            {
                Movement.moveTo(Areas.DUNGEON_RANGING_AREA.getRandomTile());
                PlayerIsMoving(50);
            }
    }

    @Override
    public String status()
    {
        return "Going to fight rat with range";
    }
}
