package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

public class ResultDisplayTest extends GuiUnitTest {

    private static final NewResultAvailableEvent NEW_RESULT_EVENT_STUB_SUCCESS =
            new NewResultAvailableEvent("Stub", true);
    private static final NewResultAvailableEvent NEW_RESULT_EVENT_STUB_ERROR =
            new NewResultAvailableEvent("Stub", false);

    private ResultDisplayHandle resultDisplayHandle;
    private List<String> defaultStyle;
    private List<String> errorStyle;

    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));

        defaultStyle = new ArrayList<>(resultDisplayHandle.getStyleClass());
        errorStyle = new ArrayList<>(resultDisplayHandle.getStyleClass());
        errorStyle.add(ResultDisplay.ERROR_STYLE);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());
        assertEquals(resultDisplayHandle.getStyleClass(), defaultStyle);

        // new result received
        assertResultDisplay(NEW_RESULT_EVENT_STUB_SUCCESS);
        assertResultDisplay(NEW_RESULT_EVENT_STUB_ERROR);
    }

    /**
     * Tests correctness of result display's text and style in response
     * to a new event that triggers an update in the result display.
     * @param event New event that requires UI to display something in ResultDisplay
     */
    private void assertResultDisplay(NewResultAvailableEvent event) {
        postNow(event);
        List<String> correctStyle = event.isSuccessful ? defaultStyle : errorStyle;
        assertEquals(event.message, resultDisplayHandle.getText());
        assertEquals(correctStyle, resultDisplayHandle.getStyleClass());
    }
}
