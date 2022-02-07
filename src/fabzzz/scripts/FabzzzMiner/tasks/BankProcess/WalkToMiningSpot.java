package fabzzz.scripts.FabzzzMiner.tasks.BankProcess;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import static fabzzz.scripts.FabzzzMiner.Configuration.*;

public class WalkToMiningSpot extends Task {
    @Override
    public boolean activate()
    {
        System.out.println("WalkToMiningSpot -> activate");
        return !Inventory.isFull();
    }

    @Override
    public void execute()
    {
        System.out.println("WalkToMiningSpot -> execute -> Walking to mining spot");
        Movement.builder(STARTING_TILE).setRunMin(35).setRunMax(75).setForceWeb(true).move();

        if(Condition.wait(() -> Players.local().inMotion(), 50, 20))
        {
            System.out.println("WalkToMiningSpot -> execute -> inside if");
            Condition.wait(() -> !Players.local().inMotion(), 150, 40);
        }

    }
}
