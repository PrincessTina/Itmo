//
// Created by princess on 31.08.2019.
//

#ifndef PA3_LAMPORT_TIME_H
#define PA3_LAMPORT_TIME_H

#include "main.h"

/**
 * Устанавливает lamport_time равным переданному значению
 * @param value
 */
void set_lamport_time(timestamp_t value);

/**
 * Инкрементирует lamport_time на единицу
 */
timestamp_t inc_lamport_time();

timestamp_t receive_lamport_time(Message message);

#endif //PA3_LAMPORT_TIME_H
