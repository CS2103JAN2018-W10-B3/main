package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.food.exceptions.DuplicateFoodException;
import seedu.address.model.food.exceptions.FoodNotFoundException;

/**
 * A list of foods that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Food#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueFoodList implements Iterable<Food> {

    private final ObservableList<Food> internalList = FXCollections.observableArrayList();

    public UniqueFoodList() {}

    /**
     * Creates a UniqueFoodList using given foods.
     * Enforces no nulls.
     */
    public UniqueFoodList(Set<Food> foods) {
        requireAllNonNull(foods);
        internalList.addAll(foods);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent food as the given argument.
     */
    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a food to the list.
     *
     * @throws DuplicateFoodException if the food to add is a duplicate of an existing food in the list.
     */
    public void add(Food toAdd) throws DuplicateFoodException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFoodException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the food {@code target} in the list with {@code editedFood}.
     *
     * @throws DuplicateFoodException if the replacement is equivalent to another existing food in the list.
     * @throws FoodNotFoundException if {@code target} could not be found in the list.
     */
    public void setFood(Food target, Food editedFood)
            throws DuplicateFoodException, FoodNotFoundException {
        requireNonNull(editedFood);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        if (!target.equals(editedFood) && internalList.contains(editedFood)) {
            throw new DuplicateFoodException();
        }

        internalList.set(index, editedFood);
    }

    /**
     * Removes the equivalent food from the list.
     *
     * @throws FoodNotFoundException if no such food could be found in the list.
     */
    public boolean remove(Food toRemove) throws FoodNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new FoodNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setFoods(UniqueFoodList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setFoods(List<Food> foods) throws DuplicateFoodException {
        requireAllNonNull(foods);
        final UniqueFoodList replacement = new UniqueFoodList();
        for (final Food food : foods) {
            replacement.add(food);
        }
        setFoods(replacement);
    }

    /**
     * Returns all Foods in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Food> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Food> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Food> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFoodList // instanceof handles nulls
                        && this.internalList.equals(((UniqueFoodList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
