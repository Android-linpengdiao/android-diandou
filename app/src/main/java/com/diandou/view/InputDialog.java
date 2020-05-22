package com.diandou.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.diandou.R;


public class InputDialog extends Dialog{

    private InputMethodManager mInputMethodManager;
    private EditText mEditText;
    private TextView mTextView;
    private Activity mActivity;

    public InputDialog(Activity activity) {
        super(activity);
        mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.mActivity = activity;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setWindow();
    }

    private void setWindow() {
        Window window = getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, getWindow().getAttributes().height);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.dimAmount = 0.0f;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_comment_input_layout);
        mEditText = findViewById(R.id.et_message_input);
        mTextView = findViewById(R.id.tv_message_send);
        mEditText.requestFocus();
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage(mEditText.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(mEditText.getText().toString().trim());
            }
        });
    }

    @Override
    public void dismiss() {
        if (mInputMethodManager != null) {
            mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        }
        super.dismiss();
    }

    public void setmOnTextSendListener(OnTextSendListener onTextSendListener) {
        this.mOnTextSendListener = onTextSendListener;
    }

    private OnTextSendListener mOnTextSendListener;

    public interface OnTextSendListener {
        void onTextSend(String msg);
    }

    public void sendMessage(String messageText) {

        if (mOnTextSendListener != null) {
            mOnTextSendListener.onTextSend(messageText);
            mEditText.setText("");
            dismiss();
        }
    }


}
