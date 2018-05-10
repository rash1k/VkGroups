package ua.rash1k.vkgroups.models.view.counter

import ua.rash1k.vkgroups.models.countable.Likes


class LikeCounterViewModel(mLikes: Likes?) : CounterViewModel(mLikes?.count ?: 0) {

    init {
        if (mLikes?.userLikes == 1) {
            setAccentColor()
        }
    }
}