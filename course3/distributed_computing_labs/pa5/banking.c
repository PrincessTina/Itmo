//
// Created by princess on 29.08.2019.
//

#include "main.h"

/**
 * Parent посылает сообщение TRANSFER к Source
 * @param currentProcess
 * @param src
 * @param dst
 * @param amount
 */
void sendFromParentToSource(Process *currentProcess, local_id src, local_id dst, balance_t amount) {
    unsigned int length;
    TransferOrder transferOrder;
    Message message;

    transferOrder.s_amount = amount;
    transferOrder.s_dst = dst;
    transferOrder.s_src = src;

    length = sizeof(TransferOrder);

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = TRANSFER;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_payload_len = length;
    memcpy(message.s_payload, &transferOrder, length);

    send(currentProcess, src, &message);
}

/**
 * Parent ожидает ACK от процесса Destination, которому был послан запрос на TRANSFER от Source
 * @param currentProcess
 * @param dstreceive_any
 */
void receiveAckFromDestination(Process *currentProcess, local_id dst) {
    Message message;

    while (1) {
        int bytesRead = receive(currentProcess, dst, &message);

        if (bytesRead != ERROR_CODE && message.s_header.s_type == ACK) {
            receive_lamport_time(message);
            break;
        }
    }
}

/**
 * Процесс Source посылает сообщение TRANSFER к Destination
 * @param currentProcess
 * @param src
 * @param dst
 * @param amount
 */
void sendFromSourceToDestination(Process *currentProcess, local_id src, local_id dst, balance_t amount) {
    unsigned int length;
    TransferOrder transferOrder;
    Message message;

    transferOrder.s_amount = amount;
    transferOrder.s_dst = dst;
    transferOrder.s_src = src;

    length = sizeof(TransferOrder);

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = TRANSFER;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_payload_len = length;
    memcpy(message.s_payload, &transferOrder, length);

    send(currentProcess, dst, &message);
}

/**
 * Процесс Destination посылает сообщение ACK к Parent или Source, что свидетельствует о завершении транзакции
 * @param currentProcess
 */
void sendAckFromDestination(Process *currentProcess, local_id dst) {
    Message message;

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_type = ACK;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_payload_len = 0;

    send(currentProcess, dst, &message);
}

/**
 * Перенаправляет пришедшие запросы
 * @param parent_data
 * @param src
 * @param dst
 * @param amount
 */
void transfer(void *parent_data, local_id src, local_id dst, balance_t amount) {
    Process *currentProcess = (Process *) parent_data;

    switch (currentProcess->id) {
        case PARENT_ID:
            sendFromParentToSource(currentProcess, src, dst, amount);
            receiveAckFromDestination(currentProcess, dst);
            break;
        default:
            if (currentProcess->id == src) {
                sendFromSourceToDestination(currentProcess, src, dst, amount);
            } else if (currentProcess->id == dst) {
                sendAckFromDestination(currentProcess, src);
                sendAckFromDestination(currentProcess, PARENT_ID);
            }
            break;
    }
}
