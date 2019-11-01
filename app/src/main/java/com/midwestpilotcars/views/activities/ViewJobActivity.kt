package com.midwestpilotcars.views.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.midwestpilotcars.R
import com.midwestpilotcars.base.BaseActivity
import com.midwestpilotcars.constants.AppConstants
import com.midwestpilotcars.models.getAllJobs.UPCOMING
import com.midwestpilotcars.views.startJob.StartJobActivity

class ViewJobActivity : BaseActivity(), View.OnClickListener {
    private var upcoming: UPCOMING? = null

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_btn_add_job -> {
                var startJobIntent = Intent(this, StartJobActivity::class.java)
                startJobIntent.putExtra(AppConstants.JOBS_UPCOMING, upcoming)
                startActivity(startJobIntent)
                finish()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewJobBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_job)
        supportActionBar!!.title = getString(R.string.view_job)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        upcoming = intent.getParcelableExtra<UPCOMING>(AppConstants.JOBS_UPCOMING)
        if (upcoming != null)
            setViewData(upcoming!!)
        setClickListeners()
    }

    private fun setClickListeners() {
        activityViewJobBinding.imgBtnAddJob.setOnClickListener(this)
    }

    private fun setViewData(upcoming: UPCOMING) {
        var startDateLayout = findViewById<View>(R.id.startDateLayout)
        startDateLayout.findViewById<TextView>(R.id.title).text = AppConstants.START_DATE
        startDateLayout.findViewById<TextView>(R.id.value).text = upcoming.jobStartDate

        var startLocationLayout = findViewById<View>(R.id.start_location_layout)
        startLocationLayout.findViewById<TextView>(R.id.title).text = AppConstants.START_LOCATION
        startLocationLayout.findViewById<TextView>(R.id.value).text = upcoming.jobStartingPoint

        var endLocationLayout = findViewById<View>(R.id.end_location_layout)
        endLocationLayout.findViewById<TextView>(R.id.title).text = AppConstants.END_LOCATION
        endLocationLayout.findViewById<TextView>(R.id.value).text = upcoming.jobEndingPoint

        var driverNameLayout = findViewById<View>(R.id.driver_name_layout)
        driverNameLayout.findViewById<TextView>(R.id.title).text = AppConstants.DRIVER_NAME
        driverNameLayout.findViewById<TextView>(R.id.value).text = upcoming.jobTruckDriverName

        var trucking_company_layout = findViewById<View>(R.id.trucking_company_layout)
        trucking_company_layout.findViewById<TextView>(R.id.title).text = AppConstants.TRUCKING_COMPANY
        trucking_company_layout.findViewById<TextView>(R.id.value).text = upcoming.truckingCompanyName

        var load_no_layout = findViewById<View>(R.id.load_no_layout)
        load_no_layout.findViewById<TextView>(R.id.title).text = AppConstants.LOAD_NUMBER
        load_no_layout.findViewById<TextView>(R.id.value).text = upcoming.jobLoadNumber

        var truck_no_layout = findViewById<View>(R.id.truck_no_layout)
        truck_no_layout.findViewById<TextView>(R.id.title).text = AppConstants.TRUCK_NUMBER
        truck_no_layout.findViewById<TextView>(R.id.value).text = upcoming.jobTruckNumber

        var load_description_layout = findViewById<View>(R.id.load_description_layout)
        load_description_layout.findViewById<TextView>(R.id.title).text = AppConstants.LOAD_DESCRIPTION
        load_description_layout.findViewById<TextView>(R.id.value).text = upcoming.jobLoadDescription

        var load_type_layout = findViewById<View>(R.id.load_type_layout)
        load_type_layout.findViewById<TextView>(R.id.title).text = AppConstants.LOAD_TYPE_NAME
        load_type_layout.findViewById<TextView>(R.id.value).text = upcoming.loadTypeName
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private lateinit var activityViewJobBinding: com.midwestpilotcars.databinding.ActivityViewJobBinding
    }
}
