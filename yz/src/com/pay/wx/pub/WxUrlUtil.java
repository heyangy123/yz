package com.pay.wx.pub;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WxUrlUtil
{
  public static String buildByMap(Map<String, String> params)
  {
    StringBuffer sb = new StringBuffer();
    Set es = params.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String k = (String)entry.getKey();
      String v = (String)entry.getValue();
      if ((v != null) && (!"".equals(v))) {
        sb.append(k + "=" + v + "&");
      }
    }

    return sb.toString().substring(0, sb.toString().lastIndexOf("&"));
  }
}