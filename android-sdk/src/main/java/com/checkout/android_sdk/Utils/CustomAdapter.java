package com.checkout.android_sdk.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.checkout.android_sdk.View.BillingDetailsView;
import com.checkout.android_sdk.View.CardDetailsView;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter of the viewpager used to have the 2 pages {@link CardDetailsView}
 * and {@link BillingDetailsView}
 * <p>
 * This class handles interaction initialisation and interaction between the to pages
 */
public class CustomAdapter extends PagerAdapter {

    private Context mContext;
    private CardDetailsView cardDetailsView;
    private List<LinearLayout> mViews = new ArrayList<>();
    private CardDetailsView.GoToBillingListener mCardDetailsListener;
    private BillingDetailsView billingDetailsView;
    private BillingDetailsView.Listener mBillingListener;
    private CardDetailsView.DetailsCompleted mDetailsCompletedListener;

    public CustomAdapter(Context context) {
        mContext = context;
    }

    /**
     * Pass the callback to go to the billing page
     */
    public void setCardDetailsListener(CardDetailsView.GoToBillingListener listener) {
        mCardDetailsListener = listener;
    }

    /**
     * Pass the callback to go to the card details page
     */
    public void setBillingListener(BillingDetailsView.Listener listener) {
        mBillingListener = listener;
    }

    /**
     * Pass the callback for when the card toke is generated
     */
    public void setTokenDetailsCompletedListener(CardDetailsView.DetailsCompleted listener) {
        mDetailsCompletedListener = listener;
    }

    /**
     * Indicate the {@link CardDetailsView} need to update the billing spinner
     */
    public void updateBillingSpinner() {
        cardDetailsView.updateBillingSpinner();
    }

     /**
     * Indicate the {@link CardDetailsView} need to clear the billing spinner
     */
    public void clearBillingSpinner() {
        cardDetailsView.clearBillingSpinner();
    }

    /**
     * Instantiation function
     */
    @NonNull
    @Override
    public LinearLayout instantiateItem(@NonNull ViewGroup container, int position) {
        maybeInstantiateViews(container);
        return mViews.get(position);
    }

    /**
     * Indicate there viewpager position
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "page " + position;
    }

    /**
     * Indicate there is only a 2 level depth in the viewpager
     */
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * Instantiates teh viewpager and adds the 2 pages: {@link CardDetailsView}
     * and {@link BillingDetailsView}
     */
    private void maybeInstantiateViews(ViewGroup container) {
        if (mViews.isEmpty()) {
            cardDetailsView = new CardDetailsView(mContext);
            cardDetailsView.setGoToBillingListener(mCardDetailsListener);
            cardDetailsView.setDetailsCompletedListener(mDetailsCompletedListener);

            billingDetailsView = new BillingDetailsView(mContext);
            billingDetailsView.setGoToCardDetailsListener(mBillingListener);

            mViews.add(cardDetailsView);
            mViews.add(billingDetailsView);

            container.addView(cardDetailsView);
            container.addView(billingDetailsView);
        }
    }
}