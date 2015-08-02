package rawe.gordon.com.boucingballdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by gordon on 7/29/15.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private ListView listView;
    private View refreshView;
    private SegmentAdapter adapter;
    private View info;
    private ArrayList<User> users = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        this.listView = (ListView) findViewById(R.id.list_view);
        refreshView = findViewById(R.id.refresh_view);
        info = findViewById(R.id.info);
        listView.setAdapter(adapter = new SegmentAdapter(getApplicationContext(), users));
        GeoUtil.setListViewHeightBasedOnChildren(listView);
        refreshView.setOnClickListener(this);
        ((BottomDetectScrollView)findViewById(R.id.scroll_view)).setListener(new BottomDetectScrollView.OnScrollListener() {
            @Override
            public void onScrolledDown() {
                info.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        info.setVisibility(View.VISIBLE);
                    }
                }, 1500);
            }

            @Override
            public void onScrolledUp() {
                info.setVisibility(View.VISIBLE);
            }

            @Override
            public void onBottomReached() {
                doOneRequest();
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

            }
        });
    }

    private void doOneRequest(){
        RestClient.getInstance().getCityApiService().getProvinces(new Callback<ProvinceResponseModel>() {
            @Override
            public void success(ProvinceResponseModel provinceResponseModel, Response response) {
                for (String str : provinceResponseModel.getProvinces()) {
                    users.add(new User(str, str));
                }
                adapter.notifyDataSetChanged();
                GeoUtil.setListViewHeightBasedOnChildren(listView);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.refresh_view){
            doOneRequest();
        }
    }
}
