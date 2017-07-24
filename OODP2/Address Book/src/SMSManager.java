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
 * Created by student on 2017. 4. 12..
 */
public class SMSManager {
    // Getter
    public ArrayList<SMS> getSMSCollection() {
        return SMSCollection;
    }
    public SMS get(int index){
        return SMSCollection.get(index);
    }

    // Member functions
    public void Save(){
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
            Writer writer = new FileWriter("SMS.json");
            gson.toJson(SMSCollection, writer);
            writer.close();
        }
        catch(IOException e){
            System.out.println("AddressManager Save() IOException occured!");
        }
    }
    public void Load(){
        SMSCollection.clear();
        try {
            JsonReader reader = new JsonReader(new FileReader("SMS.json"));
            reader.beginArray();
            while(reader.hasNext()){
                reader.beginObject();

                reader.nextName(); // "phoneNumber"
                String phoneNumber = reader.nextString();
                reader.nextName(); // "sent"
                Boolean sent = reader.nextBoolean();
                reader.nextName(); // "message"
                String message = reader.nextString();
                reader.nextName(); // "Date"
                String dateString = reader.nextString();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                Date date = format.parse(dateString);

                reader.endObject();

                addSMS(phoneNumber, sent, message, date);
            }
            reader.endArray();
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("SMSManager Load() FileNotFoundException occured!");
        }
        catch(IOException e){
            System.out.println("SMSManager Load() IOException occured!");
        }
        catch(ParseException e){
            System.out.println("SMSManager Load() ParseException occured!");
        }
    }
    public ArrayList<Integer> SearchByPhoneNumber(String str){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for(int i = 0; i < SMSCollection.size(); i++){
            if(SMSCollection.get(i).getPhoneNumber().contains(str)){
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
            if(SMSCollection.get(i).getPhoneNumber().contains(str)){
                newIndexList.add(i);
            }
        }
        return newIndexList;
    }
    public ArrayList<Integer> SearchByPhoneNumberExact(String str){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for(int i = 0; i < SMSCollection.size(); i++){
            if(SMSCollection.get(i).getPhoneNumber().equals(str)){
                indexList.add(i);
            }
        }
        return indexList;
    }
    public ArrayList<Integer> SearchByMessage(String str){
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for(int i = 0; i < SMSCollection.size(); i++){
            if(SMSCollection.get(i).getMessage().contains(str)){
                indexList.add(i);
            }
        }
        return indexList;
    }
    public ArrayList<Integer> SearchByMessage(String str, ArrayList<Integer> indexList){
        ArrayList<Integer> newIndexList = new ArrayList<Integer>();
        int i;
        for(Iterator<Integer> it = indexList.iterator(); it.hasNext(); ){
            i = it.next();
            if(SMSCollection.get(i).getMessage().contains(str)){
                newIndexList.add(i);
            }
        }
        return newIndexList;
    }
    public void addSMS(SMS sms) {
        SMSCollection.add(sms);
    }
    public void addSMS(String phoneNumber, Boolean sent, String message, Date date){
        addSMS(new SMS(phoneNumber, sent, message, date));
    }
    public void deleteSMS(int index){
        SMSCollection.remove(index);
    }

    // Member variable
    private ArrayList<SMS> SMSCollection = new ArrayList<SMS>();
}
