package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.selectedBank;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Area;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class WalkToBank extends Task {
    public static Area BANK;

    @Override
    public boolean activate() {
        System.out.println("WalkToBank -> activate");
        return Inventory.isFull() && !BANK.contains(Players.local());
    }

    @Override
    public void execute() {
        System.out.println("WalkToBank -> execute -> Walking to bank");
        Movement.builder(BANK.getRandomTile()).setRunMin(35).setRunMax(75).move();
        if(Condition.wait(() -> Players.local().inMotion(), 50, 20))
        {
            System.out.println("WalkToBank() -> execute -> inside if");
            Condition.wait(() -> !Players.local().inMotion(), 150, 40);
        }
    }


}
