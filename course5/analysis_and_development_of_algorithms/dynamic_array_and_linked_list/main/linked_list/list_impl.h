#ifndef DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H
#define DYNAMIC_ARRAY_AND_LINKED_LIST_LIST_IMPL_H

List::Node *List::allocateChunk() {
    return (Node *) malloc(sizeof(Node) * chunkSize);
}

void List::throwException() {
    throw std::invalid_argument("Accessing list out of bounds");
}

List::List() {
    headNode = allocateChunk();
}

List::~List() {

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

}

void List::removeHead() {

}

void List::removeTail() {

}

const int &List::head() const {
    return headNode->value;
}

const int &List::tail() const {
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
    return currentNodePointer->value;
}

void List::Iterator::set(const int &value) {
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
