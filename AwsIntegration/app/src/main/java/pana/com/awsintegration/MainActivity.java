package pana.com.awsintegration;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.RequestClientOptions;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int RESULT_CROP = 2;
    private static String BUCKET_NAME = "myfirstandroidbucket";
    private static String FILE_NAME = "1.png";
    private static String IDENTITY_POOL_ID = "us-east-1:47fd67bb-be6c-43a6-b964-e08d1b2d424c";
    File file = null;
    AmazonS3 s3;
    private ImageView imageView;
    private Button selectAndUpload, setImage;
    private Uri selectedImageUri = null;
    private String selectedImagePath;
    private Bitmap bitmap;
    private TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        selectAndUpload = (Button) findViewById(R.id.selectAndUploadImage);
        setImage = (Button) findViewById(R.id.setImageFromUrl);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                IDENTITY_POOL_ID, // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        

        /*
        Initialize the S3 TransferUtility
        First, pass your Amazon Cognito credentials provider to the S3 client constructor.
        Then, pass the client to the TransferUtility constructor along with the application context:
        */
        s3 = new AmazonS3Client(credentialsProvider);

        transferUtility = new TransferUtility(s3, getApplicationContext());
        selectAndUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSelectImage();
            }
        });
        setImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  downloadImage();
                try {
                    Log.d("URL", buildPresignedURL(BUCKET_NAME).toString() + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void uploadSelectedFileToCloudWithPermissions(File file) {
        PutObjectRequest putObject = new PutObjectRequest(BUCKET_NAME   //My Bucket Name
                , file.getName()                                        //My File Name
                , file);                                                //My File Object
        putObject.setCannedAcl(CannedAccessControlList.PublicReadWrite);//Setting permissions for all users to access
        s3.putObject(putObject);                                        //Finally upload Data.


    }

    /*

    private void uploadSelectedFileToCloud(File fileToUpload) {
//        TransferObserver observer =

        transferUtility.upload(
                BUCKET_NAME,     *//* The bucket to upload to *//*
                fileToUpload.getName(),    *//* The key for the uploaded object *//*
                fileToUpload   *//* The file where the data to upload exists *//*
                );

    }

//    private void downloadImage() {*/
    public URL buildPresignedURL(String path) throws IOException {
        long expires = System.currentTimeMillis() + 60 * 60 * 1000;
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(path, FILE_NAME);
        request.setExpiration(new Date(expires));
        // s3=new AmazonS3Client(awsCredentials);
        return s3.generatePresignedUrl(request);
        //      }
    /*    File outputDir = getApplicationContext().getCacheDir(); // context being the Activity pointer
        try {
            file = File.createTempFile("temp", ".png", outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new GeneratePresignedUrlRequest(BUCKET_NAME,file.getName(), HttpMethod.GET).
        transferUtility.download(BUCKET_NAME, FILE_NAME, file).setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d("Downloading State", state.name());

            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) ((bytesCurrent / bytesTotal) * 100);
                Log.d("Percentage", percentage + "% Downloaded");
                if (percentage == 100) {
                    imageView.setImageURI(Uri.fromFile(file));
                    Log.d("Downloaded File ", "" + (file != null ? file.getName() : null));
                }
            }

            @Override
            public void onError(int id, Exception ex) {

            }
        });
*/
    }


    ///////////////////////Select Image Code///////////////
    private void performSelectImage() {
        Log.d("INVOKED", "Perform Select");

        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case PICK_IMAGE:
                Log.d("INVOKED", "onActivityResult case PICK IMAGE");

                if (resultCode == Activity.RESULT_OK) {
                    Log.d("INVOKED", "onActivityResult == OK");

                    selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        Log.d("Image Selected", " URI" + selectedImageUri.toString());

                        try {
                            //selectedImagePath = getPath(selectedImageUri);
                            performCrop();
                            //imageView.setImageURI(selectedImageUri);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Internal error", Toast.LENGTH_LONG).show();
                            Log.e(e.getClass().getName(), e.getMessage(), e);
                        }
                    }
                }
                break;
            case RESULT_CROP:
                Log.d("INVOKED", "onActivityResult case CROP");

                if (resultCode == Activity.RESULT_OK) {
                    Log.d("CASE CROP", " Result == OK");

                    Bundle extras = data.getExtras();
                    bitmap = extras.getParcelable("data");
                    String path = saveImage(bitmap);
                    Log.d("PATH AFTER CROPPING", path);
                    if (path != null) {
                        selectedImagePath = path;
                        decodeFile(path);
                    }
                }
                break;
            default:
                break;
        }
    }

    private String saveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Chat/ProfileImages");

        if (!myDir.exists())
            myDir.mkdirs();

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image_" + n + ".jpg";
        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root + "/Chat/ProfileImages/" + fname;
    }

    private void performCrop() {
        Log.d("INVOKED", "Performed Crop");
        try {
            if (selectedImageUri != null) {
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                cropIntent.setDataAndType(selectedImageUri, "image/*");
                cropIntent.putExtra("crop", "true");
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("outputX", 280);
                cropIntent.putExtra("outputY", 280);
                cropIntent.putExtra("return-data", true);
                startActivityForResult(cropIntent, RESULT_CROP);
            }
        } catch (ActivityNotFoundException anfe) {
            String errorMessage = "your device doesn't support the crop action!";
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }


    private void decodeFile(String filePath) {

        ////////////////////////////////UPLOADING CLOUD////////////////////
        File file = new File(selectedImagePath);
        uploadSelectedFileToCloudWithPermissions2ndMethod(file);
        Log.d("Image Select To Upload", "" + file.getName());
        ////////////////////////////////UPLOADING COMPLETED////////////////
    }

    private void uploadSelectedFileToCloudWithPermissions2ndMethod(File file) {

    }


}
