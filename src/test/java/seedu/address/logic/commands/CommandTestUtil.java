package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.food.Food;
import seedu.address.model.food.NameContainsKeywordsPredicate;
import seedu.address.model.food.exceptions.FoodNotFoundException;
import seedu.address.testutil.EditFoodDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_APPLE = "Apple Pie";
    public static final String VALID_NAME_BANANA = "Banana Smoothie";
    public static final String VALID_PHONE_APPLE = "11111111";
    public static final String VALID_PHONE_BANANA = "22222222";
    public static final String VALID_EMAIL_APPLE = "appleshop@example.com";
    public static final String VALID_EMAIL_BANANA = "bananashop@example.com";
    public static final String VALID_ADDRESS_APPLE = "Block 312, Apple Street 1";
    public static final String VALID_ADDRESS_BANANA = "Block 123, Banana Street 3";
    public static final String VALID_PRICE_APPLE = "$0.50";
    public static final String VALID_PRICE_BANANA = "$0.80";
    public static final String VALID_RATING_APPLE = "3";
    public static final String VALID_RATING_BANANA = "5";
    public static final String VALID_TAG_NUTS = "nuts";
    public static final String VALID_TAG_FRIED = "fried";
    public static final String VALID_ALLERGY_LACTOSE = "lactose";

    public static final String NAME_DESC_APPLE = " " + PREFIX_NAME + VALID_NAME_APPLE;
    public static final String NAME_DESC_BANANA = " " + PREFIX_NAME + VALID_NAME_BANANA;
    public static final String PHONE_DESC_APPLE = " " + PREFIX_PHONE + VALID_PHONE_APPLE;
    public static final String PHONE_DESC_BANANA = " " + PREFIX_PHONE + VALID_PHONE_BANANA;
    public static final String EMAIL_DESC_APPLE = " " + PREFIX_EMAIL + VALID_EMAIL_APPLE;
    public static final String EMAIL_DESC_BANANA = " " + PREFIX_EMAIL + VALID_EMAIL_BANANA;
    public static final String ADDRESS_DESC_APPLE = " " + PREFIX_ADDRESS + VALID_ADDRESS_APPLE;
    public static final String ADDRESS_DESC_BANANA = " " + PREFIX_ADDRESS + VALID_ADDRESS_BANANA;
    public static final String PRICE_DESC_APPLE = " " + PREFIX_PRICE + VALID_PRICE_APPLE;
    public static final String PRICE_DESC_BANANA = " " + PREFIX_PRICE + VALID_PRICE_BANANA;
    public static final String RATING_DESC_APPLE = " " + PREFIX_RATING + VALID_RATING_APPLE;
    public static final String RATING_DESC_BANANA = " " + PREFIX_RATING + VALID_RATING_BANANA;
    public static final String TAG_DESC_FRIED = " " + PREFIX_TAG + VALID_TAG_FRIED;
    public static final String TAG_DESC_NUTS = " " + PREFIX_TAG + VALID_TAG_NUTS;
    public static final String ALLERGY_DESC_LACTOSE = " " + PREFIX_ALLERGIES + VALID_ALLERGY_LACTOSE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Panc&ke"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "banana!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "e.40"; // letters not allowed for prices
    public static final String INVALID_RATING_DESC = " " + PREFIX_RATING + "9"; // out of range
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "poise*"; // '*' not allowed in tags
    public static final String INVALID_ALLERGY_DESC = " " + PREFIX_ALLERGIES + "#peanut"; // '#' not allowed in allergy

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFoodDescriptor DESC_APPLE;
    public static final EditCommand.EditFoodDescriptor DESC_BANANA;

    static {
        DESC_APPLE = new EditFoodDescriptorBuilder().withName(VALID_NAME_APPLE)
                .withPhone(VALID_PHONE_APPLE).withEmail(VALID_EMAIL_APPLE).withAddress(VALID_ADDRESS_APPLE)
                .withPrice(VALID_PRICE_APPLE).withRating(VALID_RATING_APPLE).withTags(VALID_TAG_FRIED)
                .withAllergies(VALID_ALLERGY_LACTOSE).build();
        DESC_BANANA = new EditFoodDescriptorBuilder().withName(VALID_NAME_BANANA)
                .withPhone(VALID_PHONE_BANANA).withEmail(VALID_EMAIL_BANANA).withAddress(VALID_ADDRESS_BANANA)
                .withPrice(VALID_PRICE_BANANA).withRating(VALID_RATING_BANANA)
                .withTags(VALID_TAG_NUTS, VALID_TAG_FRIED).withAllergies(VALID_ALLERGY_LACTOSE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - HackEat and the filtered food list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Food> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFoodList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredFoodList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the food at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFoodAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFoodList().size());

        Food food = model.getFilteredFoodList().get(targetIndex.getZeroBased());
        final String[] splitName = food.getName().fullName.split("\\s+");
        model.updateFilteredFoodList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFoodList().size());
    }

    /**
     * Deletes the first food in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstFood(Model model) {
        Food firstFood = model.getFilteredFoodList().get(0);
        try {
            model.deleteFood(firstFood);
        } catch (FoodNotFoundException pnfe) {
            throw new AssertionError("Food in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
