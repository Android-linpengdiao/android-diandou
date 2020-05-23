package com.diandou.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.baselibrary.utils.CommonUtil;
import com.baselibrary.utils.ToastUtils;
import com.diandou.R;
import com.diandou.adapter.SearchHistoryAdapter;
import com.diandou.adapter.WorkAdapter;
import com.diandou.databinding.ActivitySearchBinding;
import com.diandou.db.DBManager;
import com.diandou.view.FlowLayoutManager;
import com.diandou.view.GridItemDecoration;
import com.diandou.view.OnClickListener;
import com.diandou.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySearchBinding binding;
    private SearchHistoryAdapter searchHistoryAdapter;
    private WorkAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        binding.searchBack.setOnClickListener(this);
        binding.searchContentDelete.setOnClickListener(this);
        binding.deleteSearchHistory.setOnClickListener(this);

        List<String> searchHistorys = DBManager.getInstance().querySearchHistory(0);
        binding.searchHistoryRecyclerView.addItemDecoration(new SpaceItemDecoration(CommonUtil.dip2px(this, 10)));
        binding.searchHistoryRecyclerView.setLayoutManager(new FlowLayoutManager());
        searchHistoryAdapter = new SearchHistoryAdapter(this);
        binding.searchHistoryRecyclerView.setAdapter(searchHistoryAdapter);
        searchHistoryAdapter.refreshData(searchHistorys);

        searchResultAdapter = new WorkAdapter(this);
        binding.searchResultRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this);
        builder.color(R.color.transparent);
        builder.size(CommonUtil.dip2px(this, 10));
        binding.searchResultRecyclerView.addItemDecoration(new GridItemDecoration(builder));
        binding.searchResultRecyclerView.setAdapter(searchResultAdapter);

        searchHistoryAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view, Object object) {
                binding.etSearch.setText((String) object);
            }

            @Override
            public void onLongClick(View view, Object object) {

            }
        });

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = binding.etSearch.getText().toString().trim();
                    if (!CommonUtil.isBlank(content)) {
                        // 隐藏键盘
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        DBManager.getInstance().insertSearchContent(0, content);
                        searchHistoryAdapter.refreshData(DBManager.getInstance().querySearchHistory(0));
                        initSearchView(content);
                    } else {
                        ToastUtils.showShort(SearchActivity.this, "请输入搜索内容");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initSearchView(String content) {
        binding.searchHistoryView.setVisibility(CommonUtil.isBlank(content) ? View.VISIBLE : View.GONE);
        binding.searchResultRecyclerView.setVisibility(CommonUtil.isBlank(content) ? View.GONE : View.VISIBLE);
//        searchResultAdapter.refreshData(CommonUtil.isBlank(content) ? new ArrayList<String>() : CommonUtil.getImageListString());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_search_history:
                DBManager.getInstance().deleteSearchHistory(0);
                searchHistoryAdapter.refreshData(DBManager.getInstance().querySearchHistory(0));
                break;
            case R.id.search_back:
                finish();
                break;
            case R.id.search_content_delete:
                binding.etSearch.setText("");
                initSearchView(null);
                break;
        }
    }
}
