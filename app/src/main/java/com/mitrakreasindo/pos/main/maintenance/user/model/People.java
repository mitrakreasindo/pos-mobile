package com.mitrakreasindo.pos.main.maintenance.user.model;

import com.mitrakreasindo.pos.model.Roles;

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
  private Roles role;

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

  public Roles getRole()
  {
    return role;
  }

  public void setRole(Roles role)
  {
    this.role = role;
  }



}
