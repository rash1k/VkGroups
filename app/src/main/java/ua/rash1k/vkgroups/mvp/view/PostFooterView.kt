package ua.rash1k.vkgroups.mvp.view

import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.models.view.counter.LikeCounterViewModel


interface PostFooterView {

    fun like(likes: LikeCounterViewModel)

    fun openComments(wallItem: WallItem)
}