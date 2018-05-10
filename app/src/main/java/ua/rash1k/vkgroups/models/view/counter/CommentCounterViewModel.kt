package ua.rash1k.vkgroups.models.view.counter

import ua.rash1k.vkgroups.models.countable.Comments


class CommentCounterViewModel(mComments: Comments)
    : CounterViewModel(mComments.count) {
   /* init {
        if (mComments.canPost == 1) {
            setAccentColor()
        }
    }*/
}



