#include <stdio.h>
#include <malloc.h>
#include "main.h"

bmpHeader header;
image_t image;
int condition = 0;

int main(void) {
    char *filename = "/home/princess/itmo/yasp/lab5/cmake-build-debug/cat.bmp";
    FILE *file = openFile(filename);
    double degree = 90;

    switch (condition) {
        case READ_INVALID_SIGNATURE:
            printf("Failed while opening file. Maybe file is corrupted\n");
            goto breakPoint;
        default:
            printf("Successfully read from file %s\n", filename);
    }

    readBMP(file, degree);

    switch (condition) {
        case ROTATE_OK:
            printf("Successfully created new array with rotated points of pixels\n");
            break;
        case READ_OK:
            printf("Can read from file but cannot create new array with rotated points\n");
        default:
            printf("Something bad adventured while rotating\n");
            goto breakPoint;
    }

    filename = "test.bmp";
    saveBMP(filename, degree);

    switch (condition) {
        case WRITE_OK:
            printf("Successfully write in file %s\n", filename);
            break;
        default:
            printf("Something bad adventured while writing in file\n");
    }

    breakPoint:
    return 0;
}

FILE *openFile(char *filename) {
    FILE *file = fopen(filename, "rb");
    fread(&header, sizeof(bmpHeader), 1, file);

    if (header.b != 'B' || header.m != 'M' || header.biBitCount != 24) {
        condition = READ_INVALID_SIGNATURE;
    } else {
        condition = OPEN_OK;
    }

    return file;
}

void rotate(double degree) {
    int i, k = 0, j = 0;
    int size = image.width * image.height;
    pixel_t **massiv;
    pixel_t **massiv2;

    if (degree == 90) {
        massiv = (pixel_t **) calloc(image.height, sizeof(pixel_t *));
        for (i = 0; i < image.height; i++) {
            massiv[i] = (pixel_t *) calloc(image.width, sizeof(pixel_t));
        }

        massiv2 = (pixel_t **) calloc(image.width, sizeof(pixel_t *));
        for (i = 0; i < image.width; i++) {
            massiv2[i] = (pixel_t *) calloc(image.height, sizeof(pixel_t));
        }

        for (i = 0; i < image.height; i++) {
            for (j = 0; j < image.width; j++) {
                massiv[i][j] = image.array[k];
                k++;
            }
        }

        for (i = 0; i < image.height; i++) {
            for (j = 0; j < image.width; j++) {
                massiv2[image.width - j - 1][i] = massiv[i][j];
            }
        }

        k = 0;

        for (i = 0; i < image.width; i++) {
            for (j = 0; j < image.height; j++) {
                image.array[k] = massiv2[i][j];
                k++;
            }
        }
    } else if (degree == 180) {
        for (i = 0; i <= size / 2; i++) {
            pixel_t t = image.array[size - 1 - i];
            image.array[size - i - 1] = image.array[i];
            image.array[i] = t;
        }
    }
}

void readBMP(FILE *file, double degree) {
    int size;
    int ideal_width;
    int difference = 0;

    image.width = header.biWidth;
    image.height = header.biHeight;
    image.array = (pixel_t *) calloc(image.width * image.height, sizeof(pixel_t));
    ideal_width = 3 * image.width;

    while (ideal_width % 4 != 0) {
        ideal_width ++;
        difference = ideal_width - 3 * image.width;
    }

    fseek(file, header.bfOffBytes, SEEK_SET);

    for (size = 0; size < image.height; size++) {
        fread(image.array + image.width * size, sizeof(pixel_t), image.width, file);
        fseek(file, (difference), SEEK_CUR);
    }

    condition = READ_OK;

    /* turn over file */
    rotate(degree);

    fclose(file);
    condition = ROTATE_OK;
}

void saveBMP(char *filename, double degree) {
    FILE *file = fopen(filename, "wb");
    int size;
    int ideal_width = 0;
    int difference = 0;
    int i = 0;
    int j = 0;

    if (degree == 90) {
        ideal_width = 3 * image.height;
        i = image.width;
        j = image.height;
        header.biWidth = image.height;
        header.biHeight = image.width;
    } else if (degree == 180) {
        ideal_width = 3 * image.width;
        i = image.height;
        j = image.width;
    }

    while (ideal_width % 4 != 0) {
        ideal_width ++;
        difference = ideal_width - 3 * j;
    }

    header.bfOffBytes = sizeof(header);
    header.headerSize = 40;

    fwrite(&header, sizeof(bmpHeader), 1, file);

    for (size = 0; size < i; size++) {
        fwrite(image.array + j * size, sizeof(pixel_t), (size_t) j, file);
        fseek(file, difference, SEEK_CUR);
    }

    fclose(file);
    condition = WRITE_OK;
}
