package fabzzz.scripts.FabzzzMiner.tasks;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzMiner.Configuration.TILES_AWAY;

public class MineOre extends Task
{
    public static int[] OresToMine;

    @Override
    public boolean activate()
    {
        System.out.println("MineOre -> activate");
        return Objects.stream().within(TILES_AWAY).id(OresToMine).nearest().first().inViewport()
                && !Inventory.isFull()
                && (Equipment.itemAt(Equipment.Slot.MAIN_HAND).name().contains("pick")
                    || Inventory.stream().filtered(x -> x.name().contains("pick")).first().valid());
    }

    @Override
    public void execute()
    {
        System.out.println("MineOre -> execute");
        GameObject rock = Objects.stream().within(TILES_AWAY).id(OresToMine).nearest().first();
        if (rock.valid() && rock.inViewport()) {
            System.out.println("Mining ore");
            rock.interact("Mine");
            Condition.wait(() -> !rock.valid(), 150, 60);
        }
    }


}
