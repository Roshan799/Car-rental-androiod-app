package com.example.roshan.rapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    List<subjects> subjectsList;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    String HTTP_JSON_URL = "http://192.168.43.229/cartest/getcars.php";

    String GET_JSON_FROM_SERVER_NAME = "name";

    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;

    View ChildView ;

    int GetItemPosition,basesumo=200,basescorpio=300,baseaspire=400,basewagonr=100 ;

    ArrayList<String> SubjectNames;
    String pick,city,drop,pickdate,picktime,dropdate,droptime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        subjectsList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        progressBar.setVisibility(View.VISIBLE);

        SubjectNames = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        pick = bundle.getString("pick");
        city=bundle.getString("city");
        drop=bundle.getString("drop");
        pickdate=bundle.getString("pickdate");
        picktime=bundle.getString("picktime");
        dropdate=bundle.getString("dropdate");
        droptime=bundle.getString("droptime");

        JSON_DATA_WEB_CALL();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    GetItemPosition = Recyclerview.getChildAdapterPosition(ChildView);

                    Toast.makeText(MainActivity.this, SubjectNames.get(GetItemPosition), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this,BookingActivity.class);
                    intent.putExtra( "city",city);
                    intent.putExtra("pick",pick);
                    intent.putExtra( "drop",drop);
                    intent.putExtra( "pickdate",pickdate);
                    intent.putExtra( "picktime",picktime);
                    intent.putExtra( "dropdate",dropdate);
                    intent.putExtra( "droptime",droptime);
                    intent.putExtra("cname",SubjectNames.get(GetItemPosition));
                    //intent.putExtra("price",price.get(GetItemPosition));
                    //intent.putExtra("desc",desc.get(GetItemPosition));
                    startActivity(intent);
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            subjects GetDataAdapter2 = new subjects();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                if(pick.equals("Firayalal"))
                {
if(json.getString("place_fk").equals("8")&&(json.getString("car_id").equals("1")||json.getString("car_id").equals("4"))) {
    GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
    GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
    if (json.getString("car_id").equals("1")) {
        GetDataAdapter2.setImg(R.drawable.sumo);
        GetDataAdapter2.setPrice(Integer.toString(basesumo));

    } else if (json.getString("car_id").equals("2")) {
        GetDataAdapter2.setImg(R.drawable.scorpio);
        GetDataAdapter2.setPrice(Integer.toString(basescorpio));

    } else if (json.getString("car_id").equals("3")) {
        GetDataAdapter2.setImg(R.drawable.aspire);
        GetDataAdapter2.setPrice(Integer.toString(baseaspire));

    } else {
        GetDataAdapter2.setImg(R.drawable.wagonr);
        GetDataAdapter2.setPrice(Integer.toString(basewagonr));

    }


    SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

    subjectsList.add(GetDataAdapter2);
}
}
else if(pick.equals("Ratu Road"))
                {
                    if(json.getString("place_fk").equals("7")) {
                        GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                        GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
                        if (json.getString("car_id").equals("1")) {
                            GetDataAdapter2.setImg(R.drawable.sumo);
                            GetDataAdapter2.setPrice(Integer.toString(basesumo));

                        } else if (json.getString("car_id").equals("2")) {
                            GetDataAdapter2.setImg(R.drawable.scorpio);
                            GetDataAdapter2.setPrice(Integer.toString(basescorpio));

                        } else if (json.getString("car_id").equals("3")) {
                            GetDataAdapter2.setImg(R.drawable.aspire);
                            GetDataAdapter2.setPrice(Integer.toString(baseaspire));

                        } else {
                            GetDataAdapter2.setImg(R.drawable.wagonr);
                            GetDataAdapter2.setPrice(Integer.toString(basewagonr));

                        }


                        SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

                        subjectsList.add(GetDataAdapter2);
                    }

                }
                else if(pick.equals("Naupada"))
                {
                    if(json.getString("place_fk").equals("6")&&(json.getString("car_id").equals("1")||json.getString("car_id").equals("4"))||json.getString("car_id").equals("2")) {
                        GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                        GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
                        if (json.getString("car_id").equals("1")) {
                            GetDataAdapter2.setImg(R.drawable.sumo);
                            GetDataAdapter2.setPrice(Integer.toString(basesumo));

                        } else if (json.getString("car_id").equals("2")) {
                            GetDataAdapter2.setImg(R.drawable.scorpio);
                            GetDataAdapter2.setPrice(Integer.toString(basescorpio));

                        } else if (json.getString("car_id").equals("3")) {
                            GetDataAdapter2.setImg(R.drawable.aspire);
                            GetDataAdapter2.setPrice(Integer.toString(baseaspire));

                        } else {
                            GetDataAdapter2.setImg(R.drawable.wagonr);
                            GetDataAdapter2.setPrice(Integer.toString(basewagonr));

                        }


                        SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

                        subjectsList.add(GetDataAdapter2);
                    }

                }
                else if(pick.equals("kamathipura"))
                {
                    if(json.getString("place_fk").equals("5")&&(json.getString("car_id").equals("1")||json.getString("car_id").equals("4"))) {
                        GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                        GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
                        if (json.getString("car_id").equals("1")) {
                            GetDataAdapter2.setImg(R.drawable.sumo);
                            GetDataAdapter2.setPrice(Integer.toString(basesumo));

                        } else if (json.getString("car_id").equals("2")) {
                            GetDataAdapter2.setImg(R.drawable.scorpio);
                            GetDataAdapter2.setPrice(Integer.toString(basescorpio));

                        } else if (json.getString("car_id").equals("3")) {
                            GetDataAdapter2.setImg(R.drawable.aspire);
                            GetDataAdapter2.setPrice(Integer.toString(baseaspire));

                        } else {
                            GetDataAdapter2.setImg(R.drawable.wagonr);
                            GetDataAdapter2.setPrice(Integer.toString(basewagonr));

                        }


                        SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

                        subjectsList.add(GetDataAdapter2);
                    }

                }
                else if(pick.equals("NewTown"))
                {
                    if(json.getString("place_fk").equals("4")&&(json.getString("car_id").equals("1")||json.getString("car_id").equals("2"))||json.getString("car_id").equals("3")) {
                        GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                        GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
                        if (json.getString("car_id").equals("1")) {
                            GetDataAdapter2.setImg(R.drawable.sumo);
                            GetDataAdapter2.setPrice(Integer.toString(basesumo));

                        } else if (json.getString("car_id").equals("2")) {
                            GetDataAdapter2.setImg(R.drawable.scorpio);
                            GetDataAdapter2.setPrice(Integer.toString(basescorpio));

                        } else if (json.getString("car_id").equals("3")) {
                            GetDataAdapter2.setImg(R.drawable.aspire);
                            GetDataAdapter2.setPrice(Integer.toString(baseaspire));

                        } else {
                            GetDataAdapter2.setImg(R.drawable.wagonr);
                            GetDataAdapter2.setPrice(Integer.toString(basewagonr));

                        }


                        SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

                        subjectsList.add(GetDataAdapter2);
                    }

                }
                else if(pick.equals("park street"))
                {
                    if(json.getString("place_fk").equals("3")&&(json.getString("car_id").equals("2")||json.getString("car_id").equals("4"))) {
                        GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                        GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
                        if (json.getString("car_id").equals("1")) {
                            GetDataAdapter2.setImg(R.drawable.sumo);
                            GetDataAdapter2.setPrice(Integer.toString(basesumo));

                        } else if (json.getString("car_id").equals("2")) {
                            GetDataAdapter2.setImg(R.drawable.scorpio);
                            GetDataAdapter2.setPrice(Integer.toString(basescorpio));

                        } else if (json.getString("car_id").equals("3")) {
                            GetDataAdapter2.setImg(R.drawable.aspire);
                            GetDataAdapter2.setPrice(Integer.toString(baseaspire));

                        } else {
                            GetDataAdapter2.setImg(R.drawable.wagonr);
                            GetDataAdapter2.setPrice(Integer.toString(basewagonr));

                        }


                        SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

                        subjectsList.add(GetDataAdapter2);
                    }

                }
                else if(pick.equals("connaught place"))
                {
                    if(json.getString("place_fk").equals("2")&&(json.getString("car_id").equals("1")||json.getString("car_id").equals("4"))) {
                        GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                        GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
                        if (json.getString("car_id").equals("1")) {
                            GetDataAdapter2.setImg(R.drawable.sumo);
                            GetDataAdapter2.setPrice(Integer.toString(basesumo));

                        } else if (json.getString("car_id").equals("2")) {
                            GetDataAdapter2.setImg(R.drawable.scorpio);
                            GetDataAdapter2.setPrice(Integer.toString(basescorpio));

                        } else if (json.getString("car_id").equals("3")) {
                            GetDataAdapter2.setImg(R.drawable.aspire);
                            GetDataAdapter2.setPrice(Integer.toString(baseaspire));

                        } else {
                            GetDataAdapter2.setImg(R.drawable.wagonr);
                            GetDataAdapter2.setPrice(Integer.toString(basewagonr));

                        }



                        SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

                        subjectsList.add(GetDataAdapter2);
                    }

                }
                else {
                    if(json.getString("place_fk").equals("1")&&(json.getString("car_id").equals("1")||json.getString("car_id").equals("4")||json.getString("car_id").equals("2"))) {
                        GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                        GetDataAdapter2.setDesc(json.getString("mileage") + "km/hr|" + json.getString("fuel_type") + "|" + json.getString("transmission") + "|Seating capacity: " + json.getString("Seating_capacity") + "|available: " + json.getString("no_of_cars"));
                        if (json.getString("car_id").equals("1")) {
                            GetDataAdapter2.setImg(R.drawable.sumo);
                            GetDataAdapter2.setPrice(Integer.toString(basesumo));

                        } else if (json.getString("car_id").equals("2")) {
                            GetDataAdapter2.setImg(R.drawable.scorpio);
                            GetDataAdapter2.setPrice(Integer.toString(basescorpio));

                        } else if (json.getString("car_id").equals("3")) {
                            GetDataAdapter2.setImg(R.drawable.aspire);
                            GetDataAdapter2.setPrice(Integer.toString(baseaspire));

                        } else {
                            GetDataAdapter2.setImg(R.drawable.wagonr);
                            GetDataAdapter2.setPrice(Integer.toString(basewagonr));

                        }


                        SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));

                        subjectsList.add(GetDataAdapter2);
                    }

                }
                } catch(JSONException e){

                    e.printStackTrace();
                }


        }

        recyclerViewadapter = new RecyclerViewCardViewAdapter(subjectsList, this);

        recyclerView.setAdapter(recyclerViewadapter);

    }
}