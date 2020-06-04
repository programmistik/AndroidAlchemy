package com.example.alchemy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] elements = {"Fire", "Water", "Earth", "Air"};
    ArrayList<Alch> AlchemyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Create();

        findViewById(R.id.linLayout).setOnDragListener(new MyDragListener());
        findViewById(R.id.dragLayout).setOnDragListener(new MyDragListener());

        Spinner spinner = (Spinner) findViewById(R.id.elmSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void AddElement(View view) {
        LinearLayout linearLayout = findViewById(R.id.linLayout);
        TextView textView1 = new TextView(this);
        textView1.setOnTouchListener(new MyTouchListener());
        // textView1.setOnTouchListener(this);
        // установка фонового цвета
        textView1.setBackgroundColor(0xffe8eaf6);
        // установка цвета текста
        textView1.setTextColor(0xff5c6bc0);
        // делаем все буквы заглавными
        textView1.setAllCaps(true);
        // устанавливаем вравнивание текста по центру
        textView1.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        // устанавливаем текста
        Spinner spinner = (Spinner) findViewById(R.id.elmSpinner);

        textView1.setText(spinner.getSelectedItem().toString());
        // установка шрифта
        textView1.setTypeface(Typeface.create("casual", Typeface.NORMAL));
        // устанавливаем высоту текста
        textView1.setTextSize(25);
        textView1.setId(view.generateViewId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 90);
        // установка внешних отступов
        layoutParams.setMargins(20,20,20,20);
        // устанавливаем размеры
        textView1.setLayoutParams(layoutParams);

        linearLayout.addView(textView1);
        // setContentView(linearLayout);
    }

    private final class MyTouchListener implements View.OnTouchListener  {

        // called when the item is long-clicked
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            // TODO Auto-generated method stub

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {                

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }


        }
    }

    class MyDragListener implements View.OnDragListener {
        Drawable normalShape = getResources().getDrawable(R.drawable.normal_shape);
        Drawable targetShape = getResources().getDrawable(R.drawable.target_shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;

                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:


                    v.invalidate();
                    return true;
                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);   //change the shape of the view back to normal
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    // Получаем надпись перетаскиваемого элемента
                    String clipData = ((TextView) event.getLocalState()).getText().toString();
                    TextView tv = (TextView) event.getLocalState();

                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;

                    boolean isAlchemy = false;

                    for(int index = 0; index < ((ViewGroup) container).getChildCount(); index++) {
                        View nextChild = ((ViewGroup) container).getChildAt(index);

                        if (nextChild instanceof TextView) {
                            TextView textView = (TextView) nextChild;
                            // do what you want with textView

                            int top = nextChild.getTop();
                            int bottom = nextChild.getBottom();
                            int left = nextChild.getLeft();
                            int right = nextChild.getRight();

                            if (x >= left & x <= right & y >= top & y <= bottom) {
                                // проверка рецепта
                                String childName = textView.getText().toString();
                                String Alchemy = newAlchemy(clipData, childName);
                                if (!Alchemy.equals("NoResult"))
                                {
                                    textView.setText(Alchemy);
                                    tv.setVisibility(View.GONE);
                                    isAlchemy = true;
                                }
                            }

                        }
                    }

                    if (!isAlchemy){
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                    }
                    break;


                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);   //go back to normal shape

                default:
                    break;
            }
            return true;
        }
    }

    public String newAlchemy(String name1, String name2){

        for (Alch a: AlchemyList) {
            String res = a.getResult(name1, name2);
            if(!res.equals("NoResult")){
                return res;
            }
        }
        return "NoResult";
    }

    public void Create(){

        AlchemyList = new ArrayList<>();

        AlchemyList.add(new Alch("Water", "Air", "Steam"));
        AlchemyList.add(new Alch("Air", "Water", "Steam"));
        AlchemyList.add(new Alch("Water", "Water", "Lake"));
        AlchemyList.add(new Alch("Water", "Fire", "Alcohol"));
        AlchemyList.add(new Alch("Fire", "Water", "Alcohol"));
        AlchemyList.add(new Alch("Water", "Earth", "Swamp"));
        AlchemyList.add(new Alch("Earth", "Water", "Swamp"));
        AlchemyList.add(new Alch("Air", "Fire", "Energy"));
        AlchemyList.add(new Alch("Fire", "Air", "Energy"));
        AlchemyList.add(new Alch("Air", "Earth", "Dust"));
        AlchemyList.add(new Alch("Earth", "Air", "Dust"));
        AlchemyList.add(new Alch("Fire", "Earth", "Lava"));
        AlchemyList.add(new Alch("Earth", "Fire", "Lava"));
       
    }

}