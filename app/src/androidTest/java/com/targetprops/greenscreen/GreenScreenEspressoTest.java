package com.targetprops.greenscreen;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.v4.content.ContextCompat;
import android.test.ActivityInstrumentationTestCase2;

import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class GreenScreenEspressoTest extends ActivityInstrumentationTestCase2<ColorScreen> {

    // Used to obtain IDs and activity context
    private ColorScreen mActivity;

    /*
     * Required constructor to send info to parent class
     */
    public GreenScreenEspressoTest() {
        super(ColorScreen.class);
    }

    /*
     * Sets up the environment and starts the proper activity depending on the
     * generic of the parent class
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    /*
     * Changes the background color from green to blue then checks
     * to see if the background successfully changed on the lower
     * level
     */
    public void testTogglePenColorBlue() {
        // Makes sure that the Color change button is displayed when available
        onView(withId(R.id.action_colors)).check(matches(isDisplayed()));

        // Clicks the Color change button option in the action bar
        onView(withId(R.id.action_colors)).perform(click());

        // Finds the option with the text "Blue" and clicks it
        onView(withText("Blue")).perform(click());

        // Gets the background color from the ColorScreen's content ID
        Drawable background = mActivity.findViewById(android.R.id.content).getBackground();
        int backgroundColor = ((ColorDrawable) background).getColor();

        // Assertion to check that the background color is actually blue
        assertEquals(backgroundColor, ContextCompat.getColor(mActivity, R.color.blue));
    }

    /*
     * Clicks the tracker location toggle option once to put the
     * trackers in the corners only
     */
    public void testTrackerLocationCorners() {
        // Waits until the toggle tracker option is displayed and available
        onView(withId(R.id.action_trackers)).check(matches(isDisplayed()));

        // Performs a single click on the toggle tracker option
        onView(withId(R.id.action_trackers)).perform(click());

        // Multiple assertions to check that the proper trackers are visible
        onView(withId(R.id.imageView)).check(matches(isDisplayed())); // Right Top
        onView(withId(R.id.imageView2)).check(matches(isDisplayed())); // Left Top
        onView(withId(R.id.imageView3)).check(matches(isDisplayed())); // Left Bottom
        onView(withId(R.id.imageView4)).check(matches(isDisplayed())); // Right Bottom
        onView(withId(R.id.imageView5)).check(matches(not(isDisplayed()))); // Center
    }

    /*
     * Clicks the tracker location toggle option once to put the
     * trackers in all available positions (corners and center)
     */
    public void testTrackerLocationAll() {
        // Waits until the toggle tracker option is displayed and available
        onView(withId(R.id.action_trackers)).check(matches(isDisplayed()));

        // Performs a double click on the toggle tracker option
        onView(withId(R.id.action_trackers)).perform(doubleClick());

        // Multiple assertions to check that the proper trackers are visible
        onView(withId(R.id.imageView)).check(matches(isDisplayed())); // Right Top
        onView(withId(R.id.imageView2)).check(matches(isDisplayed())); // Left Top
        onView(withId(R.id.imageView3)).check(matches(isDisplayed())); // Left Bottom
        onView(withId(R.id.imageView4)).check(matches(isDisplayed())); // Right Bottom
        onView(withId(R.id.imageView5)).check(matches(isDisplayed())); // Center
    }

    /*
     * Clicks the tracker location toggle option once to remove
     * all trackers from all positions
     */
    public void testTrackerLocationNone() {
        // Waits until the toggle tracker option is displayed and available
        onView(withId(R.id.action_trackers)).check(matches(isDisplayed()));

        // Performs a double and then a single click on the toggle tracker option
        // Simulates a triple click (Could just use 3 click() calls)
        onView(withId(R.id.action_trackers)).perform(doubleClick());
        onView(withId(R.id.action_trackers)).perform(click());

        // Multiple assertions to check that the proper trackers are visible
        onView(withId(R.id.imageView)).check(matches(not(isDisplayed()))); // Right Top
        onView(withId(R.id.imageView2)).check(matches(not(isDisplayed()))); // Left Top
        onView(withId(R.id.imageView3)).check(matches(not(isDisplayed()))); // Left Bottom
        onView(withId(R.id.imageView4)).check(matches(not(isDisplayed()))); // Right Bottom
        onView(withId(R.id.imageView5)).check(matches(not(isDisplayed()))); // Center
    }

    /*
     * Goes through the list of tracker size options one by one and checks
     * to make sure that the height and width are the correct respective
     * size
     */
    public void testTrackerSizeChange() {
        // Toggle tracker location to display all
        onView(withId(R.id.action_trackers)).check(matches(isDisplayed()));
        onView(withId(R.id.action_trackers)).perform(doubleClick());

        // Check if displayed properly
        onView(withId(R.id.action_trackerSize)).check(matches(isDisplayed()));

        // Toggle to small
        onView(withId(R.id.action_trackerSize)).perform(click());
        onView(withText("Small")).perform(click());

        assertEquals(mActivity.imageViewLT.getLayoutParams().height, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewRT.getLayoutParams().height, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewRB.getLayoutParams().height, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewLB.getLayoutParams().height, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewC.getLayoutParams().height, mActivity.dpToPx(48));

        assertEquals(mActivity.imageViewLT.getLayoutParams().width, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewRT.getLayoutParams().width, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewRB.getLayoutParams().width, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewLB.getLayoutParams().width, mActivity.dpToPx(48));
        assertEquals(mActivity.imageViewC.getLayoutParams().width, mActivity.dpToPx(48));

        // Toggle to default
        onView(withId(R.id.action_trackerSize)).perform(click());
        onView(withText("Default")).perform(click());

        assertEquals(mActivity.imageViewLT.getLayoutParams().height, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewRT.getLayoutParams().height, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewRB.getLayoutParams().height, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewLB.getLayoutParams().height, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewC.getLayoutParams().height, mActivity.dpToPx(64));

        assertEquals(mActivity.imageViewLT.getLayoutParams().width, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewRT.getLayoutParams().width, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewRB.getLayoutParams().width, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewLB.getLayoutParams().width, mActivity.dpToPx(64));
        assertEquals(mActivity.imageViewC.getLayoutParams().width, mActivity.dpToPx(64));

        // Toggle to large
        onView(withId(R.id.action_trackerSize)).perform(click());
        onView(withText("Large")).perform(click());

        assertEquals(mActivity.imageViewLT.getLayoutParams().height, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewRT.getLayoutParams().height, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewRB.getLayoutParams().height, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewLB.getLayoutParams().height, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewC.getLayoutParams().height, mActivity.dpToPx(96));

        assertEquals(mActivity.imageViewLT.getLayoutParams().width, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewRT.getLayoutParams().width, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewRB.getLayoutParams().width, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewLB.getLayoutParams().width, mActivity.dpToPx(96));
        assertEquals(mActivity.imageViewC.getLayoutParams().width, mActivity.dpToPx(96));
    }
}