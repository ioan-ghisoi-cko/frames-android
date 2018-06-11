package com.example.android_sdk.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.android_sdk.View.BillingDetailsView;
import com.example.android_sdk.View.CardDetailsView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends PagerAdapter {

    private Context mContext;
    private CardDetailsView cardDetailsView;
    private List<LinearLayout> mViews = new ArrayList<>();
    private CardDetailsView.GoToBillingListener mCardDetailsListener;
    private BillingDetailsView.Listener mBillingListener;

    public CustomAdapter(Context context) {
        mContext = context;
    }

    public void setCardDetailsListener(CardDetailsView.GoToBillingListener listener) {
        mCardDetailsListener = listener;
    }

    public void setBillingListener(BillingDetailsView.Listener listener) {
        mBillingListener = listener;
    }

    public void updateBillingSpinner(String address) {
        cardDetailsView.updateBillingSpinner(address);
    }

    @NonNull
    @Override
    public LinearLayout instantiateItem(@NonNull ViewGroup container, int position) {
        maybeInstantiateViews(container);
        return mViews.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "page " + position;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private void maybeInstantiateViews(ViewGroup container) {
        if (mViews.isEmpty()) {
            cardDetailsView = new CardDetailsView(mContext);
            cardDetailsView.setGoToBillingListener(mCardDetailsListener);

            BillingDetailsView billingDetailsView = new BillingDetailsView(mContext);
            billingDetailsView.setGoToCardDetailsListener(mBillingListener);

            mViews.add(cardDetailsView);
            mViews.add(billingDetailsView);

            container.addView(cardDetailsView);
            container.addView(billingDetailsView);
        }
    }
}