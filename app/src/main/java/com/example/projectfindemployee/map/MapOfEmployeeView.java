package com.example.projectfindemployee.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.projectfindemployee.model.Employee;
import com.example.projectfindemployee.retrofit.EmployeeApi;
import com.example.projectfindemployee.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapOfEmployeeView extends View {
    protected Paint paint = new Paint();

    public double      size ;
    public double coordinateX=0.0, coordinateY=0.0,coordinateZ=0.0;
    public double coordinateX1=0.0, coordinateY1=0.0,coordinateZ1=0.0;
    boolean flag = false;
    Employee eqads;
    public double coefficient_of_change;

    public void setContext(Context context) {
        this.context = context;
    }

    //Toast.makeText(getContext(),""+size,Toast.LENGTH_SHORT).show();

    Context context;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        size= Math.min(getWidth(), getHeight());
        coefficient_of_change=5*(size/500)*100;

    }
    public double returnSize(){
        return size;
    }

    public void setCoordinates(Double coordinateX, Double coordinateY,Double coordinateZ) {

        this.coordinateX=coordinateX*coefficient_of_change;
        this.coordinateY=coordinateY*coefficient_of_change;
        this.coordinateZ=coordinateZ*coefficient_of_change;

        this.invalidate();

    }
    public void setCoordinates1() {
        RetrofitService retrofitService =new RetrofitService();
        EmployeeApi employeeApi =  retrofitService.getRetrofit().create(EmployeeApi.class);
        eqads =new Employee();
        employeeApi.getEmployeeCoordinate().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {

                eqads     = (Employee) response.body();

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }
        });
        this.coordinateX1=eqads.getCoordinateX()*coefficient_of_change;
        this.coordinateY1=eqads.getCoordinateY()*coefficient_of_change;


        this.invalidate();

    }
    public MapOfEmployeeView(Context context) {
        super(context);

    }

    public MapOfEmployeeView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Toast.makeText(context, coordinateX+"   "+coordinateY, Toast.LENGTH_SHORT).show();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        canvas.drawRect(0, 0, (float) size, (float) size, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLACK);

        canvas.drawCircle((float) (coordinateX), (float) (coordinateY), 30, paint);
        canvas.drawCircle((float) (coordinateX1), (float) (coordinateY1), 30, paint);
    }


}
