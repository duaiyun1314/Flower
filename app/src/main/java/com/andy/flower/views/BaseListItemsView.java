package com.andy.flower.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andy.flower.R;
import com.andy.flower.presenter.BasePresenter;
import com.andy.flower.presenter.ListContract;
import com.andy.flower.presenter.ListPresenter;
import com.andy.flower.utils.recyclerheaderutils.ExStaggeredGridLayoutManager;
import com.andy.flower.utils.recyclerheaderutils.HeaderAndFooterRecyclerViewAdapter;
import com.andy.flower.utils.recyclerheaderutils.HeaderSpanSizeLookup;
import com.andy.flower.utils.recyclerheaderutils.RecyclerViewUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 16-6-12.
 */
public class BaseListItemsView<P extends ListPresenter> extends FrameLayout implements ListContract.IView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.list_view)
    RecyclerView listView;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout layoutRefresh;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.load_fail)
    TextView loadFail;
    @BindView(R.id.load_empty)
    TextView loadEmpty;

    protected int colorPrimary;
    protected int colorPrimaryDark;
    protected int colorAccent;
    protected Context mContext;
    protected HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;

    protected P mPresenter;

    public BaseListItemsView(Context context) {
        this(context, null);
    }

    public BaseListItemsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseListItemsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View.inflate(context, R.layout.layout_baselist, this);
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        initRes();
        initView();

    }

    protected P createPresenter() {
        return null;
    }

    private void initRes() {
        TypedArray array = getContext().obtainStyledAttributes(new int[]{R.attr.colorPrimary,
                R.attr.colorPrimaryDark,
                R.attr.colorAccent
        });
        colorPrimary = array.getColor(0, mContext.getResources().getColor(R.color.colorPrimary));
        colorPrimaryDark = array.getColor(1, mContext.getResources().getColor(R.color.colorPrimaryDark));
        colorAccent = array.getColor(2, mContext.getResources().getColor(R.color.colorAccent));
        array.recycle();
    }

    private void initView() {
        layoutRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        layoutRefresh.setOnRefreshListener(this);
        layoutRefresh.setColorSchemeColors(colorPrimary, colorPrimaryDark, colorAccent);
        layoutRefresh.setDistanceToTriggerSync(150);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mPresenter.getAdapter());
        listView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        listView.setLayoutManager(getLayoutManager());
        RecyclerViewUtils.setHeaderView(listView, createHeadView());

    }

    @Override
    public void showLoading(boolean show) {
        loading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showEmpty(boolean show) {
        loadEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFail(boolean show) {
        loadFail.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void update(Object... args) {

    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }

    @Override
    public void onRefresh() {
        mPresenter.loadNew();
    }

    protected View createHeadView() {
        return null;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        /*ExStaggeredGridLayoutManager manager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) listView.getAdapter(), manager.getSpanCount()));*/
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        return manager;
    }
}
