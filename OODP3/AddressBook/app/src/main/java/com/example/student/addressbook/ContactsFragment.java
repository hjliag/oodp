package com.example.student.addressbook;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student.addressbook.api.AddressManager;
import com.example.student.addressbook.dummy.DummyContent;
import com.example.student.addressbook.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends ListFragment {

    // custom member variables
    AddressManager addressManager;
    ListView listView;
    SQLiteDatabase db;
    Button buttonSearchContact;
    EditText editSearchContact;


    // basic member variables
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    // custom member functions
    public void SearchContactList(String str){
        ArrayList IDList = addressManager.GetIDByNumber(str);
        Cursor c = addressManager.GetCursorByIDList(IDList);
        ShowContactList(c);
    }
    public void StartContactView(int ID){
        // send ID to next activity
        Intent intent = new Intent(getActivity(), ContactViewActivity.class);
        intent.putExtra("VIEW_ID", ID);

        startActivity(intent);
    }
    public void ShowContactList(final Cursor c){
        String[] columns = new String[] { "NAME" };
        int[] to = new int[] { R.id.contact_name };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_contacts, c, columns, to);
        setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c.moveToFirst();
                // go to position
                c.moveToPosition(position);

                //get ID of the clicked object
                int ID = c.getInt(1);

                StartContactView(ID);
            }
        });
    }

    // basic member variables
    public ContactsFragment() { }
    public static ContactsFragment newInstance(int columnCount) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    public void onResume(){
        super.onResume();

        SearchContactList("");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        db = getActivity().openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
        addressManager = new AddressManager(db);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listView = getListView();
        buttonSearchContact = (Button)getActivity().findViewById(R.id.button_search_contact);
        editSearchContact = (EditText)getActivity().findViewById(R.id.edit_search_contact);
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
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }
}
