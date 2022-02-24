package fabzzz.scripts.FabzzzTutorialIsland.Util;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.List;
import java.util.Random;

public class Configurations
{
    public static final Random r = new Random();
    public static final int TEXT_SCREEN = 263;
    public static String USERNAME = "Dragon";
    public static boolean IRON_MAN;
    public static final int GATE_ID_RATS = 9719;
    public static final String GIANT_RAT = "Giant rat";
    public  static final int EQUIPMENT_FULL_SCREEN = 84;
    public static void OpenGameTab(Game.Tab tabToOpen)
    {
        Game.tab(tabToOpen);
        Condition.wait(() -> Game.tab() == tabToOpen, 25, 10);
    }
    public static void ContinueChat()
    {
        while (Condition.wait(Chat::canContinue, 100, 10))
        {
            System.out.println("Continuing chat...");
            Chat.clickContinue();
        }
    }
    public static boolean ChatContains(String text)
    {
        return Components.stream().widget(TEXT_SCREEN).textContains(text).isNotEmpty();
    }

    public static boolean AnyOfMultipleChatsContains(List<String> texts)
    {
        //var textOnScreen = Components.stream().widget(TEXT_SCREEN);
        for (var text : texts)
        {
            System.out.println("Check text: " + text);
        //    if(textOnScreen.textContains(text).isNotEmpty())
            if(Components.stream().widget(TEXT_SCREEN).textContains(text).isNotEmpty())
            {
                System.out.println("RETURN TRUE");
                return true;
            }
        }
        return false;
    }

    public static void TalkToNpc(String npcName)
    {
        System.out.println("TalkToNpc() -> Looking for" + npcName);
        var npc = Npcs.stream().name(npcName).nearest().first();
        if (npc.inViewport()) {
            System.out.println("TalkToNpc() -> " +npcName + " found! -> in viewport");
            if(npc.interact("Talk-to", npcName))
            {
                if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
                {
                    Condition.wait(() -> !Players.local().inMotion(), 100, 100);
                }
            }
            else
            {
                System.out.println("Can't interact.. turning camera");
                Camera.turnTo(npc);
            }
        }
        else
        {
            System.out.println("TalkToNpc() -> Turning camera to find NPC");
            Camera.turnTo(npc);
        }
    }

    public static void PlayerIsMoving(int maxWaitTimeX100MS)
    {
        if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
        {
            Condition.wait(() -> !Players.local().inMotion(), 100, maxWaitTimeX100MS);
        }
    }
}
