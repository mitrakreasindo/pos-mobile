package com.mitrakreasindo.pos.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 19/06/17.
 */

public class Queue
{

  private String name;
  private String queueNumber;
  private String value;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getQueueNumber()
  {
    return queueNumber;
  }

  public void setQueueNumber(String queueNumber)
  {
    this.queueNumber = queueNumber;
  }

  public String getValue()
  {
    return value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public static List<Queue> queueData(){

    List<Queue> queues = new ArrayList<>();

    Queue queue = new Queue();
    queue.setName("product 1 product 1 product 1");
    queue.setQueueNumber("12345");
    queue.setValue("2000");
    queues.add(queue);

    Queue queue2 = new Queue();
    queue2.setName("product 1");
    queue2.setQueueNumber("12345");
    queue2.setValue("2000");
    queues.add(queue2);

    Queue queue3 = new Queue();
    queue3.setName("product 1");
    queue3.setQueueNumber("12345");
    queue3.setValue("2000");
    queues.add(queue3);

    Queue queue4 = new Queue();
    queue4.setName("product 1");
    queue4.setQueueNumber("12345");
    queue4.setValue("2000");
    queues.add(queue4);

    Queue queue5 = new Queue();
    queue5.setName("product 1");
    queue5.setQueueNumber("12345");
    queue5.setValue("2000");
    queues.add(queue5);

    Queue queue6 = new Queue();
    queue6.setName("product 1");
    queue6.setQueueNumber("12345");
    queue6.setValue("2000");
    queues.add(queue6);

    return queues;
  }

}
