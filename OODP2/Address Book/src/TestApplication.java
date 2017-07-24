import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by student on 2017. 4. 8..
 */
public class TestApplication {
    public static void main(String[] args){
        TestApplication ta = new TestApplication();

        while(true){
            System.out.println("***************");
            System.out.println("1. Address");
            System.out.println("2. SMS");
            System.out.println("3. Call");
            System.out.println("4. Quit");
            System.out.println("***************");

            // get input
            int input;
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter a number : ");
            input = reader.nextInt();

            switch(input){
                case 1:
                    ta.AddressManagement();
                    break;
                case 2:
                    ta.SMSManagement();
                    break;
                case 3:
                    ta.CallManagement();
                    break;
                case 4:
                    System.out.println("Quit");
                    break;
                default:
                    System.out.println("main switch default");
            }
            if(input == 4){
                break;
            }
        }
    }

    // constructor
    TestApplication(){
        am.Load();
        sm.Load();
        cm.Load();
    }

    // getters
    public AddressManager getAm() {
        return am;
    }
    public SMSManager getSm() {
        return sm;
    }
    public CallManager getCm() {
        return cm;
    }


    // Address Manager
    public void AddressManagement(){
        while(true){
            System.out.println("***************");
            System.out.println("1. View Addresses");
            System.out.println("2. Add New Address");
            System.out.println("3. Delete Address");
            System.out.println("4. Quit");
            System.out.println("***************");

            int command;
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter a number : ");
            command = reader.nextInt();

            switch(command){
                case 1:
                    while(true) {
                        ArrayList<Integer> indexList1 = am.SearchByPhoneNumber("");
                        int size = indexList1.size();
                        AddressPrintAddressList(indexList1);
                        System.out.println(size + ". Quit");
                        System.out.print("Enter a number : ");

                        int input;
                        input = reader.nextInt();

                        if(input == size){
                            break;
                        }
                        else if(input > size || input < 0){
                            System.out.println("Out of bound!");
                        }
                        else {
                            AddressEdit(am.get(input));
                            am.Sort();
                            am.Save();
                        }
                    }
                    break;
                case 2:
                    AddressMake();
                    break;
                case 3:
                    AddressDelete();
                case 4:
                    break;
                default:
                    System.out.println("TestApplication AddressManagement default");
            }
            if(command == 4){
                break;
            }
        }
    }
    public void AddressMake(){
        Address newAddress = new Address();
        AddressEdit(newAddress);
        if(newAddress.getName().equals("") || newAddress.getPhoneNumber().equals("")){
            return;
        }
        else{
            am.addAddress(newAddress);
            am.Sort();
            am.Save();
        }
    }
    public void AddressDelete(){
        Scanner reader = new Scanner(System.in);
        while(true) {
            ArrayList<Integer> indexList1 = am.SearchByPhoneNumber("");
            int size = indexList1.size();
            AddressPrintAddressList(indexList1);
            System.out.println(size + ". Quit");
            System.out.print("Enter a number : ");

            int input;
            System.out.print("Enter a number : ");
            input = reader.nextInt();

            if (input == size) {
                break;
            } else if (input > size || input < 0) {
                System.out.println("Out of bound!");
            } else {
                am.deleteAddress(input);
            }
            am.Save();
        }
    }
    public void AddressEdit(Address address){
        Address tmp = address.clone();
        while(true) {
            System.out.println("**************");
            System.out.println("1. Name : " + tmp.getName());
            System.out.println("2. Phone # : " + tmp.getPhoneNumber());
            System.out.println("3. Email : " + tmp.getEmail());
            System.out.println("4. Address : " + tmp.getAddress());
            System.out.println("5. Memo : " + tmp.getMemo());
            System.out.println("**************");
            System.out.println("6. Save and Quit");
            System.out.println("7. Cancel");

            int command;
            Scanner reader = new Scanner(System.in);
            System.out.println();
            System.out.print("Enter a number : ");
            command = reader.nextInt();

            String input = "";
            BufferedReader buffer =new BufferedReader(new InputStreamReader(System.in));
            switch(command){
                case 1:
                    System.out.print("Name : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("AddressEdit IOException");
                    }
                    tmp.setName(input);
                    break;
                case 2:
                    System.out.print("Phone # : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("AddressEdit IOException");
                    }
                    tmp.setPhoneNumber(input);
                    break;
                case 3:
                    System.out.print("Email : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("AddressEdit IOException");
                    }
                    tmp.setEmail(input);
                    break;
                case 4:
                    System.out.print("Address : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("AddressEdit IOException");
                    }
                    tmp.setAddress(input);
                    break;
                case 5:
                    System.out.print("Memo : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("AddressEdit IOException");
                    }
                    tmp.setMemo(input);
                    break;
                case 6:
                    break;
                case 7:
                    break;
                default:
                    break;
            }
            if(command == 6){
                if(tmp.getName().equals("") || tmp.getPhoneNumber().equals("")){
                    System.out.println("Need to enter both \"Name\" and \"PhoneNumber\"");
                    continue;
                }
                address.setName(tmp.getName());
                address.setPhoneNumber(tmp.getPhoneNumber());
                address.setEmail(tmp.getEmail());
                address.setAddress(tmp.getAddress());
                address.setMemo(tmp.getMemo());
                break;
            }
            else if(command == 7){
                break;
            }
        }
    }
    public void AddressPrintAddressList(ArrayList<Integer> indexList){
        System.out.println("**************");
        for(int i = 0; i < indexList.size(); i++){
            System.out.print(i + ". ");
            AddressPrintAddress(indexList.get(i));
            System.out.println();
        }
        System.out.println("**************");
    }
    public void AddressPrintAddress(int index){
        Address tmp = am.get(index);
        System.out.print(tmp.getName());
    }
    public void AddressPrintAddressDetail(int index){
        Address tmp = am.get(index);
        System.out.println("**************");
        System.out.println("Name : " + tmp.getName());
        System.out.println("Phone # : " + tmp.getPhoneNumber());
        System.out.println("Email : " + tmp.getEmail());
        System.out.println("Address : " + tmp.getAddress());
        System.out.println("Memo : " + tmp.getMemo());
        System.out.println("**************");
    }


    // SMS Manager
    public void SMSManagement(){
        while(true) {
            System.out.println("***************");
            System.out.println("1. Send SMS");
            System.out.println("2. View SMS history");
            System.out.println("3. Delete SMS");
            System.out.println("4. Quit");
            System.out.println("***************");

            int command;
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter a number : ");
            command = reader.nextInt();

            switch(command){
                case 1:
                    SMSSend();
                    break;
                case 2:
                    SMSViewHistory();
                    break;
                case 3:
                    SMSDelete();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("SMSManagement default");
                    break;
            }
            if(command == 4){
                break;
            }
        }
    }
    public void SMSSend(){
        SMS tmp = new SMS();
        while(true) {
            System.out.println("**************");
            System.out.println("1. To : " + tmp.getPhoneNumber());
            System.out.println("2. Message : " + tmp.getMessage());
            System.out.println("**************");
            System.out.println("3. Send");
            System.out.println("4. Cancel");

            int command;
            Scanner reader = new Scanner(System.in);
            System.out.println();
            System.out.print("Enter a number : ");
            command = reader.nextInt();

            String input = "";
            BufferedReader buffer =new BufferedReader(new InputStreamReader(System.in));
            switch(command){
                case 1:
                    System.out.print("To : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("SMSSend IOException");
                    }
                    tmp.setPhoneNumber(input);
                    break;
                case 2:
                    System.out.print("Message : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("SMSSend IOException");
                    }
                    tmp.setMessage(input);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
            if(command == 3){
                if(tmp.getMessage().equals("") || tmp.getPhoneNumber().equals("")){
                    System.out.println("Need to enter \"To\" and \"Message\" !!");
                }
                else{
                    sm.addSMS(tmp);
                    System.out.println("Sent Successful!");
                    break;
                }
            }
            else if(command == 4){
                break;
            }
        }
        sm.Save();
    }
    public void SMSDelete(){
        Scanner reader = new Scanner(System.in);
        while(true){
            ArrayList<Integer> indexList = sm.SearchByPhoneNumber("");
            SMSPrintSMSList(indexList);
            int size = indexList.size();

            System.out.println(size + ". Quit");
            System.out.print("Enter a number : ");

            int input;
            System.out.print("Enter a number : ");
            input = reader.nextInt();

            if (input == size) {
                break;
            } else if (input > size || input < 0) {
                System.out.println("Out of bound!");
            } else {
                sm.deleteSMS(input);
            }
            sm.Save();
        }
    }
    public void SMSViewHistory(){
        Scanner reader = new Scanner(System.in);
        while(true) {
            ArrayList<Integer> indexList = sm.SearchByPhoneNumber("");
            SMSPrintSMSList(indexList);
            int size = indexList.size();

            System.out.println(size + ". Quit");
            System.out.print("Enter a number : ");

            int input;
            System.out.print("Enter a number : ");
            input = reader.nextInt();

            if (input == size) {
                break;
            } else if (input > size || input < 0) {
                System.out.println("Out of bound!");
            } else {
                SMSPrintSMSDetail(input);
            }
        }
    }
    public void SMSPrintSMSList(ArrayList<Integer> indexList){
        System.out.println("**************");
        for(int i = 0; i < indexList.size(); i++){
            System.out.print(i + ". ");
            SMSPrintPhoneNumber(indexList.get(i));
            System.out.println();
        }
        System.out.println("**************");
    }
    public void SMSPrintPhoneNumber(int index){
        String phoneNumber = sm.get(index).getPhoneNumber();
        ArrayList<Integer> indexList = am.SearchByPhoneNumber(phoneNumber);
        if(!indexList.isEmpty()){
            String name = am.get(indexList.get(0)).getName();
            System.out.print(name);
        }
        else {
            System.out.print(phoneNumber);
        }
    }
    public void SMSPrintSMSDetail(int index) {
        SMS sms = sm.get(index);
        System.out.println("**************");
        if(sms.getSent()){
            System.out.print("To : ");
        }
        else{
            System.out.print("From : ");
        }
        SMSPrintPhoneNumber(index);
        System.out.println();
        System.out.print("Date : ");
        System.out.print(sms.getDate() + "\n");
        System.out.print("Message : ");
        System.out.print(sms.getMessage());
        System.out.println();
        System.out.println("**************");
    }


    // Call Manager
    public void CallManagement(){
        while(true){
            System.out.println("***************");
            System.out.println("1. Call");
            System.out.println("2. Call View History");
            System.out.println("3. Delete Call");
            System.out.println("4. Quit");
            System.out.println("***************");

            int command;
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter a number : ");
            command = reader.nextInt();

            switch(command){
                case 1:
                    CallCall();
                    break;
                case 2:
                    CallViewHistory();
                    break;
                case 3:
                    CallDelete();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("SMSManagement default");
                    break;
            }
            if(command == 4){
                break;
            }
        }
    }
    public void CallCall(){
        Call tmp = new Call();
        while(true) {
            System.out.println("**************");
            System.out.println("1. To : " + tmp.getPhoneNumber());
            System.out.println("**************");
            System.out.println("2. Call");
            System.out.println("3. Cancel");

            int command;
            Scanner reader = new Scanner(System.in);
            System.out.println();
            System.out.print("Enter a number : ");
            command = reader.nextInt();

            String input = "";
            BufferedReader buffer =new BufferedReader(new InputStreamReader(System.in));
            switch(command){
                case 1:
                    System.out.print("To : ");
                    try {
                        input = buffer.readLine();
                    } catch(IOException e){
                        System.out.println("CallCall IOException");
                    }
                    tmp.setPhoneNumber(input);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            if(command == 2){
                if(tmp.getPhoneNumber().equals("")){
                    System.out.println("Need to enter \"To\"!!");
                }
                else{
                    tmp.setStartTime(new Date());

                    while(true){
                        System.out.println("Calling ...");
                        System.out.println("1. To end");

                        int input1 = reader.nextInt();
                        if(input1 == 1){
                            break;
                        }
                    }
                    tmp.setEndTime(new Date());

                    cm.addCall(tmp);
                    break;
                }
            }
            else if(command == 3){
                break;
            }
        }
        cm.Save();

    }
    public void CallDelete(){
        Scanner reader = new Scanner(System.in);
        while(true){
            ArrayList<Integer> indexList = cm.SearchByPhoneNumber("");
            CallPrintCallList(indexList);
            int size = indexList.size();

            System.out.println(size + ". Quit");
            System.out.print("Enter a number : ");

            int input;
            System.out.print("Enter a number : ");
            input = reader.nextInt();

            if (input == size) {
                break;
            } else if (input > size || input < 0) {
                System.out.println("Out of bound!");
            } else {
                cm.deleteCall(input);
            }
            cm.Save();
        }
    }
    public void CallViewHistory(){
        Scanner reader = new Scanner(System.in);
        while(true) {
            ArrayList<Integer> indexList = cm.SearchByPhoneNumber("");
            CallPrintCallList(indexList);
            int size = indexList.size();

            System.out.println(size + ". Quit");
            System.out.print("Enter a number : ");

            int input;
            System.out.print("Enter a number : ");
            input = reader.nextInt();

            if (input == size) {
                break;
            } else if (input > size || input < 0) {
                System.out.println("Out of bound!");
            } else {
                CallPrintCallDetail(input);
            }
        }
    }
    public void CallPrintCallList(ArrayList<Integer> indexList){
        System.out.println("**************");
        for(int i = 0; i < indexList.size(); i++){
            System.out.print(i + ". ");
            CallPrintPhoneNumber(indexList.get(i));
            System.out.println();
        }
        System.out.println("**************");
    }
    public void CallPrintPhoneNumber(int index){
        String phoneNumber = cm.get(index).getPhoneNumber();
        ArrayList<Integer> indexList = am.SearchByPhoneNumber(phoneNumber);
        if(!indexList.isEmpty()){
            String name = am.get(indexList.get(0)).getName();
            System.out.print(name);
        }
        else {
            System.out.print(phoneNumber);
        }
    }
    public void CallPrintCallDetail(int index){
        Call call = cm.get(index);
        System.out.println("**************");
        if(call.getCalled()){
            System.out.print("To : ");
        }
        else {
            System.out.print("From : ");
        }
        CallPrintPhoneNumber(index);
        System.out.println();

        if(call.getMissed()){
            System.out.println("Missed");
        }
        else {
            System.out.println("Picked Up");
        }

        System.out.println("Start Time : " + call.getStartTime());
        System.out.println("End Time : " + call.getEndTime());
        System.out.println("**************");
    }

    // Member variables
    private AddressManager am = new AddressManager();
    private SMSManager sm = new SMSManager();
    private CallManager cm = new CallManager();
}
