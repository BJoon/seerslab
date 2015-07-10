package com.seerslab.parsetuto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


public class MainActivity extends Activity {

    Button btn_gallery;
    ImageView imgv;
    ParseObject parsesave;
    private ParseFile photoFile;
    //byte[] scaledData;
    int REQ_CODE_SELECT_IMAGE = 100;
    public static final String TAG = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "QsRrYce9jE9tYLavcPxDHa9ltloWj5g4kLCeh4vf", "heeFW4c2AX9caP5fOOWF5pc5K4bmUfjhNf0d8s1O");
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

            }
        };
        Button btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_gallery.setOnClickListener(listener);

    /*    // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "QsRrYce9jE9tYLavcPxDHa9ltloWj5g4kLCeh4vf", "heeFW4c2AX9caP5fOOWF5pc5K4bmUfjhNf0d8s1O");
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/
        ParseCloud.callFunctionInBackground("hello", new HashMap<String, Object>(), new FunctionCallback<String>() {
            @Override
            public void done(String result, com.parse.ParseException e) {
             if(e==null){
                 Log.d("Result",result);
                 //result is "Hello world!"
             }
            }
        });
    }

   protected void onActivityResult(int requestCode,int resultCode, Intent data){
       Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

       if(requestCode == REQ_CODE_SELECT_IMAGE)
       {
           Log.d(TAG, "match succ");
           if(resultCode==Activity.RESULT_OK)
           {
               try {
                   Log.d(TAG, "result succ");

                   Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                   ImageView image = (ImageView)findViewById(R.id.imgv);
                   image.setImageBitmap(image_bitmap);

                   Bitmap mealImageScaled = Bitmap.createScaledBitmap(image_bitmap, 200, 200
                           * image_bitmap.getHeight() / image_bitmap.getWidth(), false);

                   Matrix matrix = new Matrix();
                   matrix.postRotate(90);
                   Bitmap rotatedScaledMealImage = Bitmap.createBitmap(mealImageScaled, 0,
                           0, mealImageScaled.getWidth(), mealImageScaled.getHeight(),
                           matrix, true);


                   ByteArrayOutputStream bos = new ByteArrayOutputStream();
                   rotatedScaledMealImage.compress(Bitmap.CompressFormat.JPEG,100,bos);

                   byte[] scaledData = bos.toByteArray();
                   Log.d(TAG, "tobyte succ");
                   parsesave = new ParseObject("Imagesave");

                   photoFile = new ParseFile("test_photo.jpg",scaledData);
                   Log.d(TAG, "photefile succ");

                   Log.d(TAG, "save succ");
                   parsesave.put("parseimage", photoFile);
                   Log.d(TAG, "parse succ");
                   parsesave.saveInBackground();
                   Log.d(TAG, "save succ2");

                  /* photoFile.saveInBackground(new SaveCallback() {
                       @Override
                       public void done(com.parse.ParseException e) {
                           if (e != null) {
                               Log.d(TAG, "Error" + e.getMessage());
                           } else {
                               Log.d(TAG, "done succ");
                               parsesave.put("parseimage", scaledData);
                               //photoFile.saveInBackground();
                           }
                       }
                   });*/

               } catch (Exception e)
               {
                   e.printStackTrace();
               }
           }
       }

   }

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        return imgPath.substring(imgPath.lastIndexOf("/")+1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
