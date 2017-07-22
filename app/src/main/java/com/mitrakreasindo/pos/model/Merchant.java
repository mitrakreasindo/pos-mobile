package com.mitrakreasindo.pos.model;

/**
 * Created by lisa on 22/06/17.
 */

public class Merchant
{
  private String code;
  private String name;
  private String email;
  private String phone;
  private String address;
  private String npwpperusahaan;
  private boolean sflag;
  private MerchantCategories category;

  public String getCode()
  {
    return code;
  }

  public void setCode(String code)
  {
    this.code = code;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getNpwpperusahaan()
  {
    return npwpperusahaan;
  }

  public void setNpwpperusahaan(String npwpperusahaan)
  {
    this.npwpperusahaan = npwpperusahaan;
  }

  public boolean isSflag()
  {
    return sflag;
  }

  public void setSflag(boolean sflag)
  {
    this.sflag = sflag;
  }

  public MerchantCategories getCategory()
  {
    return category;
  }

  public void setCategory(MerchantCategories category)
  {
    this.category = category;
  }
}
