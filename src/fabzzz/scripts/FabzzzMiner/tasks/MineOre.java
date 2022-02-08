package fabzzz.scripts.FabzzzMiner.tasks;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import static fabzzz.scripts.FabzzzMiner.Configuration.TILES_AWAY;
import static fabzzz.scripts.FabzzzMiner.Configuration.TurnCamera;

public class MineOre extends Task
{
    public static int[] OresToMine;
    private GameObject rock;

    public boolean activate() {
        System.out.println("MineOre -> activate");
        rock = Objects.stream(TILES_AWAY, GameObject.Type.INTERACTIVE).id(OresToMine).nearest().first();
        return rock.inViewport() && !Inventory.isFull()
                && (Equipment.itemAt(Equipment.Slot.MAIN_HAND).name().contains("pick")
                    || Inventory.stream().filtered(x -> x.name().contains("pick")).first().valid());
    }

    @Override
    public void execute()
    {
        System.out.println("MineOre -> execute");
        if (rock.valid()) {
            if(rock.inViewport())
            {
                System.out.println("Mining ore");
                if(rock.interact("Mine"))
                {
                    Condition.wait(() -> !rock.valid(), 150, 90);
                }
                else
                {
                    System.out.println("Couldn't interact with the ore to mine... turning camera!");
                    TurnCamera();
                }
            }
            else
            {
                System.out.println("Rock not in viewport... turning camera");
                TurnCamera();
            }
        }
    }




}
