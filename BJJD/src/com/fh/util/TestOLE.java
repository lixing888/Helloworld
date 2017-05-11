package com.fh.util;

import com.jacob.com.*;
import com.jacob.activeX.*;
 
public class TestOLE
{   
    public static void main(String[] args)
    {
      ActiveXComponent sap = new ActiveXComponent("Sapi.SpVocie");
      {
          Dispatch sapo = sap.getObject();
          boolean flag = false;
          try
          {
              //调整音量和读的速度
              sap.setProperty("Volume",new Variant(100));
              sap.setProperty("Rate",new Variant(0));
              //这一句是读出来abc这三个字母的   
              Dispatch.call(sapo,"Speak",new Variant("abc"));
              flag = true;
          }
          catch(Exception e)
          {
                flag = false;
                e.printStackTrace();
          }
          finally
          {
                if(flag)
                {
                    System.out.println("Over!");
                }
                else
                    System.out.println("Application end with exception!");
          }
      }
    }
 }   
  
