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

import com.example.student.addressbook.api.Address;
import com.example.student.addressbook.api.AddressManager;
import com.example.student.addressbook.api.SMSManager;
import com.example.student.addressbook.dummy.DummyContent;
import com.example.student.addressbook.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SMSListFragment extends ListFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private AddressManager addressManager;
    private SMSManager smsManager;
    SQLiteDatabase db;
    ListView listView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SMSListFragment() { }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SMSListFragment newInstance(int columnCount) {
        SMSListFragment fragment = new SMSListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        db = getActivity().openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
        addressManager = new AddressManager(db);
        smsManager = new SMSManager(db);

    }
    public void SearchSMSList(String str){
        ArrayList IDList = smsManager.GetIDByNumber(str);
        Cursor c = smsManager.GetCursorByIDList(IDList);
        ShowContactList(c);
    }
    public void ShowContactList(final Cursor c){
        String[] columns = new String[] { "NUMBER", "MESSAGE" };
        int[] to = new int[] { R.id.list_number, R.id.list_content};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_sms, c, columns, to);
        setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c.moveToFirst();
                // go to position
                c.moveToPosition(position);

                //get ID of the clicked object
                int ID = c.getInt(1);
                smsManager.deleteSMS(ID);
                Toast.makeText(getActivity(), "Delete successful!!!!! ", Toast.LENGTH_SHORT).show();
                SearchSMSList("");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sms_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listView = getListView();
        SearchSMSList("");
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
    public void onResume(){
        super.onResume();
        SearchSMSList("");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
