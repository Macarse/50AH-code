/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack017.model;

import java.util.HashMap;

public class Account {

  private long accountId;
  private String name;
  private String email;
  private String password;
  private String gender;
  private String city;
  private String state;
  private String country;
  private String postalCode;
  private boolean isGuest = true;
  private HashMap<String, String> errors;

  public Account() {
    errors = new HashMap<String, String>();
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long accountId) {
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public boolean isGuest() {
    return isGuest;
  }

  public void setGuest(boolean isGuest) {
    this.isGuest = isGuest;
  }

  public HashMap<String, String> getErrors() {
    return errors;
  }

  public void setErrors(HashMap<String, String> errors) {
    this.errors = errors;
  }
}
