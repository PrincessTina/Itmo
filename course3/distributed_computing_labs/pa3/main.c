#include "main.h"

/**
 * Ищет начало необходимых аргументов
 * @param argc
 * @param argv
 * @return
 */
int findStartPointOfArguments(int argc, char *const *argv) {
    for (int i = 0; i < argc; i++) {
        if ((strcmp(argv[i], "-p") == 0)) {
            return i + 1;
        }
    }

    return ERROR_CODE;
}

/**
 * Возвращает количество процессов как аргумент командной строки
 * @param argc
 * @param argv
 * @param start_point
 * @return
 */
int getN(int argc, char *const *argv, int start_point) {
    if (start_point < argc) {
        int x = atoi(argv[start_point]); // NOLINT(cert-err34-c)

        if (x <= 0) {
            return ERROR_CODE;
        }

        return x + 1;
    }

    return ERROR_CODE;
}

/**
 * Возвраащет массив балансов каждого процесса
 * @param argc
 * @param argv
 * @param start_point
 * @return
 */
int* getBalance(int argc, char *const *argv, int start_point) {
    int *balance = malloc(sizeof(int) * (argc - start_point));

    for (int i = start_point; i < argc; i++) {
        int x = atoi(argv[i]);  // NOLINT(cert-err34-c)

        if (x <= 0) {
            return NULL;
        }

        balance[i - start_point] = x;
    }

    return balance;
}

/**
 * Ожидает завершения исполнения всех процессов-детей
 * @param currentProcess
 */
void waitAllProcesses(Process* currentProcess) {
    for (int i = 1; i < currentProcess->processes_amount; i++) {
        wait(NULL);
    }
}

/**
 * Рассылает и принимает сообщения STARTED и DONE в зависимости от их типа
 * @param currentProcess
 * @param event_log
 * @param messageType
 */
void sendSynchronizeMessage(Process *currentProcess, FILE *event_log, MessageType messageType) {
    char *text = malloc(MAX_PAYLOAD_LEN);
    unsigned int text_length;
    int time;
    Message message;

    time = inc_lamport_time();

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_local_time = time;

    switch (messageType) {
        case STARTED:
            // Запись в text строки сообщения
            sprintf(text, log_started_fmt, time, currentProcess->id, currentProcess->pid,
                    currentProcess->parent_pid, currentProcess->balanceState.s_balance);

            text_length = strlen(text) * sizeof(char);

            message.s_header.s_type = STARTED;
            message.s_header.s_payload_len = text_length;
            memcpy(message.s_payload, text, text_length);

            break;
        case DONE:
            // Запись в text строки сообщения
            sprintf(text, log_done_fmt, time, currentProcess->id, currentProcess->balanceState.s_balance);

            text_length = strlen(text) * sizeof(char);

            message.s_header.s_type = DONE;
            message.s_header.s_payload_len = text_length;
            memcpy(message.s_payload, text, text_length);

            break;
        default:
            break;
    }

    if (currentProcess->id != PARENT_ID) {
        fprintf(event_log, "%s", text); // Запись события в event_log
        printf("%s", text); // Запись события на stdout

        send_multicast(currentProcess, &message);
    }

    // Ожидаем сообщение от всех
    for (int i = 1; i < currentProcess->processes_amount;) {
        if (i == currentProcess->id) {
            i++;
            continue;
        }

        int bytesRead = receive(currentProcess, i, &message);
        if (bytesRead == ERROR_CODE) {
            continue;
        }

        time = receive_lamport_time(message);

        // Если пришло сообщение типа, которого ожидаем, - переходим к следующему процессу
        if (message.s_header.s_type == messageType) {
            i++;
        }
    }

    switch (messageType) {
        case STARTED:
            fprintf(event_log, log_received_all_started_fmt, time, currentProcess->id); // Запись события в event_log
            printf(log_received_all_started_fmt, time, currentProcess->id); // Запись события на stdout
            break;
        case DONE:
            fprintf(event_log, log_received_all_done_fmt, time, currentProcess->id); // Запись события в event_log
            printf(log_received_all_done_fmt, time, currentProcess->id); // Запись события на stdout
            break;
        default:
            break;
    }

    free(text);
}

/**
 * Рассылает и принимает сообщения о готовности процессов к работе
 * @param currentProcess
 * @param event_log
 */
void synchronizeBeforeWork(Process *currentProcess, FILE *event_log) {
    sendSynchronizeMessage(currentProcess, event_log, STARTED);
}

/**
 * Рассылает и принимает сообщения о завершении выполнения процессами работы
 * @param currentProcess
 * @param event_log
 */
void synchronizeAfterWork(Process *currentProcess, FILE *event_log) {
    sendSynchronizeMessage(currentProcess, event_log, DONE);
}

/**
 * Parent отсылает всем процессам сообщение STOP
 * @param currentProcess
 */
void sendStopMessage(Process *currentProcess) {
    Message message;

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_type = STOP;
    message.s_header.s_payload_len = 0;

    send_multicast(currentProcess, &message);
}

/**
 * Заполняет историю в местах, где нет времени, а также новым пришедшим значением
 * @param currentProcess
 */
void spreadMissingHistory(Process *currentProcess) {
    const int nextHistoryId = currentProcess->balanceHistory.s_history_len;
    const int difference = currentProcess->balanceState.s_time - 1 - currentProcess->balanceHistory.s_history[nextHistoryId - 1].s_time;
    BalanceState lastHistory = currentProcess->balanceHistory.s_history[nextHistoryId - 1];

    if (difference == -1) {
        currentProcess->balanceHistory.s_history[nextHistoryId - 1] = currentProcess->balanceState;

        printf("REWRITE HISTORY IN PROCESS %d WITH TIME %d", currentProcess->id, currentProcess->balanceState.s_time);

        return;
    }

    for (int i = nextHistoryId; i < nextHistoryId + difference; i++) {
        lastHistory.s_time += 1;

        currentProcess->balanceHistory.s_history[i] = lastHistory;
        currentProcess->balanceHistory.s_history_len += 1;
    }

    currentProcess->balanceHistory.s_history[currentProcess->balanceHistory.s_history_len] = currentProcess->balanceState;
    currentProcess->balanceHistory.s_history_len += 1;
}

/**
 * Обработка сообщения TRANSFER
 * @param currentProcess
 * @param event_log
 * @param message
 */
void handleTransferMessage(Process *currentProcess, FILE *event_log, Message *message) {
    TransferOrder *transferOrder = (TransferOrder *) message->s_payload;
    const int src = transferOrder->s_src;
    const int dst = transferOrder->s_dst;
    const int amount = transferOrder->s_amount;
    int time;

    transfer(currentProcess, src, dst, amount);

    time = get_lamport_time();

    if (currentProcess->id == src) {
        currentProcess->balanceState.s_balance -= amount;
        currentProcess->balanceState.s_balance_pending_in = amount;
        currentProcess->balanceState.s_time = time;

        spreadMissingHistory(currentProcess);

        fprintf(event_log, log_transfer_out_fmt, time, src, amount, dst); // Запись в event_log
        printf(log_transfer_out_fmt, time, src, amount, dst); // Запись на консоль
    } else if (currentProcess->id == dst) {
        currentProcess->balanceState.s_balance += amount;
        currentProcess->balanceState.s_time = time;

        spreadMissingHistory(currentProcess);

        fprintf(event_log, log_transfer_in_fmt, time, dst, amount, src); // Запись в event_log
        printf(log_transfer_in_fmt, time, dst, amount, src); // Запись на консоль
    }
}

/**
 * Обработка сообщения ACK
 * @param currentProcess
 */
void handleAckMessage(Process *currentProcess) {
    int time;

    time = get_lamport_time();

    currentProcess->balanceState.s_time = time;
    currentProcess->balanceState.s_balance_pending_in = 0;

    spreadMissingHistory(currentProcess);
}

/**
 * Процессы выполняют полезную работу
 * @param currentProcess
 * @param event_log
 */
void work(Process *currentProcess, FILE *event_log) {
    Message message;
    int stop = 0;

    while (!stop) {
        if (receive_any(currentProcess, &message) != ERROR_CODE) {
            receive_lamport_time(message);

            switch (message.s_header.s_type) {
                case ACK: {
                    handleAckMessage(currentProcess);
                    break;
                }

                case TRANSFER: {
                    if (currentProcess->balanceState.s_balance_pending_in == 0) {
                        handleTransferMessage(currentProcess, event_log, &message);
                    }

                    break;
                }

                case STOP:
                    stop = 1;
                    break;

                default:
                    printf("Not handled type %d", message.s_header.s_type);
            }
        }
    }
}

/**
 * Выравниваем каждую историю до общей длины
 * @param history
 * @return
 */
AllHistory getAlignedHistory(AllHistory history) {
    int maxHistoryLength = 0;

    // Ищем максимальную длину истории
    for (int i = 0; i < history.s_history_len; i++) {
        if (history.s_history[i].s_history_len > maxHistoryLength) {
            maxHistoryLength = history.s_history[i].s_history_len;
        }
    }

    // Добавляем недостающие истории, чтобы заполнить массив до общей длины
    for (int i = 0; i < history.s_history_len; i++) {
        const int nextHistoryId = history.s_history[i].s_history_len;

        if (nextHistoryId < maxHistoryLength) {
            BalanceState lastHistory = history.s_history[i].s_history[nextHistoryId - 1];

            for (int j = nextHistoryId; j < maxHistoryLength; j++) {
                lastHistory.s_time += 1;

                history.s_history[i].s_history[j] = lastHistory;
                history.s_history[i].s_history_len += 1;
            }
        }
    }

    return history;
}

/**
 * Parent получает и возвращает balanceHistory всех процессов
 * @param currentProcess
 * @return
 */
AllHistory getHistoryFromProcesses(Process *currentProcess) {
    AllHistory history;
    history.s_history_len = 0;
    Message message;

    while (history.s_history_len != currentProcess->processes_amount - 1) {
        if (receive_any(currentProcess, &message) != ERROR_CODE && message.s_header.s_type == BALANCE_HISTORY) {
            receive_lamport_time(message);

            BalanceHistory balanceHistory;
            memcpy(&balanceHistory, message.s_payload, message.s_header.s_payload_len);

            history.s_history[history.s_history_len] = balanceHistory;
            history.s_history_len += 1;
        }
    }

    return getAlignedHistory(history);
}

/**
 * Процессы отсылают свою историю к Parent
 * @param currentProcess
 */
void sendBalanceHistory(Process *currentProcess) {
    unsigned int length = sizeof(BalanceHistory);
    Message message;

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_local_time = inc_lamport_time();
    message.s_header.s_type = BALANCE_HISTORY;
    message.s_header.s_payload_len = length;
    memcpy(message.s_payload, &currentProcess->balanceHistory, length);

    send(currentProcess, PARENT_ID, &message);
}

/**
 * Закрывает каналы, недоступные на чтение и запись для текущего процесса
 * @param currentProcess
 */
void closeUnreachablePipes(Process *currentProcess) {
    for (int i = 0; i < currentProcess->processes_amount; i++) {
        for (int j = 0; j < currentProcess->processes_amount; j++) {
            if (j == i) {
                continue;
            }

            if (i == currentProcess->id) {
                close(currentProcess->pipes[i][j].read);
            } else if (j == currentProcess->id) {
                close(currentProcess->pipes[i][j].write);
            } else {
                close(currentProcess->pipes[i][j].read);
                close(currentProcess->pipes[i][j].write);
            }
        }
    }
}

/**
 * Инициализирует процессы при помощи fork()
 * @param currentProcess
 */
void initProcesses(Process *currentProcess, const int *balance) {
    for (int i = 1; i < currentProcess->processes_amount; i++) {
        int pid = fork();

        if (pid == 0) {
            currentProcess->id = i;
            currentProcess->pid = getpid();

            currentProcess->balanceState.s_balance = balance[i - 1];
            currentProcess->balanceState.s_time = get_lamport_time();
            currentProcess->balanceState.s_balance_pending_in = 0;

            currentProcess->balanceHistory.s_history[0] = currentProcess->balanceState;
            currentProcess->balanceHistory.s_history_len = 1;
            currentProcess->balanceHistory.s_id = currentProcess->id;

            closeUnreachablePipes(currentProcess);

            return;
        }
    }

    // Закрываем каналы родителя
    closeUnreachablePipes(currentProcess);
}

/**
 * Создает однонаправленные pipes на чтение и запись между всеми процессами при помощи pipes()
 * @param currentProcess
 */
void createPipes(Process *currentProcess) {
    FILE *pipe_log = fopen(pipes_log, "a");

    currentProcess->pipes = malloc((sizeof(Pipe*) * currentProcess->processes_amount));

    for (int i = 0; i < currentProcess->processes_amount; i++) {
        currentProcess->pipes[i] = malloc(sizeof(Pipe) * currentProcess->processes_amount);

        for (int j = 0; j < currentProcess->processes_amount; j++) {
            int descriptors[2];

            if (j == i) {
                continue;
            }

            if (pipe(descriptors) < 0) {
                return;
            }

            currentProcess->pipes[i][j].read = descriptors[0];
            currentProcess->pipes[i][j].write = descriptors[1];

            // Делаем read и write неблокирующим
            int fcntlResponseRead = fcntl(currentProcess->pipes[i][j].read, F_SETFL, O_NONBLOCK);
            int fcntlResponseWrite = fcntl(currentProcess->pipes[i][j].write, F_SETFL, O_NONBLOCK);
            if (fcntlResponseRead < 0 || fcntlResponseWrite < 0) {
                return;
            }

            // Запись в pipe_log
            fprintf(pipe_log, "Pipe was created read %d process write %d process\n", i, j);
        }
    }

    fclose(pipe_log);
}

/**
 * Закрывает созданные каналы
 * @param currentProcess
 */
void closePipes(Process *currentProcess) {
    for (int i = 0; i < currentProcess->processes_amount; i++) {
        for (int j = 0; j < currentProcess->processes_amount; j++) {
            if (j == i) {
                continue;
            }

            close(currentProcess->pipes[i][j].read);
            close(currentProcess->pipes[i][j].write);
        }

        free(currentProcess->pipes[i]);
    }

    free(currentProcess->pipes);
}

int main(int argc, char *argv[]) {
    FILE *event_log = fopen(events_log, "a");
    Process currentProcess; // На данном этапе - родитель
    int startPointOfArgument = findStartPointOfArguments(argc, argv);
    int *balance;

    currentProcess.id = PARENT_ID;
    currentProcess.pid = getpid();
    currentProcess.parent_pid = currentProcess.pid;

    if (startPointOfArgument == ERROR_CODE) {
        return ERROR_CODE;
    }

    currentProcess.processes_amount = getN(argc, argv, startPointOfArgument);
    if (currentProcess.processes_amount == ERROR_CODE) {
        return ERROR_CODE;
    }

    balance = getBalance(argc, argv, startPointOfArgument + 1);
    if (balance == NULL) {
        return ERROR_CODE;
    }

    createPipes(&currentProcess);
    initProcesses(&currentProcess, balance);

    synchronizeBeforeWork(&currentProcess, event_log);

    if (currentProcess.id == PARENT_ID) {
        bank_robbery(&currentProcess, currentProcess.processes_amount - 1);
        sendStopMessage(&currentProcess);
    } else {
        work(&currentProcess, event_log);
    }

    synchronizeAfterWork(&currentProcess, event_log);

    if (currentProcess.id == PARENT_ID) {
        AllHistory history = getHistoryFromProcesses(&currentProcess);
        print_history(&history);
        waitAllProcesses(&currentProcess);
        closePipes(&currentProcess);
        free(balance);
        fclose(event_log);
    } else {
        sendBalanceHistory(&currentProcess);
    }

    return 0;
}
