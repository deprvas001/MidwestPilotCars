package com.midwestpilotcars.amazonS3Image;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.midwestpilotcars.views.activities.ExpenseDayAdd;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class S3ImageProcess {
    private static TransferUtility transferUtility;
  private static   AmazonS3 s3Client;
    File fileToS3;
    private static  Context context;

    public static void s3credentialsProvider(Context context){
        //     us-east-2:bbcd9ba3-70bc-4c66-ba14-59b57e6637af
        // Initialize the AWS Credential
        context = context;
        CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider =
                new CognitoCachingCredentialsProvider(
                        context,
                        "us-east-2:bbcd9ba3-70bc-4c66-ba14-59b57e6637af", // Identity Pool ID
                        Regions.US_EAST_2 // Region
                );
       createAmazonS3Client(cognitoCachingCredentialsProvider);
    }

    /**
     *  Create a AmazonS3Client constructor and pass the credentialsProvider.
     * @param credentialsProvider
     */
    public static void createAmazonS3Client(CognitoCachingCredentialsProvider
                                                    credentialsProvider){

        // Create an S3 client
        s3Client = new AmazonS3Client(credentialsProvider);

        // Set the region of your S3 bucket
        s3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
    }

    public void setTransferUtility(){

        transferUtility = new TransferUtility(s3Client, context);
    }


    public void uploadFileToS3(Context context, File fileToS3){
        this.context = context;

        TransferObserver transferObserver = transferUtility.upload(
                "midwestpilotcars",          /* The bucket to upload to */
                fileToS3.getName(),/* The key for the uploaded object */
                fileToS3       /* The file where the data to upload exists */
        );

        transferObserverListener(transferObserver);
    }

    /* This is listener method of the TransferObserver
     * Within this listener method, we get status of uploading and downloading file,
     * to display percentage of the part of file to be uploaded or downloaded to S3
     * It displays an error, when there is a problem in
     * uploading or downloading file to or from S3.
     * @param transferObserver
     */

    public void transferObserverListener(TransferObserver transferObserver){
        Date expiration = new Date();
        long msec = expiration.getTime();
        msec += 1000 * 6000 * 6000; // 1 hour.
        Date d = new Date();
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse("July 14, 2019");
            expiration.setTime(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            expiration.setTime(msec);
        }
        System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010

        transferObserver.setTransferListener(new TransferListener(){

            @Override
            public void onStateChanged(int id, TransferState state) {
               /* Toast.makeText(getApplicationContext(), "State Change" + state,
                        Toast.LENGTH_SHORT).show();*/

                ResponseHeaderOverrides overrideHeader = new ResponseHeaderOverrides();
                overrideHeader.setContentType("image/jpeg");
                String mediaUrl = fileToS3.getName();
                GeneratePresignedUrlRequest generatePresignedUrlRequest =
                        new GeneratePresignedUrlRequest("midwestpilotcars", mediaUrl);
                generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
                generatePresignedUrlRequest.setExpiration(expiration);
                generatePresignedUrlRequest.setResponseHeaders(overrideHeader);

                URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
                Log.e("s", url.toString());
                //  return url.toString();
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent/bytesTotal * 100);
                Toast.makeText(context, "Progress in %" + percentage,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("error",ex.getMessage());
            }

        });
    }

}
