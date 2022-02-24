package fabzzz.scripts.FabzzzTutorialIsland.tasks.Dungeon.Combat;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.Arrays;
import java.util.List;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.ContinueChat;

public class RatFightMelee extends Task
{
    private static final List<String> CHATCONTAINS = Arrays.asList("It's time to slay some rats!","While you are fighting you will see a bar over your head.","Pass through the gate and talk to the combat instructor");

    @Override
    public boolean activate()
    {
        System.out.println("RatFightMelee -> activate");
        return Areas.DUNGEON_COMBAT_AREA.contains(Players.local().tile()) && AnyOfMultipleChatsContains(CHATCONTAINS);
    }

    @Override
    public void execute()
    {
        System.out.println("RatFightMelee -> execute");
        if (ChatContains("It's time to slay some rats!") || ChatContains("While you are fighting you will see a bar over your head.") )
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
                            Condition.wait(() -> !Players.local().interacting().valid(), 200, 200);
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
    }

    @Override
    public String status()
    {
        return "Going to fight rat with melee";
    }
}
