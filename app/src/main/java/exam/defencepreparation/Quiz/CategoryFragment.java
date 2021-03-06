package exam.defencepreparation.Quiz;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import exam.defencepreparation.Quiz.Common.Common;
import exam.defencepreparation.Quiz.Interface.ItemClickListener;
import exam.defencepreparation.Quiz.Model.Category;
import exam.defencepreparation.Quiz.ViewHolder.CategoryViewHolder;
import exam.defencepreparation.R;


public class CategoryFragment extends Fragment {
    AdView mAdView;
    View myFragment;
    RecyclerView listCategory,listCategory1,listCategory2;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter1;
    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter2;
    FirebaseDatabase database;
    DatabaseReference categories;
    FirebaseDatabase database1;
    DatabaseReference categories1;
    FirebaseDatabase database2;
    DatabaseReference categories2;
    public static CategoryFragment newInstance(){
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        categories = database.getReference().child("QUIZ").child("daily_quiz");
        database1 = FirebaseDatabase.getInstance();
        categories1 = database1.getReference().child("QUIZ").child("GS_quiz");
        database2 = FirebaseDatabase.getInstance();
        categories2 = database2.getReference().child("QUIZ").child("defence_quiz");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.english_youtube,container,false);
        listCategory = myFragment.findViewById(R.id.rec_1);
        listCategory1 = myFragment.findViewById(R.id.rec_2);
        listCategory2 = myFragment.findViewById(R.id.rec_3);
        TextView textView=(TextView)myFragment.findViewById(R.id.text_1);
        TextView textView1=(TextView)myFragment.findViewById(R.id.text_2);
        TextView textView2=(TextView)myFragment.findViewById(R.id.text_3);
        textView.setText("Current Affair Quiz");
        textView1.setText("General Studies Quiz");
        textView2.setText("Defence Quiz");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, true);

        listCategory.hasFixedSize();
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        listCategory.setLayoutManager(layoutManager);
        loadCategories();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, true);
        listCategory1.hasFixedSize();
        layoutManager1.setReverseLayout(true);
        layoutManager1.setStackFromEnd(true);
        listCategory1.setLayoutManager(layoutManager1);
        loadCategories();

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, true);
        listCategory2.hasFixedSize();
        layoutManager2.setReverseLayout(true);
        layoutManager2.setStackFromEnd(true);
        listCategory2.setLayoutManager(layoutManager2);
        loadCategories();

        // 2nd list code here
        mAdView = (AdView)myFragment.findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() { }
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
        return myFragment;
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

    private void loadCategories() {

        // First  recyclerview code-----------------
        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class,
                R.layout.rec_horigental_design,
                CategoryViewHolder.class,
                categories) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.category_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.category_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent startGame = new Intent(getActivity(),Start.class);
                        Common.categoryId = adapter.getRef(position).getKey();
                        Common.categoryName = model.getName();
                        startActivity(startGame);

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);

        // 2nd  recyclerview code-----------------
        adapter1 = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class,
                R.layout.rec_horigental_design,
                CategoryViewHolder.class,
                categories1) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.category_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.category_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent startGame = new Intent(getActivity(),Start.class);
                        Common.categoryId = adapter1.getRef(position).getKey();
                        Common.categoryName = model.getName();
                        startActivity(startGame);

                    }
                });
            }
        };
        adapter1.notifyDataSetChanged();
        listCategory1.setAdapter(adapter1);

        // 3rd   recyclerview code-----------------
        adapter2 = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(Category.class,
                R.layout.rec_horigental_design,
                CategoryViewHolder.class,
                categories2) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.category_name.setText(model.getName());
                Picasso.with(getActivity()).load(model.getImage()).into(viewHolder.category_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent startGame = new Intent(getActivity(),Start.class);
                        Common.categoryId = adapter2.getRef(position).getKey();
                        Common.categoryName = model.getName();
                        startActivity(startGame); }
                });
            }
        };
        adapter2.notifyDataSetChanged();
        listCategory2.setAdapter(adapter2);
    }


}
