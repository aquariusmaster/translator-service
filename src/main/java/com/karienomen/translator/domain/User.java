package com.karienomen.translator.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by karienomen on 23.08.16.
 */
@Document(indexName = "customers", type = "user")
public class User {

        @Id
        private long id;
        @Field(type = FieldType.String)
        private String gender;
        @Field(type = FieldType.String)
        private String first_name;
        @Field(type = FieldType.String)
        private String last_name;
        @Field(type = FieldType.String)
        private String email;
        @Field(type = FieldType.String)
        private String ip_address;

        public User(){}

        public User(long id, String gender, String first_name, String last_name, String email, String ip_address) {
                this.id = id;
                this.gender = gender;
                this.first_name = first_name;
                this.last_name = last_name;
                this.email = email;
                this.ip_address = ip_address;
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

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                User user = (User) o;

                return id == user.id;

        }

        @Override
        public int hashCode() {
                return (int) (id ^ (id >>> 32));
        }

        @Override
        public String toString() {
                return "User{" +
                        "id=" + id +
                        ", gender='" + gender + '\'' +
                        ", first_name='" + first_name + '\'' +
                        ", last_name='" + last_name + '\'' +
                        ", email='" + email + '\'' +
                        ", ip_address='" + ip_address + '\'' +
                        '}';
        }
}
