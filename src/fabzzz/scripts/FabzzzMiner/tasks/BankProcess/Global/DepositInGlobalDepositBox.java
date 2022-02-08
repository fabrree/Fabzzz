package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global;

import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class DepositInGlobalDepositBox extends Task
{
    @Override
    public boolean activate()
    {
        System.out.println("DepositDepositBoxGLOBAL -> activate");

        return DepositBox.INSTANCE.getDepositBox().tile().distanceTo(Players.local().tile()) < 5 && Inventory.isFull();
    }

    @Override
    public void execute()
    {
        System.out.println("DepositDepositBoxGLOBAL -> execute -> Found deposit box");
        Condition.wait(() -> DepositBox.open(), 150, 10);
        if (DepositBox.opened())
        {
            Item pick = Inventory.stream().filtered(x -> x.name().contains("pick")).first();
            if(pick.valid())
            {
                DepositBox.depositAllExcept(DepositBox.stream().filtered(x -> x.name().contains("pick")).first().name());

            }
            else
            {
                DepositBox.depositInventory();
            }


            System.out.println("done depositing");
            DepositBox.close();
            System.out.println("done closing");
        }
    }
}
