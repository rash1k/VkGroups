package ua.rash1k.vkgroups.ui.dialog

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import droidninja.filepicker.FilePickerBuilder
import ua.rash1k.vkgroups.R


//show dialog add attachment
class AddAttachmentDialogFragment : DialogFragment() {


    companion object {
        const val FIND_IMAGE_PERMISSION_CODE = 1
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                        context!!, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                val builder = AlertDialog.Builder(context!!)
                builder.setMessage(R.string.permission_explanation)
                builder.setPositiveButton(android.R.string.ok) { _, _ ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.requestPermissions(activity!!,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                FIND_IMAGE_PERMISSION_CODE)
                    }
                }

                builder.create().show()

            } else {
                ActivityCompat.requestPermissions(activity!!,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        } else {

            //в зависимости от выбранного типа прикрепления вызывается активити из библиотеки,
            // отображающее список элементов для прикрепления
            // после выбора передает данные через контекст в метод onActivityResult CreatePostActivity
            val builder = AlertDialog.Builder(context!!)

            builder.setTitle("Attachment")
            builder.setItems(arrayOf("Photo", "Document")) { _, which ->
                when (which) {
                    0 -> FilePickerBuilder.getInstance().setMaxCount(10).showFolderView(false).pickPhoto(activity)
                    1 -> FilePickerBuilder.getInstance().setMaxCount(10).pickFile(activity)
                }
            }

            return builder.create()
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            FIND_IMAGE_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] ==
                                PackageManager.PERMISSION_GRANTED)) {

                    //в зависимости от выбранного типа прикрепления вызывается активити из библиотеки,
                    // отображающее список элементов для прикрепления
                    // после выбора передает данные через контекст в метод onActivityResult CreatePostActivity
                    val builder = AlertDialog.Builder(context!!)

                    builder.setTitle("Attachment")
                    builder.setItems(arrayOf("Photo", "Document")) { _, which ->
                        when (which) {
                            0 -> FilePickerBuilder.getInstance().setMaxCount(10)
                                    .showFolderView(false).pickPhoto(activity)
                            1 -> FilePickerBuilder.getInstance().setMaxCount(10).pickFile(activity)
                        }
                    }
                } else {

                }
                return
            }
        }
    }
}