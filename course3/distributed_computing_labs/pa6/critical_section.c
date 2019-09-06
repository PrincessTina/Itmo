//

#include "main.h"

//
// Created by princess on 02.09.2019.
//

/**
 * Проверяет наличие всех вилок, которые нужны процессу, чтобы войти в критическую секцию
 * @param currentProcess
 * @return
 */
int gotAllForks(Process *currentProcess) {
    for (int i = 1; i < currentProcess->processes_amount; i++) {
        if (currentProcess->id == i || currentProcess->finishedProcesses[i] == 1) {
            continue;
        }

        if (currentProcess->forks[i].presence == 0 || currentProcess->forks[i].dirty == 1) {
            return FALSE;
        }
    }

    return TRUE;
}

/**
 * Отсылает сообщение CS_REPLY процессу Destination
 * @param self
 * @param dst
 * @return
 */
int reply_cs(const void *self, local_id dst) {
    Process *currentProcess = (Process*) self;
    Message message;

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = CS_REPLY;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_payload_len = 0;

    send(currentProcess, dst, &message);

    return 0;
}

/**
 * Посылает запрос на вилку
 * @param currentProcess
 * @param src
 */
void sendForkRequest(Process *currentProcess, local_id src) {
    Message message;

    // Посылает сообщение CS_REQUEST
    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = CS_REQUEST;
    message.s_header.s_payload_len = 0;
    message.s_header.s_local_time = inc_lamport_time();

    send(currentProcess, src, &message);
}

/**
 * Ожидает своей очереди и обрабатывает запросы от других процессов
 * @param currentProcess
 */
void waitQueue(Process *currentProcess) {
    Message message;

    while (!gotAllForks(currentProcess)) {
        local_id src = receive_any(currentProcess, &message);
        if (src == ERROR_CODE) {
            continue;
        }

        receive_lamport_time(message);

        switch (message.s_header.s_type) {
            case CS_REQUEST:
                currentProcess->forks[src].token = 1;

                if (currentProcess->forks[src].presence == 1 && currentProcess->forks[src].dirty == 1) {
                    currentProcess->forks[src].presence = 0;
                    currentProcess->forks[src].dirty = 0;
                    currentProcess->forks[src].token = 0;

                    reply_cs(currentProcess, src);
                    sendForkRequest(currentProcess, src);
                }

                break;

            case CS_REPLY:
                currentProcess->forks[src].presence = 1;
                break;

            case DONE:
                currentProcess->finishedProcesses[src] = 1;
                break;

            default:
                printf("Got strange message type in waitQueue: %d\n", message.s_header.s_type);
                fflush(stdout);
                break;
        }
    }
}

/**
 * Отсылает сообщение типа CS_REQUEST всем процессам, вилки от которых нужны, чтобы попасть в критическую секцию
 * @param self
 * @return
 */
int request_cs(const void *self) {
    Process *currentProcess = (Process*) self;

    for (int i = 1; i < currentProcess->processes_amount; i++) {
        if (i == currentProcess->id || currentProcess->finishedProcesses[i] == 1) {
            continue;
        }

        if (currentProcess->forks[i].presence == 0 && currentProcess->forks[i].token == 1) {
            currentProcess->forks[i].token = 0;
            sendForkRequest(currentProcess, i);
        }
    }

    // Ждет подтверждения от всех и наступления своей очереди
    waitQueue(currentProcess);

    return 0;
}

/**
 * Отсылает сообщение типа CS_RELEASE всем процессам, которые запросили CS_REQUEST, чтобы освободить критическую секцию
 * Если не было запроса CS_REQUEST от процесса, делает вилку "грязной"
 * @param self
 * @return
 */
int release_cs(const void *self) {
    Process *currentProcess = (Process*) self;

    for (int i = 1; i < currentProcess->processes_amount; i++) {
        if (i == currentProcess->id || currentProcess->finishedProcesses[i] == 1) {
            continue;
        }

        if (currentProcess->forks[i].token == 1) {
            currentProcess->forks[i].presence = 0;
            currentProcess->forks[i].dirty = 0;

            reply_cs(currentProcess, i);
        } else {
            currentProcess->forks[i].dirty = 1;
        }
    }

    return 0;
}
