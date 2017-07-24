package com.mitrakreasindo.pos.common;

import android.content.Context;

import com.mitrakreasindo.pos.common.TableHelper.TableRoleHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miftakhul on 7/24/17.
 */

public class PermissionUtil {

  public static List<String> getInactive(Context context, String tag)
  {
    List<String> list = new ArrayList<>();
    TableRoleHelper tableRoleHelper = new TableRoleHelper(context);
    byte[] permission = tableRoleHelper.getPermission(IDs.getLoginUser());
    if (permission != null)
    {
      list = XMLHelper.XMLReader(context, tag, permission);
    }
    return list;
  }

}
