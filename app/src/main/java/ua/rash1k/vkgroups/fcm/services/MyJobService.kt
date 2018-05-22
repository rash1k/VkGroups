package ua.rash1k.vkgroups.fcm.services

import android.os.Build
import android.support.annotation.RequiresApi
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MyJobService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        return true
    }


}