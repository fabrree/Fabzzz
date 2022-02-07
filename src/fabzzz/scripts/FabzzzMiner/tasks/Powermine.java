package fabzzz.scripts.FabzzzMiner.tasks;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

public class Powermine extends Task{
    @Override
    public boolean activate() {
        System.out.println("Powermine -> activate");
        return Inventory.isFull();
    }

    @Override
    public void execute()
    {
        System.out.println("Powermine -> execute");
        Inventory.stream().forEach(i ->
            {
                System.out.println("In for each...");
                if (i.name().toLowerCase().contains("pick"))
                    return;
                i.interact("Drop");
                Condition.sleep(Random.nextGaussian(50,150,30));
            }
        );
    }
}
