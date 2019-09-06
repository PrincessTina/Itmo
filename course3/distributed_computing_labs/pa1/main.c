#include "main.h"

/**
 * Принимает количество процессов как аргумент командной строки
 * @param argc
 * @param argv
 * @return
 */
int getN(int argc, char *const *argv) {
    for (int i = 0; i < argc; i++) {
        if ((strcmp(argv[i], "-p") == 0) && (i + 1 < argc)) {
            int x = atoi(argv[i+1]);

            if (x <= 0) {
                return ERROR_CODE;
            }

            return x + 1;
        }
    }

    return ERROR_CODE;
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
 * Рассылает и принимает сообщения в зависимости от их типа
 * @param currentProcess
 * @param event_log
 * @param messageType
 */
void sendMessage(Process *currentProcess, FILE *event_log, MessageType messageType) {
    char *text = malloc(MAX_PAYLOAD_LEN);
    unsigned int text_length;

    Message message;
    message.s_header.s_magic = MESSAGE_MAGIC;

    switch (messageType) {
        case STARTED:
            sprintf(text, log_started_fmt, currentProcess->id, currentProcess->pid, currentProcess->parent_pid); // Запись в text строки сообщения

            text_length = strlen(text) * sizeof(char);

            message.s_header.s_type = STARTED;
            message.s_header.s_payload_len = text_length;
            memcpy(message.s_payload, text, text_length);

            break;
        case DONE:
            sprintf(text, log_done_fmt, currentProcess->id); // Запись в text строки сообщения

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

    for (int i = 1; i < currentProcess->processes_amount; i++) {
        if (i == currentProcess->id) {
            continue;
        }

        receive(currentProcess, i, &message);
    }

    switch (messageType) {
        case STARTED:
            fprintf(event_log, log_received_all_started_fmt, currentProcess->id); // Запись события в event_log
            printf(log_received_all_started_fmt, currentProcess->id); // Запись события на stdout
            break;
        case DONE:
            fprintf(event_log, log_received_all_done_fmt, currentProcess->id); // Запись события в event_log
            printf(log_received_all_done_fmt, currentProcess->id); // Запись события на stdout
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
    sendMessage(currentProcess, event_log, STARTED);
}

/**
 * Рассылает и принимает сообщения о завершении выполнения процессами работы
 * @param currentProcess
 * @param event_log
 */
void synchronizeAfterWork(Process *currentProcess, FILE *event_log) {
    sendMessage(currentProcess, event_log, DONE);
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

    closeUnreachablePipes(currentProcess); // Закрываем каналы родителя
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

            fprintf(pipe_log, "Pipe was created read %d process write %d process\n", i, j); // Запись в pipe_log
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

    currentProcess.id = PARENT_ID;
    currentProcess.pid = getpid();
    currentProcess.parent_pid = currentProcess.pid;
    currentProcess.processes_amount = getN(argc, argv);

    if (currentProcess.processes_amount == -1) {
        return ERROR_CODE;
    }

    createPipes(&currentProcess);
    initProcesses(&currentProcess);

    synchronizeBeforeWork(&currentProcess, event_log);
    synchronizeAfterWork(&currentProcess, event_log);

    if (currentProcess.id == PARENT_ID) {
        waitAllProcesses(&currentProcess);
        closePipes(&currentProcess);
        fclose(event_log);
    }

    return 0;
}
