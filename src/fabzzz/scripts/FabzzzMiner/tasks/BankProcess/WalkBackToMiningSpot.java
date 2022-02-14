package fabzzz.scripts.FabzzzMiner.tasks.BankProcess;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzMiner.Utils.Configuration.*;

public class WalkBackToMiningSpot extends Task {
    @Override
    public boolean activate()
    {
        System.out.println("WalkToMiningSpot -> activate");
        return !Inventory.isFull() && !STARTING_TILE.equals(Players.local().tile()) ;
    }

    @Override
    public void execute()
    {
        System.out.println("WalkToMiningSpot -> execute -> Walking to mining spot");
        Movement.moveTo(STARTING_TILE);
    }
}
