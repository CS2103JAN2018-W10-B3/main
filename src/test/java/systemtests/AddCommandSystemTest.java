package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_LACTOSE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIED;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NUTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_LACTOSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalFoods.ALMOND;
import static seedu.address.testutil.TypicalFoods.APPLE;
import static seedu.address.testutil.TypicalFoods.BANANA;
import static seedu.address.testutil.TypicalFoods.CAKE;
import static seedu.address.testutil.TypicalFoods.HAM;
import static seedu.address.testutil.TypicalFoods.ICECREAM;
import static seedu.address.testutil.TypicalFoods.KEYWORD_MATCHING_MOUSSE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.food.Address;
import seedu.address.model.food.Email;
import seedu.address.model.food.Food;
import seedu.address.model.food.Name;
import seedu.address.model.food.Phone;
import seedu.address.model.food.Price;
import seedu.address.model.food.Rating;
import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FoodBuilder;
import seedu.address.testutil.FoodUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a food without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Food toAdd = APPLE;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_APPLE + "  " + PHONE_DESC_APPLE + " "
                + EMAIL_DESC_APPLE + "   " + ADDRESS_DESC_APPLE + "   " + PRICE_DESC_APPLE + "   "
                + RATING_DESC_APPLE + "  " + TAG_DESC_FRIED + " " + ALLERGY_DESC_LACTOSE + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addFood(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a food with all fields same as another food in HackEat except name -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_BANANA).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_APPLE)
                .withAddress(VALID_ADDRESS_APPLE).withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_APPLE)
                .withTags(VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BANANA + PHONE_DESC_APPLE + EMAIL_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food with all fields same as another food in HackEat except phone -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_BANANA).withEmail(VALID_EMAIL_APPLE)
                .withAddress(VALID_ADDRESS_APPLE).withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_APPLE)
                .withTags(VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_BANANA + EMAIL_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food with all fields same as another food in HackEat except email -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_BANANA)
                .withAddress(VALID_ADDRESS_APPLE).withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_APPLE)
                .withTags(VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_BANANA + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food with all fields same as another food in HackEat except address -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_APPLE)
                .withAddress(VALID_ADDRESS_BANANA).withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_APPLE)
                .withTags(VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_APPLE + ADDRESS_DESC_BANANA
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food with all fields same as another food in HackEat except price -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_APPLE)
                .withAddress(VALID_ADDRESS_APPLE).withPrice(VALID_PRICE_BANANA).withRating(VALID_RATING_APPLE)
                .withTags(VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_BANANA + RATING_DESC_APPLE + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food with all fields same as another food in HackEat except rating -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_APPLE)
                .withAddress(VALID_ADDRESS_APPLE).withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_BANANA)
                .withTags(VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_BANANA + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllFoods();
        assertCommandSuccess(ALMOND);

        /* Case: add a food with tags, command with parameters in random order -> added */
        toAdd = BANANA;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIED + PHONE_DESC_BANANA + ADDRESS_DESC_BANANA + NAME_DESC_BANANA
                + TAG_DESC_NUTS + EMAIL_DESC_BANANA + PRICE_DESC_BANANA + RATING_DESC_BANANA + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add with missing fields (optional fields) ------------------------------*/

        /* Case: add a food, missing tags -> added */
        assertCommandSuccess(HAM);

        /* Case: add a food, missing email -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE)
                .withAddress(VALID_ADDRESS_APPLE).withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_APPLE)
                .withTags(VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food, missing address -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_BANANA)
                .withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_APPLE).withTags(VALID_TAG_FRIED)
                .withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_BANANA
                + PRICE_DESC_APPLE + RATING_DESC_APPLE  + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food, missing price -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_BANANA)
                .withAddress(VALID_ADDRESS_APPLE).withRating(VALID_RATING_APPLE).withTags(VALID_TAG_FRIED)
                .withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_BANANA
                + ADDRESS_DESC_APPLE + RATING_DESC_APPLE  + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a food, missing rating -> added */
        toAdd = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_BANANA)
                .withAddress(VALID_ADDRESS_APPLE).withPrice(VALID_PRICE_APPLE).withTags(VALID_TAG_FRIED)
                .withAllergies(VALID_ALLERGY_LACTOSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_BANANA
                + ADDRESS_DESC_APPLE + PRICE_DESC_APPLE + TAG_DESC_FRIED + ALLERGY_DESC_LACTOSE;
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the food list before adding -> added */
        showFoodsWithName(KEYWORD_MATCHING_MOUSSE);
        assertCommandSuccess(ICECREAM);

        /* ------------------------ Perform add operation while a food card is selected --------------------------- */

        /* Case: selects first card in the food list, add a food -> added, card selection remains unchanged */
        selectFood(Index.fromOneBased(1));
        assertCommandSuccess(CAKE);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate food -> rejected */
        command = FoodUtil.getAddCommand(HAM);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FOOD);

        /* Case: add a duplicate food except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalFoods#ALMOND
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addFood(Food)
        command = FoodUtil.getAddCommand(HAM) + " " + PREFIX_TAG.getPrefix() + "fried";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_FOOD);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_APPLE + EMAIL_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + EMAIL_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + FoodUtil.getFoodDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_APPLE + EMAIL_DESC_APPLE
                + ADDRESS_DESC_APPLE + PRICE_DESC_APPLE + RATING_DESC_APPLE + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + INVALID_PHONE_DESC + EMAIL_DESC_APPLE
                + ADDRESS_DESC_APPLE + PRICE_DESC_APPLE + RATING_DESC_APPLE + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + INVALID_EMAIL_DESC
                + ADDRESS_DESC_APPLE + PRICE_DESC_APPLE + RATING_DESC_APPLE + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_APPLE
                + INVALID_ADDRESS_DESC + PRICE_DESC_APPLE + RATING_DESC_APPLE + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_APPLE
                + ADDRESS_DESC_APPLE + INVALID_PRICE_DESC + RATING_DESC_APPLE + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid rating -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_APPLE
                + ADDRESS_DESC_APPLE + RATING_DESC_APPLE + INVALID_RATING_DESC + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, Rating.MESSAGE_RATING_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_APPLE + PHONE_DESC_APPLE + EMAIL_DESC_APPLE + ADDRESS_DESC_APPLE
                + PRICE_DESC_APPLE + RATING_DESC_APPLE + INVALID_TAG_DESC + ALLERGY_DESC_LACTOSE;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code FoodListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Food toAdd) {
        assertCommandSuccess(FoodUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Food)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Food)
     */
    private void assertCommandSuccess(String command, Food toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addFood(toAdd);
        } catch (DuplicateFoodException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Food)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code FoodListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Food)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code FoodListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
