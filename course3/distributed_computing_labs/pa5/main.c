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
 * Возвращает TRUE или FALSE в зависимости от того, нашел ли метод параметр mutexl или нет
 * @param argc
 * @param argv
 * @return
 */
int findMutex(int argc, char *const *argv) {
    for (int i = 0; i < argc; i++) {
        if ((strcmp(argv[i], "--mutexl") == 0)) {
            return TRUE;
        }
    }

    return FALSE;
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
 * Проверяет наличие сообщений Done от всех процессов
 * @param currentProcess
 * @return
 */
int checkForAllDoneMessages(Process* currentProcess) {
    for (int i = 1; i < currentProcess->processes_amount; i++) {
        if (i == currentProcess->id) {
            continue;
        }

        if (currentProcess->finishedProcesses[i] == 0) {
            return FALSE;
        }
    }

    return TRUE;
}

/**
 * Рассылает и принимает сообщения Done
 * @param currentProcess
 * @param event_log
 */
void sendDoneMessage(Process *currentProcess, FILE *event_log) {
    char *text = malloc(MAX_PAYLOAD_LEN);
    unsigned int text_length;
    int time = inc_lamport_time();
    Message message;

    // Запись в text строки сообщения
    sprintf(text, log_done_fmt, time, currentProcess->id, 0);

    text_length = strlen(text) * sizeof(char);

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_local_time = time;
    message.s_header.s_type = DONE;
    message.s_header.s_payload_len = text_length;
    memcpy(message.s_payload, text, text_length);

    if (currentProcess->id != PARENT_ID) {
        fprintf(event_log, "%s", text); // Запись события в event_log
        printf("%s", text); // Запись события на stdout

        send_multicast(currentProcess, &message);

        // Ожидаем Done от всех процессов
        while (!checkForAllDoneMessages(currentProcess)) {
            local_id src = receive_any(currentProcess, &message);

            if (src == ERROR_CODE) {
                continue;
            }

            receive_lamport_time(message);

            switch (message.s_header.s_type) {
                case CS_REQUEST:
                    reply_cs(currentProcess, src);
                    break;

                case CS_REPLY:
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

    fprintf(event_log, log_received_all_done_fmt, time, currentProcess->id); // Запись события в event_log
    printf(log_received_all_done_fmt, time, currentProcess->id); // Запись события на stdout

    free(text);
}

/**
 * Рассылает и принимает сообщения Start
 * @param currentProcess
 * @param event_log
 * @param messageType
 */
void sendStartMessage(Process *currentProcess, FILE *event_log, MessageType messageType) {
    char *text = malloc(MAX_PAYLOAD_LEN);
    unsigned int text_length;
    int time = inc_lamport_time();
    Message message;

    // Запись в text строки сообщения
    sprintf(text, log_started_fmt, time, currentProcess->id, currentProcess->pid, currentProcess->parent_pid, 0);

    text_length = strlen(text) * sizeof(char);

    message.s_header.s_magic = MESSAGE_MAGIC;
    message.s_header.s_local_time = time;
    message.s_header.s_type = STARTED;
    message.s_header.s_payload_len = text_length;
    memcpy(message.s_payload, text, text_length);

    if (currentProcess->id != PARENT_ID) {
        fprintf(event_log, "%s", text); // Запись события в event_log
        printf("%s", text); // Запись события на stdout

        send_multicast(currentProcess, &message);
    }

    // Ожидаем Start от всех процессов
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

    fprintf(event_log, log_received_all_started_fmt, time, currentProcess->id); // Запись события в event_log
    printf(log_received_all_started_fmt, time, currentProcess->id); // Запись события на stdout

    free(text);
}

/**
 * Рассылает и принимает сообщения о готовности процессов к работе
 * @param currentProcess
 * @param event_log
 */
void synchronizeBeforeWork(Process *currentProcess, FILE *event_log) {
    sendStartMessage(currentProcess, event_log, STARTED);
}

/**
 * Рассылает и принимает сообщения о завершении выполнения процессами работы
 * @param currentProcess
 * @param event_log
 */
void synchronizeAfterWork(Process *currentProcess, FILE *event_log) {
    sendDoneMessage(currentProcess, event_log);
}

/**
 * Процессы выполняют полезную работу
 * @param currentProcess
 * @param event_log
 */
void work(Process *currentProcess, int mutexEnabled) {
    int printIterationsAmount = currentProcess->id * 5;

    for (int i = 1; i <= printIterationsAmount; i++) {
        char *string = malloc(sizeof(char) * 255);

        sprintf(string, log_loop_operation_fmt, currentProcess->id, i, currentProcess->id * 5);

        if (mutexEnabled) {
            request_cs(currentProcess);
        }

        print(string);

        if (mutexEnabled) {
            release_cs(currentProcess);
        }
    }
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
void initProcesses(Process *currentProcess) {
    for (int i = 1; i < currentProcess->processes_amount; i++) {
        int pid = fork();

        if (pid == 0) {
            currentProcess->id = i;
            currentProcess->pid = getpid();

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
    int mutexEnabled = findMutex(argc, argv);

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

    currentProcess.finishedProcesses = calloc(sizeof(int), currentProcess.processes_amount + 1);
    currentProcess.demandingAckProcesses = calloc(sizeof(int), currentProcess.processes_amount + 1);

    createPipes(&currentProcess);
    initProcesses(&currentProcess);

    synchronizeBeforeWork(&currentProcess, event_log);

    if (currentProcess.id != PARENT_ID) {
        work(&currentProcess, mutexEnabled);
    }

    synchronizeAfterWork(&currentProcess, event_log);

    if (currentProcess.id == PARENT_ID) {
        waitAllProcesses(&currentProcess);
        closePipes(&currentProcess);
        fclose(event_log);
    }

    return 0;
}
