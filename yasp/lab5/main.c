#include <stdio.h>
#include <malloc.h>
#include "main.h"

bmpHeader header;
image_t image;
int condition = 0;

int main(void) {
    char *filename = "/home/princess/itmo/yasp/lab5/cmake-build-debug/1496934050_75.bmp";
    FILE *file;

    file = openFile(filename);

    switch (condition) {
        case READ_INVALID_SIGNATURE:
            printf("Failed while opening file. Maybe file is corrupted\n");
            goto breakPoint;
        default:
            printf("Successfully read from file %s\n", filename);
    }

    readBMP(file);

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
    saveBMP(filename);

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
    int i, k = 0, j = 0, g = 0;
    int size = image.width * image.height;
    pixel_t **massiv;
    pixel_t **massiv2;

    if (degree == 90) {
        massiv = (pixel_t **) calloc(image.width, sizeof(pixel_t*));
        for (i = 0; i < image.width; i++) {
            massiv[i] = (pixel_t *) calloc(image.height, sizeof(pixel_t));
        }

        massiv2 = (pixel_t **) calloc(image.height, sizeof(pixel_t*));
        for (i = 0; i < image.height; i++) {
            massiv2[i] = (pixel_t *) calloc(image.width, sizeof(pixel_t));
        }

        for (i = 0; i < image.height; i++) {
            while (k != image.width) {
                massiv[i][k] = image.array[j];
                k++;
                j++;
            }
            k = 0;
        }

        for (i = 0; i < image.height; i++) {
            while (k != image.width) {
                massiv2[image.width - k - 1][i] = massiv[i][k];
                k++;
            }
            k = 0;
        }

        for (i = 0; i < image.width; i++) {
            for (j = 0; j < image.height; j++) {
                image.array[g] = massiv2[i][j];
                g++;
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

void readBMP(FILE *file) {
    double degree = 90;

    image.width = header.biWidth;
    image.height = header.biHeight;
    image.width3 = 3 * image.width;

    while (image.width3 % 4 != 0) {
        image.width3++;
    }

    image.array = (pixel_t *) calloc(image.width3 * image.height, sizeof(pixel_t));
    fread(image.array, 1, image.width3 * image.height, file);

    condition = READ_OK;

    /* turn over file */
    rotate(degree);

    fclose(file);
    condition = ROTATE_OK;
}

void saveBMP(char *filename) {
    FILE *file = fopen(filename, "wb");

    fwrite(&header, sizeof(bmpHeader), 1, file);
    fwrite(image.array, sizeof(pixel_t), image.width3 * image.height, file);
    fclose(file);
    condition = WRITE_OK;
}
