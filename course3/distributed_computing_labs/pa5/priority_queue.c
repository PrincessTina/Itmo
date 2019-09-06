//
// Created by princess on 02.09.2019.
//

#include "main.h"

/**
 * Создает новый элемент очереди по заданным параметрам, указатель на следующий элемент - NULL
 * @param data
 * @param time_mark
 * @param process_id
 * @return
 */
QueueNode* createNode(int data, int time_mark, int process_id) {
    QueueNode *node = malloc(sizeof(QueueNode));
    QueuePriority priority;

    priority.time_mark = time_mark;
    priority.process_id = process_id;

    node->data = data;
    node->priority = priority;
    node->next = NULL;

    return node;
}

/**
 * Сравнивает два элемента очереди
 * Возвращает TRUE / FALSE, если первый аргумент больше / меньше
 * @param first
 * @param second
 * @return
 */
int compareNodes(QueueNode *first, QueueNode *second) {
    if (first->priority.time_mark > second->priority.time_mark) {
        return FALSE;
    }

    if (first->priority.time_mark < second->priority.time_mark) {
        return TRUE;
    }

    if (first->priority.process_id < second->priority.process_id) {
        return TRUE;
    }

    return FALSE;
}

void push(QueueNode **head, int data, int time_mark, int process_id) {
    QueueNode *newNode = createNode(data, time_mark, process_id);

    if ((*head) == NULL) {
        (*head) = newNode;

        return;
    }

    if (compareNodes(newNode, (*head))) {
        QueueNode *exHead = *head;
        (*head) = newNode;
        (*head)->next = exHead;
    } else {
        QueueNode *previousNode = *head;
        QueueNode *currentNode = previousNode->next;

        while(1) {
            if (currentNode == NULL) {
                previousNode->next = newNode;

                return;
            }

            if (compareNodes(newNode, currentNode)) {
                previousNode->next = newNode;
                newNode->next = currentNode;

                return;
            }

            previousNode = currentNode;
            currentNode = previousNode->next;
        }
    }
}

void pop(QueueNode **head) {
    QueueNode *secondNode = (*head) == NULL ? NULL : (*head)->next;

    if (secondNode == NULL) {
        (*head) = NULL;

        return;
    }

    (*head)->data = secondNode->data;
    (*head)->priority = secondNode->priority;
    (*head)->next = secondNode->next;

    free(secondNode);
}
