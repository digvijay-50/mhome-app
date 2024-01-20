package com.propertybuysell.mhome.Activities;

import static com.propertybuysell.mhome.RetrofitUtils.Constant.Avaliablefor;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Floordetail;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Furnished;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Typesofproperty;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.configuration;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.property;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.propertybuysell.mhome.Adapters.StringArrayAdapter;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.Constant;

import java.util.ArrayList;

public class EditPostthirdActivity extends AppCompatActivity {

    TextView tv_proceed, tv_areaprice;
    Spinner spinnerarea, spinner_cat, spinnerpricearea;
    ArrayList<String> arrayListarea = new ArrayList<>();
    TextView tv_north, tv_south, tv_east, tv_west;
    ArrayList<String> arrayListcat = new ArrayList<>();
    LinearLayout ll_flor, ll_furni, ll_confi, ll_avaliable, ll_price2, ll_price1,ll_facing;
    TextView tv_1rk, tv_1bhk, tv_2bhk, tv_3bhk, tv_4bhk, tv_5bhk, tv_other, tv_unfur, tv_semifur, tv_fur, tv_family, tv_bechlor, tv_avother;
    EditText et_area, et_exprice, et_priceper, et_exprice2, et_flornumber;

    GetProperty_Inner getProperty_inner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addthird_post);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });

        getProperty_inner = (GetProperty_Inner) getIntent().getSerializableExtra("getProperty_inner");

        tv_north = findViewById(R.id.tv_north);
        tv_south = findViewById(R.id.tv_south);
        tv_east = findViewById(R.id.tv_east);
        tv_west = findViewById(R.id.tv_west);
        ll_facing = findViewById(R.id.ll_facing);

        tv_proceed = findViewById(R.id.tv_proceed);
        tv_areaprice = findViewById(R.id.tv_areaprice);
        spinnerarea = findViewById(R.id.spinnerarea);
        spinnerpricearea = findViewById(R.id.spinnerpricearea);
        ll_flor = findViewById(R.id.ll_flor);
        ll_price2 = findViewById(R.id.ll_price2);
        ll_price1 = findViewById(R.id.ll_price1);
        ll_furni = findViewById(R.id.ll_furni);
        ll_confi = findViewById(R.id.ll_confi);
        spinner_cat = findViewById(R.id.spinner_cat);
        ll_avaliable = findViewById(R.id.ll_avaliable);
        tv_1rk = findViewById(R.id.tv_1rk);
        tv_1bhk = findViewById(R.id.tv_1bhk);
        tv_2bhk = findViewById(R.id.tv_2bhk);
        tv_3bhk = findViewById(R.id.tv_3bhk);
        tv_4bhk = findViewById(R.id.tv_4bhk);
        tv_5bhk = findViewById(R.id.tv_5bhk);
        tv_other = findViewById(R.id.tv_other);
        tv_unfur = findViewById(R.id.tv_unfur);
        tv_semifur = findViewById(R.id.tv_semifur);
        tv_fur = findViewById(R.id.tv_fur);
        tv_family = findViewById(R.id.tv_family);
        tv_bechlor = findViewById(R.id.tv_bechlor);
        tv_avother = findViewById(R.id.tv_avother);
        et_area = findViewById(R.id.et_area);
        et_exprice = findViewById(R.id.et_exprice);
        et_priceper = findViewById(R.id.et_priceper);
        et_exprice2 = findViewById(R.id.et_exprice2);
        et_flornumber = findViewById(R.id.et_flornumber);


        TextView tv_top1 = findViewById(R.id.tv_top1);
        tv_top1.setText("Edit Property Details");

        arrayListcat.add("Select Category");
        arrayListcat.add("Gen");
        arrayListcat.add("OBC");
        arrayListcat.add("ST");
        arrayListcat.add("SC");


        if (Typesofproperty.equalsIgnoreCase("plot") || Typesofproperty.equalsIgnoreCase("Land")) {
            arrayListarea.add("Sq.Ft");
            arrayListarea.add("Biswa");
            arrayListarea.add("Bigha");
            arrayListarea.add("Guj");
        } else {
            arrayListarea.add("Sq.Ft");
            arrayListarea.add("Sq.Mtrs");
        }


        StringArrayAdapter adapter4 = new StringArrayAdapter(EditPostthirdActivity.this,
                R.layout.single_spinnerlist, arrayListcat);
        spinner_cat.setAdapter(adapter4);


        StringArrayAdapter adapter2 = new StringArrayAdapter(EditPostthirdActivity.this,
                R.layout.single_spinnerlist, arrayListarea);
        spinnerarea.setAdapter(adapter2);

        StringArrayAdapter adapter3 = new StringArrayAdapter(EditPostthirdActivity.this,
                R.layout.single_spinnerlist, arrayListarea);
        spinnerpricearea.setAdapter(adapter3);

        spinnerarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constant.area_sq = spinnerarea.getSelectedItem().toString();
                tv_areaprice.setText(spinnerarea.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        spinnerpricearea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Constant.price_sq=spinnerpricearea.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constant.s_cast = spinner_cat.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        et_area.setText(getProperty_inner.getAreaDetail());
        et_exprice.setText(getProperty_inner.getProperty_final_price());
        et_priceper.setText(getProperty_inner.getPriceDetail());
        et_flornumber.setText(getProperty_inner.getFloorDetail());

        spinner_cat.setSelection(getcatselection(getProperty_inner.getUserCategory(), arrayListcat));
        spinnerarea.setSelection(getcatselection(getProperty_inner.getAreaUnitDetail(), arrayListarea));


//        getcatselection(getProperty_inner.getUserCategory(),arrayListarea);

        if (et_priceper.getText().toString().equalsIgnoreCase("")) {
            et_exprice2.setText("0");
        } else {
            try {
                Double priceper = Double.valueOf(et_priceper.getText().toString());
                Double areadetail = Double.valueOf(et_area.getText().toString()) * priceper;


                float firstNumber = Float.parseFloat(String.valueOf(areadetail));

                et_exprice2.setText(String.format("%.0f", firstNumber) + "");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        configuration = getProperty_inner.getPropertyConfiguration();
        Furnished = getProperty_inner.getFurnishingDetail();
        Avaliablefor = getProperty_inner.getAvailableFor();

        if (getProperty_inner.getAvailableFor().equalsIgnoreCase("Family")) {
            tv_family.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_bechlor.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_avother.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getAvailableFor().equalsIgnoreCase("Bachelor")) {
            tv_family.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_bechlor.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_avother.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getAvailableFor().equalsIgnoreCase("Other")) {
            tv_family.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_bechlor.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_avother.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        }


        if (getProperty_inner.getProperty_facing().equalsIgnoreCase("North")) {
            Constant.property_facing="North";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getProperty_facing().equalsIgnoreCase("East")) {
            Constant.property_facing="East";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getProperty_facing().equalsIgnoreCase("West")) {
            Constant.property_facing="West";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        }else if (getProperty_inner.getProperty_facing().equalsIgnoreCase("South")) {
            Constant.property_facing="South";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        }

        if (getProperty_inner.getFurnishingDetail().equalsIgnoreCase("Unfurnished")) {
            tv_unfur.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_semifur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getFurnishingDetail().equalsIgnoreCase("Semi Furnished")) {
            tv_unfur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_fur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getFurnishingDetail().equalsIgnoreCase("Furnished")) {
            tv_unfur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        }


        if (getProperty_inner.getPropertyConfiguration().equalsIgnoreCase("1 RK")) {
            tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getPropertyConfiguration().equalsIgnoreCase("1 BHK")) {
            tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getPropertyConfiguration().equalsIgnoreCase("2 BHK")) {
            tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getPropertyConfiguration().equalsIgnoreCase("3 BHK")) {
            tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getPropertyConfiguration().equalsIgnoreCase("4 BHK")) {
            tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getPropertyConfiguration().equalsIgnoreCase("5 BHK")) {
            tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (getProperty_inner.getPropertyConfiguration().equalsIgnoreCase("Other")) {
            tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        }


        tv_north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.property_facing="North";
                tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_east.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.property_facing="East";
                tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.property_facing="West";
                tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_south.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.property_facing="South";
                tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            }
        });

        tv_1rk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "1 RK";
                tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_1bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "1 BHK";
                tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_2bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "2 BHK";
                tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_3bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "3 BHK";
                tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_4bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "4 BHK";
                tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_5bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "5 BHK";
                tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });

        tv_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "Other";
                tv_1rk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            }
        });

        tv_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Avaliablefor = "Family";
                tv_family.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_bechlor.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_bechlor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Avaliablefor = "Bachelor";
                tv_family.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_avother.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_avother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Avaliablefor = "Other";
                tv_family.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            }
        });

        tv_unfur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Unfurnished";
                tv_unfur.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_semifur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_semifur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Semi Furnished";
                tv_unfur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_fur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_fur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Furnished";
                tv_unfur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            }
        });

        if (SearchlookingTo.equalsIgnoreCase("Rent")) {
            if (property.equalsIgnoreCase("Residential")) {
                if (Typesofproperty.equalsIgnoreCase("Home")) {
                    ll_price1.setVisibility(View.VISIBLE);
                    ll_price2.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.GONE);
                    ll_furni.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_confi.setVisibility(View.VISIBLE);
                    ll_avaliable.setVisibility(View.VISIBLE);
                } else {
                    ll_price1.setVisibility(View.VISIBLE);
                    ll_price2.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_confi.setVisibility(View.VISIBLE);
                    ll_avaliable.setVisibility(View.VISIBLE);
                }
            } else if (property.equalsIgnoreCase("Commercial")) {
                if (Typesofproperty.equalsIgnoreCase("Office")) {
                    ll_price1.setVisibility(View.VISIBLE);
                    ll_price2.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_confi.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                } else {
                    ll_price1.setVisibility(View.VISIBLE);
                    ll_price2.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_confi.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                }
            }
        } else if (SearchlookingTo.equalsIgnoreCase("Sell")) {
            if (property.equalsIgnoreCase("Residential")) {
                if (Typesofproperty.equalsIgnoreCase("Home")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.GONE);
                    ll_furni.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_confi.setVisibility(View.VISIBLE);
                    ll_avaliable.setVisibility(View.GONE);
                } else if (Typesofproperty.equalsIgnoreCase("Apartment")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.VISIBLE);
                    ll_confi.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_avaliable.setVisibility(View.GONE);
                } else if (Typesofproperty.equalsIgnoreCase("Plot")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.GONE);
                    ll_furni.setVisibility(View.GONE);
                    ll_confi.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                } else if (Typesofproperty.equalsIgnoreCase("Land")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.GONE);
                    ll_furni.setVisibility(View.GONE);
                    ll_confi.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                }
            } else {
                if (Typesofproperty.equalsIgnoreCase("Office")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.GONE);
                    ll_confi.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                } else if (Typesofproperty.equalsIgnoreCase("Shop")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.GONE);
                    ll_confi.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                } else if (Typesofproperty.equalsIgnoreCase("Plot")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.GONE);
                    ll_furni.setVisibility(View.GONE);
                    ll_confi.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                } else if (Typesofproperty.equalsIgnoreCase("Land")) {
                    ll_price1.setVisibility(View.GONE);
                    ll_price2.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.GONE);
                    ll_furni.setVisibility(View.GONE);
                    ll_confi.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
                    ll_avaliable.setVisibility(View.GONE);
                }
            }
        }


        et_priceper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Toast.makeText(EditPostthirdActivity.this, "hii", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_priceper.getText().toString().equalsIgnoreCase("")) {
                    et_exprice2.setText("0");
                } else {
                    try {
                        Double priceper = Double.valueOf(et_priceper.getText().toString());
                        Double areadetail = Double.valueOf(et_area.getText().toString()) * priceper;

                        et_exprice2.setText(areadetail + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_area.getText().toString())) {
                    Toast.makeText(EditPostthirdActivity.this, "Please Enter Area", Toast.LENGTH_SHORT).show();
                } else if (spinner_cat.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                    Toast.makeText(EditPostthirdActivity.this, "Please Select Categories", Toast.LENGTH_SHORT).show();
                } else {
                    if (SearchlookingTo.equalsIgnoreCase("Rent")) {
                        if (TextUtils.isEmpty(et_exprice.getText().toString())) {
                            Toast.makeText(EditPostthirdActivity.this, "Enter Expected Price", Toast.LENGTH_SHORT).show();
                        } else {
                            Floordetail = et_flornumber.getText().toString();
                            Intent i = new Intent(EditPostthirdActivity.this, EditPostfourthActivity.class);
                            i.putExtra("et_area", et_area.getText().toString());
                            i.putExtra("et_exprice", et_exprice.getText().toString());
                            i.putExtra("et_priceper", et_priceper.getText().toString());
                            i.putExtra("et_exprice2", et_exprice.getText().toString());
                            i.putExtra("et_flornumber", et_flornumber.getText().toString());
                            i.putExtra("getProperty_inner", getProperty_inner);

                            startActivity(i);
                        }
                    } else {
                        if (TextUtils.isEmpty(et_exprice2.getText().toString())) {
                            Toast.makeText(EditPostthirdActivity.this, "Please Enter Price Per.", Toast.LENGTH_SHORT).show();
                        } else {
                            Floordetail = et_flornumber.getText().toString();
                            Intent i = new Intent(EditPostthirdActivity.this, EditPostfourthActivity.class);
                            i.putExtra("et_area", et_area.getText().toString());
                            i.putExtra("et_exprice", et_exprice.getText().toString());
                            i.putExtra("et_priceper", et_priceper.getText().toString());
                            i.putExtra("et_exprice2", et_exprice2.getText().toString());
                            i.putExtra("et_flornumber", et_flornumber.getText().toString());
                            i.putExtra("getProperty_inner", getProperty_inner);

                            startActivity(i);
                        }
                    }
                }
            }
        });
    }

    private int getcatselection(String cat_name, ArrayList<String> job_sub_inners) {
        try {
            int itemlist = 0;
            if (job_sub_inners == null) {
                itemlist = 0;
            } else {
                for (int i = 0; i < job_sub_inners.size(); i++) {
                    if (cat_name.equalsIgnoreCase(job_sub_inners.get(i))) {
                        itemlist = i;
                        break;
                    }
                }
            }
            return itemlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}