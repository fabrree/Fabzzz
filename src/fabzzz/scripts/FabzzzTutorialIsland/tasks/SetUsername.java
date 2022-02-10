package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Component;
import org.powbot.api.rt4.Components;
import org.powbot.api.rt4.Widgets;
import org.powbot.api.rt4.stream.widget.ComponentStream;
import org.powbot.mobile.input.Keyboard;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;


public class SetUsername extends Task
{
    private static final int setDisplayNameScreen = 558;
    private static final int textbox = 12;
    //private static ComponentStream a = Components.stream().widget(setDisplayNameScreen);
    @Override
    public boolean activate()
    {
        return Components.stream().widget(setDisplayNameScreen).textContains("Set display name").isNotEmpty();
    }

    @Override
    public void execute()
    {
        System.out.println("Execute -> SetUsername with name: " + USERNAME);
        if (Components.stream(setDisplayNameScreen).text("*").first().text().equals("*"))
        {
            System.out.println("Click on text box");
            Components.stream().widget(setDisplayNameScreen).text("*").viewable().first().click();
            System.out.println("Typing..");
            Keyboard.INSTANCE.type(USERNAME);
            Condition.wait(() -> Components.stream(setDisplayNameScreen).text("*").first().text().equals(USERNAME + "*"), 50, 30);
        }
        else if (TextContainsExactUsername())
        {
            System.out.println("Username is typed in the chat box...!");


            if(Components.stream().widget(setDisplayNameScreen).text("Look up name").viewable().isNotEmpty()
                    && Components.stream().widget(setDisplayNameScreen).text("Great").viewable().isEmpty()
                    && Components.stream().widget(setDisplayNameScreen).text("Sorry").viewable().isEmpty())
            {
                System.out.println("Clicking on look up name");
                Components.stream().widget(setDisplayNameScreen).text("Look up name").viewable().first().click();
                Condition.wait(() -> Components.stream().widget(setDisplayNameScreen).text("Sorry").viewable().isNotEmpty()
                        ||Components.stream().widget(setDisplayNameScreen).text("Great").viewable().isNotEmpty(), 50, 60 );
            }
            else if (Components.stream().widget(setDisplayNameScreen).text("Great").viewable().isNotEmpty())
            {
                System.out.println("Great -> username is available!");
                System.out.println("Setting username!");
            }
            else if(Components.stream().widget(setDisplayNameScreen).text("Sorry").viewable().isNotEmpty())
            {
                System.out.println("Sorry -> username is not available");
                ClickRandomGeneratedUsername();
            }
        }
        else
        {
            System.out.println("Deleting typed text..");
            if(ClickOnTextBox())
            {
                DeletypedText();
            }
        }
    }
    private boolean TextContainsExactUsername()
    {
        return Widgets.widget(setDisplayNameScreen).component(textbox).text().equals(USERNAME)
                || Widgets.widget(setDisplayNameScreen).component(textbox).text().equals(USERNAME + "*");
    }

    private boolean ClickOnTextBox()
    {
        if(Widgets.widget(setDisplayNameScreen).component(textbox).inViewport())
        {
            Widgets.widget(setDisplayNameScreen).component(textbox).click();
            Condition.wait(() -> Components.stream().widget(setDisplayNameScreen).textContains("*").viewable().isNotEmpty(), 50, 20);
            if(Components.stream().widget(setDisplayNameScreen).textContains("*").viewable().isNotEmpty())
            {
                return true;
            }
        }
        return false;
    }

    private void DeletypedText()
    {
        while(!Components.stream(setDisplayNameScreen).text("*").first().text().equals("*"))
        {
            int deleteButton = 67; //android universal delete button
            System.out.println("Pressing deletebutton");
            Keyboard.INSTANCE.pressKey(deleteButton);
            System.out.println("Name is: " + Components.stream(setDisplayNameScreen).first().text() );
        }
    }

    private void ClickRandomGeneratedUsername()
    {
        var randomNumber = r.nextInt(3); //option 0,1,2 -> 0 inclusive -> bound exclusive
        int generatedNameWidgetId;
        if(randomNumber == 0)
        {
            generatedNameWidgetId = 15;
        }
        else if(randomNumber == 1)
        {
            generatedNameWidgetId = 16;
        }
        else
        {
            generatedNameWidgetId = 17;
        }
        Condition.wait(() -> Widgets.widget(setDisplayNameScreen).component(generatedNameWidgetId).visible(), 50, 50);
        Widgets.widget(setDisplayNameScreen).component(generatedNameWidgetId).click();
        Condition.wait(() -> Components.stream().widget(setDisplayNameScreen).text("Great").viewable().isNotEmpty(), 100, 30);
        if(Components.stream().widget(setDisplayNameScreen).text("Great").viewable().isNotEmpty())
        {
            System.out.println("Setting USERNAME to " + Widgets.widget(setDisplayNameScreen).component(generatedNameWidgetId).text());
            USERNAME = Widgets.widget(setDisplayNameScreen).component(generatedNameWidgetId).text();
        }
    }
}

//deleting text func
    //            int deleteButton = 67; //android universal? delete button
    //            System.out.println("Deleting typed text..");
    //            Components.stream().widget(setDisplayNameScreen).textContains(USERNAME).viewable().first().click();
    //            //Components.stream(setDisplayNameScreen).text("*").first().text().equals("*")
    //            while(Components.stream(setDisplayNameScreen).text("*").first().text().equals("*") || Components.stream(setDisplayNameScreen).textContains("*").isNotEmpty())
    //            {
    //                System.out.println("Pressing deletebutton");
    //                Keyboard.INSTANCE.pressKey(deleteButton);
    //                System.out.println("Name is: " + Components.stream(setDisplayNameScreen).first().text() );
    //            }


//        if(Components.stream().widget(setDisplayNameScreen).text("*").isNotEmpty())
//
//        if(Condition.wait(() -> Components.stream().widget(setDisplayNameScreen).text(USERNAME).viewable().isNotEmpty(), 100, 20))
//        {
//            System.out.println("In if text contains" + USERNAME);
//
//            //click look up name
//            System.out.println("before if look up name");
//            if(Components.stream().widget(setDisplayNameScreen).text("Look up name").viewable().isNotEmpty())
//            {
//                System.out.println("Clicking on look up name");
//                //Components.stream().widget(widgetId).text("Look up name").viewable().first().click();
//            }
//
//            if(Condition.wait(() -> Components.stream().widget(setDisplayNameScreen).text("Set name").viewable().isNotEmpty(), 100, 20))
//            {
//                System.out.println("Set name found! (used given name)");
//                Components.stream().widget(setDisplayNameScreen).text("Set name").viewable().first().click();
//            }
//            else while(Components.stream().widget(setDisplayNameScreen).textContains("Sorry").viewable().isNotEmpty())
//            {
//                System.out.println("Username is not available - going to select a random one");
//                var num = r.nextInt(3); //option 0,1,2 -> 0 inclusive -> bound exclusive
//
//                if(num == 0)
//                {
//                    System.out.println("Number 0 chosen -> first name");
//                    Condition.wait(() -> Widgets.widget(setDisplayNameScreen).component(15).visible(), 50, 50);
//                    Widgets.widget(setDisplayNameScreen).component(15).click();
//                }
//                if(num == 1)
//                {
//                    System.out.println("Number 1 chosen -> first name");
//                    Condition.wait(() -> Widgets.widget(setDisplayNameScreen).component(16).visible(), 50, 50);
//                    Widgets.widget(setDisplayNameScreen).component(16).click();
//                }
//                if(num == 2)
//                {
//                    System.out.println("Number 2 chosen -> first name");
//                    Condition.wait(() -> Widgets.widget(setDisplayNameScreen).component(17).visible(), 50, 50);
//                    Widgets.widget(setDisplayNameScreen).component(17).click();
//                }





//                    if(Condition.wait(() -> Components.stream().widget(widgetId).textContains("Great!").isNotEmpty(), 50, 50))
//                    {
//                        if(Components.stream().widget(widgetId).text("Set name").isNotEmpty())
//                        {
//                            System.out.println("Click Set name (random name used)");
//                            Components.stream().widget(widgetId).text("Set name").first().click();
//                        }
//                    }

//            }
//        }



