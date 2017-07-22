package com.mitrakreasindo.pos.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miftakhul on 7/18/17.
 */

public class Permission
{
  @ElementList(entry = "inactive", name = "navigation", required = false)
  public List<String> navigation = new ArrayList<>();

  @ElementList(entry = "inactive", name = "main", required = false)
  public List<String> main = new ArrayList<>();

  @ElementList(entry = "inactive", name = "maintenance", required = false)
  public List<String> maintenace = new ArrayList<>();

  @ElementList(entry = "inactive", name = "stock", required = false)
  public List<String> stock = new ArrayList<>();

}