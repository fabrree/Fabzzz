package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class WalkToGlobalDespositBox extends Task {

    @Override
    public boolean activate() {
        System.out.println("WalkToDepositBoxGLOBAL -> activate");
        return !(DepositBox.INSTANCE.getDepositBox().tile().distanceTo(Players.local().tile()) < 5) && Inventory.isFull();

    }

    @Override
    public void execute() {
        System.out.println("WalkToDepositBoxGLOBAL -> execute -> Walking to depositbox");
        Movement.moveTo(DepositBox.INSTANCE.getDepositBox().tile());
        if(Condition.wait(() -> Players.local().inMotion(), 50, 20))
        {
            System.out.println("WalkToDepositBoxGLOBAL -> execute -> inside if");
            Condition.wait(() -> !Players.local().inMotion(), 150, 40);
        }
    }


}
