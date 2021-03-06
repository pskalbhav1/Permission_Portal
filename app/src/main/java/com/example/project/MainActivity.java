package com.example.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {
    String[] Rooms = {"AB5303","AB5304","AB5308","NLH203","NLH304"};
    String[] Booked_Rooms = {"NLH203","NLH304","","","","",""};
    List<String> ListRoom = new ArrayList<>(Arrays.asList(Booked_Rooms));
    List<String> ListBookings = new ArrayList<>(Arrays.asList());
    List<String> BookingDetails = new ArrayList<>(Arrays.asList());
    ListView booked, accept, reject,pending;
    Button b_login,b_register,b_cancel,b_logout,b_book,b_booking,b_request,b_send,b_grant,b_deny;
    EditText ed_name, ed_regno,ed_psw,ed_roomno,ed_date,ed_time,ed_to,ed_subject,ed_message;
    TextView t_login,t_register,t_pending,t_accept,t_reject,t_print;
    boolean admin=false,student=false;
    String status="0";
    String bookingDetails="";
    String role="admin";
    String regno="m",name="admin",psw="admin",c_role,Room_No="NLH203",Date="123456",Time="2",To_Id="E1",Subject="Hi",Message="Hello";
    private DBHandler dbHandler;
    private DBSlot dbSlot;
    private DBRequest dbRequest;
    int j;
    List<String> Pending= new ArrayList<>(Arrays.asList());
    List<String> Accepted= new ArrayList<>(Arrays.asList());
    List<String> Rejected= new ArrayList<>(Arrays.asList());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t_login=(TextView) findViewById(R.id.textView1);
        t_register=(TextView) findViewById(R.id.textView2);

        t_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });
        t_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegister(view);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        admin=false;
        student=false;
        switch (view.getId()) {
            case R.id.radio_admin:
                if (checked) {
                    admin = true;
                    role = "admin";
                    break;
                }

            case R.id.radio_student:
                if (checked) {
                    student=true;
                    role="student";
                    break;
                }
        }
    }
    public void onLogin(View view) {
        setContentView(R.layout.login);

        b_login = (Button) findViewById(R.id.buttony);
        ed_name = (EditText) findViewById(R.id.editText1);
        ed_psw = (EditText) findViewById(R.id.editText2);
        b_cancel = (Button) findViewById(R.id.button2);
        t_register=(TextView) findViewById(R.id.TextView);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make corrections
                String query;

                query = "Select * From STUDENTS where Name = '"+ed_name.getText().toString() +"'";
                if(admin && student){
                    Toast.makeText(getApplicationContext(),"Select only one option",Toast.LENGTH_SHORT).show();
                    onLogin(v);
                }

                else if(ed_regno.getText().toString().isEmpty() || ed_psw.getText().toString().isEmpty() || ed_name.getText().toString().isEmpty() || c_role==" ")
                {
                    Toast.makeText(getApplicationContext(),"Fill in all the details correctly", Toast.LENGTH_SHORT).show();
                    onLogin(v);
                }else if(query.contains(ed_name.getText().toString())  && query.contains(ed_psw.getText().toString()) && student && !admin)
                {
                    Toast.makeText(getApplicationContext(), "Student Login", Toast.LENGTH_SHORT).show();
                    onProfile_student(v);
                }
                else if(query.contains(ed_name.getText().toString()) && query.contains(ed_psw.getText().toString()) && admin && !student)
                {
                    Toast.makeText(getApplicationContext(), "Admin Login", Toast.LENGTH_SHORT).show();
                    onProfile_admin(v);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    onLogin(v);
                }
            }
        });

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Bye..", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        t_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegister(view);
            }
        });
    }

    public void onRegister(View view) {
        setContentView(R.layout.register);
        b_register=(Button) findViewById(R.id.button);
        b_cancel = (Button) findViewById(R.id.button2);
        t_login = (TextView) findViewById(R.id.TextView1);
        ed_name = (EditText) findViewById(R.id.editText1);
        ed_psw = (EditText) findViewById(R.id.editText2);
        ed_regno = (EditText) findViewById(R.id.editText3);

        dbHandler = new DBHandler(MainActivity.this);

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regno=ed_regno.getText().toString();
                name=ed_name.getText().toString();
                psw=ed_psw.getText().toString();
                c_role=role;
                if(ed_name.getText().toString().isEmpty() || ed_psw.getText().toString().isEmpty() || ed_regno.getText().toString().isEmpty() || c_role==" ")
                {
                    Toast.makeText(getApplicationContext(),"Fill in all the details", Toast.LENGTH_SHORT).show();
                    onRegister(view);
                }
                else {
                    dbHandler.addNewCourse(regno,name,psw,c_role);
                    Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                    onLogin(view);
                }
            }
        });

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Bye..", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        t_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(view);

            }
        });
    }

    public void onProfile_admin(View view){
        setContentView(R.layout.profile);
        b_booking=(Button) findViewById(R.id.button);
        b_request = (Button) findViewById(R.id.button2);
        b_logout=(Button) findViewById(R.id.button3);
        b_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check_aBooking(v);
            }
        });
        b_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check_Request(v);
            }
        });
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout(v);
            }
        });
    }

    public void onProfile_student(View view){
        setContentView(R.layout.profile_student);
        b_book=(Button)findViewById(R.id.button);
        b_request = (Button) findViewById(R.id.button1);
        b_logout=(Button) findViewById(R.id.button3);

        b_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBooking(v);
            }
        });
        b_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRequest(v);
            }
        });
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout(v);
            }
        });
    }

    public void onLogout(View v){

        setContentView(R.layout.activity_main);
        t_login=(TextView) findViewById(R.id.textView1);
        t_register=(TextView) findViewById(R.id.textView2);

        t_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });
        t_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegister(view);
            }
        });
    }

    public void onBooking(View v){

        setContentView(R.layout.slot);
        b_book=(Button) findViewById(R.id.button3);
        b_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBooking(v);
            }
        });
        ed_roomno = (EditText) findViewById(R.id.editText);
        ed_date = (EditText) findViewById(R.id.editText2);

        ed_date.setOnClickListener(new View.OnClickListener() {
            int mYear, mMonth, MDate;
            @Override
            public void onClick(View view) {
                if(view== ed_date)
                {
                    final Calendar calendar = Calendar.getInstance();
                    mYear=calendar.get(Calendar.YEAR);
                    mMonth=calendar.get(Calendar.MONTH);
                    MDate=calendar.get((Calendar.DATE));
                    DatePickerDialog datePickerDialog = new DatePickerDialog (view.getContext(), new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            ed_date.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, MDate );
                    datePickerDialog.show ();
                }

            }});

        ed_time = (EditText) findViewById(R.id.editText3);

        ed_time.setOnClickListener(new View.OnClickListener() {
            int mYear, mMonth, MDate;
            @Override
            public void onClick(View view) {
                if(view== ed_time)
                {
                    final Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String min=""+selectedMinute;
                            if(selectedMinute<10)
                            { min ="0"+selectedMinute;
                            }
                            String hr =""+selectedHour;
                            if(selectedHour<10)
                            { hr ="0"+selectedHour;
                            }
                            ed_time.setText(hr+":"+ min);
                        }
                    }, hour, minute, false);
                    timePickerDialog.show();
                }

            }});


        dbSlot = new DBSlot(MainActivity.this);
        boolean Booked=true;
        b_booking=(Button) findViewById(R.id.button);
        b_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=2;
                Room_No = ed_roomno.getText().toString();
                Date = ed_date.getText().toString();
                Time= ed_time.getText().toString();

                if (ed_roomno.getText().toString().isEmpty() || ed_date.getText().toString().isEmpty() || ed_time.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_SHORT).show();
                    onBooking(view);
                } else if(ListRoom.contains(Room_No)){
                    Toast.makeText(getApplicationContext(), "Room already in use, Fill again", Toast.LENGTH_SHORT).show();
                    onBooking(view);
                }
                else {
                    dbSlot.addNewCourse(Room_No,Date, Time,Booked);
                    Booked_Rooms[i]=Room_No;
                    String bookingDetails = "" +"Room No:" +Room_No+" \nDate:" +Date+" \nTime: "+ Time;
                    ListRoom.add(Room_No);
                    BookingDetails.add(bookingDetails);

                    j=i;
                    Toast.makeText(getApplicationContext(), "Room Booked successfully", Toast.LENGTH_SHORT).show();
                    Check_Booking(view);
                    i=i+1;
                }

            }
        });

    }


    public void onRequest(View v){

        setContentView(R.layout.request);
        ed_to= (EditText) findViewById(R.id.editText);
        ed_subject = (EditText) findViewById(R.id.editText2);
        ed_message  = (EditText) findViewById(R.id.editText3);
        b_send=(Button) findViewById(R.id.buttonx);
        dbRequest = new DBRequest(MainActivity.this);
        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                To_Id = ed_to.getText().toString();
                Subject = ed_subject.getText().toString();
                Message= ed_message.getText().toString();
                if (ed_to.getText().toString().isEmpty() || ed_subject.getText().toString().isEmpty() || ed_message.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_SHORT).show();
                    onRequest(view);
                }
                else {
                    bookingDetails = "" +"To_id:" +To_Id+" Subject:" +Subject+" Message: "+ Message;
                    Pending.add(bookingDetails);
                    Toast.makeText(getApplicationContext(), "Request Sent successfully", Toast.LENGTH_SHORT).show();
                    view_Request(view);
                }
            }
        });

    }

    public void view_Request(View v){
        setContentView(R.layout.request_view);
        t_pending=(TextView) findViewById(R.id.TextView);
        pending = findViewById(R.id.Pending);
        ArrayAdapter<String> arr2;
        arr2 = new ArrayAdapter<String>(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item,Pending);
        pending.setAdapter(arr2);
        t_accept=(TextView) findViewById(R.id.TextView1);
        accept = findViewById(R.id.accepted);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item,Accepted);
        accept.setAdapter(arr);
        t_reject=(TextView) findViewById(R.id.TextView2);
        reject = findViewById(R.id.rejected);
        ArrayAdapter<String> arr1;
        arr1 = new ArrayAdapter<String>(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item,Rejected);
        reject.setAdapter(arr1);

    }

    public void view_aRequest(View v){
        setContentView(R.layout.request_aview);


        t_accept=(TextView) findViewById(R.id.TextView1);
        accept = findViewById(R.id.accepted);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item,Accepted);
        accept.setAdapter(arr);
        t_reject=(TextView) findViewById(R.id.TextView2);
        reject = findViewById(R.id.rejected);
        ArrayAdapter<String> arr1;
        arr1 = new ArrayAdapter<String>(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item,Rejected);
        reject.setAdapter(arr1);
    }

    public void Check_Booking(View v){
        setContentView(R.layout.slot_view);

        booked = findViewById(R.id.list);
        ListBookings.add(Booked_Rooms[j]);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item,BookingDetails);
        booked.setAdapter(arr);

        Toast.makeText(getApplicationContext(), "The room booked is"+Booked_Rooms[j], Toast.LENGTH_SHORT).show();
    }

    public void Check_aBooking(View v){
        setContentView(R.layout.slot_aview);

        booked = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item,BookingDetails);
        booked.setAdapter(arr);
    }

    public void Check_Request(View v){
        setContentView(R.layout.grant_request);
        dbRequest = new DBRequest(MainActivity.this);
        b_booking=(Button) findViewById(R.id.button);
        b_request=(Button) findViewById(R.id.button1);
        b_logout=(Button) findViewById(R.id.button3);
        b_grant=(Button) findViewById(R.id.button4);
        b_deny=(Button) findViewById(R.id.button5);

        for(int i=0;i<Pending.size();i++)
        {
            t_print= (TextView) findViewById(R.id.textview);
            t_print.setText(Pending.get(i));
            Pending.set(i,"");
        }


        b_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check_aBooking(v);
            }
        });
        b_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_aRequest(v);
            }
        });

        b_grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accepted.add(bookingDetails);
                view_aRequest(v);
            }
        });

        b_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rejected.add(bookingDetails);
                view_aRequest(v);
            }
        });

        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout(v);
            }
        });

    }
}
