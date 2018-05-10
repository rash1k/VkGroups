package ua.rash1k.vkgroups.mvp.view

import ua.rash1k.vkgroups.models.view.NewsItemFooterViewModel


interface OpenedPostView : BaseFeedView {

    fun setFooter(viewModel: NewsItemFooterViewModel)
}