package fabzzz.scripts.FabzzzTutorialIsland.Util;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.Random;

public class Configurations
{
    public static final Random r = new Random();
    public static final int TEXT_SCREEN = 263;
    public static String USERNAME = "Dragon";
    public static boolean IRON_MAN;

    public static void ContinueChat()
    {
        while (Condition.wait(() -> Chat.canContinue(), 100, 20))
        {
            System.out.println("Continuing chat...");
            Chat.clickContinue();
        }
    }
    public static boolean ChatContains(String text)
    {
        if(Components.stream().widget(TEXT_SCREEN).textContains(text).isNotEmpty())
            return true;
        return false;
    }
    public static void TalkToNpc(String npcName)
    {
        System.out.println("TalkToNpc() -> Looking for" + npcName);
        var npc = Npcs.stream().name(npcName).nearest().first();
        if (npc.inViewport()) {
            System.out.println("TalkToNpc() -> " +npcName + " found! -> in viewport");
            npc.interact("Talk-to", npcName);
            if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
            {
                Condition.wait(() -> !Players.local().inMotion(), 100, 100);
            }
        }
        else
        {
            System.out.println("TalkToNpc() -> Turning camera to find NPC");
            TurnCamera();
        }
    }

    public static void PlayerIsMoving(int maxWaitTime)
    {
        if(Condition.wait(() -> Players.local().inMotion(), 15, 20))
        {
            Condition.wait(() -> !Players.local().inMotion(), 100, maxWaitTime);
        }
    }

    public static void TurnCamera()
    {
        Random r = new Random();
        var yaw = Camera.yaw();
        if(r.nextBoolean())
        {
            int high = yaw + 150;
            int low = yaw + 80;
            int result = r.nextInt(high-low) + low;
            Camera.pitch(true);
            Camera.angle(result);
        }
        else
        {
            int high = yaw - 80;
            int low = yaw - 150;
            int result = r.nextInt(high-low) + low;
            Camera.pitch(true);
            Camera.angle(result);
        }
    }
}
