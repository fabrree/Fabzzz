package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Components;
import org.powbot.api.rt4.Widgets;
import org.powbot.mobile.input.Keyboard;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;


public class SetUsername extends Task
{
    private static final int widgetId = 558;
    @Override
    public boolean activate()
    {
        return Components.stream().widget(widgetId).textContains("Set display name").isNotEmpty();
    }

    @Override
    public void execute()
    {
        System.out.println("Execute SetUsername with name " + USERNAME  );
        if(Components.stream().widget(widgetId).text("*").isNotEmpty())
        {
            System.out.println("Click on textbox");
            Components.stream().widget(widgetId).text("*").viewable().first().click();
            System.out.println("Typing..");
            Keyboard.INSTANCE.type(USERNAME);
            if(Condition.wait(() -> Components.stream().widget(widgetId).text(USERNAME).viewable().isNotEmpty(), 100, 20))
            {
                System.out.println("In if text contains" + USERNAME);

                //click look up name
                System.out.println("before if look up name");
                if(Components.stream().widget(widgetId).text("Look up name").viewable().isNotEmpty())
                {
                    System.out.println("Clicking on look up name");
                    Components.stream().widget(widgetId).text("Look up name").viewable().first().click();
                }
                //please wait
                Condition.wait(() -> Components.stream().widget(widgetId).text("Set name").viewable().isNotEmpty(), 100, 20);
                if(Components.stream().widget(widgetId).text("Set name").viewable().isNotEmpty())
                {
                    System.out.println("Set name gevonden! (used given name)");
                    Components.stream().widget(widgetId).text("Set name").viewable().first().click();
                }
                else while(Components.stream().widget(widgetId).textContains("Sorry").viewable().isNotEmpty())
                {
                    System.out.println("Username is not available - going to select a random one");
                    var num = r.nextInt(3); //option 0,1,2 -> 0 inclusive -> bound exclusive

                    if(num == 0)
                    {
                        System.out.println("Number 0 chosen -> first name");
                        Condition.wait(() -> Widgets.widget(widgetId).component(15).visible(), 50, 50);
                        Widgets.widget(widgetId).component(15).click();
                    }
                    if(num == 1)
                    {
                        System.out.println("Number 1 chosen -> first name");
                        Condition.wait(() -> Widgets.widget(widgetId).component(16).visible(), 50, 50);
                        Widgets.widget(widgetId).component(16).click();
                    }
                    if(num == 2)
                    {
                        System.out.println("Number 2 chosen -> first name");
                        Condition.wait(() -> Widgets.widget(widgetId).component(17).visible(), 50, 50);
                        Widgets.widget(widgetId).component(17).click();
                    }

                    if(Condition.wait(() -> Components.stream().widget(widgetId).textContains("Great!").isNotEmpty(), 50, 50))
                    {
                        if(Components.stream().widget(widgetId).text("Set name").isNotEmpty())
                        {
                            System.out.println("Click Set name (random name used)");
                            Components.stream().widget(widgetId).text("Set name").first().click();
                        }
                    }

                }
            }
        }
    }
}
