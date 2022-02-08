package fabzzz.scripts.FabzzzMiner.tasks;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;

public class DropOre extends Task{
    @Override
    public boolean activate() {
        System.out.println("Powermine -> activate");
        return Inventory.isFull();
    }

    @Override
    public void execute()
    {
        System.out.println("Powermine -> execute");
        Inventory.stream().filtered(x -> !x.name().contains("pick")).forEach(i ->
            {
                i.interact("Drop");
                Condition.sleep(Random.nextGaussian(50,150,30));
            }
        );
    }
}
