package com.example.student.addressbook;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student.addressbook.api.AddressManager;
import com.example.student.addressbook.api.CallManager;
import com.example.student.addressbook.dummy.DummyContent;
import com.example.student.addressbook.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class CallFragment extends ListFragment {

    // custom member variables
    AddressManager addressManager;
    CallManager callManager;
    ListView listView;
    SQLiteDatabase db;

    // basic member variables
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    // custom member functions
    public void SearchContactList(String str){
        ArrayList IDList = callManager.GetIDByNumber(str);
        Cursor c = callManager.GetCursorByIDList(IDList);
        ShowContactList(c);
    }
    public void ShowContactList(final Cursor c) {
        String[] columns = new String[]{"NUMBER", "STARTTIME"};
        int[] to = new int[]{R.id.list_number, R.id.list_start_time};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_sms, c, columns, to);
        setListAdapter(adapter);

        // !!!!!!!!DELETE WHEN CLICKED!!!!!!!!!!
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c.moveToFirst();
                // go to position
                c.moveToPosition(position);

                //get ID of the clicked object
                int ID = c.getInt(1);
                callManager.deleteCall(ID);
                Toast.makeText(getActivity(), "Delete successful!!!!!", Toast.LENGTH_SHORT).show();
                SearchContactList("");
            }
        });
    }

    // basic member functions
    public CallFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = getActivity().openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
        addressManager = new AddressManager(db);
        callManager = new CallManager(db);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_list, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listView = getListView();
        SearchContactList("");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
    public void onResume(){
        super.onResume();
        SearchContactList("");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
