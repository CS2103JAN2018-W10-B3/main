package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.ProfilePictureChangedEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.user.UserProfile;

//@@author tohcheryl
/**
 * The User Profile panel of the App.
 */
public class UserProfilePanel extends UiPart<Region> {

    private static final String FXML = "UserProfilePanel.fxml";

    private static final Logger logger = LogsCenter.getLogger(UserProfilePanel.class);

    private static final String PROFILE_PICTURE_PATH = "profilepic.png";

    final Circle clip = new Circle(75, 75, 75);

    private ReadOnlyAddressBook addressBook;

    @FXML
    private ImageView profilepic;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private FlowPane allergies;

    public UserProfilePanel(ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.addressBook = addressBook;
        name.setWrapText(true);
        phone.setWrapText(true);
        address.setWrapText(true);
        setUserProfile(addressBook.getUserProfile());
        setProfilePicture();
        registerAsAnEventHandler(this);
    }

    public void setUserProfile(UserProfile userProfile) {
        name.setText(userProfile.getName().fullName);
        phone.setText(userProfile.getPhone().value);
        address.setText(userProfile.getAddress().value);
        allergies.getChildren().clear();
        userProfile.getAllergies().forEach(allergy -> allergies.getChildren().add(new Label(allergy.allergyName)));
    }

    public void setProfilePicture() {
        profilepic.setImage(new Image("file:" + PROFILE_PICTURE_PATH));
        profilepic.setClip(clip);
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        UserProfile newUserProfile = addressBook.getUserProfile();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "User Profile updated to: " + newUserProfile));
        Platform.runLater(() -> {
            setUserProfile(newUserProfile);
        });
    }

    @Subscribe
    public void handleProfilePictureChangedEvent(ProfilePictureChangedEvent ppce) {
        Platform.runLater(() -> {
            setProfilePicture();
        });
    }
}
