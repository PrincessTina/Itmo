//
// Created by princess on 31.08.2019.
//

#include "main.h"

timestamp_t lamport_time = 0;

void set_lamport_time(const timestamp_t value) {
    lamport_time = value;
}

timestamp_t inc_lamport_time() {
    lamport_time++;

    return lamport_time;
}

timestamp_t receive_lamport_time(Message message) {
    if (message.s_header.s_local_time > lamport_time) {
        set_lamport_time(message.s_header.s_local_time);
    }

    return inc_lamport_time();
}
