package com.diandou.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baselibrary.utils.BasePopupWindow;
import com.baselibrary.utils.CommonUtil;
import com.diandou.R;
import com.diandou.adapter.CommentListAdapter;

public class CommentListPopupWindow extends BasePopupWindow {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CommentListPopupWindow(Context context) {
        super(context);
    }

    @Override
    protected int animationStyle() {
        return R.style.PopupAnimation;
    }


    @Override
    protected View initContentView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.view_comment_list_popup_layout, null, false);
        View viewLayout = contentView.findViewById(R.id.view_layout);
        View close = contentView.findViewById(R.id.iv_close);
        RecyclerView recyclerView = contentView.findViewById(R.id.recyclerView);
        TextView tvMessageInput = contentView.findViewById(R.id.tv_message_input);
        tvMessageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog inputDialog = new InputDialog((Activity) context);
                inputDialog.setmOnTextSendListener(new InputDialog.OnTextSendListener() {
                    @Override
                    public void onTextSend(String msg) {

                    }
                });
                inputDialog.show();
            }
        });


        CommentListAdapter adapter = new CommentListAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.refreshData(CommonUtil.getImageListString());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                if (onClickListener != null) {
                    onClickListener.onClick(view, object);
                    dismiss();
                }
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return contentView;
    }

    @Override
    protected int initWidth() {
        return CommonUtil.getScreenWidth(context);
    }

    @Override
    protected int initHeight() {
        return -1;
    }
}