package ua.rash1k.vkgroups.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.NewsFeedPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView


open class NewsFeedFragment : BaseFeedFragment() {

    private val TAG = NewsFeedFragment::class.java.name


    @InjectPresenter
    lateinit var mPresenter: NewsFeedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.sApplicationComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//region
       /* //У Observable можем вызывать методы которые преобразуют Observable
        //flatMap принимает данные одним Observable и возвращает данные другим
        mWallApi.get(WallGetRequestModel(ownerId = -86529522).toMap())
                .flatMap { response: GetWallResponse ->
                    io.reactivex.Observable.fromIterable<WallItem?>(
                            //Здесь преобразуем данные с getWallResponse на WallItem
                            getWallList(response.response!!))
                }
                .flatMap { wallItem: WallItem ->
                    val baseItems = ArrayList<BaseViewModel>()

                    baseItems.add(NewsItemHeaderViewModel(wallItem))
                    baseItems.add(NewsItemBodyViewModel(wallItem))
                    baseItems.add(NewsItemFooterViewModel(wallItem))
                    io.reactivex.Observable.fromIterable(baseItems)

                }
                //Оператор toList преобразует все элементы излучаемые Rx-цепочкой в Observable
                //которыый излучает список из этих элементов
                //В нашем случае Rx-цепочка передает в него Observable с параметром BaseViewModel
                //А возвращает он List<BaseViewModel> список
                //Делается это чтобы добавлять объеты в BaseAdapter порционно(На каждую загрузку новый список)
                .toList()
                //Указывает поток в котором будет выполняться процесс Observable
                //В нашем случае все преобразования данных
                .subscribeOn(Schedulers.io())
                //Указывает поток в котором будет выполняться все последущие операции над излученными данными
                //Переданными в этот метод. Так-как мы вызываем этот метод перед .subscribe()(подпиской)
                //В этом потоке выполняется только добавление объектов BaseViewModel в BaseAdapter и
                //оповещение адаптера так его оповещение затрагивает UI-поток
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<List<BaseViewModel>> { listBaseItem ->
                    mBaseAdapter.addItems(listBaseItem)
                })*/
//endregion
        //-86529522
        //region
        /* mWallApi.get(WallGetRequestModel(ownerId = -86529522).toMap())
                 .enqueue(object : Callback<GetWallResponse> {
                     override fun onFailure(call: Call<GetWallResponse>?, t: Throwable?) {
                         t?.printStackTrace()
                         Log.d(TAG, "onFailure")
                     }

                     override fun onResponse(call: Call<GetWallResponse>,
                                             response: Response<GetWallResponse>) {

                         val wallItems: List<WallItem>? =
                                 response.body()?.response?.let { getWallList(it) }

                         val list = ArrayList<BaseViewModel>()
                         if (wallItems != null) {
                             for (wallItem in wallItems) {
                                 list.add(NewsItemHeaderViewModel(wallItem))
                                 list.add(NewsItemBodyViewModel(wallItem))
                                 list.add(NewsItemFooterViewModel(wallItem))
                             }
                         }
                         mBaseAdapter.addItems(list)
                     }
                 })*/
        //endregion

        //region
        /*mWallApi.get(WallGetRequestModel(ownerId = -86529522).toMap())
                .enqueue(object : Callback<GetWallResponse> {
                    override fun onFailure(call: Call<GetWallResponse>?, t: Throwable?) {
                        t?.printStackTrace()
                        Log.d(TAG, "onFailure")
                    }

                    override fun onResponse(call: Call<GetWallResponse>,
                                            response: Response<GetWallResponse>) {
    //                        Log.d(TAG, "onResponse ${response.body()?.response}")
                        activity?.toast("Likes ${response.body()?.response?.items?.get(0)?.likes?.count}")

                        val list: MutableList<NewsItemBodyViewModel> = mutableListOf()
                        val listItems = response.body()?.response?.items

                        if (listItems != null) {
                            for (item in listItems) {
                                list.add(NewsItemBodyViewModel(item))
                            }
                            mBaseAdapter.addItems(list)
                        }
                    }
                })*/
        //endregion
    }

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        return mPresenter
    }

    @Override
    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_news_feed_title
    }


}
