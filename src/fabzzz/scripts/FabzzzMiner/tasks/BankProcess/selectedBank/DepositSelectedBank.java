package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.selectedBank;
import fabzzz.scripts.FabzzzMiner.Utils.Pickaxe;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzMiner.Utils.Configuration.USE_BEST_PICKAXE_BANK;
import static fabzzz.scripts.FabzzzMiner.tasks.BankProcess.selectedBank.WalkToSelectedBank.BANK;

public class DepositSelectedBank extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("Deposit -> activate");
        return BANK.contains(Players.local()) && Inventory.isFull();
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
            System.out.println("Bank not in viewport... turning camera");
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
