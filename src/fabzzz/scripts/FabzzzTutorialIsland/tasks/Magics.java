package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class Magics extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("Magics -> activate");
        return Areas.MAGIC_AREA.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("Magics -> execute");

        if(ChatContains("Follow the path to the wizard's house,"))
        {
            if(Areas.MAGIC_AREA.contains(Players.local().tile()))
            {
                TalkToNpc("Magic Instructor");
                ContinueChat();
            }
            else
            {
                System.out.println("Walking into magic house...");
                Movement.moveTo(Areas.MAGIC_AREA.getRandomTile());
                PlayerIsMoving(120);
            }
        }

        if(ChatContains("Open up the magic interface by tapping"))
        {
            System.out.println("Opening magic tab");
            Condition.wait(() -> Game.tab(Game.Tab.MAGIC), 25, 40);
            Condition.wait(() -> Game.tab() == Game.Tab.MAGIC, 100, 20);
        }

        if(ChatContains("This is your magic interface. All of your spells"))
        {
            TalkToNpc("Magic Instructor");
            ContinueChat();
        }

        if(ChatContains("You now have some runes."))
        {
            System.out.println("Are we on kill spot? " + (Areas.MAGIC_KILL_CHICKEN_SPOT.tile() == Players.local().tile()));
            if(Areas.MAGIC_KILL_CHICKEN_SPOT.tile().equals(Players.local().tile()))
            {
                System.out.println("We are on the chicken kill tile!");
                int chickenId = 3316;
                Magic.Spell.WIND_STRIKE.cast("Chicken");
                Magic.Spell.WIND_STRIKE.casting();
                Npc chicken = Npcs.stream().id(chickenId).nearest().first();
                chicken.interact("Cast", "Wind Strike");
                Condition.wait(() -> Players.local().interacting().inMotion(), 50, 100);
                System.out.println("Wind strike casted!");
            }
            else
            {
                System.out.println("Walking to the chicken kill tile");
                Movement.moveTo(Areas.MAGIC_KILL_CHICKEN_SPOT);
                PlayerIsMoving(40);
            }
        }

        if(ChatContains("You're nearly finished with the tutorial"))
        {
            if(IRON_MAN)
            {
                Game.logout();
                ScriptManager.INSTANCE.stop();
            }
            else
            {
                TalkToNpc("Magic Instructor");
                ContinueChat();

                if(Condition.wait(() -> Chat.INSTANCE.optionBarComponent().visible(), 100, 20))
                {
                    System.out.println("Chat click -> Yes");
                    Chat.stream().textContains("Yes").first().select();
                }
                ContinueChat();
                if(Condition.wait(() -> Chat.INSTANCE.optionBarComponent().visible(), 100, 20))
                {
                    System.out.println("Chat click -> No, I'm not planning to do that.");
                    Chat.stream().textContains("No, I'm not planning to do that.").first().select();
                }
                ContinueChat();
            }
        }

        if(!Areas.MAGIC_AREA.contains(Players.local().tile()))
        {
            Game.logout();
            ScriptManager.INSTANCE.stop();
        }
    }
}
