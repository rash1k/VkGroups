package ua.rash1k.vkgroups.models.view.counter

import ua.rash1k.vkgroups.models.countable.Reposts


class RepostCounterViewModel(mReposts: Reposts)
    : CounterViewModel(mReposts.count) {

    init {
        if (mReposts.userReposted == 1) {
            setAccentColor()
        }
    }
}