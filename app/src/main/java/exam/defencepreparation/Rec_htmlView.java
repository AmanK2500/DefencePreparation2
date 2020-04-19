package exam.defencepreparation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;

import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import exam.defencepreparation.Screenshot.ScreenshotType;
import exam.defencepreparation.Screenshot.ScreenshotUtils;
import exam.defencepreparation.news.NewsDetail;

import static exam.defencepreparation.R.layout.grid_youtube_3;

public class Rec_htmlView  extends AppCompatActivity implements View.OnClickListener {
    AdView mAdView;
    TextView textView,textView1,textView2;
    private String topic,databaseID;
    private String detail,date,image;
    private KenBurnsView post_image;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    ImageView share;
    HtmlTextView htmlTextView;
    private LinearLayout rootContent;
    TextView app_name;
    ImageView imageView;
    ImageView btn;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler__view__click);

        findViews();
        implementClickEvents();

        Bundle extras = getIntent().getExtras();
        topic = extras.getString("topic");
        detail= extras.getString("detail");
        date= extras.getString("date");
        image= extras.getString("image");
        databaseID=extras.getString("datalink");
        imageView=(ImageView)findViewById(R.id.share);



        post_image=(KenBurnsView) findViewById(R.id.pic);



        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        RandomTransitionGenerator generator = new RandomTransitionGenerator(4000, ACCELERATE_DECELERATE);
        post_image.setTransitionGenerator(generator);
        post_image.setTransitionListener(onTransittionListener());



        textView=(TextView)findViewById(R.id.topic);
        htmlTextView=(HtmlTextView)findViewById(R.id.detail);
        textView2=(TextView)findViewById(R.id.date);
        share=(ImageView)findViewById(R.id.share);
        //  rootContent = (LinearLayout)findViewById(R.id.root_content);



        mAdView = (AdView) findViewById(R.id.adView);
        mDatabase = FirebaseDatabase.getInstance().getReference(databaseID);
        mDatabase.keepSynced(true);
        mRecyclerView=(RecyclerView)findViewById(R.id.rec_click);
        mRecyclerView.hasFixedSize();
        LinearLayoutManager mLayoutManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true);
        mLayoutManger.setReverseLayout(true);
        mLayoutManger.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManger);


        AdRequest adRequest1 = new AdRequest.Builder()
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                // Toast.makeText(getActivity(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Toast.makeText(getActivity(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                //  Toast.makeText(getActivity(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest1);





    }


    private KenBurnsView.TransitionListener onTransittionListener() {
        return new KenBurnsView.TransitionListener() {

            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                //Toast.makeText(MainActivity.this, "end", Toast.LENGTH_SHORT).show();
            }
        };
    }


    // share code here...................................

    /*  Find all views Ids  */
    private void findViews() {
        share = (ImageView) findViewById(R.id.share);


        rootContent = (LinearLayout) findViewById(R.id.root_content);



    }

    /*  Implement Click events over Buttons */
    private void implementClickEvents() {
        share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                share.setVisibility(View.GONE);

                takeScreenshot(ScreenshotType.FULL);
                break;

        }
    }

    /*  Method which will take screenshot on Basis of Screenshot Type ENUM  */
    private void takeScreenshot(ScreenshotType screenshotType) {

        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                //If Screenshot type is FULL take full page screenshot i.e our root content.

                b = ScreenshotUtils.getScreenShot(rootContent);
                break;

        }

        //If bitmap is not null
        if (b != null) {
            showScreenShotImage(b);//show bitmap over imageview

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);//save the screenshot to selected path
            shareScreenshot(file);//finally share screenshot
        } else
            //If bitmap is null show toast message
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();

    }

    /*  Show screenshot Bitmap */
    private void showScreenShotImage(Bitmap b) {
        imageView.setImageBitmap(b);
    }



    /*  Share Screenshot  */
    private void shareScreenshot(File file) {
        Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        //  intent.putExtra(android.content.Intent.EXTRA_TEXT, "Prepare yourself for various defence exam with");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Prepare yourself for various defence exam with" +" \n" +"https://play.google.com/store/apps/details?id=exam.defencepreparation");
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
    }
    //'''''''''''''''''''''''''''''''''''''''


    @Override
    public void onStart() {
        super.onStart();



        FirebaseRecyclerAdapter<NewsDetail, Recycler_View_Click.MyViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<NewsDetail, Recycler_View_Click.MyViewHolder>
                (NewsDetail.class ,grid_youtube_3, Recycler_View_Click.MyViewHolder.class,mDatabase) {

            @Override
            protected void populateViewHolder(final Recycler_View_Click.MyViewHolder viewHolder, final NewsDetail model, int position) {
                viewHolder.setTopic(model.getTopic());
                viewHolder.setDetail(model.getDetail());
                viewHolder.setDate(model.getDate());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String topic="";
                        String detail="";
                        String date="";
                        String image="";


                        topic=model.getTopic();
                        detail=model.getDetail();
                        date=model.getDate();
                        image=model.getImage();

                        Intent imgFullScrn = new Intent(Rec_htmlView.this, Rec_htmlView.class);
                        imgFullScrn.putExtra("topic",topic);
                        imgFullScrn.putExtra("detail",detail);
                        imgFullScrn.putExtra("date",date);
                        imgFullScrn.putExtra("image",image);
                        imgFullScrn.putExtra("datalink",databaseID);


                        startActivity(imgFullScrn);

                    }
                });

            }
        };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        textView.setText(topic);
      //  htmlTextView.setText(Html.fromHtml(detail));
        htmlTextView.setHtml(detail,
                new HtmlHttpImageGetter(htmlTextView));

        textView2.setText(date);
        Picasso
                .with(getApplicationContext())
                .load(image)
                .placeholder(R.color.cardview_dark_background)
                .fit()
                .centerCrop()
                .into(post_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


    }



    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView post_desc;
        View mView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }

        public void setTopic(String topic)
        {
            TextView Topic=(TextView)mView.findViewById(R.id.tittle);
            Topic.setText(topic);
        }

        public void setDetail(String detail){
            post_desc = (TextView)mView.findViewById(R.id.topic1);
            post_desc.setText(Html.fromHtml(detail));
        }

        public void setDate(String date){
            TextView  Date = (TextView)mView.findViewById(R.id.time);
            Date.setText(date);
        }

        public void setImage(final Context ctx, final String image){
            final ImageView post_image=(ImageView) mView.findViewById(R.id.card_image);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.loadingpic).into(post_image, new Callback() {
                @Override
                public void onSuccess() {

                }
                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).placeholder(R.drawable.loadingpic).into(post_image);

                }
            });
        }
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void createPdf(){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.BLACK);
        //canvas.drawText(sometext, 80, 50, paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page
        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 100, paint);
        document.finishPage(page);
        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"test-2.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }

}

