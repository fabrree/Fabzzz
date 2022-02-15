package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Areas.FISHING_CONTINUE_GATE_SPOT;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class Fishing extends Task
{
    private final int SHRIMP_ID = 2514;
    private final int FIRE_ID = 26185;
    private final int GATE_ID = 9470;

    @Override
    public boolean activate()
    {
        System.out.println("Fishing  -> activate");
        return Areas.FISHING_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("Fishing -> execute");
        if(ChatContains("Moving around"))
        {
            System.out.println("Moving to survival expert");
            Movement.step(Areas.FISHING_SURVIVAL_EXPERT.getRandomTile());
            PlayerIsMoving(30);
            TalkToNpc("Survival Expert");
            ContinueChat();
        }
        else if(ChatContains("You've been given an item"))
        {
            OpenGameTab(Game.Tab.INVENTORY);
        }
        else if(ChatContains("Catch some shrimp") && Inventory.stream().id(SHRIMP_ID).isEmpty())
        {
            System.out.println("going to fish");
            var fishingSpot = Npcs.stream().filtered(x -> x.name().contains("Fishing spot")).nearest().first();
            if (fishingSpot.inViewport()) {
                System.out.println(fishingSpot.name() + " fishing spot found! -> in viewport");
                fishingSpot.interact("Net");
                PlayerIsMoving(30); //moving to fishing spot
                Condition.wait(() -> Inventory.stream().id(SHRIMP_ID).isNotEmpty(), 100, 80); // fishing until fish
            }
            else
            {
                Camera.turnTo(fishingSpot);
            }
        }
        else if(ChatContains("You manage to catch some shrimp."))
        {
            ContinueChat();
        }
        else if(ChatContains("You've gained some experience"))
        {
            System.out.println("going to skills tab ");
            OpenGameTab(Game.Tab.STATS);
        }
        else if(ChatContains("On this menu you can view your skills."))
        {
            TalkToNpc("Survival Expert");
            ContinueChat();
        }
        else if(ChatContains("Woodcutting"))
        {
            GameObject tree = Objects.stream().within(12).id(9730).nearest().first();
            if (tree.inViewport()) {
                tree.interact("Chop down", "Tree");
                Condition.wait(() -> Objects.stream().at(tree.tile()).id(9730).isEmpty(), 150, 50);
            }
            else
            {
                Camera.turnTo(tree);
            }
            ContinueChat();
        }
        else if(ChatContains("Now that you have some logs,"))
        {
            System.out.println("Making fire");
            GameObject fire = Objects.stream().id(FIRE_ID).nearest().first();
            System.out.println("Fire tile = " + fire.tile());
            System.out.println("Player tile = " + Players.local().tile());
            if(!Players.local().tile().equals(fire.tile()))
            {
                if (Game.tab(Game.Tab.INVENTORY))
                {
                    Item tinderbox = Inventory.stream().name("tinderbox").first();
                    Item log = Inventory.stream().name("Logs").first();
                    if (Inventory.selectedItem().id() == -1)
                    {
                        if(Condition.wait(() -> tinderbox.interact("Use"), 50, 50 ))
                        {
                            log.interact("Use");
                        }
                    } else if (Inventory.selectedItem().id() == tinderbox.id()) {
                        log.interact("Use");
                    }
                    else
                    {
                        Inventory.stream().id(Inventory.selectedItem().id()).first().click();
                    }
                }
            }
            else
            {
                System.out.println("I AM STANDING ON A FIRE!! ARGHH!! going to move");
                Movement.moveTo(Areas.FISHING_SURVIVAL_EXPERT.getRandomTile().tile());
                PlayerIsMoving(30);
            }
        }
        else if(ChatContains("Cooking") && Objects.stream().id(FIRE_ID).within(10).isNotEmpty())
        {
            if(Game.tab(Game.Tab.INVENTORY))
            {
                System.out.println("Going to cook my shrimp");
                Item shrimpItem = Inventory.stream().id(SHRIMP_ID).first();
                if (Inventory.selectedItem().id() == -1)
                {
                    if(shrimpItem.interact("Use", "Fire"))
                    {
                        Objects.stream().id(FIRE_ID).nearest().first().click();
                        PlayerIsMoving(50); // walking to the fire
                        PlayerIsMoving(50); //cooking the shimp
                    }
                }
                else
                {
                    System.out.println("De-selecting selcted item");
                    Inventory.stream().id(Inventory.selectedItem().id()).first().click();
                }
            }
        }
        else if(ChatContains("burn the shrimp") || ChatContains("You manage to cook some shrimp") )
        {
            ContinueChat();
        }
        else if(ChatContains("Moving on") && Areas.FISHING_AREA.contains(Players.local().tile()))
        {
            if(Areas.FISHING_CONTINUE_GATE_SPOT.contains(Players.local()))
            {
                System.out.println("I am at the gate already");
                var gate = Objects.stream().id(GATE_ID).nearest().first();
                if(gate.inViewport())
                {
                    if(gate.interact("Open"))
                    {
                        Condition.wait(() -> !FISHING_CONTINUE_GATE_SPOT.contains(Players.local().tile()), 100, 40);
                    }
                }
                else
                {
                    Camera.turnTo(gate);
                }
            }
            else
            {
                System.out.println("Walking to the gate");
                Movement.step(Areas.FISHING_CONTINUE_GATE_SPOT.getRandomTile());
                PlayerIsMoving(30);
            }
        }
    }
    @Override
    public String status()
    {
        return "Fishing part";
    }
}
