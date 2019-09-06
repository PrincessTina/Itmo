//
// Created by princess on 27.08.2019.
//

#include "main.h"

int send(void *self, local_id dst, const Message *msg) {
    Process *currentProcess = (Process*) self;

    write(currentProcess->pipes[currentProcess->id][dst].write, msg, MAX_MESSAGE_LEN);

    return 0;
}

int send_multicast(void *self, const Message *msg) {
    Process *currentProcess = (Process*) self;

    for (int i = 0; i < currentProcess->processes_amount; i++) {
        if (i == currentProcess->id) {
            continue;
        }

        send(currentProcess, i, msg);
    }

    return 0;
}

int receive(void *self, local_id from, Message *msg) {
    Process *currentProcess = (Process*) self;

    read(currentProcess->pipes[from][currentProcess->id].read, msg, MAX_MESSAGE_LEN);

    return 0;
}
