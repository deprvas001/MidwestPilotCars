package com.midwestpilotcars.models.addDayExpenses

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.google.gson.Gson
import com.midwestpilotcars.R
import com.midwestpilotcars.adapters.ExpensesAdapter
import com.midwestpilotcars.base.BaseActivity
import com.midwestpilotcars.callbacks.ImageClickCallBack
import com.midwestpilotcars.constants.AppConstants
import com.midwestpilotcars.databinding.ActivityAddDayExpensesBinding
import com.midwestpilotcars.enums.ExpensesEnum
import com.midwestpilotcars.helpers.AwsUtils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.lang.Exception

class AddDayExpenses : AppCompatActivity(), ImageClickCallBack, TransferListener {
    private var fileToS3: File? = null
    private lateinit var currentExpenesModel: GasExpensesModel;
    private var gasExpenes = ArrayList<GasExpensesModel>()
    private lateinit var activityAddDayExpensesBinding: ActivityAddDayExpensesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddDayExpensesBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_day_expenses)
        activityAddDayExpensesBinding.gasRecyclerView.layoutManager = LinearLayoutManager(this)
        activityAddDayExpensesBinding.gasRecyclerView.setHasFixedSize(true)
        setViews()
        setClickListners()
    }

    private fun setClickListners() {
        activityAddDayExpensesBinding.addGasExpenses.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                gasExpenes.add(GasExpensesModel())
                newJobsAdapter.notifyDataSetChanged()
                var gson = Gson()
                Log.e("Gas Expenes", gson.toJson(gasExpenes))
            }
        })
    }

    private lateinit var newJobsAdapter: ExpensesAdapter

    private fun setViews() {
        gasExpenes.add(GasExpensesModel())
        newJobsAdapter = ExpensesAdapter(gasExpenes, ExpensesEnum.GAS, this)
        activityAddDayExpensesBinding.gasRecyclerView.adapter = newJobsAdapter
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
                .start(this@AddDayExpenses)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == BaseActivity.RESULT_OK) {
                    val resultUri = result.uri
                    val imagePath = resultUri.path
                    fileToS3 = File(imagePath!!)
                    AwsUtils.uploadFile(fileToS3!!, this@AddDayExpenses)
                    Toast.makeText(this@AddDayExpenses, imagePath, Toast.LENGTH_LONG).show()
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this, "Cropping failed: " + result.error, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {


    }

    override fun onStateChanged(id: Int, state: TransferState?) {
        if (state == TransferState.COMPLETED) {
            val imageURL = AppConstants.S3_BASE_URL + fileToS3!!.name
            currentExpenesModel.expenseImage = imageURL
            var gson = Gson()
            Log.e("Gas Expenes", gson.toJson(gasExpenes))
        }
    }

    override fun onError(id: Int, ex: Exception?) {
    }

    override fun clickImage(position: Int, expensesType: ExpensesEnum) {
        if (expensesType == ExpensesEnum.GAS) {
            currentExpenesModel = gasExpenes[position]
            openImagePicker()
        }
    }
}
