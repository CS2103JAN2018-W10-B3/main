package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_LACTOSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BANANA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NUTS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.food.Food;
import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Food} objects to be used in tests.
 */
public class TypicalFoods {

    public static final Food ALMOND = new FoodBuilder().withName("Almond Biscuits")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("almond@example.com")
            .withPhone("85355255").withPrice("$1").withRating("0").withTags("fried")
            .withAllergies("lactose").build();
    public static final Food BACON = new FoodBuilder().withName("Bacon Mousse")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("baconator@example.com").withPhone("98765432")
            .withPrice("$2").withRating("1").withTags("avoid", "fried").withAllergies("lactose").build();
    public static final Food CAKE = new FoodBuilder().withName("Cake Pops").withPhone("95352563")
            .withEmail("caker@example.com").withAddress("dessert street")
            .withPrice("$3").withRating("2").withAllergies("lactose").build();
    public static final Food DUMPLING = new FoodBuilder().withName("Dumpling Mousse").withPhone("87652533")
            .withEmail("dumper@example.com").withAddress("10th street")
            .withPrice("$4").withRating("3").withAllergies("lactose").build();
    public static final Food EGG = new FoodBuilder().withName("Egg Tart").withPhone("9482224")
            .withEmail("yumcha@example.com").withAddress("china ave")
            .withPrice("$5").withRating("4").withAllergies("lactose").build();
    public static final Food FRIES = new FoodBuilder().withName("Fries and Gravy").withPhone("9482427")
            .withEmail("maccas@example.com").withAddress("little tokyo")
            .withPrice("$6").withRating("5").build();
    public static final Food GRAPE = new FoodBuilder().withName("Grape Juice").withPhone("9482442")
            .withEmail("boost@example.com").withAddress("4th street")
            .withPrice("$7").withRating("5").build();

    // Manually added
    public static final Food HAM = new FoodBuilder().withName("Ham Mousse").withPhone("8482424")
            .withEmail("weird@example.com").withAddress("little india")
            .withPrice("$8").withRating("5").build();
    public static final Food ICECREAM = new FoodBuilder().withName("Ice Cream").withPhone("8482131")
            .withEmail("cold@example.com").withAddress("chicago ave")
            .withPrice("$9").withRating("5").build();

    // Manually added - Food's details found in {@code CommandTestUtil}
    public static final Food APPLE = new FoodBuilder().withName(VALID_NAME_APPLE).withPhone(VALID_PHONE_APPLE)
            .withEmail(VALID_EMAIL_APPLE).withAddress(VALID_ADDRESS_APPLE)
            .withPrice("$0.50").withRating(VALID_RATING_APPLE).withTags(VALID_TAG_FRIED)
            .withAllergies(VALID_ALLERGY_LACTOSE).build();
    public static final Food BANANA = new FoodBuilder().withName(VALID_NAME_BANANA).withPhone(VALID_PHONE_BANANA)
            .withEmail(VALID_EMAIL_BANANA).withAddress(VALID_ADDRESS_BANANA)
            .withPrice("$0.80").withRating(VALID_RATING_BANANA).withTags(VALID_TAG_NUTS, VALID_TAG_FRIED)
            .withAllergies(VALID_ALLERGY_LACTOSE).build();

    public static final String KEYWORD_MATCHING_MOUSSE = "Mousse"; // A keyword that matches MOUSSE

    private TypicalFoods() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical foods and default user profile
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        ab.setUserProfile(SampleDataUtil.getSampleProfile());
        for (Food food : getTypicalFoods()) {
            try {
                ab.addFood(food);
            } catch (DuplicateFoodException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Food> getTypicalFoods() {
        return new ArrayList<>(Arrays.asList(ALMOND, BACON, CAKE, DUMPLING, EGG, FRIES, GRAPE));
    }
}
