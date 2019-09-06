//
// Created by princess on 03.09.2019.
//

#ifndef PA4_CRITICAL_SECTION_H
#define PA4_CRITICAL_SECTION_H

#include "main.h"

/**
 * Отсылает сообщение типа CS_REPLY при добавлении процесса в собственную очередь
 * @param self
 * @param dst
 * @return
 */
int reply_cs(const void *self, local_id dst);

#endif //PA4_CRITICAL_SECTION_H
