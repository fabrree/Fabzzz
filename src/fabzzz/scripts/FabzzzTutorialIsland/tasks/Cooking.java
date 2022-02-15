package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.ContinueChat;

public class Cooking extends Task
{
    private static final int BREAD_DOUGH_ID = 2307;
    private static final int DOOR_ID = 9710;
    @Override
    public boolean activate()
    {
        System.out.println("Cooking -> activate");
        return Areas.COOKING_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("Cooking -> execute");
        if(ChatContains("Talk to the chef indicated"))
        {
            System.out.println("going to talk to master chef..");
            TalkToNpc("Master Chef");
            ContinueChat();
        }
        else if(ChatContains("Making dough"))
        {
            System.out.println("Making dough...");
            if (Game.tab(Game.Tab.INVENTORY))
            {
                Item potOfFlour = Inventory.stream().name("Pot of flour").first();
                Item bucketOfWater = Inventory.stream().name("Bucket of water").first();
                if (Inventory.selectedItem().id() == -1)
                {
                    if(Condition.wait(() -> potOfFlour.interact("Use"), 50, 50 ))
                    {
                        bucketOfWater.interact("Use");
                    }
                } else if (Inventory.selectedItem().id() == potOfFlour.id()) {
                    bucketOfWater.interact("Use");
                    Condition.wait(() -> Inventory.stream().id(BREAD_DOUGH_ID).isNotEmpty(),50, 10);
                }
                else
                {
                    Inventory.stream().id(Inventory.selectedItem().id()).first().click();
                }
            }
        }
        else if(ChatContains("Bake it into some bread."))
        {
            GameObject range = Objects.stream().name("Range").first();
            if(range.inViewport())
            {
                range.interact("Cook", "Range");
                PlayerIsMoving(40); // walking to the oven
                PlayerIsMoving(50); // cooking the bread
            }
            else
            {
                Camera.turnTo(range);
            }
        }
        else if(ChatContains("Moving on"))
        {
            System.out.println("Starting to go out of cooking house");
            if (Areas.COOKING_DOOR_OUT.tile().equals(Players.local().tile()))
            {
                System.out.println("Clicking on the door");

                Objects.stream().id(DOOR_ID).nearest().first().interact("Open");
                if (Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> Areas.BETWEEN_COOK_AND_QUEST.contains(Players.local().tile()), 100, 30);
                }
            }
            else
            {
                System.out.println("Walking to the door...");
                Movement.step(Areas.COOKING_DOOR_OUT);
                PlayerIsMoving(20);
            }
        }
    }
    @Override
    public String status()
    {
        return "Cooking part";
    }
}
