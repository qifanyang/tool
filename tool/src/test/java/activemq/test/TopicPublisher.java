/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

//topic是发布订阅模型,listener需要先连接到服务器订阅后,publisher发布消息listener才可以收到消息
class TopicPublisher {

    public static void main(String []args) throws JMSException {

        String user = env("ACTIVEMQ_USER", "admin");
        String password = env("ACTIVEMQ_PASSWORD", "password");
        String host = env("ACTIVEMQ_HOST", "localhost");
        int port = Integer.parseInt(env("ACTIVEMQ_PORT", "61616"));
        String destination = arg(args, 0, "event");//

        int messages = 1000;
        int size = 256;

        String DATA = "abcdefghijklmnopqrstuvwxyz";
        String body = "";
        for( int i=0; i < size; i ++) {
            body += DATA.charAt(i%DATA.length());
        }

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);
        factory.setUseAsyncSend(true);
        Connection connection = factory.createConnection(user, password);
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      //获取session，destination是一个服务器的queue      destination = session.createQueue(destination); 
        Destination dest = new ActiveMQTopic(destination);
        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);//使用持久化消息两打的话持久化很慢的
//        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//不使用持久化发送256byte的消息每100个用时几毫秒,都是本地测试.无网络延时.差别全部来自数据存储
        //测试一:DeliveryMode.NON_PERSISTENT
        //启动服务器>publisher发布消息>listener注册>listener无法收到消息,  必须要先订阅才可以接受到消息
        //启动服务器>listener注册>publisher发布消息>listener收到消息
        //启动服务器>listener注册>关闭listener>publisher发布消息>打开listener收到消息
        
      //测试一:DeliveryMode.PERSISTENT
       //启动服务器>listener注册>关闭listener>publisher发布消息>关闭服务器>启动服务器>listener注册收到消息  
        //持久化消息速度太慢,发送被阻塞住,平均发送256byte字节的消息需要27ms
//        Sent 100 messages, cost time 2709ms
//        Sent 100 messages, cost time 2882ms
//        Sent 100 messages, cost time 2623ms
//        Sent 100 messages, cost time 2600ms
//        Sent 100 messages, cost time 2403ms
//        Sent 100 messages, cost time 2923ms
//        Sent 100 messages, cost time 2816ms
//        Sent 100 messages, cost time 3560ms
//        Sent 100 messages, cost time 3014ms
//        Sent 100 messages, cost time 3124ms

        long start = System.currentTimeMillis();
        for( int i=1; i <= messages; i ++) {
            TextMessage msg = session.createTextMessage(body);
            msg.setIntProperty("id", i);
            producer.send(msg);
            if( (i % 100) == 0) {
                System.out.println(String.format("Sent %d messages, cost time %dms", 100, System.currentTimeMillis() - start));
                start = System.currentTimeMillis();
            }
        }

        producer.send(session.createTextMessage("SHUTDOWN"));
        connection.close();

    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if( rc== null )
            return defaultValue;
        return rc;
    }

    private static String arg(String []args, int index, String defaultValue) {
        if( index < args.length )
            return args[index];
        else
            return defaultValue;
    }

}