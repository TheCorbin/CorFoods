/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.corfoods.listener;

/**
 *
 * @author ryancorbin
 */


import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;

public class MySessionCounter implements HttpSessionListener {

  private static int activeSessions = 0;

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    activeSessions++;
    System.out.println("sessionCreated - add one session into counter" + activeSessions);
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    if(activeSessions > 0)
      activeSessions--;
  }

  public static int getActiveSessions() {
    return activeSessions;
  }

}