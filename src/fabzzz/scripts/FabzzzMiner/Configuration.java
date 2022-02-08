package fabzzz.scripts.FabzzzMiner;

import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.selectedBank.WalkToSelectedBank;
import fabzzz.scripts.FabzzzMiner.tasks.MineOre;
import org.powbot.api.Area;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.walking.model.Skill;

public class Configuration {

    public static Tile STARTING_TILE;
    public static int TILES_AWAY;
    public static boolean USE_BEST_PICKAXE_BANK;

    // select ore configuration
    public static void SelectBank(String bankName){
        System.out.println("SelectBank() = " + bankName);
        if (bankName.equals("Mining guild"))
            WalkToSelectedBank.BANK = BANK_MINING_GUILD;
        if (bankName.equals("Varrock east"))
            WalkToSelectedBank.BANK = BANK_VARROCK_EAST;
        if (bankName.equals("Varrock west"))
            WalkToSelectedBank.BANK = BANK_VARROCK_WEST;
        if (bankName.equals("Al Kharid"))
            WalkToSelectedBank.BANK = BANK_AL_KHARID;
        if (bankName.equals("Draynor"))
            WalkToSelectedBank.BANK = BANK_DRAYNOR;
    }

    // select ore configuration
    public static void SelectOreToMine(String oreName){
        if (oreName.equals("Clay"))
            MineOre.OresToMine = Configuration.CLAY;
        if (oreName.equals("Copper"))
            MineOre.OresToMine = Configuration.COPPER_ORE;
        if (oreName.equals("Tin"))
            MineOre.OresToMine = Configuration.TIN_ORE;
        if (oreName.equals("Iron"))
            MineOre.OresToMine = Configuration.IRON_ORE;
        if (oreName.equals("Silver"))
            MineOre.OresToMine = Configuration.SILVER_ORE;
        if (oreName.equals("Coal"))
            MineOre.OresToMine = Configuration.COAL_ORE;
        if (oreName.equals("Gold"))
            MineOre.OresToMine = Configuration.GOLD_ORE;
        if (oreName.equals("Mithril"))
            MineOre.OresToMine = Configuration.MITHRIL_ORE;
        if (oreName.equals("Adamant"))
            MineOre.OresToMine = Configuration.ADAMANT_ORE;
        if (oreName.equals("Runite"))
            MineOre.OresToMine = Configuration.RUNITE_ORE;
    }

    public static final int[] CLAY = {11362, 11363};
    public static final int[] COPPER_ORE = {11161, 10943};
    public static final int[] TIN_ORE = {11361, 11360};
    public static final int[] IRON_ORE = {11365, 11364};
    public static final int[] SILVER_ORE = {11368, 11369};
    public static final int[] COAL_ORE ={11367, 11366};
    public static final int[] GOLD_ORE = {11370, 11371};
    public static final int[] MITHRIL_ORE ={11373, 11372};
    public static final int[] ADAMANT_ORE ={11375, 11374 };
    public static final int[] RUNITE_ORE = {11377,11376};


    public static void EquipPickaxeIfPossible()
    {
        System.out.println("EquipPickaxeIfPossible()");
        Item pickaxe = Inventory.stream().filtered(x -> x.name().contains("pick")).first();
        if (Game.tab(Game.Tab.INVENTORY) && pickaxe.valid())
        {
            System.out.println("Pickaxe found in inventory");
            int attackLevel = Skill.Attack.realLevel();
            int miningLevel = Skill.Mining.realLevel();

            if (pickaxe.name().contains("Bronze") || pickaxe.name().contains("Iron"))
                pickaxe.interact("Wield");
            if (pickaxe.name().contains("Steel") && attackLevel >= 5 && miningLevel >= 6)
                pickaxe.interact("Wield");
            if (pickaxe.name().contains("Black") && attackLevel >= 10 && miningLevel >= 11)
                pickaxe.interact("Wield");
            if (pickaxe.name().contains("Mithril") && attackLevel >= 20 && miningLevel >= 21)
                pickaxe.interact("Wield");
            if (pickaxe.name().contains("Adamant") && attackLevel >= 30 && miningLevel >= 31)
                pickaxe.interact("Wield");
            if (pickaxe.name().contains("Rune") && attackLevel >= 40 && miningLevel >= 41)
                pickaxe.interact("Wield");
            if (pickaxe.name().contains("Dragon") && attackLevel >= 60 && miningLevel >= 61)
                pickaxe.interact("Wield");
        }
    }


    public static Area BANK_VARROCK_EAST = new Area(
            new Tile(3250, 3422, 0),
            new Tile(3250, 3420, 0),
            new Tile(3257, 3420, 0),
            new Tile(3256, 3420, 0),
            new Tile(3255, 3420, 0),
            new Tile(3254, 3420, 0),
            new Tile(3254, 3422, 0)
    );


    public static Area BANK_VARROCK_WEST= new Area(
            new Tile(3185, 3437, 0),
            new Tile(3182, 3437, 0),
            new Tile(3182, 3434, 0),
            new Tile(3185, 3435, 0)
    );

    public static Area BANK_MINING_GUILD = new Area(
            new Tile(3013, 9717, 0),
            new Tile(3013, 9719, 0),
            new Tile(3014, 9719, 0),
            new Tile(3014, 9720, 0),
            new Tile(3015, 9720, 0),
            new Tile(3015, 9718, 0)
    );

    public static Area BANK_AL_KHARID = new Area(
            new Tile(3269, 3170, 0),
            new Tile(3271, 3170, 0),
            new Tile(3271, 3165, 0),
            new Tile(3269, 3165, 0)
    );

    public static Area BANK_DRAYNOR = new Area(
            new Tile(3092, 3246, 0),
            new Tile(3092, 3242, 0),
            new Tile(3093, 3242, 0),
            new Tile(3093, 3246, 0)
    );
}
