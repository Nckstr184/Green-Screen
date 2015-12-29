package com.targetprops.greenscreen;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.v4.content.ContextCompat;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class GreenScreenEspressoTest extends ActivityInstrumentationTestCase2<ColorScreen> {

    private ColorScreen mActivity;

    public GreenScreenEspressoTest() {
        super(ColorScreen.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testTogglePenColorBlue() {
        onView(withId(R.id.action_colors)).check(matches(isDisplayed()));
        onView(withId(R.id.action_colors)).perform(click());
        onView(withText("Blue")).perform(click());

        Drawable background = mActivity.findViewById(android.R.id.content).getBackground();
        int backgroundColor = ((ColorDrawable) background).getColor();

        assertEquals(backgroundColor, ContextCompat.getColor(mActivity, R.color.blue));
    }

    public void testTrackerLocationCorners() {
        onView(withId(R.id.action_trackers)).perform(click());


        assertTrue(mActivity.imageViewLT.isShown());
        assertTrue(mActivity.imageViewRT.isShown());
        assertTrue(mActivity.imageViewLB.isShown());
        assertTrue(mActivity.imageViewRB.isShown());
        assertFalse(mActivity.imageViewC.isShown());
    }

    public void testTrackerLocationAll() {
        onView(withId(R.id.action_trackers)).perform(click());
        onView(withId(R.id.action_trackers)).perform(click());

        assertTrue(mActivity.imageViewLT.isShown());
        assertTrue(mActivity.imageViewRT.isShown());
        assertTrue(mActivity.imageViewLB.isShown());
        assertTrue(mActivity.imageViewRB.isShown());
        assertTrue(mActivity.imageViewC.isShown());
    }

    public void testTrackerLocationNone() {
        onView(withId(R.id.action_trackers)).perform(click());
        onView(withId(R.id.action_trackers)).perform(click());
        onView(withId(R.id.action_trackers)).perform(click());

        assertFalse(mActivity.imageViewLT.isShown());
        assertFalse(mActivity.imageViewRT.isShown());
        assertFalse(mActivity.imageViewLB.isShown());
        assertFalse(mActivity.imageViewRB.isShown());
        assertFalse(mActivity.imageViewC.isShown());
    }
}