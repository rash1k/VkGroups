package ua.rash1k.vkgroups.di.component

import dagger.Component
import ua.rash1k.vkgroups.common.manager.NetworkManager
import ua.rash1k.vkgroups.di.module.AppModule
import ua.rash1k.vkgroups.di.module.ManagerModule
import ua.rash1k.vkgroups.di.module.RestModule
import ua.rash1k.vkgroups.fcm.services.MyFirebaseMessagingService
import ua.rash1k.vkgroups.models.view.*
import ua.rash1k.vkgroups.mvp.presenter.*
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.activity.MainActivity
import ua.rash1k.vkgroups.ui.fragment.*
import ua.rash1k.vkgroups.ui.view.holder.NewsItemBodyHolder
import ua.rash1k.vkgroups.ui.view.holder.NewsItemFooterHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.ImageAttachmentHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.VideoAttachmentViewHolder
import javax.inject.Singleton

@Singleton
//Аннотация которая связывает модули частями, которые запрашивают зависимости
//@Component(modules = [AppModule::class, ManagerModule::class, RestModule::class, ContextModule::class])
@Component(modules = [AppModule::class, ManagerModule::class, RestModule::class])
interface ApplicationComponent {

    //Методы для Inject зависимойстей
//    fun inject(application: MyApplication)

    //activities
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

    //fragments
    fun inject(newsFeedFragment: NewsFeedFragment)

    fun inject(memberFragment: MemberFragment)

    fun inject(topicFragment: TopicFragment)

    fun inject(newsFragment: NewsFragment)

    fun inject(openedPostFragment: OpenedPostFragment)

    fun inject(commentsFragment: CommentsFragment)

    fun inject(openedCommentFragment: OpenedCommentFragment)

    fun inject(topicCommentsFragment: TopicCommentsFragment)



    //holders
    fun inject(viewHolder: NewsItemBodyHolder)

    fun inject(viewHolder: NewsItemFooterHolder)

    fun inject(viewHolder: NewsFeedViewModel.NewsFeedViewHolder)

    fun inject(viewHolder: ImageAttachmentHolder)

    fun inject(viewHolder: VideoAttachmentViewHolder)

    fun inject(viewHolder: CommentFooterViewModel.CommentFooterHolder)

    fun inject(viewHolder: OpenedPostHeaderViewModel.OpenedPostHeaderHolder)

    fun inject(viewHolder: OpenedPostRepostHeaderViewModel.OpenedPostRepostViewHolder)

    fun inject(viewHolder: CommentBodyViewModel.CommentBodyViewHolder)

    fun inject(viewHolder: TopicViewModel.TopicViewHolder)

    //presenter
    fun inject(newsFeedPresenter: NewsFeedPresenter)

    fun inject(newsPresenter: NewsPresenter)

    fun inject(presenter: MainPresenter)

    fun inject(presenter: MembersPresenter)

    fun inject(presenter: BoardPresenter)

    fun inject(presenter: InfoPresenter)

    fun inject(presenter: OpenedPostPresenter)

    fun inject(presenter: CommentsPresenter)

    fun inject(presenter: OpenedCommentPresenter)

    fun inject(presenter: TopicCommentsPresenter)

    fun inject(presenter: InfoLinksPresenter)

    fun inject(presenter: InfoContactsPresenter)

    fun inject(presenter: GroupsPresenter)


    //NetworkManager
    fun inject(networkManager: NetworkManager)

    //Services
    fun inject(service: MyFirebaseMessagingService)

}