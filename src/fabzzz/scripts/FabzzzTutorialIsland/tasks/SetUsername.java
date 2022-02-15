package fabzzz.scripts.FabzzzTutorialIsland.tasks;

import fabzzz.scripts.FabzzzTutorialIsland.Task;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Components;
import org.powbot.api.rt4.Widgets;
import org.powbot.api.rt4.stream.widget.ComponentStream;
import org.powbot.mobile.input.Keyboard;
import static fabzzz.scripts.FabzzzTutorialIsland.Util.Configurations.*;


public class SetUsername extends Task
{
    private static final int setDisplayNameScreen = 558;
    private static final int textbox = 12;

    @Override
    public boolean activate()
    {
        return Components.stream(setDisplayNameScreen).textContains("Set display name").isNotEmpty();
    }

    @Override
    public void execute()
    {
        System.out.println("Execute -> SetUsername with name: " + USERNAME);
        if (Components.stream(setDisplayNameScreen).text("*").first().text().equals("*"))
        {
            System.out.println("Click on username text box");
            if(Components.stream(setDisplayNameScreen).text("*").viewable().first().click())
            {
                System.out.println("Typing username...");
                Keyboard.INSTANCE.type(USERNAME);
                Condition.wait(() -> Components.stream(setDisplayNameScreen).text("*").first().text().equals(USERNAME + "*"), 50, 30);
            }
        }
        else if (TextContainsExactUsername())
        {

            if(Components.stream(setDisplayNameScreen).text("Look up name").first().visible() && Components.stream(setDisplayNameScreen).text("Great").viewable().isEmpty()
                    && Components.stream(setDisplayNameScreen).text("Sorry").viewable().isEmpty())
            {
                System.out.println("Clicking on look up name");
                Components.stream(setDisplayNameScreen).text("Look up name").viewable().first().click();
                Condition.wait(() -> Components.stream(setDisplayNameScreen).text("Sorry").viewable().isNotEmpty()
                        ||Components.stream(setDisplayNameScreen).text("Great").viewable().isNotEmpty(), 50, 60 );
            }
            else if (Components.stream(setDisplayNameScreen).text("Great").viewable().isNotEmpty()
                    && TextContainsExactUsername())
            {
                System.out.println("Great -> username is available!");
                System.out.println("Setting username!");
                if(Components.stream(setDisplayNameScreen).text("Set name").viewable().isNotEmpty())
                {
                    Components.stream(setDisplayNameScreen).text("Set name").first().click();
                    System.out.println("Waiting for appearance menu to open..");
                    Condition.wait(() -> Components.stream(TEXT_SCREEN).textContains("appearance").isNotEmpty(), 500, 4);
                }
            }
            else if(Components.stream(setDisplayNameScreen).text("Sorry").viewable().isNotEmpty())
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
                DeleteTypedText();
            }
        }
    }

    private boolean TextContainsExactUsername()
    {
        var widget = Widgets.widget(setDisplayNameScreen).component(textbox).text();
        return widget.equals(USERNAME) || widget.equals(USERNAME + "*");
    }

    private boolean ClickOnTextBox()
    {
        var widget = Widgets.widget(setDisplayNameScreen).component(textbox);
        if(widget.inViewport())
        {
            widget.click();
            var textContainingWidget = Components.stream().widget(setDisplayNameScreen).textContains("*").viewable().isNotEmpty();
            Condition.wait(() -> textContainingWidget , 50, 20);
            return textContainingWidget;
        }
        return false;
    }

    private void DeleteTypedText()
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
        if( Widgets.widget(setDisplayNameScreen).component(generatedNameWidgetId).visible())
        {
            Widgets.widget(setDisplayNameScreen).component(generatedNameWidgetId).click();
            Condition.wait(() -> Components.stream().widget(setDisplayNameScreen).text("Great").viewable().isNotEmpty(), 100, 30);
            if(Components.stream().widget(setDisplayNameScreen).text("Great").viewable().isNotEmpty())
            {
                System.out.println("Setting new USERNAME");
                USERNAME = Widgets.widget(setDisplayNameScreen).component(generatedNameWidgetId).text();
            }
        }
    }
    @Override
    public String status()
    {
        return "Setting username";
    }
}