//
// Created by princess on 26.08.2019.
//

#ifndef PA1_MAIN_H
#define PA1_MAIN_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <wait.h>
#include <sys/types.h>
#include <fcntl.h>

#include "common.h"
#include "ipc.h"
#include "pa2345.h"
#include "banking.h"

#define ERROR_CODE -1

typedef struct {
    int read;
    int write;
} Pipe;

typedef struct {
    pid_t pid;
    int id;
    int parent_pid;
    int processes_amount;
    Pipe **pipes;
    BalanceState balanceState;
    BalanceHistory balanceHistory;
} Process;

#endif //PA1_MAIN_H
