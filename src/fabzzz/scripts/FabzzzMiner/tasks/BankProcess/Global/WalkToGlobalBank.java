package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;


public class WalkToGlobalBank extends Task {


    @Override
    public boolean activate() {
        System.out.println("WalkToBankGLOBAL -> activate");
        return !(Bank.nearest().tile().distanceTo(Players.local()) < 5) && Inventory.isFull();
    }

    @Override
    public void execute() {
        System.out.println("WalkToBankGLOBAL -> execute -> Walking to bank");
        Movement.moveToBank();
        if(Condition.wait(() -> Players.local().inMotion(), 50, 20))
        {
            System.out.println("WalkToBank() -> execute -> inside if");
            Condition.wait(() -> !Players.local().inMotion(), 150, 40);
        }
    }


}
