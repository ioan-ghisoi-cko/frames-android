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
public class BillingDetails_Test {

    @Rule
    public ActivityTestRule<DemoActivity> mActivityTestRule = new ActivityTestRule<>(DemoActivity.class);

    @Test
    public void Save_Values_To_Billing_Input_When_Completed() {
        ViewInteraction billingInput = onView(
                allOf(withId(R.id.go_to_billing),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        billingInput.perform(scrollTo(), click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView.perform(click());

        ViewInteraction touchlessViewPager = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.checkout_card_form),
                                        0),
                                0),
                        isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.name_input_layout),
                                        0),
                                0)));
        defaultInput.perform(scrollTo(), replaceText("test name"), closeSoftKeyboard());

        ViewInteraction countryInput = onView(
                allOf(withId(R.id.country_input),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        countryInput.perform(scrollTo(), click());

        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(234);
        checkedTextView2.perform(click());

        ViewInteraction addressOneInput = onView(
                allOf(withId(R.id.address_one_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_one_input_layout),
                                        0),
                                0)));
        addressOneInput.perform(scrollTo(), click());

        ViewInteraction addressOneInput2 = onView(
                allOf(withId(R.id.address_one_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_one_input_layout),
                                        0),
                                0)));
        addressOneInput2.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction defaultInput2 = onView(
                allOf(withId(R.id.address_two_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.address_two_input_layout),
                                        0),
                                0)));
        defaultInput2.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction defaultInput3 = onView(
                allOf(withId(R.id.city_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.city_input_layout),
                                        0),
                                0)));
        defaultInput3.perform(scrollTo(), replaceText("w1w w1w"), closeSoftKeyboard());

        ViewInteraction defaultInput4 = onView(
                allOf(withId(R.id.state_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.state_input_layout),
                                        0),
                                0)));
        defaultInput4.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction defaultInput5 = onView(
                allOf(withId(R.id.zipcode_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.zipcode_input_layout),
                                        0),
                                0)));
        defaultInput5.perform(scrollTo(), replaceText("w1ww1w"), closeSoftKeyboard());

        ViewInteraction phoneInput = onView(
                allOf(withId(R.id.phone_input), withText("+44 "),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.phone_input_layout),
                                        0),
                                0)));
        phoneInput.perform(scrollTo(), replaceText("+44 12345667"));

        ViewInteraction phoneInput2 = onView(
                allOf(withId(R.id.phone_input), withText("+44 12345667"),
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
                allOf(withId(android.R.id.text1), withText("test, test, w1w w1w, test"),
                        childAtPosition(
                                allOf(withId(R.id.go_to_billing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                7)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("test, test, w1w w1w, test")));

    }

    @Test
    public void Clear_Billing_Values_On_Clear() {
        ViewInteraction billingInput = onView(
                allOf(withId(R.id.go_to_billing),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        billingInput.perform(scrollTo(), click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView.perform(click());

        ViewInteraction touchlessViewPager = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.checkout_card_form),
                                        0),
                                0),
                        isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.name_input_layout),
                                        0),
                                0)));
        defaultInput.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.clear_button), withText("Clear"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                0)));
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
                allOf(withId(android.R.id.text1), withText("SELECT"),
                        childAtPosition(
                                allOf(withId(R.id.go_to_billing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                7)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("SELECT")));

        ViewInteraction billingInput2 = onView(
                allOf(withId(R.id.go_to_billing),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        billingInput2.perform(scrollTo(), click());

        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView2.perform(click());

        ViewInteraction touchlessViewPager3 = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.checkout_card_form),
                                        0),
                                0),
                        isDisplayed()));
        touchlessViewPager3.perform(swipeLeft());

        ViewInteraction editText = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.name_input_layout),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("")));

    }

    @Test
    public void Retain_Value_Unless_Cleared() {
        ViewInteraction billingInput = onView(
                allOf(withId(R.id.go_to_billing),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        billingInput.perform(scrollTo(), click());

        DataInteraction checkedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView.perform(click());

        ViewInteraction touchlessViewPager = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.checkout_card_form),
                                        0),
                                0),
                        isDisplayed()));
        touchlessViewPager.perform(swipeLeft());

        ViewInteraction defaultInput = onView(
                allOf(withId(R.id.name_input),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.name_input_layout),
                                        0),
                                0)));
        defaultInput.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.my_toolbar),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0)),
                        1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

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
                allOf(withId(android.R.id.text1), withText("SELECT"),
                        childAtPosition(
                                allOf(withId(R.id.go_to_billing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                7)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("SELECT")));

        ViewInteraction billingInput2 = onView(
                allOf(withId(R.id.go_to_billing),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        billingInput2.perform(scrollTo(), click());

        DataInteraction checkedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        checkedTextView2.perform(click());

        ViewInteraction touchlessViewPager3 = onView(
                allOf(withId(R.id.view_pager),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.checkout_card_form),
                                        0),
                                0),
                        isDisplayed()));
        touchlessViewPager3.perform(swipeLeft());

        ViewInteraction editText = onView(
                allOf(withId(R.id.name_input), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.name_input_layout),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("test")));

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
