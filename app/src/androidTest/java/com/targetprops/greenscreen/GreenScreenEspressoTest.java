package com.targetprops.greenscreen;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.v4.content.ContextCompat;
import android.test.ActivityInstrumentationTestCase2;

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
        // Makes sure that the Color change button is displayed
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
        // Waits until the toggle tracker option is displayed
        onView(withId(R.id.action_trackers)).check(matches(isDisplayed()));

        // Performs a single click on the toggle tracker option
        onView(withId(R.id.action_trackers)).perform(click());

        // Multiple assertions to check that the proper trackers are visible
        assertTrue(mActivity.imageViewLT.isShown());
        assertTrue(mActivity.imageViewRT.isShown());
        assertTrue(mActivity.imageViewLB.isShown());
        assertTrue(mActivity.imageViewRB.isShown());
        assertFalse(mActivity.imageViewC.isShown());
    }

    /*
     * Clicks the tracker location toggle option once to put the
     * trackers in all available positions (corners and center)
     */
    public void testTrackerLocationAll() {
        // Waits until the toggle tracker option is displayed
        onView(withId(R.id.action_trackers)).check(matches(isDisplayed()));

        // Performs a double click on the toggle tracker option
        onView(withId(R.id.action_trackers)).perform(doubleClick());

        // Multiple assertions to check that the proper trackers are visible
        assertTrue(mActivity.imageViewLT.isShown());
        assertTrue(mActivity.imageViewRT.isShown());
        assertTrue(mActivity.imageViewLB.isShown());
        assertTrue(mActivity.imageViewRB.isShown());
        assertTrue(mActivity.imageViewC.isShown());
    }

    /*
     * Clicks the tracker location toggle option once to remove
     * all trackers from all positions
     */
    public void testTrackerLocationNone() {
        // Waits until the toggle tracker option is displayed
        onView(withId(R.id.action_trackers)).check(matches(isDisplayed()));

        // Performs a double and then a single click on the toggle tracker option
        // Simulates a triple click (Could just use 3 click() calls)
        onView(withId(R.id.action_trackers)).perform(doubleClick());
        onView(withId(R.id.action_trackers)).perform(click());

        // Multiple assertions to check that the proper trackers are visible
        assertFalse(mActivity.imageViewLT.isShown());
        assertFalse(mActivity.imageViewRT.isShown());
        assertFalse(mActivity.imageViewLB.isShown());
        assertFalse(mActivity.imageViewRB.isShown());
        assertFalse(mActivity.imageViewC.isShown());
    }
}