import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by student on 2017. 4. 13..
 */
public class CallManager {

    // getters
    public ArrayList<Call> getCallCollection() {
        return callCollection;
    }
    public Call get(int index){
        return callCollection.get(index);
    }

    // member functions
    public void Save(){
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
            Writer writer = new FileWriter("call.json");
            gson.toJson(callCollection, writer);
            writer.close();
        }
        catch(IOException e){
            System.out.println("CallManager Save() IOException occured!");
        }
    }
    public void Load(){
        callCollection.clear();
        try {
            JsonReader reader = new JsonReader(new FileReader("call.json"));
            reader.beginArray();
            while(reader.hasNext()){
                reader.beginObject();

                reader.nextName(); // "phoneNumber"
                String phoneNumber = reader.nextString();
                reader.nextName(); // "called"
                Boolean called = reader.nextBoolean();
                reader.nextName(); // "missed"
                Boolean missed = reader.nextBoolean();
                reader.nextName(); // "startTime"
                String startTimeString = reader.nextString();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                Date startTime = format.parse(startTimeString);
                reader.nextName(); // "endTime"
                String endTimeString = reader.nextString();
                Date endTime = format.parse(endTimeString);
                reader.endObject();

                addCall(phoneNumber, called, missed, startTime, endTime);
            }
            reader.endArray();
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("CallManager Load() FileNotFoundException occured!");
        }
        catch(IOException e){
            System.out.println("CallManager Load() IOException occured!");
        }
        catch(ParseException e){
            System.out.println("CallManager Load() ParseException occured!");
        }
    }
    public ArrayList<Integer> SearchByPhoneNumber(String str){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for(int i = 0; i < callCollection.size(); i++){
            if(callCollection.get(i).getPhoneNumber().contains(str)){
                indexList.add(i);
            }
        }
        return indexList;
    }
    public ArrayList<Integer> SearchByPhoneNumber(String str, ArrayList<Integer> indexList){
        ArrayList<Integer> newIndexList = new ArrayList<Integer>();
        int i;
        for(Iterator<Integer> it = indexList.iterator(); it.hasNext(); ){
            i = it.next();
            if(callCollection.get(i).getPhoneNumber().contains(str)){
                newIndexList.add(i);
            }
        }
        return newIndexList;
    }
    public ArrayList<Integer> SearchByPhoneNumberExact(String str){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for(int i = 0; i < callCollection.size(); i++){
            if(callCollection.get(i).getPhoneNumber().equals(str)){
                indexList.add(i);
            }
        }
        return indexList;
    }
    public void addCall(Call call){
        callCollection.add(call);
    }
    public void addCall(String phoneNumber, Boolean called, Boolean missed, Date startTime, Date endTime){
        addCall(new Call(phoneNumber, called, missed, startTime, endTime));
    }
    public void deleteCall(int index){
        callCollection.remove(index);
    }

    // member variable
    private ArrayList<Call> callCollection = new ArrayList<Call>();
}
