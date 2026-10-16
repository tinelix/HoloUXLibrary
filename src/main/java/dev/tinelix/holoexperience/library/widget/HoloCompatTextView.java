package dev.tinelix.holoexperience.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import dev.tinelix.holoexperience.library.R;

@SuppressLint("AppCompatCustomView")
public class HoloCompatTextView extends TextView {

    private boolean mTextAllCaps;
    private String mOriginalText;

    public HoloCompatTextView(Context context) {
        super(context);
    }

    public HoloCompatTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.HoloCompatTextView, 0, 0
        );

        mTextAllCaps = a.getBoolean(R.styleable.HoloCompatTextView_textAllCaps, false);

        mOriginalText = getText().toString();

        setAllCaps(mTextAllCaps);

        a.recycle();
    }

    public HoloCompatTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.HoloCompatTextView, 0, 0
        );

        mTextAllCaps = a.getBoolean(R.styleable.HoloCompatTextView_textAllCaps, false);

        mOriginalText = getText().toString();

        setAllCaps(mTextAllCaps);

        a.recycle();
    }

    @Override
    public void setAllCaps(boolean value) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.mTextAllCaps = value;

            setText(value ? mOriginalText.toUpperCase() : mOriginalText);
            return;
        }
        super.setAllCaps(value);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(mTextAllCaps)
            super.setText(text.toString().toUpperCase(), type);
        else
            super.setText(text, type);
    }
}
