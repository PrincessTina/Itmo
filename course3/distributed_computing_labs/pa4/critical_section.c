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
int gotAllConfirmations(const int *confirmations, int confirmationsLength) {
    for (int i = 0; i < confirmationsLength; i++) {
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
 * Ожидает своей очереди и обрабатывает запросы от других процессов
 * @param currentProcess
 */
void waitQueue(Process *currentProcess) {
    Message message;
    int currentConfirmationId = 0;
    int confirmationsLength = currentProcess->processes_amount - 2;
    int *confirmations = calloc(sizeof(int), confirmationsLength);

    while (!gotAllConfirmations(confirmations, confirmationsLength) || currentProcess->cs_queue->priority.process_id != currentProcess->id) {
        local_id src = receive_any(currentProcess, &message);
        if (src == ERROR_CODE) {
            continue;
        }

        switch (message.s_header.s_type) {
            case CS_REQUEST:
                push(&currentProcess->cs_queue, 0, message.s_header.s_local_time, src);
                reply_cs(currentProcess, src);
                break;

            case CS_REPLY:
                confirmations[currentConfirmationId] = 1;
                currentConfirmationId += 1;
                break;

            case CS_RELEASE:
                pop(&currentProcess->cs_queue);
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

    // Добавляет себя в очередь
    push(&currentProcess->cs_queue, 0, get_lamport_time(), currentProcess->id);

    // Ждет подтверждения от всех и наступления своей очереди
    waitQueue(currentProcess);

    return 0;
}

/**
 * Отсылает сообщение типа CS_RELEASE всем процессам, чтобы освободить критическую секцию
 * @param self
 * @return
 */
int release_cs(const void *self) {
    Process *currentProcess = (Process*) self;
    Message message;

    // Выходит из очереди
    pop(&currentProcess->cs_queue);

    // Посылает сообщение CS_RELEASE
    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = CS_RELEASE;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_payload_len = 0;

    send_multicast(currentProcess, &message);

    return 0;
}
