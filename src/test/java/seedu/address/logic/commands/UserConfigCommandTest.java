package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getAllergySet;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.food.Address;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.user.UserProfile;

public class UserConfigCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private UserProfile validUserProfile = new UserProfile(new Name("Wei wei"), new Phone("92304333"),
                new Address("Blk 343 Serangoon Ave 3 #23-23 Singapore 349343"), getAllergySet("banana"));

    @Test
    public void execute_validUserProfile_setSuccessfully() throws Exception {
        CommandResult commandResult = getUserConfigCommand(validUserProfile, model).execute();
        assertEquals(UserConfigCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(validUserProfile, model.getAddressBook().getUserProfile());
    }

    /**
     * Generates a new UserConfigCommand.
     */
    private UserConfigCommand getUserConfigCommand(UserProfile userProfile, Model model) {
        UserConfigCommand command = new UserConfigCommand(userProfile);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
