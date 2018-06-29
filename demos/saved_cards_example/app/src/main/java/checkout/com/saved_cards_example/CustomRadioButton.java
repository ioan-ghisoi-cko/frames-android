package checkout.com.saved_cards_example;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.checkout.android_sdk.Utils.CardUtils;

/**
 * This class is used to extend the RadioButton element so it can hold information
 * about the card id and display an appropriate card icon.
 */
public class CustomRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private String  mType;
    private String mCardId;
    private Context mContext;

    public CustomRadioButton(Context context, String type, String cardId) {
        super(context);
        this.mType = type;
        this.mCardId = cardId;
        this.mContext = context;
        iniView();
    }

    public String getCardId() {
        return mCardId;
    }

    private void iniView() {
        setIconForType(mType);
        // Add some margin
        setWidth(getResources().getDisplayMetrics().widthPixels-15);
    }


    /**
     * This function will determine the type of card and it will add a card icon to the entry
     * corresponding with the type
     *
     * @param type the type of card
     */
    private void setIconForType(String type) {
        Drawable img = null;
        switch (type.toLowerCase()) {
            case "visa":
                img = mContext.getResources().getDrawable(CardUtils.Cards.VISA.resourceId);
                break;
            case "mastercard":
                img = mContext.getResources().getDrawable(CardUtils.Cards.MASTERCARD.resourceId);
                break;
            case "amex":
                img = mContext.getResources().getDrawable(CardUtils.Cards.AMEX.resourceId);
                break;
            case "dinersclub":
                img = mContext.getResources().getDrawable(CardUtils.Cards.DINERSCLUB.resourceId);
                break;
            case "discover":
                img = mContext.getResources().getDrawable(CardUtils.Cards.DISCOVER.resourceId);
                break;
            case "jcb":
                img = mContext.getResources().getDrawable(CardUtils.Cards.JCB.resourceId);
                break;
        }
        img.setBounds(0, 0, 68, 68);
        setCompoundDrawables(null, null, img, null);
        setCompoundDrawablePadding(5);
    }
}
