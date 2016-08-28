package com.karienomen.translator.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by karienomen on 23.08.16.
 */
public class CommonRecord {

    @Id
    private long id;
    private String gender;
    private String first_name;
    private String last_name;
    private String email;
    private String ip_address;
    private String bitcoin;

    public CommonRecord() {}

    public CommonRecord(long id, String gender, String first_name, String last_name, String email, String ip_address, String bitcoin) {
        this.id = id;
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.ip_address = ip_address;
        this.bitcoin = bitcoin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getBitcoin() {
        return bitcoin;
    }

    public void setBitcoin(String bitcoin) {
        this.bitcoin = bitcoin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonRecord commonRecord = (CommonRecord) o;

        return id == commonRecord.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "CommonRecord{" +
                "id=" + id +
                ", gender='" + gender + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", bitcoin='" + bitcoin + '\'' +
                '}';
    }
}
