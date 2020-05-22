package com.diandou.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.NavData;
import com.diandou.R;
import com.diandou.adapter.PagerAdapter;
import com.diandou.adapter.TabTypeAdapter;
import com.diandou.databinding.ActivityTabTypeBinding;
import com.diandou.fragment.HomeItemFragment;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;
import com.okhttp.SendRequest;
import com.okhttp.callbacks.GenericsCallback;
import com.okhttp.sample_okhttp.JsonGenericsSerializator;

import okhttp3.Call;

public class TabTypeActivity extends BaseActivity {

    private ActivityTabTypeBinding binding;
    private TabTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_type);

        adapter = new TabTypeAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                Intent intent = new Intent();
                intent.putExtra("position", (int) object);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        SendRequest.commonNav(new GenericsCallback<NavData>(new JsonGenericsSerializator()) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(NavData response, int id) {
                if (response.getCode() == 200 && response.getData() != null) {
                    for (int i = 0; i < response.getData().size(); i++) {
                        adapter.refreshData(response.getData());
                    }
                } else {
                    ToastUtils.showShort(TabTypeActivity.this, response.getMsg());
                }
            }

        });

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
