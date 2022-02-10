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
    private static boolean GENDER_SELECTED = false;
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
        if(r.nextBoolean() && !GENDER_SELECTED)
        {
            if(Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Male").isNotEmpty())
            {
                System.out.println("Selecting male");
                Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Male").viewable().first().click();
                IS_FEMALE = false;
                GENDER_SELECTED = true;
            }
        }
        else
        {
            if(Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Female").isNotEmpty())
            {
                System.out.println("Selecting female");
                Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Female").viewable().first().click();
                IS_FEMALE = true;
                GENDER_SELECTED = true;
            }
        }

//        if (IS_FEMALE)
//        {
//            CharacterDesign.remove(1); //make it not click on the beard thingy if is female is true
//        }

        for (var designElement : CharacterDesign.values())
        {
            var arrowRightInAppearance = Widgets.widget(APPEARENCE_MENU_WIDGET).component(designElement.getArrowRight());
            if(arrowRightInAppearance.visible()) // does visible check if it is clickable?
            {
                int amountToClick = r.nextInt(10);
                for (int j = 0; j < amountToClick; j++)
                {
                    arrowRightInAppearance.click();
                    System.out.println("item=" + designElement.getName() + j + " amountToclick= " + amountToClick);
                }
            }
        }

        if(Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Confirm").isNotEmpty())
        {
            System.out.println("confirming appearance");
            if(Components.stream().widget(APPEARENCE_MENU_WIDGET).text("Confirm").first().click())
            {
                Condition.wait(() -> Components.stream().widget(TEXT_SCREEN).textContains("Getting started").isNotEmpty(), 100, 80);
            }
        }
    }
}
