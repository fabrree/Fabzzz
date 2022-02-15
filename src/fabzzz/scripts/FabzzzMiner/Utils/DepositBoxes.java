package fabzzz.scripts.FabzzzMiner.Utils;

import org.powbot.api.Tile;
import org.powbot.api.rt4.Players;

public enum DepositBoxes
{
    CRAFTING_GUILD(new Tile( 2931, 3284, 0)),
    DRAYNOR(new Tile(3093, 3243, 0)),
    DUEL_ARENA(new Tile(3382, 3268, 0)),
    EDGEVILLe(new Tile(3093, 3495, 0)),
    FALADOR_WEST(new Tile(2945, 3370, 0)),
    FALADOR_EAST(new Tile(3017, 3356, 0)),
    MINING_GUILD(new Tile(3014, 9720, 0)),
    SEERS_VILLAGE(new Tile(2726, 3491, 0)),
    PORT_KHAZARD(new Tile(2660, 3159, 0)),
    YANILLE(new Tile(2612, 3092, 0));

    private Tile depositBox;
    DepositBoxes(Tile depositBox)
    {
        this.depositBox = depositBox;
    }

    public static Tile getClosestDepositBox()
    {
        System.out.println("getClosestDepositBox!");
        System.out.println("A");
        double closestLocation = Double.MAX_VALUE;
        Tile finalLocation = null;
        for (var location: DepositBoxes.values())
        {
            var distance = Players.local().tile().distanceTo(location.getDepositBox());

            if (distance< closestLocation)
            {
                System.out.println("New shortest distance!");
                closestLocation = distance;
                finalLocation = location.depositBox;
            }
        }
        System.out.println("Final location: " + finalLocation);
        return finalLocation;
    }

    public Tile getDepositBox()
    {
        return depositBox;
    }
}
