package fabzzz.scripts.FabzzzTutorialIsland;

public enum CharacterDesign
{
    HEAD("Head", 13),
    JAW("Jaw", 17),
    TORSO("Torso", 21),
    ARMS("Arms", 25),
    HANDS("Hands", 19),
    LEGS("Legs", 33),
    FEET("Feet", 37),
    HAIR_COLOUR("HairColour", 44),
    TORSO_COLOUR("TorsoColour", 48),
    LEGS_COLOUR("LegsColour", 52),
    FEET_COLOUR("FeetColour", 56),
    SKIN_COLOUR("SkinColour", 60);

    public final String name;
    public final int arrowRight;

    CharacterDesign(String name, int arrowRight)
    {
        this.name = name;
        this.arrowRight = arrowRight;
    }

    public String getName()
    {
        return name;
    }

    public int getArrowRight()
    {
        return arrowRight;
    }
}
