package ua.rash1k.vkgroups.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiDocument
import com.vk.sdk.api.model.VKApiPhoto
import com.vk.sdk.api.model.VKAttachments
import droidninja.filepicker.FilePickerConst
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_create_post.*
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.event.SendCommentEventOnSusbscribe
import ua.rash1k.vkgroups.event.SendCreatedPostEventOnSubscribe
import ua.rash1k.vkgroups.event.UploadFileEventOnSubscribe
import ua.rash1k.vkgroups.event.UploadPhotoEventOnSubscribe
import ua.rash1k.vkgroups.models.view.CreatePostViewModel
import ua.rash1k.vkgroups.models.view.attachment.DocAttachmentViewModel
import ua.rash1k.vkgroups.models.view.attachment.ImageAttachmentViewModel
import ua.rash1k.vkgroups.ui.adapter.BaseAdapter
import ua.rash1k.vkgroups.ui.dialog.AddAttachmentDialogFragment
import java.io.File

class CreatePostActivity : BaseActivity() {

    lateinit var mType: String
    var mOwnerId: Int = 0
    var mId: Int = 0

    lateinit var mBaseAdapter: BaseAdapter

    lateinit var postCreateViewModel: CreatePostViewModel

    lateinit var attachments: VKAttachments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        fab.visibility = View.GONE

        setData(bundle)
        initRecyclerView()
        initToolbar()
    }


    override fun getMainContentLayout(): Int {
        return R.layout.activity_create_post
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.action_attach -> attach()
            R.id.action_post -> post()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setData(bundle: Bundle?) {
        if (bundle != null) {
            mType = bundle.getString("type")
            mOwnerId = bundle.getInt("owner_id")
            mId = bundle.getInt("id")
        }
        postCreateViewModel = CreatePostViewModel()
        attachments = VKAttachments()
    }

    private fun initToolbar() {
        supportActionBar?.setTitle(R.string.new_message_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
    }

    private fun initRecyclerView() {
        mBaseAdapter = BaseAdapter()
        mBaseAdapter.insertItem(postCreateViewModel)

        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = mBaseAdapter
    }

    //закрытие активити и передача сообщения об успехе в главное активити
    private fun postResult(response: VKResponse) {
        val intentResult = Intent()
        intentResult.putExtra("result", "OK")

        setResult(Activity.RESULT_OK, intentResult)
        finish()
    }


    private fun post() {
        if (postCreateViewModel.mMessage.isEmpty()) {
            Toast.makeText(this, R.string.add_message_text, Toast.LENGTH_LONG).show()
            return
        }

        val observable: ObservableOnSubscribe<VKResponse> = if (mType == "comment") {
            SendCommentEventOnSusbscribe(mOwnerId, mId, postCreateViewModel.mMessage, attachments)
        } else {
            SendCreatedPostEventOnSubscribe(mOwnerId, mId, postCreateViewModel.mMessage, attachments)
        }

        //создаем Observable и подписываем Observer на него
        Observable.create(observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<VKResponse> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(response: VKResponse) {
                        postResult(response)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        Toast.makeText(baseContext, R.string.message_not_published, Toast.LENGTH_LONG).show()
                    }
                })

    }

    private fun attach() {
        AddAttachmentDialogFragment().show(supportFragmentManager,
                AddAttachmentDialogFragment::class.java.simpleName)
    }


    //В зависимости от выбранного типа прикрепления вызывается активити из библиотеки,
    // отображающее список элементов для прикрепления
    // после выбора передает данные через контекст в метод onActivityResult CreatePostActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //в зависимости от типа элементов, полученных от библиотеки "FilePicker", вызываем uploadFile или uploadPhoto
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                FilePickerConst.REQUEST_CODE_PHOTO -> {
                    val photoPaths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA)

                    photoPaths.forEach { photoPath ->
                        val photo = ImageAttachmentViewModel(photoPath)
                        uploadPhoto(photo)
                    }
                }

                FilePickerConst.REQUEST_CODE_DOC -> {
                    val docsPath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS)

                    docsPath.forEach { pathFile ->
                        val file = File(pathFile)

                        val docModel = DocAttachmentViewModel(file)

                        uploadFile(docModel)
                    }
                }
            }
        }
    }

    private fun uploadPhoto(itemModel: ImageAttachmentViewModel) {
        progressBar.visibility = View.VISIBLE

        Observable.create(UploadPhotoEventOnSubscribe(itemModel.mPhotoUrl))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<VKApiPhoto> {
                    override fun onComplete() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(apiPhoto: VKApiPhoto) {
                        attachments.add(apiPhoto)
                        mBaseAdapter.insertItem(itemModel)
                        Log.d("attach", "photoUrl: " + apiPhoto.photo_130)
                        Log.d("attach", "compl")
                        Toast.makeText(baseContext, R.string.loading_completed, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        progressBar.visibility = View.GONE
                        Toast.makeText(baseContext, R.string.loading_failed, Toast.LENGTH_LONG).show()
                    }

                })
    }

    private fun uploadFile(docModel: DocAttachmentViewModel) {
        progressBar.visibility = View.VISIBLE

        Observable.create(UploadFileEventOnSubscribe(docModel.mFile))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<VKApiDocument> {
                    override fun onComplete() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(apiDoc: VKApiDocument) {
                        attachments.add(apiDoc)
                        mBaseAdapter.insertItem(docModel)
                        Log.d("attach", "photoUrl: " + apiDoc.id)
                        Log.d("attach", "compl")
                        Toast.makeText(baseContext, R.string.loading_completed, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        progressBar.visibility = View.GONE
                        Toast.makeText(baseContext, R.string.loading_failed, Toast.LENGTH_LONG).show()
                    }
                })
    }
}
