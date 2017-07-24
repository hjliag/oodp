import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

/**
 * Created by student on 2017. 4. 8..
 */
public class AddressManager {
    // getter
    public ArrayList<Address> getAddressCollection() {
        return addressCollection;
    }
    public Address get(int index){
        return addressCollection.get(index);
    }

    // member functions
    public void Save(){
        try {
            Gson gson = new GsonBuilder().create();
            Writer writer = new FileWriter("address.json");
            gson.toJson(addressCollection, writer);
            writer.close();
        }
        catch(IOException e){
            System.out.println("AddressManager Save() IOException occured!");
        }
    }
    public void Load(){
        addressCollection.clear();
        try {
            JsonReader reader = new JsonReader(new FileReader("address.json"));
            reader.beginArray();
            while(reader.hasNext()){
                reader.beginObject();

                reader.nextName(); // "name"
                String name = reader.nextString();
                reader.nextName(); // "phoneNumber"
                String phoneNumber = reader.nextString();
                reader.nextName(); // "email"
                String email = reader.nextString();
                reader.nextName(); // "address"
                String address = reader.nextString();
                reader.nextName(); // "memo"
                String memo = reader.nextString();

                reader.endObject();

                addAddress(name, phoneNumber, email, address, memo);
            }
            reader.endArray();
            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("AddressManager Load() FileNotFoundException occured!");
        }
        catch(IOException e){
            System.out.println("AddressManager Load() IOException occured!");
        }
    }
    public void Sort(){
        Collections.sort(addressCollection, new Comparator<Address>() {
            public int compare(Address o1, Address o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
    public ArrayList<Integer> SearchByName(String str){
        ArrayList<Integer> indexList = new ArrayList();
        for(int i = 0; i < addressCollection.size(); i++) {
            if(addressCollection.get(i).getName().contains(str)){
                indexList.add(i);
            }
        }
        return indexList;
    }
    public ArrayList<Integer> SearchByName(String str, ArrayList<Integer> indexList){
        ArrayList newIndexList = new ArrayList();
        int i;
        for(Iterator<Integer> it = indexList.iterator(); it.hasNext(); ){
            i = it.next();
            if(addressCollection.get(i).getName().contains(str)){
                newIndexList.add(i);
            }
        }
        return newIndexList;
    }
    public ArrayList<Integer> SearchByPhoneNumber(String str){
        ArrayList<Integer> indexList = new ArrayList();
        for(int i = 0; i < addressCollection.size(); i++) {
            if(addressCollection.get(i).getPhoneNumber().contains(str)){
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
            if(addressCollection.get(i).getPhoneNumber().contains(str)){
                newIndexList.add(i);
            }
        }
        return newIndexList;
    }
    public void addAddress(Address address){
        addressCollection.add(address);
    }
    public void addAddress(String name, String phoneNumber, String email, String address, String memo){
        addAddress(new Address(name, phoneNumber, email, address, memo));
    }
    public void deleteAddress(int index){
        addressCollection.remove(index);
    }


    // member variable
    private ArrayList<Address> addressCollection = new ArrayList<Address>();
}