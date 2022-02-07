package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;
import fabzzz.scripts.FabzzzTutorialIsland.Task;
import fabzzz.scripts.FabzzzTutorialIsland.Util.Areas;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class Banking extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("Banking -> activate");
        return Areas.BANKING_AREA_INSIDE.contains(Players.local().tile());
    }

    @Override
    public void execute()
    {
        System.out.println("Banking -> execute");

        if(ChatContains("Follow the path and you will come to the front of the building."))
        {
            if (Bank.inViewport())
            {
                System.out.println("Opening bank");
                Condition.wait(() -> Bank.open(), 50, 10);
            }
        }

        if(ChatContains("This is your bank."))
        {
            if(Bank.close())
            {
                System.out.println("Walking to poll booth");
                int pollBoothId = 26801;
                GameObject pollBooth = Objects.stream().id(pollBoothId).nearest().first();
                if(pollBooth.inViewport())
                {
                    System.out.println("Clicking on poll booth");
                    pollBooth.interact("Use", "Poll booth");
                    PlayerIsMoving(40);
                    ContinueChat();
                }
                else
                {
                    System.out.println("Poll booth not in viewport.. turning camera");
                    TurnCamera();
                }
            }
        }

        if(ChatContains("Polls are run periodically to let"))
        {
            if(Areas.BANK_FIRST_DOOR.tile().equals(Players.local().tile()))
            {
                int doorId = 9721;
                GameObject door = Objects.stream().id(doorId).nearest().first();
                if(door.inViewport())
                {
                    door.interact("Open", "Door");
                }
                else
                {
                    TurnCamera();
                }
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> Areas.BANK_AREA_SECOND.contains(Players.local().tile()), 100, 50);
                }
            }
            else
            {
                System.out.println("Walking to first door in bank");
                Movement.moveTo(Areas.BANK_FIRST_DOOR);
                PlayerIsMoving(30);
            }
        }

        if(ChatContains("all about your account."))
        {
            TalkToNpc("Account Guide");
            ContinueChat();
        }

        if(ChatContains("to open your Account Management menu."))
        {
            System.out.println("Opening account management tab");
            Condition.wait(() -> Game.tab(Game.Tab.ACCOUNT_MANAGEMENT), 25, 40);
            Condition.wait(() -> Game.tab() == Game.Tab.ACCOUNT_MANAGEMENT, 100, 20);
            System.out.println("Account management tab is open");
        }

        if(ChatContains("This is your Account Management menu where"))
        {
            TalkToNpc("Account Guide");
            ContinueChat();
        }
        if(ChatContains("Continue through the next door."))
        {
            int doorId = 9722;
            GameObject door = Objects.stream().id(doorId).nearest().first();
            if(door.inViewport())
            {
                door.interact("Open", "Door");
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> Areas.BETWEEN_BANK_PRAYER.contains(Players.local().tile()), 100, 30);
                }
            }
            else
            {
                TurnCamera();
            }
        }
    }
}
