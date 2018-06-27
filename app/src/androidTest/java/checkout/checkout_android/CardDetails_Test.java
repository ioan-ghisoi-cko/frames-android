package checkout.checkout_android;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CardDetails_Test {

    @Rule
    public ActivityTestRule<DemoActivity> mActivityTestRule = new ActivityTestRule<>(DemoActivity.class);

    @Test
    public void All_Inputs_Empty() {
        ViewInteraction button = onView(
                allOf(withId(R.id.pay_button), withText("Pay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                0)));
        button.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textinput_error), withText("The card number is invalid"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_input_layout),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("The card number is invalid")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid Cvv"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Enter a valid Cvv")));

    }

    @Test
    public void Card_Input_Empty() {
        ViewInteraction defaultInput = onView(
                allOf(withId(R.id.cvv_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        0),
                                0)));
        defaultInput.perform(scrollTo(), replaceText("100"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.pay_button), withText("Pay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                0)));
        button.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textinput_error), withText("The card number is invalid"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_input_layout),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("The card number is invalid")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid Cvv"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Enter a valid Cvv")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid Cvv"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        1),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Enter a valid Cvv")));

    }

    @Test
    public void Cvv_Input_Empty() {
        ViewInteraction cardInput = onView(
                allOf(withId(R.id.card_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_input_layout),
                                        0),
                                0)));
        cardInput.perform(scrollTo(), replaceText("4242 4242 4242 4242"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.pay_button), withText("Pay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                0)));
        button.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid Cvv"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Enter a valid Cvv")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textinput_error), withText("Enter a valid Cvv"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        1),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Enter a valid Cvv")));

    }

    @Test
    public void All_Inputs_Correct() throws InterruptedException {
        ViewInteraction cardInput = onView(
                allOf(withId(R.id.card_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_input_layout),
                                        0),
                                0)));
        cardInput.perform(scrollTo(), click());

        ViewInteraction cardInput2 = onView(
                allOf(withId(R.id.card_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.card_input_layout),
                                        0),
                                0)));
        cardInput2.perform(scrollTo(), replaceText("4242 4242 4242 4242"), closeSoftKeyboard());

        ViewInteraction monthInput = onView(
                allOf(withId(R.id.month_input),
                        childAtPosition(
                                allOf(withId(R.id.date_input_layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                4)),
                                0)));
        monthInput.perform(scrollTo(), click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5);
        checkedTextView.perform(click());

        ViewInteraction defaultInput = onView(
                allOf(withId(R.id.cvv_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        0),
                                0)));
        defaultInput.perform(scrollTo(), replaceText("100"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.pay_button), withText("Pay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                0)));
        button.perform(scrollTo(), click());

        Thread.sleep(5000);

        onView(withText("Success!")).check(matches(isDisplayed()));

    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
