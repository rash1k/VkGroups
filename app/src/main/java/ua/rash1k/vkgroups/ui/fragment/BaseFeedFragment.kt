package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.fragment_feed.*
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyLinearLayoutManager
import ua.rash1k.vkgroups.common.utils.toast
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.ui.adapter.BaseAdapter


abstract class BaseFeedFragment : BaseFragment(), BaseFeedView {

    protected lateinit var mRecyclerView: RecyclerView

    protected lateinit var mBaseAdapter: BaseAdapter

    protected lateinit var mSwipeRefLayout: SwipeRefreshLayout

    protected lateinit var mBaseFeedPresenter: BaseFeedPresenter<BaseFeedView>

    abstract fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView>


    //Бесконечный список
    protected var isWithEndlessList: Boolean = false

    override fun getMainContentLayout(): Int {
        return R.layout.fragment_feed
    }

    abstract override fun onCreateToolbarTitle(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWithEndlessList = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSwipeToRefreshLayout(view)
        setUpRecyclerView(view)
        setUpAdapter()

        mBaseFeedPresenter = onCreateFeedPresenter()
        mBaseFeedPresenter.loadStart()
    }


    private fun setUpSwipeToRefreshLayout(rootView: View) {
//        mSwipeRefLayout = swipe_refresh_layout
        mSwipeRefLayout = rootView.findViewById(R.id.swipe_refresh_layout)
        mSwipeRefLayout.setColorSchemeResources(R.color.colorAccent)
        //Для обновления данных при свайпе вниз
        mSwipeRefLayout.setOnRefreshListener { mBaseFeedPresenter.loadRefresh() }

    }

    private fun setUpRecyclerView(rootView: View) {
        mRecyclerView = rootView.findViewById(R.id.recycler_view_list)
        //Добавляем кастомный менеджер который проверяет нужно ли загружать новые элементы
        //и скроллистенер, который слушает список и оповещает когда он скролится

        val myLinearLayoutManager = MyLinearLayoutManager(getBaseActivity())
        mRecyclerView.layoutManager = myLinearLayoutManager

        if (isWithEndlessList) {
            mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    if (myLinearLayoutManager.isOnNextPagePosition()) {
                        mBaseFeedPresenter.loadNext(mBaseAdapter.getRealItemCount())
                    }
                }
            })
        }
        (mRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }

    private fun setUpAdapter() {
        mBaseAdapter = BaseAdapter()
        recycler_view_list.adapter = mBaseAdapter
    }

    override fun showRefreshing() {
        mSwipeRefLayout.isRefreshing = true
    }

    override fun hideRefreshing() {
        mSwipeRefLayout.isRefreshing = false
    }

    override fun showListProgress() {
        activity?.progressBar?.visibility = View.VISIBLE
    }

    override fun hideListProgress() {
        activity?.progressBar?.visibility = View.GONE

    }

    override fun showError(messageError: String) {
        getBaseActivity().toast(messageError)
    }

    override fun setItems(items: List<BaseViewModel>) {
        mBaseAdapter.setItems(items)
    }

    override fun addItems(items: List<BaseViewModel>) {
        mBaseAdapter.addItems(items)
    }
}