package com.targetprops.greenscreen;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


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

    public void testTogglePenColor() {
        onData(withId(R.id.action_colors)).perform(click());
        onView(withId(R.id.background_blue)).perform(click());

        Drawable background = mActivity.findViewById(android.R.id.content).getBackground();
        int backgroundColor = ((ColorDrawable) background).getColor();

        assertEquals(backgroundColor, R.color.blue);
    }

}