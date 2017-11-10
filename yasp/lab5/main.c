#include <stdio.h>
#include <malloc.h>

#pragma pack(push, 2)
typedef struct bmpHeader {
    unsigned char b1, b2; // Символы BM (2 байта)
    unsigned long size; // Размер файла (4 байта)
    unsigned short notUse1; // (2 байта)
    unsigned short notUse2; // (2 байта)
    unsigned long massPos; // Местанахождение данных растрового массива (4 байта)

    unsigned long headerLength; // Длинна этого заголовка (4 байта)
    unsigned int width; // Ширина изображения (4 байта)
    unsigned int height; // Высота изображения (4 байта)
    unsigned short colorPlaneNumber; // Число цветовых плоскостей (2 байта)
    unsigned int bitPixel; // Бит/пиксель (2 байта)
    unsigned long compressMethod; // Метод сжатия (4 байта)
    unsigned long massLength; // Длинна массива с мусоро (4 байта)
    unsigned long massWidth; // Ширина массива с мусором (4 байта)
    unsigned long massHeight; // Высота массива с мусором (4 байта)
    unsigned long colorNumber; // Число цветов изображения (4 байта)
    unsigned long generalColorNumber; // Число основных цветов (4 байта)

} bmpHeader;
#pragma pack(pop)

typedef struct pixel_t {
    unsigned char b;
    unsigned char g;
    unsigned char r;
} pixel_t;

typedef struct image_t {
    unsigned int width;
    unsigned int width3;
    unsigned int height;
    pixel_t *array;
} image_t;


int readBMP(char *path);
int saveBMP(char *path);
void swap(struct pixel_t *a, struct pixel_t *b, unsigned int bytes);

bmpHeader header;
image_t image;

int main(void) {
    if (readBMP("u_88f1435a74e6a038244bb6ba08fe13f2_800.bmp") == 0) {
        printf("bad path");
        return 0;
    }

    if (saveBMP("test.bmp") == 0) {
        printf("bad path");
        return 0;
    }
    return 0;
}

int readBMP(char *path) {
    //ToDO: организовать проверку ширины на кратность 4
    FILE *file = fopen(path, "rb");
    int i, size, k = 0;
    double degree = 90;

    if (file == NULL) {
        return 0;
    }

    fread(&header, sizeof(bmpHeader), 1, file);

    if (header.b1 != 'B' || header.b2 != 'M' || header.bitPixel != 24) {
        printf("corrupted file");
        return 0;
    }

    image.width = header.width;
    image.height = header.height;
    image.width3 = 3 * image.width;

    image.array = (pixel_t *) calloc(image.width3 * image.height, sizeof(char));
    fread(image.array, 1, image.width3 * image.height, file);

    size = image.height * image.width;

    //переворачиваю
    if (degree == 90) {
        //pixel_t *massiv = (pixel_t *) calloc(image.width3 * image.height, sizeof(char));
        //int h = image.height;//8
        //int j = 0;
        //int c = 1;

        //for (i = 0; i < size; i ++) {
          //  if (j != image.width) {
            //    massiv[h*(j + 1) - c] = image.array[i];
              //  j ++;
            //} else {
              //  c ++;
                //j = 0;
            //}
       // }
        size_t width = image.height;
        size_t height = image.width3;

        pixel_t** pixels = (pixel_t**) calloc(height, sizeof(char*));
        for (i = 0; i < width; i ++) {
            pixels[i] = (pixel_t*) calloc(width, sizeof(char));
        }

        for (i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int y = height - i - 1;
                int x = width - j - 1;
                pixels[y][x] = image.array[width * i - 1 + j]; //initial_pixels[i][j];
            }
        }

        for (i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.array[width * i -1 + j] = pixels[i][j];
            }
        }

        //for (i = 0; i < size; i++) {
          //  image.array[i] = massiv[i];
        //}
    } else if (degree == 180) {
        for (i = 0; i <= size / 2; i++) {
            pixel_t t = image.array[size - 1 - i];
            image.array[size - i - 1] = image.array[i];
            image.array[i] = t;
        }
    }


    fclose(file);
    return 1;
}

void swap(pixel_t* a, pixel_t* b, unsigned int bytes) {
    pixel_t tmp;
    while (bytes--) {
        tmp = *a;
        *a++ = *b;
        *b++ = tmp;
    }
}

int saveBMP(char *path) {
    FILE *file = fopen(path, "wb");

    if (file == NULL) {
        return 0;
    }

    fwrite(&header, sizeof(bmpHeader), 1, file);
    fwrite(image.array, sizeof(char), image.width3 * image.height, file);
    fclose(file);
    return 1;
}