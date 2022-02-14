package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.Global.DepositBox;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzMiner.Utils.DepositBoxes.getClosestDepositBox;


public class WalkToGlobalDespositBox extends Task {

    @Override
    public boolean activate() {
        System.out.println("WalkToDepositBoxGLOBAL -> activate");
        return !(DepositBox.INSTANCE.getDepositBox().tile().distanceTo(Players.local().tile()) < 5) && Inventory.isFull();

    }

    @Override
    public void execute() {
        System.out.println("WalkToDepositBoxGLOBAL -> execute -> Walking to depositbox");
        var depositBox = getClosestDepositBox();
        Movement.moveTo(depositBox);
    }


}
