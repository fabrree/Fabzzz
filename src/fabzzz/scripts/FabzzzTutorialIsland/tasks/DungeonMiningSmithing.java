package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;


import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class DungeonMiningSmithing extends Task
{
    private static final int GATE_ID = 9717; //can also be 9719 (random choose 1? )
    private static final int TIN_ORE_ID = 10080;
    private static final int COPPER_ORE_ID = 10079;
    private static final int SMITHING_MENU_ID = 312;
    private static final int BRONZE_DAGGER_ID = 1205;
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
            }
        }
        else if(ChatContains("It's quite simple really. To mine a rock,"))
        {
            GameObject tinOre = Objects.stream().id(TIN_ORE_ID).nearest().first();
            if (tinOre.inViewport()) {
                if(tinOre.valid())
                {
                    System.out.println("Mining white ore");
                    tinOre.interact("Mine");
                    PlayerIsMoving(60);
                    Condition.wait(() -> !tinOre.valid(), 150, 60);
                }
            }
            else
            {
                Camera.turnTo(tinOre);
            }

        }
        else if(ChatContains("You manage to mine some tin."))
        {
            System.out.println("You manage to mine some tin -> continue chat");
            ContinueChat();
        }
        else if(ChatContains("you just need some copper"))
        {
            System.out.println("Going to mine copper");
            GameObject copperOre = Objects.stream().id(COPPER_ORE_ID).nearest().first();
            if(copperOre.inViewport())
            {
                if (copperOre.valid()) {
                    System.out.println("Mining white ore");
                    copperOre.interact("Mine");
                    PlayerIsMoving(60);
                    Condition.wait(() -> !copperOre.valid(), 150, 60);
                }
            }
            else
            {
                Camera.turnTo(copperOre);
            }
        }
        else if(ChatContains("You manage to mine some copper"))
        {
            ContinueChat();
        }
        else if(ChatContains("You now have some tin ore and some copper ore."))
        {
            GameObject furnace = Objects.stream().name("Furnace").first();
            if(furnace.inViewport())
            {
                var currentSmithingXp = Skill.Smithing.experience();
                furnace.interact("Use", "Furnace");
                PlayerIsMoving(40); //walking to the furnace
                Condition.wait(() ->Skill.Smithing.experience() > currentSmithingXp, 50, 60); // smelthing ore in furnace -> wait for xp drop
            }
            else
            {
                Camera.turnTo(furnace);
            }
        }
        else if(ChatContains("You've made a bronze bar!"))
        {
            System.out.println("Talking to mining instructor for bronze bar");
            Movement.moveTo(Areas.MINING_INSTRUCTOR_AREA.getRandomTile());
            PlayerIsMoving(50);
            TalkToNpc("Mining Instructor");
            PlayerIsMoving(30);
            ContinueChat();
        }
        else if(ChatContains("To smith you'll need a hammer and enough metal bars") || ChatContains("Use an anvil to open the smithing menu,"))
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
}
