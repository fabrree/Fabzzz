package fabzzz.scripts.FabzzzMiner.tasks.BankProcess.BestPickaxe;

public class PickaxeObjWithRequirements
{
    private String pickaxeName;
    private int pickaxeId;
    private int miningLevelRequired;
    private int attackLevelRequired;

    public PickaxeObjWithRequirements(String name, int pickaxeId, int miningLevelRequired, int attackLevelRequired )
    {
        this.pickaxeName = name;
        this.pickaxeId = pickaxeId;
        this.miningLevelRequired = miningLevelRequired;
        this.attackLevelRequired = attackLevelRequired;
    }

    public String getPickaxeName()
    {
        return pickaxeName;
    }
    public int getPickaxeId()
    {
        return pickaxeId;
    }

    public int getMiningLevelRequired()
    {
        return miningLevelRequired;
    }

    public int getAttackLevelRequired()
    {
        return attackLevelRequired;
    }

}
