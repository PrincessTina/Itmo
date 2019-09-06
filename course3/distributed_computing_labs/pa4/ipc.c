//
// Created by princess on 27.08.2019.
//

#include "main.h"

int send(void *self, local_id dst, const Message *msg) {
    Process *currentProcess = (Process*) self;

    write(currentProcess->pipes[currentProcess->id][dst].write, msg, sizeof(MessageHeader) + msg->s_header.s_payload_len);

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

    int bytesRead = read(currentProcess->pipes[from][currentProcess->id].read, &msg->s_header, sizeof(MessageHeader));

    if (bytesRead == ERROR_CODE || bytesRead == 0) {
        return ERROR_CODE;
    }

    if (msg->s_header.s_payload_len) {
        read(currentProcess->pipes[from][currentProcess->id].read, msg->s_payload, msg->s_header.s_payload_len);
    }

    return 0;
}

int receive_any(void *self, Message * msg) {
    Process *currentProcess = (Process*) self;

    for (int i = 0; i < currentProcess->processes_amount; i++) {
        if (i == currentProcess->id) {
            continue;
        }

        int bytesRead = receive(currentProcess, i, msg);

        if (bytesRead == ERROR_CODE) {
            continue;
        }

        return i;
    }

    return ERROR_CODE;
}
