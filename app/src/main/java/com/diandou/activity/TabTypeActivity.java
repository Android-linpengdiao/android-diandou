package com.diandou.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.baselibrary.utils.CommonUtil;
import com.diandou.R;
import com.diandou.adapter.TabTypeAdapter;
import com.diandou.databinding.ActivityTabTypeBinding;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;

public class TabTypeActivity extends BaseActivity {

    private ActivityTabTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_type);

        TabTypeAdapter adapter = new TabTypeAdapter(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setAdapter(adapter);
        adapter.refreshData(CommonUtil.getTextTabListString());
        adapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                Intent intent = new Intent();
                intent.putExtra("position",(int)object);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onLongClick(View view, Object object) {

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
