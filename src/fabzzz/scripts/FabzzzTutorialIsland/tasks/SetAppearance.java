package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.CharacterDesign;
import fabzzz.scripts.FabzzzTutorialIsland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Components;
import org.powbot.api.rt4.Widgets;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;


public class SetAppearance extends Task
{

    private static boolean IS_FEMALE;
    private static final int APPEARENCE_MENU_WIDGET = 679;
    @Override
    public boolean activate()
    {
        System.out.println("Set appearance activate");
        return Components.stream().widget(TEXT_SCREEN).textContains("appearance").isNotEmpty();
    }

    @Override
    public void execute()
    {

        //add a check here? do we run this everytime? i hope not.
        if(r.nextBoolean()) //random gender
        {
            if(Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Male").isNotEmpty())
            {
                System.out.println("Selecting male");
                Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Male").viewable().first().click();
                IS_FEMALE = false;
            }
        }
        else
        {
            if(Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Female").isNotEmpty())
            {
                System.out.println("Selecting female");
                Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Female").viewable().first().click();
                IS_FEMALE = true;
            }
        }

        //design
//        List<CharacterDesign> characterDesigns = new ArrayList<>(){{
//            add(new CharacterDesign("Head", 13));
//            add(new CharacterDesign("Jaw", 17));
//            add(new CharacterDesign("Torso", 21));
//            add(new CharacterDesign("Arms", 25));
//            add(new CharacterDesign("Hands", 19));
//            add(new CharacterDesign("Legs", 33));
//            add(new CharacterDesign("Feet", 37));
//            add(new CharacterDesign("HairColour", 44));
//            add(new CharacterDesign("TorsoColour", 48));
//            add(new CharacterDesign("LegsColour", 52));
//            add(new CharacterDesign("FeetColour", 56));
//            add(new CharacterDesign("SkinColour", 60));
//        }};

//        if (IS_FEMALE)
//        {
//            CharacterDesign.remove(1); //make it not click on the beard thingy if is female is true
//        }


        for (var i : CharacterDesign.values())
        {
            if(Widgets.widget(APPEARENCE_MENU_WIDGET).component(i.arrowRight).visible())
            {
                int amountToClick = r.nextInt(10);
                for (int j = 0; j < amountToClick; j++)
                {
                    Widgets.widget(APPEARENCE_MENU_WIDGET).component(i.getArrowRight()).click();
                    System.out.println("item=" + i.getName() + j + " amountToclick= " + amountToClick);
                }
            }
        }
        //confirm

        if(Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Confirm").isNotEmpty())
        {
            System.out.println("confirming appearance");
            Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Confirm").first().click();
            Condition.wait(() -> Components.stream().widget(TEXT_SCREEN).textContains("Getting started").isNotEmpty(), 100, 80);
        }
    }
}
