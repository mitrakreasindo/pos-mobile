package com.mitrakreasindo.pos.model;

/**
 * Created by hendric on 2017-05-26.
 */

public class Login
{
  private String kodeMerchant;
  private String username;
  private String password;

  public Login(String kodeMerchant, String username, String password) {
    this.kodeMerchant = kodeMerchant;
    this.username = username;
    this.password = password;
  }

  public String getKodeMerchant()
  {
    return kodeMerchant;
  }

  public void setKodeMerchant(String kodeMerchant)
  {
    this.kodeMerchant = kodeMerchant;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }
}
