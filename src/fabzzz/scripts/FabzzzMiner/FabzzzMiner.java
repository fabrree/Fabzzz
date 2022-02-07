package fabzzz.scripts.FabzzzMiner;
import com.google.common.eventbus.Subscribe;
import fabzzz.scripts.FabzzzMiner.tasks.*;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.*;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global.DepositDepositBoxGLOBAL;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global.DepositGLOBAL;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global.WalkToBankGLOBAL;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global.WalkToDepositBoxGLOBAL;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.selectedBank.Deposit;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.selectedBank.WalkToBank;
import org.powbot.api.Color;
import org.powbot.api.event.RenderEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.api.script.paint.TrackInventoryOption;
import org.powbot.api.script.paint.TrackSkillOption;
import org.powbot.mobile.drawing.Graphics;
import org.powbot.mobile.service.ScriptUploader;
import static fabzzz.scripts.FabzzzMiner.Configuration.*;

/*
2nd feb
    first time, getting to know the api, lots of nice help from the discord community.
    first step was making the miner mine and drop,
    then focused on some Drawing on Screen,
    Made Tasks
    Made scriptconfigurations with UI -> selecting in configurations what ore someone wants, works nicely!
    mining UI done!
    Good progress for today! tomorrow coding exam! Asp.net ORM :(

 */

import java.util.ArrayList;

@ScriptManifest(
        name = "Fabzzz Miner",
        description = "Start me AT your mining spot             <3 Mining for love <3",
        version = "0.0.1"
)

@ScriptConfiguration.List(
    {
        @ScriptConfiguration(
                name = "Ore",
                description = "Select ore to mine",
                defaultValue = "Clay",
                allowedValues = {"Clay","Copper", "Iron", "Tin", "Silver", "Coal", "Gold", "Mithril", "Adamant", "Runite"}
        ),
        @ScriptConfiguration(
                name = "Use banking or deposit box",
                description = "Tick this to bank/use deposit box (otherwise powermines)",
                defaultValue = "false",
                optionType = OptionType.BOOLEAN
        ),
        @ScriptConfiguration(
                name = "Use depositbox (automatically closest)",
                description = "Tick this to use deposit box (automatically closest)",
                defaultValue = "false",
                optionType = OptionType.BOOLEAN,
                enabled = false,
                visible = false
        ),
        @ScriptConfiguration(
                name = "Use banking",
                description = "Tick this to bank",
                defaultValue = "false",
                optionType = OptionType.BOOLEAN,
                enabled = false,
                visible = false
        ),
        @ScriptConfiguration(
                name = "Select bank",
                description = "Select a bank or deposit box",
                defaultValue = "Automatic (closest)",
                allowedValues = {"Bank (Automatic closest)","Deposit box (Automatic closest)", "Mining guild", "Varrock east", "Varrock west", "Al Kharid", "Draynor"},
                enabled = false,
                visible = false
        ),
        @ScriptConfiguration(
                name = "Auto upgrade bank",
                description = "Automatically upgrade pickaxe at bank if possible",
                defaultValue = "false",
                optionType = OptionType.BOOLEAN,
                enabled = false,
                visible = false
        ),
        @ScriptConfiguration(
                name = "Tiles away",
                description = "How many tiles do we go for the ore (1 to stand in same spot)",
                defaultValue = "1",
                optionType = OptionType.INTEGER
        )
    }
)

public class FabzzzMiner extends AbstractScript {
    private ArrayList<Task> tasklist = new ArrayList<>();
    public static void main(String[] args)
    {
        new ScriptUploader().uploadAndStart("Fabzzz Miner", "Freek", "127.0.0.1:5585", true, false);
    }

    @ValueChanged(keyName = "Use banking or deposit box")
    public void selectedUseBankOrDepositBox(boolean a) {
        updateVisibility("Use banking", a);
        updateEnabled("Use banking", a);
        updateOption("Use banking", false, OptionType.BOOLEAN);

        updateVisibility("Use depositbox (automatically closest)", a);
        updateEnabled("Use depositbox (automatically closest)", a);
        updateOption("Use depositbox (automatically closest)", false, OptionType.BOOLEAN);
    }

    @ValueChanged(keyName = "Use banking")
    public void selectedUseBanking(boolean show) {
        System.out.println("UPDATED USE BANKING");
        updateVisibility("Select bank", show);
        updateEnabled("Select bank", show);

        updateVisibility("Auto upgrade bank", show);
        updateEnabled("Auto upgrade bank", show);
        updateOption("Auto upgrade bank", false, OptionType.BOOLEAN);
        System.out.println("Auto upgrade -> TURNED TO FALSE");
    }



    @Override
    public void onStart()
    {
        PaintUI();

        String oreToMine = getOption("Ore");
        Boolean useBanking = getOption("Use banking");
        Boolean useDepositBox = getOption("Use depositbox (automatically closest)");
        Boolean useBestPickaxeBank = getOption("Auto upgrade bank");
        int tilesAway = getOption("Tiles away");
        String bankName = getOption("Select bank");

        //configurations start
        SelectBank(bankName);
        EquipPickaxeIfPossible();
        STARTING_TILE = Players.local().tile();
        TILES_AWAY = tilesAway;
        USE_BEST_PICKAXE_BANK = useBestPickaxeBank;
        SelectOreToMine(oreToMine);
        //configurations end

        tasklist.add(new MineOre());
        if(useDepositBox)
        {
            System.out.println("Tasklist adding useDepositBox");
            tasklist.add(new WalkToDepositBoxGLOBAL());
            tasklist.add(new DepositDepositBoxGLOBAL());
            tasklist.add(new WalkToMiningSpot());
        }
        if(useBanking)
        {
            System.out.println("Tasklist adding useBanking");
            if(bankName.equals("Bank (Automatic closest)"))
            {
                tasklist.add(new WalkToBankGLOBAL());
                tasklist.add(new DepositGLOBAL());
            }
            else
            {
                tasklist.add(new WalkToBank());
                tasklist.add(new Deposit());
            }
            tasklist.add(new WalkToMiningSpot());
        }
        else
        {
            System.out.println("Tasklist adding else -> powermine");
            tasklist.add(new Powermine());
        }

        System.out.println("onStart() -> finished");

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
                .trackSkill(Skill.Mining, "Xp gained", TrackSkillOption.Exp)
                .trackSkill(Skill.Mining, "Level", TrackSkillOption.LevelProgressBar)
                .y(100) //45
                .x(100)  //55
                .build();
        addPaint(Monet);
    }

}

//    List<GameObject> rocks = new ArrayList<>();
//    @Subscribe
//    public void onGameTick(TickEvent evt){
//        rocks.clear();
//        rocks = Objects.stream(10).id(ore).list();
//    }
//
//    @Subscribe
//    public void onRender(RenderEvent evt){
//        Graphics g = evt.getGraphics();
//        g.setScale(1.0f);
//        g.setColor(Color.getGREEN());
//        for(GameObject obj:rocks) {
//            if (obj != GameObject.getNil() && obj.inViewport()) {
//                obj.draw(g);
//            }
//        }
//    }