package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global.Bank;

import fabzzz.scripts.FabzzzMiner.Utils.Pickaxe;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzMiner.Utils.Configuration.USE_BEST_PICKAXE_BANK;

public class DepositInGlobalBank extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("Deposit -> activate");
        return Bank.nearest().tile().distanceTo(Players.local()) < 8 && Inventory.isFull();
    }

    @Override
    public void execute()
    {

        if(Bank.inViewport())
        {
            if (Bank.open())
            {
                Bank.depositAllExcept(Inventory.stream().filtered(x -> x.name().contains("pick")).first().name());
                if(USE_BEST_PICKAXE_BANK)
                {
                    Pickaxe.CheckForBetterPickaxe();
                }
                Bank.close();
            }
        }
        else
        {
            System.out.println("Depositbox not in viewport... turning camera");
            Camera.turnTo(Bank.nearest());
        }
    }
}
