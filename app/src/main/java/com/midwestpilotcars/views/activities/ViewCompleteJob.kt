package com.midwestpilotcars.views.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.midwestpilotcars.R
import com.midwestpilotcars.constants.AppConstants
import com.midwestpilotcars.models.getAllJobs.COMPLETE
import com.midwestpilotcars.models.getAllJobs.UPCOMING

class ViewCompleteJob : AppCompatActivity() {
    private var upcoming: COMPLETE? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_complete_job)
        supportActionBar!!.title = getString(R.string.view_job)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        upcoming = intent.getParcelableExtra<COMPLETE>(AppConstants.JOBS_COMPLETE)
        if (upcoming != null){

        }
    }
}
