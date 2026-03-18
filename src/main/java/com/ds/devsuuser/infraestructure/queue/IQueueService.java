package com.ds.devsuuser.infraestructure.queue;

public interface IQueueService {

    void publish(Object data, QueueClient client);

}
