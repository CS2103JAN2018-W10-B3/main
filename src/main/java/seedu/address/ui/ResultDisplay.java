package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    public static final String ERROR_STYLE = "error";

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        Platform.runLater(() -> {

            if (event.isSuccessful) {
                setStyleForSuccessfulCommand();
            } else {
                setStyleForFailedCommand();
            }

            displayed.setValue(event.message);
        });
    }

    /** Adds error style for failed command's result display */
    private void setStyleForFailedCommand() {
        if (resultDisplay.getStyleClass().contains(ERROR_STYLE)) {
            return;
        }
        resultDisplay.getStyleClass().add(ERROR_STYLE);
    }

    /** Removes error style for successful command's result display */
    private void setStyleForSuccessfulCommand() {
        if (resultDisplay.getStyleClass().contains(ERROR_STYLE)) {
            resultDisplay.getStyleClass().remove(ERROR_STYLE);
        }
    }


}
