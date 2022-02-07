package fabzzz.scripts.FabzzzTutorialIsland;
import fabzzz.scripts.FabzzzTutorialIsland.tasks.*;
import fabzzz.scripts.FabzzzTutorialIsland.tasks.Walking.*;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;

import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.IRON_MAN;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.USERNAME;


@ScriptManifest(
        name = "FAB Tutorial island",
        description = "Gtfo this island!",
        version = "0.0.1"
)


@ScriptConfiguration.List(
    {
        @ScriptConfiguration(
                name = "OwnUsername",
                description = "Tick this to use your own username(grabs a default otherwise)",
                defaultValue = "false",
                optionType = OptionType.BOOLEAN
        ),
        @ScriptConfiguration(
                name = "Username",
                description = "Type your username (if not available we use generated)",
                enabled = false,
                visible = false
        ),
        @ScriptConfiguration(
                name = "IronMan",
                description = "Tick this if you want to have IRON MAN (log out at final step)",
                defaultValue = "false",
                optionType = OptionType.BOOLEAN,
                enabled = true,
                visible = true
        )
    }
)


public class FabTutorialIsland extends AbstractScript
{
    private ArrayList<Task> tasklist = new ArrayList<>();
        public static void main(String[] args) {
            new ScriptUploader().uploadAndStart("FAB Tutorial island", "Freek", "127.0.0.1:5585", true, false);
        }


    @ValueChanged(keyName = "OwnUsername")
    public void selectedUseBankOrDepositBox(boolean a) {
        updateVisibility("Username", a);
        updateEnabled("Username", a);
    }

    @Override
    public void onStart()
    {
        PaintUI();

        String username = getOption("Username");
        Boolean ownUsername = getOption("OwnUsername");
        Boolean ironMan = getOption("IronMan");

        //configurations start
        if(ownUsername)
        {
            USERNAME = username;
        }
        IRON_MAN = ironMan;
        //configurations end


        tasklist.add(new SetUsername());
        tasklist.add(new setAppearance());
        tasklist.add(new TalkToFirstInstructor());
        tasklist.add(new Fishing());
        tasklist.add(new GoInsideCookingBuilding());
        tasklist.add(new Cooking());
        tasklist.add(new GoInsideQuestBuilding());
        tasklist.add(new Quests());
        tasklist.add(new DungeonMiningSmithing());
        tasklist.add(new DungeonCombat());
        tasklist.add(new GoInsideBankBuilding());
        tasklist.add(new Banking());
        tasklist.add(new GoInsidePrayerBuilding());
        tasklist.add(new Prayer());
        tasklist.add(new GoInsideMagicBuilding());
        tasklist.add(new Magics());
        System.out.println("OnStart() finished");
    }


    @Override
    public void poll() {
        System.out.println("poll() -> starting...");
        for (Task task : tasklist)
        {
            if(task.activate())
            {
                task.execute();
                return;
            }
        }
    }

    private void PaintUI()
    {
        Paint Monet = PaintBuilder.newBuilder()
                .y(100)
                .x(100)
                .build();
        addPaint(Monet);
    }
}



