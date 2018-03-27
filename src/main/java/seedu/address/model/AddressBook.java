package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.food.Food;
import seedu.address.model.food.UniqueFoodList;
import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.model.food.exceptions.FoodNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.user.UserProfile;
import seedu.address.model.util.SampleDataUtil;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueFoodList foods;
    private final UniqueTagList tags;
    private UserProfile profile;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        foods = new UniqueFoodList();
        tags = new UniqueTagList();
        profile = SampleDataUtil.getSampleProfile();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Foods, Tags, and User Profile in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setFoods(List<Food> foods) throws DuplicateFoodException {
        this.foods.setFoods(foods);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setUserProfile(UserProfile profile) {
        this.profile = profile;
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Food> syncedFoodList = newData.getFoodList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        setUserProfile(newData.getUserProfile());

        try {
            setFoods(syncedFoodList);
        } catch (DuplicateFoodException e) {
            throw new AssertionError("AddressBooks should not have duplicate foods");
        }
    }

    //// food-level operations

    /**
     * Adds a food to HackEat.
     * Also checks the new food's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the food to point to those in {@link #tags}.
     *
     * @throws DuplicateFoodException if an equivalent food already exists.
     */
    public void addFood(Food p) throws DuplicateFoodException {
        Food food = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any food
        // in the food list.
        foods.add(food);
    }

    /**
     * Replaces the given food {@code target} in the list with {@code editedFood}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedFood}.
     *
     * @throws DuplicateFoodException if updating the food's details causes the food to be equivalent to
     *      another existing food in the list.
     * @throws FoodNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Food)
     */
    public void updateFood(Food target, Food editedFood)
            throws DuplicateFoodException, FoodNotFoundException {
        requireNonNull(editedFood);

        Food syncedEditedFood = syncWithMasterTagList(editedFood);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any food
        // in the food list.
        foods.setFood(target, syncedEditedFood);
    }

    /**
     * Replaces the old profile with the new profile {@code newProfile}.
     */
    public void updateUserProfile(UserProfile newProfile) {
        profile = newProfile;
    }

    /**
     *  Updates the master tag list to include tags in {@code food} that are not in the list.
     *  @return a copy of this {@code food} such that every tag in this food points to a Tag object in the master
     *  list.
     */
    private Food syncWithMasterTagList(Food food) {
        final UniqueTagList personTags = new UniqueTagList(food.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking food tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of food tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Food(
                food.getName(), food.getPhone(), food.getEmail(), food.getAddress(),
                food.getPrice(), food.getRating() , correctTagReferences, food.getAllergies());
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws FoodNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeFood(Food key) throws FoodNotFoundException {
        if (foods.remove(key)) {
            return true;
        } else {
            throw new FoodNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return foods.asObservableList().size() + " foods, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Food> getFoodList() {
        return foods.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public UserProfile getUserProfile() {
        return profile;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.foods.equals(((AddressBook) other).foods)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(foods, tags);
    }
}
