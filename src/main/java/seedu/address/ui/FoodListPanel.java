package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FoodPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.food.Food;

/**
 * Panel containing the list of foods.
 */
public class FoodListPanel extends UiPart<Region> {
    private static final String FXML = "FoodListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FoodListPanel.class);

    @FXML
    private ListView<FoodCard> foodListView;

    public FoodListPanel(ObservableList<Food> foodList) {
        super(FXML);
        setConnections(foodList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Food> foodList) {
        ObservableList<FoodCard> mappedList = EasyBind.map(
                foodList, (food) -> new FoodCard(food, foodList.indexOf(food) + 1));
        foodListView.setItems(mappedList);
        foodListView.setCellFactory(listView -> new FoodListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        foodListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in food list panel changed to : '" + newValue + "'");
                        raise(new FoodPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code FoodCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            foodListView.scrollTo(index);
            foodListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code FoodCard}.
     */
    class FoodListViewCell extends ListCell<FoodCard> {

        @Override
        protected void updateItem(FoodCard food, boolean empty) {
            super.updateItem(food, empty);

            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(food.getRoot());
            }
        }
    }

}
