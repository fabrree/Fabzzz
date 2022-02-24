package fabzzz.scripts.FabzzzTutorialIsland.tasks.Dungeon.MiningSmithing;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;


import java.util.Arrays;
import java.util.List;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class DungeonMiningSmelting extends Task
{
    private static final int TIN_ORE_ID = 10080;
    private static final int COPPER_ORE_ID = 10079;
    private static final List<String> CHATCONTAINS = Arrays.asList("Next let's get you a weapon,","It's quite simple really. To mine a rock,","You manage to mine some tin.","you just need some copper","You manage to mine some copper","You now have some tin ore and some copper ore.","You've made a bronze bar!");

    @Override
    public boolean activate()
    {
        System.out.println("DungeonMiningSmithing -> activate");
        return Areas.DUNGEON_MINING_SMITHING_AREA.contains(Players.local().tile()) && AnyOfMultipleChatsContains(CHATCONTAINS);
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
            GameObject furnace = Objects.stream().name("Furnace").nearest().first();
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
    }
    @Override
    public String status()
    {
        return "Dungeon mining/smelting part";
    }
}
