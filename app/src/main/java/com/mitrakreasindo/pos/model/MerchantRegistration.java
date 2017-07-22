package com.mitrakreasindo.pos.model;

/**
 * Created by hendric on 2017-07-22.
 */

public class MerchantRegistration
{
  private Merchant merchant;
  private People people;

  public Merchant getMerchant()
  {
    return merchant;
  }

  public void setMerchant(Merchant merchant)
  {
    this.merchant = merchant;
  }

  public People getPeople()
  {
    return people;
  }

  public void setPeople(People people)
  {
    this.people = people;
  }
}
