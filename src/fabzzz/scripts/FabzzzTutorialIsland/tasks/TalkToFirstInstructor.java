package fabzzz.scripts.FabzzzTutorialIsland.tasks;
import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;

public class TalkToFirstInstructor extends Task
{

    @Override
    public boolean activate()
    {
        System.out.println("TalkToFirstInstructor -> activate");
        return Areas.FIRST_HOUSE_NEW.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("TalkToFirstInstructor -> execute");

        if(ChatContains("Getting started"))
        {
            TalkToNpc("Gielinor Guide");
            ContinueChat();
            if(Chat.INSTANCE.optionBarComponent().visible())
            {
                System.out.println("Chat send input 1");
                if(Chat.stream().textContains("I am an experienced player.").isNotEmpty())
                {
                    System.out.println("Clicking on option");
                    Chat.stream().textContains("I am an experienced player.").first().select();
                }
                ContinueChat();
            }
        }
        else if(ChatContains("Please tap on the flashing spanner icon found on the right side of your screen"))
        {
            System.out.println("In settings menu text");
            Game.tab(Game.Tab.SETTINGS);
            Condition.wait(() -> Game.tab() == Game.Tab.SETTINGS, 25, 20);
        }
        else if(ChatContains("variety of game settings."))
        {
            TalkToNpc("Gielinor Guide");
            ContinueChat();
        }

        if(ChatContains("Moving on") || ChatContains("Moving around"))
        {
            System.out.println("Walking to door");
            int doorId = 9398;
            GameObject door = Objects.stream().id(doorId).first();
            if(door.inViewport())
            {
                door.interact("Open");
                PlayerIsMoving(60);
            }
            System.out.println("Door is opened!");
        }
    }
}
