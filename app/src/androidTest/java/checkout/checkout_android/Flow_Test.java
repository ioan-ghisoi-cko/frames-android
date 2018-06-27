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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
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
public class Flow_Test {

    @Rule
    public ActivityTestRule<DemoActivity> mActivityTestRule = new ActivityTestRule<>(DemoActivity.class);

    @Test
    public void Card_And_Billing() throws InterruptedException {
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

        ViewInteraction yearInput = onView(
                allOf(withId(R.id.year_input),
                        childAtPosition(
                                allOf(withId(R.id.date_input_layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                4)),
                                1)));
        yearInput.perform(scrollTo(), click());

        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        checkedTextView2.perform(click());

        ViewInteraction defaultInput = onView(
                allOf(withId(R.id.cvv_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cvv_input_layout),
                                        0),
                                0)));
        defaultInput.perform(scrollTo(), replaceText("100"), closeSoftKeyboard());

        ViewInteraction billingInput = onView(
                allOf(withId(R.id.go_to_billing),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        billingInput.perform(scrollTo(), click());

        DataInteraction checkedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView3.perform(click());

        ViewInteraction touchlessViewPager = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.checkout_card_form),
                                        0),
                                0),
                        isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput2 = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.name_input_layout),
                                        0),
                                0)));
        defaultInput2.perform(scrollTo(), replaceText("john smith"), closeSoftKeyboard());

        ViewInteraction countryInput = onView(
                allOf(withId(R.id.country_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        countryInput.perform(scrollTo(), click());

        DataInteraction checkedTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(234);
        checkedTextView4.perform(click());

        ViewInteraction addressOneInput = onView(
                allOf(withId(R.id.address_one_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_one_input_layout),
                                        0),
                                0)));
        addressOneInput.perform(scrollTo(), replaceText("address1"), closeSoftKeyboard());

        ViewInteraction defaultInput3 = onView(
                allOf(withId(R.id.address_two_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_two_input_layout),
                                        0),
                                0)));
        defaultInput3.perform(scrollTo(), replaceText("address2"), closeSoftKeyboard());

        ViewInteraction defaultInput4 = onView(
                allOf(withId(R.id.city_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.city_input_layout),
                                        0),
                                0)));
        defaultInput4.perform(scrollTo(), longClick());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction defaultInput5 = onView(
                allOf(withId(R.id.city_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.city_input_layout),
                                        0),
                                0)));
        defaultInput5.perform(scrollTo(), replaceText("town"), closeSoftKeyboard());

        ViewInteraction defaultInput6 = onView(
                allOf(withId(R.id.state_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.state_input_layout),
                                        0),
                                0)));
        defaultInput6.perform(scrollTo(), replaceText("state"), closeSoftKeyboard());

        ViewInteraction defaultInput7 = onView(
                allOf(withId(R.id.zipcode_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.zipcode_input_layout),
                                        0),
                                0)));
        defaultInput7.perform(scrollTo(), replaceText("w1w w1w"), closeSoftKeyboard());

        ViewInteraction phoneInput = onView(
                allOf(withId(R.id.phone_input), withText("+44 "),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_input_layout),
                                        0),
                                0)));
        phoneInput.perform(scrollTo(), replaceText("+44 7123456789"));

        ViewInteraction phoneInput2 = onView(
                allOf(withId(R.id.phone_input), withText("+44 7123456789"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_input_layout),
                                        0),
                                0),
                        isDisplayed()));
        phoneInput2.perform(closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.done_button), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                1)));
        button.perform(scrollTo(), click());

        ViewInteraction touchlessViewPager2 = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.checkout_card_form),
                                        0),
                                0),
                        isDisplayed()));
        touchlessViewPager2.perform(swipeRight());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("address1, address2, town, state"),
                        childAtPosition(
                                allOf(withId(R.id.go_to_billing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                7)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("address1, address2, town, state")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.pay_button), withText("Pay"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                0)));
        button2.perform(scrollTo(), click());

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
