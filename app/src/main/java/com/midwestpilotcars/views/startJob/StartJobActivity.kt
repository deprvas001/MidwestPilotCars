package com.midwestpilotcars.views.startJob

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.midwestpilotcars.R
import com.midwestpilotcars.base.BaseActivity
import com.midwestpilotcars.callbacks.OnDefaultDataItemClicked
import com.midwestpilotcars.constants.AppConstants
import com.midwestpilotcars.databinding.ActivityStartJobBinding
import com.midwestpilotcars.helpers.AwsUtils
import com.midwestpilotcars.helpers.DialogUtils
import com.midwestpilotcars.helpers.Utils
import com.midwestpilotcars.models.CommonResponse
import com.midwestpilotcars.models.allDefaultModels.LOADTYPE
import com.midwestpilotcars.models.getAllJobs.UPCOMING
import com.midwestpilotcars.models.startJob.StartJobRequestModel
import com.midwestpilotcars.models.startJob.StartJobResponseModel
import com.midwestpilotcars.views.activities.MapActivity
import com.midwestpilotcars.views.home.MainActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_start_job.view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class StartJobActivity : BaseActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener, OnDefaultDataItemClicked, TransferListener {
    var upload_imageurl: String? = ""
    var load_type_id: String? = ""
    var time:String?=""
    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
    }

    override fun onStateChanged(id: Int, state: TransferState?) {
        if (state == TransferState.COMPLETED) {
            val imageURL = AppConstants.S3_BASE_URL + fileToS3!!.name
            Log.e("image url", imageURL)
            upload_imageurl = imageURL
            startJobRequestModel.jobPilotVehileOdometerReadingUrl = imageURL
        }
    }

    override fun onError(id: Int, ex: java.lang.Exception?) {
    }

    private var upcoming: UPCOMING? = null
    private var startJobRequestModel = StartJobRequestModel()
    private var fileToS3: File? = null


    lateinit var activityStartJobBinding: ActivityStartJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //activity_start_job
        activityStartJobBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_job)
        upcoming = intent.getParcelableExtra<UPCOMING>(AppConstants.JOBS_UPCOMING)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(resources.getString(R.string.start_job_activity))
        if (upcoming != null) {
            setViewData(upcoming!!)
        }
        activityStartJobBinding.startJobModel = startJobRequestModel
        setClickListeners()
    }

    private fun setViewData(upcoming: UPCOMING) {
        startJobRequestModel.jobId = upcoming!!.jobId

        var driverNameLayout = activityStartJobBinding.driverNameLayout
        driverNameLayout.findViewById<TextView>(R.id.title).text = AppConstants.DRIVER_NAME
        driverNameLayout.findViewById<TextView>(R.id.value).text = upcoming.jobTruckDriverName

        var trucking_company_layout = activityStartJobBinding.truckingCompanyLayout
        trucking_company_layout.findViewById<TextView>(R.id.title).text = AppConstants.TRUCKING_COMPANY
        trucking_company_layout.findViewById<TextView>(R.id.value).text = upcoming.truckingCompanyName

        var load_no_layout = activityStartJobBinding.loadNoLayout
        load_no_layout.findViewById<TextView>(R.id.title).text = AppConstants.LOAD_NUMBER
        load_no_layout.findViewById<TextView>(R.id.value).text = upcoming.jobLoadNumber

        var truck_no_layout = activityStartJobBinding.truckNoLayout
        truck_no_layout.findViewById<TextView>(R.id.title).text = AppConstants.TRUCK_NUMBER
        truck_no_layout.findViewById<TextView>(R.id.value).text = upcoming.jobTruckNumber

        var load_description_layout = activityStartJobBinding.loadDescriptionLayout
        load_description_layout.findViewById<TextView>(R.id.title).text = AppConstants.LOAD_DESCRIPTION
        load_description_layout.findViewById<TextView>(R.id.value).text = upcoming.jobLoadDescription

        var phone_no_layout = activityStartJobBinding.phoneNoView
        phone_no_layout.findViewById<TextView>(R.id.title).text = AppConstants.PHONE_NUMBER
        phone_no_layout.findViewById<TextView>(R.id.value).text = upcoming.jobTruckDriverPhoneNumber

        var cash_advance_layout = activityStartJobBinding.cashAdvanceView
        cash_advance_layout.findViewById<TextView>(R.id.title).text = AppConstants.CASH_IN_ADVANCE
        cash_advance_layout.findViewById<TextView>(R.id.value).text = upcoming.jobCashInAdvance

        var start_date_layout =  activityStartJobBinding.startDateLayout
        start_date_layout.findViewById<TextView>(R.id.title).text = AppConstants.START_DATE
        start_date_layout.findViewById<TextView>(R.id.value).text = upcoming.jobStartDate

        time = upcoming.jobStartDate.split(" ")[0]
        activityStartJobBinding.etSelectDate.setText(upcoming.jobStartDate)

        activityStartJobBinding.etLoadType.setText(upcoming.loadTypeName)
        activityStartJobBinding.etPerDayCost.setText(upcoming.jobPerDayCost)
        activityStartJobBinding.etPerMileCost.setText(upcoming.jobPayPerMileCost)
        activityStartJobBinding.etNoGoCost.setText(upcoming.jobNoGoesPrice)
        activityStartJobBinding.etDetentionPrice.setText(upcoming.jobDetentionPrice)

        activityStartJobBinding.etLoadNumber.setText(upcoming.jobLoadNumber)
        activityStartJobBinding.etLoadDesc.setText(upcoming.jobLoadDescription)
        activityStartJobBinding.etJobDot.setText(upcoming.jabDotNumber)
        activityStartJobBinding.etDriverName.setText(upcoming.jobTruckDriverName)
        activityStartJobBinding.etDriverNumber.setText(upcoming.jobTruckDriverPhoneNumber)
        activityStartJobBinding.etTruckNumber.setText(upcoming.jobTruckNumber)
        activityStartJobBinding.etTruckHeight.setText(upcoming.jobTruckHeight)
        activityStartJobBinding.etTruckWidth.setText(upcoming.jobTruckWidth)
        activityStartJobBinding.etTruckWeight.setText(upcoming.job_truck_weight)
        activityStartJobBinding.etTruckLength.setText(upcoming.job_truck_length)
        activityStartJobBinding.etJobComment.setText(upcoming.jobComments)
        activityStartJobBinding.etCashAdvanceView.setText(upcoming.jobCashInAdvance)

        load_type_id = upcoming.jobTruckLoadTypeId

        viewFocus()

    }

    private fun setClickListeners() {
        activityStartJobBinding.tvDateComment.setOnClickListener(this)
        activityStartJobBinding.tvPerMileCostComment.setOnClickListener(this)
        activityStartJobBinding.tvPerDayCostComment.setOnClickListener(this)
        activityStartJobBinding.tvNoGoCostComment.setOnClickListener(this)
        activityStartJobBinding.tvDetentionPriceComment.setOnClickListener(this)
        activityStartJobBinding.etLoadType.setOnClickListener(this)
        activityStartJobBinding.etStartLocation.setOnClickListener(this)
        activityStartJobBinding.etSelectDate.setOnClickListener(this)
        activityStartJobBinding.imgBtnAddJob.setOnClickListener(this)
        activityStartJobBinding.imgCameraOdoMeter.setOnClickListener(this)
        activityStartJobBinding.phoneNoView.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_miles_travelled_comment -> {
                Utils.toggleView(activityStartJobBinding.etDateComment)
            }
            R.id.tv_per_mile_cost_comment -> {
                Utils.toggleView(activityStartJobBinding.etPerMileCostComment)
            }
            R.id.tv_per_day_cost_comment -> {
                Utils.toggleView(activityStartJobBinding.etPerDayCostComment)
            }
            R.id.tv_no_go_cost_comment -> {
                Utils.toggleView(activityStartJobBinding.etNoGoCostComment)
            }
            R.id.tv_detention_price_comment -> {
                Utils.toggleView(activityStartJobBinding.etDetentionPriceComment)
            }
            R.id.phone_no_view -> {
                checkPermission()
            }

            R.id.et_load_type -> {
                selectLoadType()
            }
            R.id.et_start_location -> {
                getLocation(AppConstants.START_LOCATION_REQUEST_CODE)
            }
            R.id.et_select_date -> {
                selectDate()
            }
            R.id.img_btn_add_job -> {
                startJob()
//                openImagePicker()

            }
            R.id.img_camera_odo_meter -> {
                openImagePicker()
            }
        }
    }

    private fun openImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Pickup Image")
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAutoZoomEnabled(true)
                .start(this@StartJobActivity)
    }

    private fun selectLoadType() {
        DialogUtils.showDefaultDataDialog(this, AppConstants.LOAD_TYPE)
    }

    private fun selectDate() {
        DialogUtils.getCurrentDate(this)
    }

    override fun onDefaultItemClicked(defaultdata: Parcelable?) {
        if (defaultdata is LOADTYPE) run {
            startJobRequestModel.jobTruckLoadTypeId = defaultdata.loadTypeId
            load_type_id = defaultdata.loadTypeId
            activityStartJobBinding.etLoadType.setText(defaultdata.loadTypeName.toString())
        }
        DialogUtils.hideDefaultDataDialog(this)
    }


    private fun getLocation(requestCode: Int) {
        startActivityForResult(Intent(this, MapActivity::class.java), requestCode)
    }

    private fun startJob() {
        if (activityStartJobBinding.etLoadType.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_load_type))
            return
        }
        if (activityStartJobBinding.etOdoMeter.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_odo_meter))
            return
        } /*else if (upload_imageurl.isNullOrBlank()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_odo_meter_image))
            return
        }*/
        if (activityStartJobBinding.etStartLocation.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_start_location))
            return
        }
       /* if (startJobRequestModel.jobPilotDriverStartLocationLatitude.isEmpty() || startJobRequestModel.jobPilotDriverStartLocationLongitude.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_start_location_coordinates))
            return
        }*/
        if (activityStartJobBinding.etSelectDate.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_date))
            return
        }
        if (activityStartJobBinding.etPerMileCost.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_per_mile))
            return
        }
        if (activityStartJobBinding.etPerDayCost.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_per_day))
            return
        }
        if (activityStartJobBinding.etNoGoCost.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_no_go_day))
            return
        }
        if (activityStartJobBinding.etDetentionPrice.text.isEmpty()) {
            DialogUtils.showAlertDialog(this, getString(R.string.empty_detention_price))
            return
        } else {
            startJobCall()
        }
    }

    private fun startJobCall() {
        showProgressDialog(getString(R.string.starting_job))
        var startJobViewModel = ViewModelProviders.of(this).get(StartJobViewModel::class.java)
        startJobRequestModel.jobDetentionPrice = activityStartJobBinding.etDetentionPrice.text.toString()
        startJobRequestModel.jobDetentionPriceComment = activityStartJobBinding.etDetentionPriceComment.text.toString()
        startJobRequestModel.jobNoGoesPrice = activityStartJobBinding.etNoGoCost.text.toString()
        startJobRequestModel.jobNoGoesPriceComment = activityStartJobBinding.etNoGoCostComment.text.toString()
        startJobRequestModel.jobPerDayRate = activityStartJobBinding.etPerDayCost.text.toString()
        startJobRequestModel.jobPerDayRateComment = activityStartJobBinding.etPerDayCostComment.text.toString()
        startJobRequestModel.jobPayPerMileCost = activityStartJobBinding.etPerMileCost.text.toString()
        startJobRequestModel.jobStartDateTime = activityStartJobBinding.etSelectDate.text.toString()
        startJobRequestModel.jobStartDateTimeComment = activityStartJobBinding.etDateComment.text.toString()
        startJobRequestModel.jobPilotVehileOdometerReading = activityStartJobBinding.etOdoMeter.text.toString()
        startJobRequestModel.jobPilotVehileOdometerReadingUrl = upload_imageurl


        startJobRequestModel.jobLoadNumber = activityStartJobBinding.etLoadNumber.text.toString()
        startJobRequestModel.jobLoadDescription = activityStartJobBinding.etLoadDesc.text.toString()
        startJobRequestModel.jabDotNumber = activityStartJobBinding.etJobDot.text.toString()
        startJobRequestModel.jobTruckDriverName = activityStartJobBinding.etDriverName.text.toString()
        startJobRequestModel.jobTruckDriverPhoneNumber = activityStartJobBinding.etDriverNumber.text.toString()
        startJobRequestModel.jobTruckNumber = activityStartJobBinding.etTruckNumber.text.toString()
        startJobRequestModel.jobTruckHeight = activityStartJobBinding.etTruckHeight.text.toString()
        startJobRequestModel.jobTruckWidth = activityStartJobBinding.etTruckWidth.text.toString()
        startJobRequestModel.job_truck_weight = activityStartJobBinding.etTruckWeight.text.toString()
        startJobRequestModel.job_truck_length = activityStartJobBinding.etTruckLength.text.toString()
        startJobRequestModel.jobCashInAdvance = activityStartJobBinding.etCashAdvanceView.text.toString()
        startJobRequestModel.jobStartDateTime = activityStartJobBinding.etSelectDate.text.toString()
        activityStartJobBinding.etJobComment.text.toString()

        startJobRequestModel.jobTruckLoadTypeId = load_type_id

        startJobViewModel.startJob(this, startJobRequestModel).observe(
                this,
                android.arch.lifecycle.Observer { commonResponse: CommonResponse? ->
                    if (commonResponse!!.isStatus) {
                        val startJobResponseModel = commonResponse.data as StartJobResponseModel
                        hideProgressDialog()
                        Toast.makeText(this, startJobResponseModel.message, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        hideProgressDialog()
                        if (commonResponse.data == null) {
                            Toast.makeText(this, getString(R.string.auth_failure), Toast.LENGTH_SHORT).show()
                        } else {
                            val ex = commonResponse.data as Exception
                            if (ex.message!!.contains("400")) {
                                Toast.makeText(this, getString(R.string.job_alredy_in_progress), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val myCalendar = Calendar.getInstance()
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "yyyy-MM-dd" //Change as you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        if(sdf.format(myCalendar.time) >=time.toString() ){
            val myFormat = "yyyy-MM-dd hh:mm:ss"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
          //  Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show()
            activityStartJobBinding.etSelectDate.setText(sdf.format(myCalendar.time))
        }else{
            DialogUtils.showAlertDialog(this, getString(R.string.select_date_lesser))
        }


    }

    @SuppressLint("ResourceType")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            AppConstants.START_LOCATION_REQUEST_CODE -> {
                if (data != null) {
                    activityStartJobBinding.etStartLocation.setText(data.getStringExtra("Address"))
                    startJobRequestModel.jobPilotDriverStartLocation = data.getStringExtra("Address")
                    startJobRequestModel.jobPilotDriverStartLocationLatitude = data.getStringExtra("Lat")
                    startJobRequestModel.jobPilotDriverStartLocationLongitude = data.getStringExtra("Long")
                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    val resultUri = result.uri
                    val imagePath = resultUri.path
                    fileToS3 = File(imagePath!!)
                    AwsUtils.uploadFile(fileToS3!!, this@StartJobActivity)
                    activityStartJobBinding.imgCameraOdoMeter.setColorFilter(resources.getColor(R.color.colorPrimary))
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this, "Cropping failed: " + result.error, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun openDialer() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1122334455"))
        startActivity(intent)
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                DialogUtils.showAlertDialog(this, getString(R.string.enable_permission))
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
                DialogUtils.showAlertDialog(this, getString(R.string.enable_permission))
            }
            return
        }
    }

   private  fun callPhone() {
        val number = upcoming!!.jobTruckDriverPhoneNumber
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
    }

  private fun viewFocus() {
        val number = upcoming!!.jobTruckDriverPhoneNumber
        if (number.isBlank()){
            activityStartJobBinding.phoneNoView.visibility = View.GONE
            activityStartJobBinding.etDriverNumber.isEnabled = true

        } else{
            activityStartJobBinding.phoneNoView.visibility = View.VISIBLE
            activityStartJobBinding.etDriverNumber.isEnabled = false
        }

      val cashAdvance = upcoming!!.jobCashInAdvance
      if(cashAdvance.isBlank()){
          activityStartJobBinding.cashAdvanceView.visibility = View.GONE
          activityStartJobBinding.etCashAdvanceView.isEnabled = true
      }else{
          activityStartJobBinding.cashAdvanceView.visibility = View.VISIBLE
          activityStartJobBinding.etCashAdvanceView.isEnabled = false
      }
    }
}
