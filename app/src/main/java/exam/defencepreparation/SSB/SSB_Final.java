package exam.defencepreparation.SSB;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import exam.defencepreparation.R;


public class SSB_Final extends AppCompatActivity {

    TabLayout mtab;
    ViewPager mpage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defence__blog);


        mtab=findViewById(R.id.tab1);
        mpage=findViewById(R.id.pager1);

        MyPageAdapter adapter=new MyPageAdapter(getSupportFragmentManager());

        adapter.getFragment(new SSB(),"Chat");
        adapter.getFragment(new OIR(),"Chat");
        adapter.getFragment(new PPDT(),"Chat");
        adapter.getFragment(new WAT(),"Contact");
        adapter.getFragment(new SRT(),"SRT");

        mpage.setAdapter(adapter);
        mtab.setupWithViewPager(mpage);


    }
}
