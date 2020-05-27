package com.example.roshan.rapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {
    String pick,city,drop,pickdate,picktime,dropdate,droptime,cname,cprice,cdesc;
    TextView locpick,locdrop,dtpick,dtdrop,carn,card,carp;
    EditText nm,em,ph;
    ImageView cimg;
    Button bk;
    int basesumo=200,basescorpio=300,baseaspire=400,basewagonr=100;
    String sumo_desc="15.3km/ltr,Diesel,Manual",scorpio_desc="16.36km/ltr,Diesel,Manual",aspire_desc="26.1km/ltr,Petrol,Automatic",wgn_desc="22.5km/ltr,Petrol,Manual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        locpick=(TextView)findViewById(R.id.ploc);
        locdrop=(TextView)findViewById(R.id.dloc);
        dtpick=(TextView)findViewById(R.id.pdt);
        dtdrop=(TextView)findViewById(R.id.ddt);
        carn=(TextView)findViewById(R.id.carname);
        card=(TextView)findViewById(R.id.cardesc);
        carp=(TextView)findViewById(R.id.carprice);
        cimg=(ImageView) findViewById(R.id.carpicture);

        bk=(Button)findViewById(R.id.book);

        nm=(EditText)findViewById(R.id.name);
        em=(EditText)findViewById(R.id.email);
        ph=(EditText)findViewById(R.id.phone);

        Bundle bundle = getIntent().getExtras();
        pick = bundle.getString("pick");
        city=bundle.getString("city");
        drop=bundle.getString("drop");
        pickdate=bundle.getString("pickdate");
        picktime=bundle.getString("picktime");
        dropdate=bundle.getString("dropdate");
        droptime=bundle.getString("droptime");
        cname=bundle.getString("cname");
        //cprice=bundle.getString("price");
        //cdesc=bundle.getString("desc");


        locpick.setText("Pick up Location:"+pick);
        locdrop.setText("Drop off location:"+drop);
        dtpick.setText("Pick up Date and Time:"+pickdate+picktime);
        dtdrop.setText("Drop off Date and Time:"+dropdate+droptime);
        carn.setText("Car Name:"+cname);

        if(cname.equals("Tata sumo"))
        {
            cimg.setImageDrawable(getDrawable(R.drawable.sumo));
            card.setText("Car Description:"+sumo_desc);
            carp.setText("Total Price:"+basesumo);

        }
        else if(cname.equals("Mahindra scorpio"))
        {
            cimg.setImageDrawable(getDrawable(R.drawable.scorpio));
            card.setText("Car Description:"+scorpio_desc);
            carp.setText("Total Price:"+basescorpio);

        }
        else if(cname.equals("Ford aspire"))
        {
            cimg.setImageDrawable(getDrawable(R.drawable.aspire));
            card.setText("Car Description:"+aspire_desc);
            carp.setText("Total Price:"+baseaspire);

        }
        else if(cname.equals("Maruti wagonr"))
        {
            cimg.setImageDrawable(getDrawable(R.drawable.wagonr));
            card.setText("Car Description:"+wgn_desc);
            carp.setText("Total Price:"+basewagonr);

        }
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nm.equals("")||em.equals("")||ph.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter valid details",Toast.LENGTH_LONG).show();
                }
                else
                {
                    BackTask task=new BackTask();
                    task.execute();
                    startActivity(new Intent(BookingActivity.this,ConfirmActivity.class));

                }
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
                HttpPost httppost= new HttpPost("http://192.168.43.229/cartest/book.php?cityname="+city+"&pickloc="+pick+"&sdate="+pickdate+"&stime="+picktime+"&droploc="+drop+"&ddate="+dropdate+"&dtime="+droptime+"&custname="+nm+"&email="+em+"&mobileno="+ph+"&cost=500&carname="+cname);
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
            return null;
        }
        protected void onPostExecute(Void result){
            Toast.makeText(getApplicationContext(),"Booked",Toast.LENGTH_LONG).show();

        }
    }
}
