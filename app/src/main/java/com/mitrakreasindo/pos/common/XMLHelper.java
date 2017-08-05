package com.mitrakreasindo.pos.common;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hendric on 2017-07-04.
 */

public class XMLHelper
{
  public static List<String> XMLReader (Context context, String tag, byte[] permission)
  {
    List<String> list = new ArrayList<>();
    InputStream is = null;

    try
    {
//      InputStream is = context.getAssets().open("menu-access.xml");
//      byte[] data = Base64.decode(permission, Base64.DEFAULT);
//      String text = new String(data, StandardCharsets.UTF_8);
//      Log.d("XML Permissions: ", text);
//      InputStream is = new ByteArrayInputStream(data);

      if (permission != null)
      {
        is = new ByteArrayInputStream(permission);
        Log.d("check xml", "xml exist");
      }
      else
      {
        Log.d("check xml", "xml not exist");
        is = context.getAssets().open("menu-access.xml");
      }


      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(is);

      Element element = doc.getDocumentElement();
      element.normalize();

      NodeList nList = doc.getElementsByTagName(tag);

      Log.d("permission","tag name "+tag);
      if (nList.getLength() == 0)
      {
        Log.d("permission","nlist null ");
        return list;
      }
      else
      {
        Log.d("permission","nlist not null ");
      }

      Node nodes = nList.item(0);
      if (nodes.getNodeType() == Node.ELEMENT_NODE)
      {
        Element elementi = (Element) nodes;
        NodeList nodeList = elementi.getElementsByTagName("inactive");
        for (int i = 0; i < nodeList.getLength(); i++)
        {
          list.add(nodeList.item(i).getTextContent());
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return list;
  }
}
