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
        System.out.println("Set appearance -> activate");
        return ChatContains("Before you get started, you'll need to set the appearance of your character.");
    }

    @Override
    public void execute()
    {
        System.out.println("Set appearance -> execute");
        if(!GENDER_SELECTED)
        {
            if(r.nextBoolean())
            {
                //we do not have to select since we are male by default.
                IS_FEMALE = false;
                GENDER_SELECTED = true;
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
        }

        for (var designElement : CharacterDesign.values())
        {
            System.out.println("in designelement for loop");
            if(designElement.getName().equals("Jaw") && IS_FEMALE)
            {
                System.out.println("Skipping 'JAW' because we are a female");
                continue;
            }
            var arrowRightInAppearance = Widgets.widget(APPEARENCE_MENU_WIDGET).component(designElement.getArrowRight());

            if(Condition.wait(() -> arrowRightInAppearance.visible(), 50, 20))
            {
                System.out.println("Inside if...");
                int randomIntBelow10 = r.nextInt(10);
                for (int amountToClick = 0; amountToClick < randomIntBelow10; amountToClick++)
                {
                    arrowRightInAppearance.click();
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

    @Override
    public String status()
    {
        return "Setting appearance";
    }
}
