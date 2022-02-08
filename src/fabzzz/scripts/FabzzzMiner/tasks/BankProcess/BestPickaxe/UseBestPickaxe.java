package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.BestPickaxe;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;

import java.util.ArrayList;
import java.util.List;

public class UseBestPickaxe
{
    private static List<PickaxeModel> PICKAXES_WITH_ALL_INFO = new ArrayList<>(){{
    add(new PickaxeModel("Dragon pickaxe", 11920, 61, 60));
    add(new PickaxeModel("Rune pickaxe", 1275, 41, 40));
    add(new PickaxeModel("Adamant pickaxe", 1271, 31, 30));
    add(new PickaxeModel("Mithril pickaxe", 1273, 21, 20));
    add(new PickaxeModel("Black pickaxe", 12297, 11, 10));
    add(new PickaxeModel("Steel pickaxe", 1269, 5, 5));
    add(new PickaxeModel("Iron pickaxe", 1267, 1, 1));
    add(new PickaxeModel("Bronze pickaxe", 1265, 1, 1));
    }};


    public static void GetItNow()
    {
        var currentMiningLevel = Skill.Mining.realLevel();
        var currentAttackLevel = Skill.Attack.realLevel();
        int currentPickaxeId;
        PickaxeModel currentlyUsedPickaxe;

        System.out.println("get current equipped/in inventory pickaxe");
        currentPickaxeId = getCurrentPickaxeInInvenOrEquip();

        System.out.println("set current pickaxe to object");
        currentlyUsedPickaxe = currentPickaxeToObject(currentPickaxeId);

        System.out.println("Checking for a better pickaxe in bank");
        CheckForBetterPickaxeInBank(currentMiningLevel, currentAttackLevel, currentlyUsedPickaxe);
    }


    private static void CheckForBetterPickaxeInBank(int currentMiningLevel, int currentAttackLevel, PickaxeModel currentlyUsedPickaxe)
    {
        for (var i : PICKAXES_WITH_ALL_INFO)
        {
            if (currentMiningLevel > i.getMiningLevelRequired() && Bank.stream().id(i.getPickaxeId()).isNotEmpty() && currentlyUsedPickaxe.getMiningLevelRequired() < i.getMiningLevelRequired())
            {
                System.out.println("Found! withdrawing:" + i.getPickaxeName());
                Bank.withdraw(i.getPickaxeId(), 1);
                if(currentAttackLevel >= i.getAttackLevelRequired())
                {
                    System.out.println("going to wield item..");
                    int widgetId = 12;
                    int componentId = 113;
                    if(Widgets.widget(widgetId).component(componentId).visible()){
                        Widgets.widget(widgetId).component(componentId).click();
                        Condition.wait(()->Game.tab()==Game.Tab.INVENTORY, 150, 10);
                        Inventory.stream().id(i.getPickaxeId()).first().interact("Wield");
                        Widgets.widget(widgetId).component(componentId).click();
                        System.out.println("Depositing old pickaxe");
                        Condition.wait(() -> Bank.opened() , 100, 20);
                        Bank.depositInventory();
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

    private static PickaxeModel currentPickaxeToObject(int currentPickaxeId)
    {
        System.out.println("set current pickaxe to object");
        for (var i: PICKAXES_WITH_ALL_INFO)
        {
            if(i.getPickaxeId() == currentPickaxeId)
                return i;
        }
        return null;
    }


}

/*


//     public static List<PickaxeObjWithRequirements> pickaxes = new ArrayList<>();
//          pickaxes.add(new PickaxeObjWithRequirements("Dragon pickaxe", 11920, 61, 60));
//          pickaxes.add(new PickaxeObjWithRequirements("Rune pickaxe", 1275, 41, 40));
//          pickaxes.add(new PickaxeObjWithRequirements("Adamant pickaxe", 1271, 31, 30));
//          pickaxes.add(new PickaxeObjWithRequirements("Mithril pickaxe", 1273, 21, 20));
//          pickaxes.add(new PickaxeObjWithRequirements("Black pickaxe", 12297, 11, 10));
//          pickaxes.add(new PickaxeObjWithRequirements("Steel pickaxe", 1269, 5, 5));
//          pickaxes.add(new PickaxeObjWithRequirements("Iron pickaxe", 1267, 1, 1));
//          pickaxes.add(new PickaxeObjWithRequirements("Bronze pickaxe", 1265, 1, 1));



System.out.println("do we have a better pickaxe in the bank");
//        //do we have a better pickaxe in the bank
//        for (var i : PICKAXES_WITH_ALL_INFO)
//        {
//            if (currentMiningLevel > i.getMiningLevelRequired() && Bank.stream().id(i.getPickaxeId()).isNotEmpty()
//            && currentlyUsedPickaxe.getMiningLevelRequired() < i.getMiningLevelRequired())
//            {
//                System.out.println("Found! withdrawing:" + i.getPickaxeName());
//                Bank.withdraw(i.getPickaxeId(), 1);
//
//                if(currentAttackLevel >= i.getAttackLevelRequired())
//                {
//                    System.out.println("going to wield item..");
//                    int widgetId = 12;
//                    int componentId = 113;
//                    if(Widgets.widget(widgetId).component(componentId).visible()){
//                        Widgets.widget(widgetId).component(componentId).click();
//                        Condition.wait(()->Game.tab()==Game.Tab.INVENTORY, 150, 10);
//                        Inventory.stream().id(i.getPickaxeId()).first().interact("Wield");
//                        Widgets.widget(widgetId).component(componentId).click();
//                        System.out.println("Depositing old pickaxe");
//                        Condition.wait(() -> Bank.opened() , 100, 20);
//                        Bank.depositInventory();
//                    }
//                }
//            }
//        }



        //get current equipped/in inventory pickaxe
        System.out.println("get current equipped/in inventory pickaxe");
//        if(Equipment.itemAt(Equipment.Slot.MAIN_HAND).name().contains("pickaxe"))
//        {
//            System.out.println("Equipment contains pickaxe");
//            currentPickaxeId = Equipment.itemAt(Equipment.Slot.MAIN_HAND).id();
//        }
//        else if (Inventory.stream().filtered(x -> x.name().contains("pickaxe")).first().valid())
//        {
//            System.out.println("Inventory contains pickaxe");
//            currentPickaxeId = Inventory.stream().filtered(x -> x.name().contains("pick")).first().id();
//        }
//        else
//        {
//            System.out.println("ERROR!!! current equipped/in inventory pick can't be found -> currentPickaxeId set to 0");
//            currentPickaxeId = 0;
//        }
        //is now:
        //currentPickaxeId = getCurrentPickaxeInInvenOrEquip();





//        System.out.println("set current pickaxe to object");
//        //set current pickaxe to object
//        for (var i: pickaxes)
//        {
//            if(i.getPickaxeId() == currentPickaxeId)
//                currentlyUsedPickaxe = i;
//        }
        // is now:
//        currentlyUsedPickaxe =  currentPickaxeToObject(currentPickaxeId);



 */
