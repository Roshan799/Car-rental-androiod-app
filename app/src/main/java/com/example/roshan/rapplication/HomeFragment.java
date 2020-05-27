package com.example.roshan.rapplication;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment{

    Button srch;

    TextView DatePick,TimePick,DatePick2,TimePick2,loc1,loc2;
    ImageView ImDatePick,ImTimePick,ImDatePick2,ImTimePick2;


    ArrayList<String> listItems=new ArrayList<>();

    String placeItems[];

    ArrayAdapter<String> adapter;
    Spinner sp;

    String placelist[]={""};

    String URL= "http://192.168.43.229/cartest/getplacename.php";
    ArrayList<String> listofplace=new ArrayList<>();


    String Selectedcity="";
    String[] heroes;
    int yr,mnth,dy;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        DatePick=getActivity().findViewById(R.id.pickdate);
        TimePick=getActivity().findViewById(R.id.picktime);
        ImDatePick=getActivity(). findViewById(R.id.Impickdate);
        ImTimePick=getActivity(). findViewById(R.id.Impicktime);

        DatePick2=getActivity().findViewById(R.id.dropdate);
        TimePick2=getActivity().findViewById(R.id.droptime);
        ImDatePick2=getActivity(). findViewById(R.id.Imdropdate);
        ImTimePick2=getActivity(). findViewById(R.id.Imdroptime);

        srch=getActivity().findViewById(R.id.search);

        loc1=getActivity().findViewById(R.id.pickloc);
        loc2=getActivity().findViewById(R.id.droploc);

        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selectedcity.equals("")||loc1.getText().equals("PICK UP LOCATION")||loc2.getText().equals("DROP OFF LOCATION")||DatePick.getText().equals("Pick up date")||TimePick.getText().equals("Pick up time")||DatePick2.getText().equals("Drop off date")||TimePick2.getText().equals("Drop off time"))
                {
                    Toast.makeText(getActivity(),"One Or More Fields not filled",Toast.LENGTH_LONG).show();

                }
                else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra( "city",Selectedcity);
                    intent.putExtra("pick",loc1.getText().toString());
                    intent.putExtra( "drop",loc2.getText().toString());
                    intent.putExtra( "pickdate",DatePick.getText().toString());
                    intent.putExtra( "picktime",TimePick.getText().toString());
                    intent.putExtra( "dropdate",DatePick2.getText().toString());
                    intent.putExtra( "droptime",TimePick2.getText().toString());
                    startActivity(intent);
                }
            }
        });



        sp=getActivity().findViewById(R.id.spinner);


        HomeFragment.BackTask bt=new HomeFragment.BackTask();
        bt.execute();



        adapter=new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.txt,listItems);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selectedcity = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(),Selectedcity,Toast.LENGTH_LONG).show();
                getJSON("http://192.168.43.229/cartest/inputtest.php?name="+Selectedcity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        loc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GetPlaceName placeName=new GetPlaceName();
                //placeName.execute(Selectedcity);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("choose Location").setItems(heroes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),heroes[which],Toast.LENGTH_LONG).show();
                        loc1.setText(heroes[which]);
                    }
                });

// Set up the input
               /* final EditText input = new EditText(MainActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loc1.setText(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/

                builder.show();
            }
        });

        loc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GetPlaceName placeName=new GetPlaceName();
                //placeName.execute(Selectedcity);


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("choose Location").setItems(heroes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),heroes[which],Toast.LENGTH_LONG).show();
                        loc2.setText(heroes[which]);
                    }
                });

// Set up the input
                /*final EditText input = new EditText(MainActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loc2.setText(input.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/

                builder.show();
            }
        });


        DatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH);
                int Day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                yr=year;
                                mnth=monthOfYear;
                                dy=dayOfMonth;
                                DatePick.setText(s);
                            }
                        }, Year, Month, Day);
                Calendar minDate = Calendar.getInstance();
                minDate.set(Year, Month, Day);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        ImDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH);
                int Day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                yr=year;
                                mnth=monthOfYear;
                                dy=dayOfMonth;
                                DatePick.setText(s);
                                DatePick.setText(s);
                            }
                        }, Year, Month, Day);
                Calendar minDate = Calendar.getInstance();
                minDate.set(Year, Month, Day);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        TimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                TimePick.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }

        });

        ImTimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                TimePick.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        DatePick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH);
                int Day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                DatePick2.setText(s);
                            }
                        }, Year, Month, Day);
                Calendar minDate = Calendar.getInstance();
                minDate.set(yr, mnth, dy);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        ImDatePick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int Year = c.get(Calendar.YEAR);
                int Month = c.get(Calendar.MONTH);
                int Day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String s =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                DatePick2.setText(s);
                            }
                        }, Year, Month, Day);
                Calendar minDate = Calendar.getInstance();
                minDate.set(yr, mnth, dy);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        TimePick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                TimePick2.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }

        });

        ImTimePick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                TimePick2.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
    }

    private class BackTask extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list;
        protected void onPreExecute(){
            super.onPreExecute();
            list=new ArrayList<>();
        }
        protected Void doInBackground(Void...params){
            InputStream is=null;
            String result="";
            try{
                HttpClient httpclient=new DefaultHttpClient();
                HttpPost httppost= new HttpPost("http://192.168.43.229/cartest/getlist.php");
                HttpResponse response=httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            }catch(IOException e){
                Log.d("PRT",e.getMessage());
            }

            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result+=line;
                }
                is.close();
                //result=sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
            // parse json data
            try{
                JSONArray jArray =new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                    JSONObject jsonObject=jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    list.add(jsonObject.getString("city_name"));
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result){
            listItems.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    java.net.URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<String> list = new ArrayList<String>();
        heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i]=obj.getString("place_address");
        }
    }
}
