package com.porterlee.transfer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SoftKeyboardHandledConstraintLayout extends ConstraintLayout {
    private boolean isKeyboardShown;
    private SoftKeyboardVisibilityChangeListener listener;

    public SoftKeyboardHandledConstraintLayout(Context context) {
        super(context);
    }

    public SoftKeyboardHandledConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SoftKeyboardHandledConstraintLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // Keyboard is hidden <<< RIGHT
            if (isKeyboardShown) {
                isKeyboardShown = false;
                if (listener != null)
                    listener.onSoftKeyboardHide();
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();
        if (actualHeight > proposedHeight) {
            // Keyboard is shown
            if (!isKeyboardShown) {
                isKeyboardShown = true;
                if (listener != null)
                    listener.onSoftKeyboardShow();
            }
        } else {
            // Keyboard is hidden <<< this doesn't work sometimes, so don't use it
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnSoftKeyboardVisibilityChangeListener(SoftKeyboardVisibilityChangeListener listener) {
        this.listener = listener;
    }

    // Callback
    public interface SoftKeyboardVisibilityChangeListener {
        void onSoftKeyboardShow();
        void onSoftKeyboardHide();
    }
}