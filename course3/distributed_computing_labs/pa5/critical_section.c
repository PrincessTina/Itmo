//
// Created by princess on 02.09.2019.
//

#include "main.h"

/**
 * Проверяет наличие сообщений типа CS_REPLY от всех процессов
 * @param confirmations
 * @param confirmationsLength
 * @return
 */
int gotAllConfirmations(Process *currentProcess, const int *confirmations, int confirmationsLength) {
    for (int i = 1; i < confirmationsLength; i++) {
        if (currentProcess->id == i) {
            continue;
        }

        if (confirmations[i] == 0) {
            return FALSE;
        }
    }

    return TRUE;
}

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
 * Сравнивает приоритеты двух процессов
 * Возвращает TRUE/FALSE, если первый процесс более/менее приоритетный
 * @param currentProcess
 * @param otherProcessTimeMark
 * @param otherProcessId
 * @return
 */
int comparePriorities(int currentProcessTimeMark, int currentProcessId, int otherProcessTimeMark, int otherProcessId) {
    if (currentProcessTimeMark < otherProcessTimeMark) {
        return TRUE;
    }

    if (currentProcessTimeMark > otherProcessTimeMark) {
        return FALSE;
    }

    return (currentProcessId < otherProcessId) ? TRUE : FALSE;
}

/**
 * Ожидает своей очереди и обрабатывает запросы от других процессов
 * @param currentProcess
 */
void waitQueue(Process *currentProcess, int processTime) {
    Message message;
    int confirmationsLength = currentProcess->processes_amount;
    int *confirmations = calloc(sizeof(int), confirmationsLength);

    while (!gotAllConfirmations(currentProcess, confirmations, confirmationsLength)) {
        local_id src = receive_any(currentProcess, &message);
        if (src == ERROR_CODE) {
            continue;
        }

        receive_lamport_time(message);

        switch (message.s_header.s_type) {
            case CS_REQUEST:
                if (comparePriorities(processTime, currentProcess->id, message.s_header.s_local_time, src)) {
                    currentProcess->demandingAckProcesses[src] = 1;
                } else {
                    reply_cs(currentProcess, src);
                }

                break;

            case CS_REPLY:
                confirmations[src] = 1;
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

    free(confirmations);
}

/**
 * Отсылает сообщение типа CS_REQUEST всем процессам, чтобы попасть в очередь в критическую секцию
 * @param self
 * @return
 */
int request_cs(const void *self) {
    Process *currentProcess = (Process*) self;
    Message message;

    // Посылает сообщение CS_REQUEST
    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = CS_REQUEST;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_payload_len = 0;

    send_multicast(currentProcess, &message);

    // Ждет подтверждения от всех и наступления своей очереди
    waitQueue(currentProcess, message.s_header.s_local_time);

    return 0;
}

/**
 * Отсылает сообщение типа CS_RELEASE всем процессам, которые запросили CS_REQUEST, чтобы освободить критическую секцию
 * @param self
 * @return
 */
int release_cs(const void *self) {
    Process *currentProcess = (Process*) self;
    Message message;

    // Посылает сообщение CS_RELEASE
    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = CS_REPLY;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_payload_len = 0;

    for (int i = 1; i < currentProcess->processes_amount; i++) {
        if (currentProcess->demandingAckProcesses[i] == 1) {
            send(currentProcess, i, &message);
            currentProcess->demandingAckProcesses[i] = 0;
        }
    }

    return 0;
}
