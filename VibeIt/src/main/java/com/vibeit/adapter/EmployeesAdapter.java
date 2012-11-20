package com.vibeit.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.vibeit.R;
import com.vibeit.api.model.Employee;

/**
 * @author Maria Dzyokh
 */
public class EmployeesAdapter extends BaseAdapter {

    private LayoutInflater li;
    private Employee[] employees;

    public EmployeesAdapter(Context ctx, Employee[] employees) {
       this.li = LayoutInflater.from(ctx);
       this.employees = employees;
    }

    public Employee[] getEmployees() {
        return this.employees;
    }

    @Override
    public int getCount() {
        return employees.length;
    }

    @Override
    public Employee getItem(int position) {
        return employees[position];
    }

    @Override
    public long getItemId(int position) {
        return employees[position].getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view==null) {
            view = li.inflate(R.layout.employee_rating_list_item, null);
        }
        final int p = position;
        Employee employee = getItem(position);
        ((TextView)view.findViewById(R.id.employee_name)).setText(employee.getFirstName()+" "+employee.getLastName().substring(0,1)+".");

        RatingBar employeeRating = ((RatingBar)view.findViewById(R.id.employee_rating));
        employeeRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                  getItem(p).setUserRating(v);
            }
        });
        EditText comment = ((EditText)view.findViewById(R.id.comment));
        ((EditText)view.findViewById(R.id.comment)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   getItem(p).setUserComment(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }
}
