package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.selectedBank;
import fabzzz.scripts.FabzzzMiner.tasks.Task;
import org.powbot.api.Area;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;


public class WalkToSelectedBank extends Task {
    public static Area BANK;

    @Override
    public boolean activate() {
        System.out.println("WalkToBank -> activate");
        return Inventory.isFull() && !BANK.contains(Players.local());
    }

    @Override
    public void execute() {
        System.out.println("WalkToBank -> execute -> Walking to bank");
        Movement.moveTo(BANK.getRandomTile());
        Movement.builder(BANK.getRandomTile()).setRunMin(35).setRunMax(75).move();
    }


}
