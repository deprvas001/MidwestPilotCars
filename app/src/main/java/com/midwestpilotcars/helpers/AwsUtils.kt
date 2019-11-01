package com.midwestpilotcars.helpers

import android.content.Context
import android.util.Log

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.midwestpilotcars.MidwestPilotCars
import com.midwestpilotcars.constants.AppConstants

import java.io.File

 object AwsUtils {
    private var transferUtility: TransferUtility? = null
    private var s3Bucket: AmazonS3? = null
    private var credentialsProvider: CognitoCachingCredentialsProvider? = null


    fun uploadFile(file: File, transferListener: TransferListener) {
        if (credentialsProvider == null) {
            setupAmazonS3(file, transferListener)
        } else {
            setFileToUpload(file, transferListener)
        }
    }

    private fun setFileToUpload(fileToUpload: File, transferListener: TransferListener) {
        transferUtility = TransferUtility.builder().s3Client(s3Bucket).context(MidwestPilotCars.getInstance().applicationContext).build()
        val transferObserver = transferUtility!!.upload(
                AppConstants.BUCKET_NAME, fileToUpload.name,
                fileToUpload, CannedAccessControlList.PublicRead)
        transferObserverListener(transferObserver, transferListener)
    }

    private fun transferObserverListener(transferObserver: TransferObserver, transferListener: TransferListener) {
        transferObserver.setTransferListener(transferListener)
    }

    private fun setupAmazonS3(file: File, transferListener: TransferListener) {
        credentialsProvider = CognitoCachingCredentialsProvider(
                MidwestPilotCars.getInstance().applicationContext,
                AppConstants.IDENTITY_POLL_ID, Regions.US_EAST_2)
        setAmazonS3Client(credentialsProvider!!, file, transferListener)
    }

    private fun setAmazonS3Client(credentialsProvider: CognitoCachingCredentialsProvider, file: File, transferListener: TransferListener) {
        val clientConfiguration = ClientConfiguration()
        clientConfiguration.maxErrorRetry = 10
        clientConfiguration.connectionTimeout = 50000
        clientConfiguration.socketTimeout = 50000
        clientConfiguration.maxConnections = 500
        s3Bucket = AmazonS3Client(credentialsProvider, clientConfiguration)
        s3Bucket!!.setRegion(Region.getRegion(Regions.US_EAST_2))
        setFileToUpload(file, transferListener)

    }
}
