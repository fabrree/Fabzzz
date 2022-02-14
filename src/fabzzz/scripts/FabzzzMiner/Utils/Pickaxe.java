package fabzzz.scripts.FabzzzMiner.Utils;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;

public enum Pickaxe
{
    DRAGON("Dragon pickaxe", 11920, 61, 60),
    RUNE("Rune pickaxe", 1275, 41, 40),
    ADAMANT("Adamant pickaxe", 1271, 31, 30),
    MITHRIL("Mithril pickaxe", 1273, 21, 20),
    BLACK("Black pickaxe", 12297, 11, 10),
    STEEL("Steel pickaxe", 1269, 5, 5),
    IRON("Iron pickaxe", 1267, 1, 1),
    BRONZE("Bronze pickaxe", 1265, 1, 1);

    private final String pickaxeName;
    private final int pickaxeId;
    private final int miningLevelRequired;
    private final int attackLevelRequired;

    Pickaxe(String name, int pickaxeId, int miningLevelRequired, int attackLevelRequired )
    {
        this.pickaxeName = name;
        this.pickaxeId = pickaxeId;
        this.miningLevelRequired = miningLevelRequired;
        this.attackLevelRequired = attackLevelRequired;
    }


    public static void CheckForBetterPickaxe()
    {
        var currentMiningLevel = Skill.Mining.realLevel();
        var currentAttackLevel = Skill.Attack.realLevel();
        int currentPickaxeId;
        Pickaxe currentlyUsedPickaxe;

        System.out.println("get current equipped/in inventory pickaxe");
        currentPickaxeId = getCurrentPickaxeInInvenOrEquip();

        System.out.println("set current pickaxe to object");
        currentlyUsedPickaxe = currentPickaxeToObject(currentPickaxeId);

        System.out.println("Checking for a better pickaxe in bank");
        CheckForBetterPickaxeInBank(currentMiningLevel, currentAttackLevel, currentlyUsedPickaxe);
    }

    private static void CheckForBetterPickaxeInBank(int currentMiningLevel, int currentAttackLevel, Pickaxe currentlyUsedPickaxe)
    {
        for (var i : Pickaxe.values())
        {
            if (currentMiningLevel > i.miningLevelRequired && Bank.stream().id(i.pickaxeId).isNotEmpty() && currentlyUsedPickaxe.miningLevelRequired < i.miningLevelRequired)
            {
                System.out.println("Found! withdrawing:" + i.pickaxeName);
                Bank.withdraw(i.pickaxeId, 1);
                if(currentAttackLevel >= i.attackLevelRequired)
                {
                    System.out.println("going to wield item..");
                    Inventory.stream().id(i.pickaxeId).first().interact("Wield", i.pickaxeName);
                    if(Condition.wait(() -> Inventory.stream().id(currentlyUsedPickaxe.pickaxeId).isNotEmpty(), 50, 20))
                    {
                        Bank.deposit(currentlyUsedPickaxe.pickaxeName, 1);
                    }
                }
            }
        }
    }

    private static int getCurrentPickaxeInInvenOrEquip()
    {
        if(Equipment.itemAt(Equipment.Slot.MAIN_HAND).name().contains("pickaxe"))
        {
            System.out.println("Equipment contains pickaxe");
            return Equipment.itemAt(Equipment.Slot.MAIN_HAND).id();

        }
        else if (Inventory.stream().filtered(x -> x.name().contains("pickaxe")).first().valid())
        {
            System.out.println("Inventory contains pickaxe");
            return Inventory.stream().filtered(x -> x.name().contains("pick")).first().id();
        }
        else
        {
            System.out.println("ERROR!!! current equipped/in inventory pick can't be found -> currentPickaxeId set to 0");
            return 0;
        }
    }

    private static Pickaxe currentPickaxeToObject(int currentPickaxeId)
    {
        System.out.println("set current pickaxe to object");
        for (var i: Pickaxe.values())
        {
            if(i.pickaxeId == currentPickaxeId)
            {
                return i;
            }
        }
        return null;
    }
}
