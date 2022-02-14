package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global.Bank;

import fabzzz.scripts.FabzzzMiner.Utils.Pickaxe;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

import static fabzzz.scripts.FabzzzMiner.Utils.Configuration.USE_BEST_PICKAXE_BANK;

public class DepositInGlobalBank extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("Deposit -> activate");
        return Bank.nearest().tile().distanceTo(Players.local()) < 7 && Inventory.isFull();
    }

    @Override
    public void execute()
    {

        System.out.println("Deposit -> execute -> Found bank");
        if (Bank.inViewport())
        {
            Condition.wait(() -> Bank.open(), 50, 10);
        }
        else
        {
            System.out.println("Depositbox not in viewport... turning camera");
            Camera.turnTo(Bank.nearest());
        }

        if (Bank.opened())
        {
            Bank.depositAllExcept(Inventory.stream().filtered(x -> x.name().contains("pick")).first().name());
            if(USE_BEST_PICKAXE_BANK)
            {
                Pickaxe.CheckForBetterPickaxe();
            }
            Bank.close();
        }
    }
}
