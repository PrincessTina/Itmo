//
// Created by princess on 02.09.2019.
//

#ifndef PA4_QUEUE_H
#define PA4_QUEUE_H

typedef struct {
    int time_mark;
    int process_id;
} QueuePriority;

typedef struct node {
    int data;
    QueuePriority priority;
    struct node *next;
} QueueNode;

/**
 * Кладет элемент в очередь по ее head в зависимости от его приоритета
 * @param head
 * @param data
 * @param priority
 * @return
 */
void push(QueueNode **head, int data, int time_mark, int process_id);

/**
 * Переназначает head следующему элементу в очереди
 * @param head
 * @return
 */
void pop(QueueNode **head);

#endif //PA4_QUEUE_H
