package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class DungeonMiningSmithing extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("DungeonMiningSmithing -> activate");
        return Areas.DUNGEON_MINING_SMITHING_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("DungeonMiningSmithing -> execute");

        if(ChatContains("Next let's get you a weapon,"))
        {
            if(Areas.MINING_INSTRUCTOR_AREA.contains(Players.local().tile()))
            {
                System.out.println("Talking to mining instructor");
                TalkToNpc("Mining Instructor");
                PlayerIsMoving(30);
                ContinueChat();

            }
            else
            {
                System.out.println("Walking to mining instructor");
                Movement.moveTo(Areas.MINING_INSTRUCTOR_AREA.getRandomTile());
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> Players.local().inMotion(), 100, 150);
                }
            }
        }
        if(ChatContains("It's quite simple really. To mine a rock,"))
        {
            GameObject tinOre = Objects.stream().id(10080).nearest().first();
            if (tinOre.valid() && tinOre.inViewport()) {
                System.out.println("Mining white ore");
                tinOre.interact("Mine");
                PlayerIsMoving(60);
                Condition.wait(() -> !tinOre.valid(), 150, 60);
            }
        }

        if(ChatContains("You manage to mine some tin."))
        {
            System.out.println("You manage to mine some tin -> continue chat");
            ContinueChat();
        }

        if(ChatContains("you just need some copper"))
        {
            System.out.println("Going to mine copper");

            GameObject copperOre = Objects.stream().id(10079).nearest().first();
            if(!copperOre.inViewport())
            {
                System.out.println("Camera y =" + Camera.y());
                System.out.println("Camera x =" + Camera.x());
                Movement.moveTo(Areas.MINING_COPPER_AREA[r.nextInt(1)].getRandomTile());
                PlayerIsMoving(60);
            }
            if (copperOre.valid() && copperOre.inViewport()) {
                System.out.println("Mining white ore");
                copperOre.interact("Mine");
                PlayerIsMoving(60);
                Condition.wait(() -> !copperOre.valid(), 150, 60);
            }
        }

        if(ChatContains("You manage to mine some copper"))
        {
            ContinueChat();
        }


        if(ChatContains("You now have some tin ore and some copper ore."))
        {
            GameObject range = Objects.stream().name("Furnace").first();
            range.interact("Use", "Furnace");

            PlayerIsMoving(40); //walking to the furnace

            PlayerIsMoving(40); //smelthing ore in furnace
        }

        if(ChatContains("You've made a bronze bar!"))
        {
            System.out.println("Talking to mining instructor for bronze bar");
            Movement.moveTo(Areas.MINING_INSTRUCTOR_AREA.getRandomTile());
            PlayerIsMoving(50);

            TalkToNpc("Mining Instructor");
            PlayerIsMoving(50);
            ContinueChat();
        }


        if(ChatContains("To smith you'll need a hammer and enough metal bars"))
        {
            GameObject anvil = Objects.stream().name("Anvil").nearest().first();
            System.out.println("Anvil in viewport? " + anvil.inViewport());
            if(anvil.inViewport())
            {
                anvil.interact("Smith", "Anvil");
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20)) // check if walking to anvil
                {
                    Condition.wait(() -> Components.stream().widget(312).textContains("Dagger").viewable().isNotEmpty(), 100, 40); // we are walking to anvil
                }
            }
            else
            {
                TurnCamera();
            }

        }
        if(ChatContains("Use an anvil to open the smithing menu,"))
        {
            GameObject anvil = Objects.stream().name("Anvil").nearest().first();
            System.out.println("Anvil in viewport? " + anvil.inViewport());
            if(anvil.inViewport())
            {
                anvil.interact("Smith", "Anvil");
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20)) // check if walking to anvil
                {
                    Condition.wait(() -> Components.stream().widget(312).textContains("Dagger").viewable().isNotEmpty(), 100, 40); // we are walking to anvil
                }
            }
            else
            {
                TurnCamera();
            }
        }

        if(ChatContains("Now you have the smithing menu open,"))
        {

            if(Components.stream().widget(312).textContains("Dagger").viewable().isNotEmpty())
            {
                int bronzeDaggerId = 1205;
                System.out.println("Text contains dagger found! clicking dagger");
                Components.stream().widget(312).textContains("Dagger").first().click();
                Condition.wait(() -> Inventory.stream().id(bronzeDaggerId).isNotEmpty(), 100, 100);
            }
            else
            {
                GameObject anvil = Objects.stream().name("Anvil").nearest().first();
                anvil.interact("Smith", "Anvil");
                System.out.println("Starting to wait for smithing menu to open");
                PlayerIsMoving(40);
                Condition.wait(() -> Components.stream().widget(312).textContains("Dagger").viewable().isNotEmpty(), 100, 40);
            }
        }

        if(ChatContains("Congratulations, you've made your first weapon"))
        {
            if(Areas.DUNGEON_DOOR.contains(Players.local().tile()))
            {
                System.out.println("Clicking on the gate");
                int ladderId = 9717; //can also be 9719 (random choose 1? )
                Objects.stream().id(ladderId).nearest().first().interact("Open", "Gate");
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()), 100, 20);
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
}
