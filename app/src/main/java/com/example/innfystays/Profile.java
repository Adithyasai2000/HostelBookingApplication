package com.example.innfystays;

public class Profile {
    String Fullname,Mailid,PhoneNo,CollegeName,Address,Uid;
    Profile(String name,String mailid,String phoneno,String College,String address,String uid){
        Fullname=name;
        Mailid=mailid;
        PhoneNo=phoneno;
        CollegeName=College;
        Address=address;
        Uid=uid;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getMailid() {
        return Mailid;
    }

    public void setMailid(String mailid) {
        Mailid = mailid;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getCollegeName() {
        return CollegeName;
    }

    public void setCollegeName(String collegeName) {
        CollegeName = collegeName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
