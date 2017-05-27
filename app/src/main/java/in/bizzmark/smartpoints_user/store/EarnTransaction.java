package in.bizzmark.smartpoints_user.store;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.bizzmark.smartpoints_user.R;
import in.bizzmark.smartpoints_user.adapter.EarnTransactionAdapter;
import in.bizzmark.smartpoints_user.bo.EarnTransactionBO;

import static in.bizzmark.smartpoints_user.NavigationActivity.device_Id;
import static in.bizzmark.smartpoints_user.store.StoreHomeActivity.storeId;

/**
 * Created by User on 18-May-17.
 */

public class EarnTransaction extends Fragment {
    View view;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;

    String storeName, billAmount, points, type, dateTime;
    ArrayList<EarnTransactionBO> earnTransactionList;
    Context context = getActivity();

    String transa_url = "http://35.154.104.54/smartpoints/customer-api/get-single-customer-all-branch-transactions?customerDeviceId="+device_Id+"branchId="+storeId ;
   // String transa_url = "http://35.154.104.54/smartpoints/customer-api/get-single-customer-all-branch-transactions?customerDeviceId=867865021687841&branchId=1&limit=10";
   // String transa_url = "http://35.154.104.54/smartpoints/customer-api/get-single-customer-all-branch-transactions?customerDeviceId=911497601974005&branchId=1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.earn_transaction,container,false);
        getDataFromServer(view);
        return view;
    }

    // Earn Transaction Data-Retrieving from Server
    private void getDataFromServer(View view) {
        earnTransactionList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_earn_transaction);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        StringRequest request = new StringRequest(Request.Method.GET, transa_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {

                        try {
                            JSONObject jo = new JSONObject(result);
                            JSONArray ja = jo.getJSONArray("response");

                            for (int i = 0; i < ja.length() ; i++) {
                                JSONArray jsonArray = ja.getJSONArray(i);

                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject =  jsonArray.getJSONObject(j);

                                   // storeName = jsonObject.getString("store_name");
                                    billAmount = jsonObject.getString("bill_amount");
                                    points = jsonObject.getString("points");
                                    type = jsonObject.getString("type");
                                    dateTime = jsonObject.getString("transacted_at");

                                    if (type.equalsIgnoreCase("earn")) {

                                        EarnTransactionBO earnTransactionBO= new EarnTransactionBO();
                                        earnTransactionBO.setBill_amount(billAmount);
                                        earnTransactionBO.setPoints(points);
                                        earnTransactionBO.setType(type);
                                        earnTransactionBO.setDate_time(dateTime);

                                    earnTransactionList.add(earnTransactionBO);
                                    }
                                }
                            }

                            EarnTransactionAdapter earnTransaction = new EarnTransactionAdapter(earnTransactionList , context);
                            earnTransaction.notifyDataSetChanged();
                            recyclerView.setAdapter(earnTransaction);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
}