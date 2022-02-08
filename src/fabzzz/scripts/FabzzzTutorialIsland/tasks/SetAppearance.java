package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.CharacterDesign;
import fabzzz.scripts.FabzzzTutorialIsland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Components;
import org.powbot.api.rt4.Widgets;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;
import java.util.ArrayList;
import java.util.List;


public class SetAppearance extends Task
{
    static boolean icandothis = true;
    private static boolean IS_FEMALE;
    private static final int APPEARENCE_MENU_WIDGET = 679;
    @Override
    public boolean activate()
    {
        System.out.println("Set appearance activate");
        return icandothis && Components.stream().widget(TEXT_SCREEN).textContains("appearance").isNotEmpty();
    }

    @Override
    public void execute()
    {

        //random gender
        if(r.nextBoolean())
        {
            if(Condition.wait(() -> Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Male").isNotEmpty(), 50, 50))
            {
                System.out.println("Selecting male");
                Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Male").viewable().first().click();
                IS_FEMALE = false;
            }
        }
        else
        {
            if(Condition.wait(() -> Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Female").isNotEmpty(), 50, 50))
            {
                System.out.println("Selecting female");
                Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Female").viewable().first().click();
                IS_FEMALE = true;
            }
        }

        //design
        List<CharacterDesign> characterDesigns = new ArrayList<>(){{
            add(new CharacterDesign("Head", 13));
            add(new CharacterDesign("Jaw", 17));
            add(new CharacterDesign("Torso", 21));
            add(new CharacterDesign("Arms", 25));
            add(new CharacterDesign("Hands", 19));
            add(new CharacterDesign("Legs", 33));
            add(new CharacterDesign("Feet", 37));
            add(new CharacterDesign("HairColour", 44));
            add(new CharacterDesign("TorsoColour", 48));
            add(new CharacterDesign("LegsColour", 52));
            add(new CharacterDesign("FeetColour", 56));
            add(new CharacterDesign("SkinColour", 60));
        }};

        if (IS_FEMALE)
            characterDesigns.remove(1);

        for (var i : characterDesigns)
        {
            if(Widgets.widget(APPEARENCE_MENU_WIDGET).component(i.getArrowRight()).visible())
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
        if(Condition.wait(() -> Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Confirm").isNotEmpty(), 50, 50))
        {
            System.out.println("confirming appearance");
            Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Confirm").first().click();
            Condition.wait(() -> Components.stream().widget(TEXT_SCREEN).textContains("Getting started").isNotEmpty(), 100, 80);
        }

        icandothis = false;
    }
}
