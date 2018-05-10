package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import kotlinx.android.synthetic.main.fragment_webview.*
import ua.rash1k.vkgroups.R


class ImageFragment : BaseFragment() {


    lateinit var webView: WebView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = webview

        //Встроенные элементы упарвления зумом
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false

        /*
        * Должен ли WebView включать поддержку метатекста HTML «viewport» или
        * использовать широкий видовой экран. Когда значение параметра равно false, ширина макета
        * всегда устанавливается на ширину элемента управления WebView в пикселях, не зависящих от
        * устройства (CSS). Когда значение истинно и страница содержит метатег viewport,
        * используется значение ширины, указанного в теге. Если страница не содержит тег или не
        * имеет ширины, то будет использоваться широкий видовой экран.
        * */
        webView.settings.useWideViewPort = true


        /*
        * Загружать ли WebView страницы в режиме обзора, т. Е. Масштабирует содержимое, чтобы оно
        * поместилось на экране по ширине. Этот параметр учитывается, когда ширина содержимого
        * больше ширины элемента управления WebView, например, когда включена функция getUseWideViewPort ().
        * */
        webView.settings.loadWithOverviewMode = true

        webView.setBackgroundColor(resources.getColor(R.color.colorDefaultWhite))

        webView.loadUrl(arguments?.getString("url"))
    }

    companion object {
        fun newInstance(url: String): ImageFragment {
            val args = Bundle()
            args.putString("url", url)
            val imageFragment = ImageFragment()
            imageFragment.arguments = args
            return imageFragment
        }
    }

    override fun getMainContentLayout(): Int {
        return R.layout.fragment_webview
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_image
    }
}