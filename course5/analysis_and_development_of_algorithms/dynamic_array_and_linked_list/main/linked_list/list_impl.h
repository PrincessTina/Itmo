#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H

List::Node *List::allocateChunk() {
    return (Node *) malloc(sizeof(Node) * chunkSize);
}

void *List::freeChunk(Node *chunk) {
    free(chunk);
}

void List::throwException() {
    throw std::invalid_argument("Accessing list out of bounds");
}

List::List() {
    headNode = allocateChunk();
}

List::~List() {
    if (nodesCount == 0) {
        free(headNode);
    }
}

void List::insertHead(const int &value) {
    int index = chunkSize - 1;

    if (nodesCount == 0) {
        headNode += index;
        tailNode = headNode;
    } else if (headNode->index == 0) {
        Node *temporary = allocateChunk();
        temporary += index;

        headNode->nextChunk = temporary;
        temporary->nextChunk = headNode;
        headNode = temporary;
    } else {
        index = headNode->index - 1;
        headNode--;
    }

    headNode->value = value;
    headNode->index = index;

    nodesCount++;
}

void List::insertTail(const int &value) {
    int index = 0;

    if (nodesCount == 0) {
        tailNode = headNode;
    } else if (tailNode->index == chunkSize - 1) {
        Node *temporary = allocateChunk();

        tailNode->nextChunk = temporary;
        temporary->nextChunk = tailNode;
        tailNode = temporary;
    } else {
        index = tailNode->index + 1;
        tailNode++;
    }

    tailNode->value = value;
    tailNode->index = index;

    nodesCount++;
}

void List::removeHead() {
    if (nodesCount == 0) {
        throwException();
    }

    if (nodesCount == 1) {
        headNode = tailNode = nullptr;
    } else if (headNode->index == chunkSize - 1) {
        headNode = headNode->nextChunk;
        freeChunk(headNode->nextChunk - chunkSize + 1);
        headNode->nextChunk = nullptr;
    } else {
        headNode++;
    }

    nodesCount--;
}

void List::removeTail() {
    if (nodesCount == 0) {
        throwException();
    }

    if (nodesCount == 1) {
        headNode = tailNode = nullptr;
    } else if (tailNode->index == 0) {
        tailNode = tailNode->nextChunk;
        freeChunk(tailNode->nextChunk);
        tailNode->nextChunk = nullptr;
    } else {
        tailNode--;
    }

    nodesCount--;
}

const int &List::head() const {
    if (nodesCount == 0) {
        throwException();
    }

    return headNode->value;
}

const int &List::tail() const {
    if (nodesCount == 0) {
        throwException();
    }

    return tailNode->value;
}

int List::size() const {
    return nodesCount;
}

List::Iterator List::iterator() {
    return Iterator(this, headNode, chunkSize);
}

List::Iterator List::iterator() const {
    return Iterator(this, headNode, chunkSize);
}

List::Iterator::Iterator(List *list, Node *currentNodePointer, int chunkSize) {
    this->list = list;
    this->currentNodePointer = currentNodePointer;
    this->chunkSize = chunkSize;
}

List::Iterator::Iterator(const List *list, Node *currentNodePointer, int chunkSize) {
    this->readableList = list;
    this->currentNodePointer = currentNodePointer;
    this->chunkSize = chunkSize;
}

const int &List::Iterator::get() const {
    if (list->size() == 0) {
        throwException();
    }

    return currentNodePointer->value;
}

void List::Iterator::set(const int &value) {
    if (list->size() == 0) {
        throwException();
    }

    currentNodePointer->value = value;
}

void List::Iterator::next() {
    if (!hasNext()) {
        throwException();
    }

    if (currentNodePointer->index < chunkSize - 1) {
        currentNodePointer++;
    } else {
        currentNodePointer = currentNodePointer->nextChunk;
    }
}

void List::Iterator::prev() {
    if (!hasPrev()) {
        throwException();
    }

    if (currentNodePointer->index > 0) {
        currentNodePointer--;
    } else {
        currentNodePointer = currentNodePointer->nextChunk;
    }
}

bool List::Iterator::hasNext() const {
    return currentNodePointer != list->tailNode;
}

bool List::Iterator::hasPrev() const {
    return currentNodePointer != list->headNode;
}

#endif //DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
