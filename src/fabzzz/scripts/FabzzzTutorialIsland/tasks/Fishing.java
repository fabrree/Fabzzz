package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Areas.BETWEEN_FISH_GATE_COOKING_DOOR;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class Fishing extends Task
{
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

        if(ChatContains("You've been given an item"))
        {
            Game.tab(Game.Tab.INVENTORY);
        }

        var shrimp = 2514;
        if(ChatContains("Catch some shrimp")
               && Inventory.stream().id(shrimp).isEmpty())
        {
            System.out.println("going to fish");
            var npc = Npcs.stream().filtered(x -> x.name().contains("Fishing spot")).nearest().first();
            if (Condition.wait(() -> npc.inViewport(), 100, 20)) {
                System.out.println(npc.name() + " fishing spot found! -> in viewport");
                npc.interact("Net");
                PlayerIsMoving(30); //moving to fishing spot
                Condition.wait(() -> Inventory.stream().id(shrimp).isNotEmpty(), 100, 80); // fishing until fish
            }
        }

        if(ChatContains("You manage to catch some shrimp."))
        {
            ContinueChat();
        }

        if(ChatContains("You've gained some experience"))
        {
            System.out.println("going to skills tab ");
            Game.tab(Game.Tab.STATS);
        }

        if(ChatContains("On this menu you can view your skills."))
        {
            TalkToNpc("Survival Expert");
            ContinueChat();
        }

        if(ChatContains("Woodcutting"))
        {
            GameObject tree = Objects.stream().within(12).id(9730).nearest().first();
            if (tree.inViewport()) {
                tree.interact("Chop down", "Tree");
                Condition.wait(() -> Objects.stream().at(tree.tile()).id(9730).isEmpty(), 150, 50);
            }
            ContinueChat();
        }

        if(ChatContains("Now that you have some logs,"))
        {
            System.out.println("Making fire");
            int fireId = 26185;
            GameObject fire = Objects.stream().id(fireId).nearest().first();
            System.out.println("Fire tile = " + fire.tile());
            System.out.println("Player tile = " + Players.local().tile());
            if(!Players.local().tile().equals(fire.tile())) //26185 "Fire"
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
                        Game.tab(Game.Tab.STATS);
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

        var fire = 26185;
        if(ChatContains("Cooking")
            && Objects.stream().id(fire).within(10).isNotEmpty())
        {
            if(Game.tab(Game.Tab.INVENTORY))
            {
                System.out.println("Going to cook my shrimp");
                Item shrimpItem = Inventory.stream().id(shrimp).first();
                if (Inventory.selectedItem().id() == -1)
                {
                    if(Condition.wait(() -> shrimpItem.interact("Use", "Fire"), 50, 50 ))
                    {
                       Objects.stream().id(fire).nearest().first().click();
                       PlayerIsMoving(50);
                    }
                } else
                {
                    System.out.println("De-selecting selcted item");
                    Inventory.stream().id(Inventory.selectedItem().id()).first().click();
                }
            }
        }
        if(ChatContains("burn the shrimp"))
        {
            ContinueChat();
        }

        if(ChatContains("You manage to cook some shrimp"))
        {
            ContinueChat();
        }

        if(ChatContains("Moving on")
        && Areas.FISHING_AREA.contains(Players.local().tile()))
        {

            if(Areas.FISHING_CONTINUE_GATE_SPOT.contains(Players.local()))
            {
                System.out.println("I am at the gate already");
                TurnCamera();
                int gateId = 9470;
                Condition.wait(() -> Objects.stream().id(gateId).nearest().first().interact("Open"), 100, 10);
                Condition.wait(() -> BETWEEN_FISH_GATE_COOKING_DOOR.contains(Players.local().tile()), 100, 40);
            }
            else
            {
                System.out.println("Walking to the gate");
                Movement.step(Areas.FISHING_CONTINUE_GATE_SPOT.getRandomTile());
                PlayerIsMoving(30);
            }

        }
    }
}
