package in.bizzmark.smartpoints_user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.bizzmark.smartpoints_user.earnredeemtab.Earn;
import in.bizzmark.smartpoints_user.earnredeemtab.Redeem;
import in.bizzmark.smartpoints_user.earnredeemtab.ViewPageAdapter;

public class EarnRedeemActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapter viewPagerAdapter;

    ImageView imageView_back_arrow;
    String storeName;
    TextView tv_StoreName,tv_Points,tv_Last_Visited;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn__redeem);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("SmartpointS");

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new Earn(), "EARN");
        viewPagerAdapter.addFragments(new Redeem(), "REDEEM");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tv_StoreName = (TextView) findViewById(R.id.tv_earn_redeem_store_name);
        tv_StoreName.setMovementMethod(new ScrollingMovementMethod());

        // Get StoreName after scanning
        Bundle b = getIntent().getExtras();
        if (b != null){
            storeName = b.getString("key_store_name");
            tv_StoreName.setText(storeName);
        }else {
            storeName = tv_StoreName.getText().toString();
        }

        // save storeName in sharedPreferences
        SharedPreferences.Editor editor = getApplicationContext().
                getSharedPreferences("MY_STORE_NAME", Context.MODE_PRIVATE).edit();
        editor.putString("key_store_name", storeName);
        editor.commit();


        imageView_back_arrow = (ImageView) findViewById(R.id.btn_back_arrow);
        imageView_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
