package ht;

public interface MessageProcessor<T>{

    /**
     * 收到消息时, 将会被调用
     * @param buffer
     * @param msgId
     */
    void onMessage(T msg);
}
