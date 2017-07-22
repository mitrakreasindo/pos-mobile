package com.mitrakreasindo.pos.model;

import java.util.Date;

/**
 * Created by lisa on 24/05/17.
 */

public class People
{
  private String id;
  private String name;
  private transient String apppassword;
  private String card;
  private boolean visible;
  private byte[] image;
  private String siteguid;
  private Boolean sflag;
  private String email;
  private Role role;
  private String fullname;
  private String personalIdType;
  private String personalId;
  private String npwpPribadi;
  private String phoneNumber;
  private String gender;
  private String birthdate;


  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getApppassword()
  {
    return apppassword;
  }

  public void setApppassword(String apppassword)
  {
    this.apppassword = apppassword;
  }

  public String getCard()
  {
    return card;
  }

  public void setCard(String card)
  {
    this.card = card;
  }

  public boolean isVisible()
  {
    return visible;
  }

  public void setVisible(boolean visible)
  {
    this.visible = visible;
  }

  public byte[] getImage()
  {
    return image;
  }

  public void setImage(byte[] image)
  {
    this.image = image;
  }

  public String getSiteguid()
  {
    return siteguid;
  }

  public void setSiteguid(String siteguid)
  {
    this.siteguid = siteguid;
  }

  public Boolean getSflag()
  {
    return sflag;
  }

  public void setSflag(Boolean sflag)
  {
    this.sflag = sflag;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public Role getRole()
  {
    return role;
  }

  public void setRole(Role role)
  {
    this.role = role;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getPersonalIdType() {
    return personalIdType;
  }

  public void setPersonalIdType(String personalIdType) {
    this.personalIdType = personalIdType;
  }

  public String getPersonalId() {
    return personalId;
  }

  public void setPersonalId(String personalId) {
    this.personalId = personalId;
  }

  public String getNpwpPribadi() {
    return npwpPribadi;
  }

  public void setNpwpPribadi(String npwpPribadi) {
    this.npwpPribadi = npwpPribadi;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }
}
