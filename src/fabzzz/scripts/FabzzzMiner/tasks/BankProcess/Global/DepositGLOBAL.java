package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global;

import fabzzz.scripts.FabzzzMiner.tasks.Task;
import fabzzz.scripts.FabzzzMiner.tasks.BankProcess.BestPickaxe.UseBestPickaxe;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

import static fabzzz.scripts.FabzzzMiner.Configuration.USE_BEST_PICKAXE_BANK;

public class DepositGLOBAL extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("Deposit -> activate");
        return Bank.nearest().tile().distanceTo(Players.local()) < 5 && Inventory.isFull();
    }

    @Override
    public void execute()
    {

        System.out.println("Deposit -> execute -> Found bank");
        if (Bank.inViewport())
            Condition.wait(() -> Bank.open(), 50, 10);
        if (Bank.opened())
        {
            Bank.depositAllExcept(Inventory.stream().filtered(x -> x.name().contains("pick")).first().name());
            if(USE_BEST_PICKAXE_BANK)
                UseBestPickaxe.GetItNow();
            Bank.close();
        }
    }
}
