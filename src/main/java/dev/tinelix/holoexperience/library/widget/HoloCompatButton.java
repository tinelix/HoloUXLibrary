package dev.tinelix.holoexperience.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import dev.tinelix.holoexperience.library.R;

@SuppressLint("AppCompatCustomView")
public class HoloCompatButton extends Button {

    private boolean mTextAllCaps;
    private String mOriginalText;

    public HoloCompatButton(Context context) {
        super(context);
    }

    public HoloCompatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.HoloCompatButton, 0, 0
        );

        mTextAllCaps = a.getBoolean(R.styleable.HoloCompatButton_textAllCaps, false);

        mOriginalText = getText().toString();

        setAllCaps(mTextAllCaps);

        a.recycle();
    }

    public HoloCompatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.HoloCompatButton, 0, 0
        );

        mTextAllCaps = a.getBoolean(R.styleable.HoloCompatButton_textAllCaps, false);

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
