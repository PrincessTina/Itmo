#ifndef LAB5_MAIN_H
#define LAB5_MAIN_H

#pragma pack(push, 1)
typedef struct bmpHeader {
    unsigned char b, m; /* Символы BM*/
    unsigned long bfSize; /* Размер файла*/
    unsigned short bfReserved1; /* Зарезервированное поле */
    unsigned short bfReserved2; /* Зарезервированное поле */
    unsigned long bfOffBytes; /* Местанахождение данных растрового массива */
    unsigned long biSize; /* Размер структуры */
    unsigned int biWidth; /* Ширина изображения */
    unsigned int biHeight; /* Высота изображения */
    unsigned short biPlanes; /* Число цветовых плоскостей */
    unsigned int biBitCount; /* Бит/пиксель */
    unsigned long biCompression; /* Метод сжатия */
    unsigned long biSIzeImage; /* Размер изображения в байт */
    unsigned long biXPelsPerMeter; /* Горизонтальное разрешение */
    unsigned long biYPelsPerMeter; /* Вертикальное разрешение */
    unsigned long biClrUsed; /* Число цветов изображения */
    unsigned long biClrImportant; /* Число основных цветов */

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

typedef enum {
    OPEN_OK = 0,
    READ_INVALID_SIGNATURE,
    READ_OK,
    ROTATE_OK,
    WRITE_OK
} codes;

FILE* openFile(char *filename);

void rotate(double degree);

void readBMP(FILE *file);

void saveBMP(char *filename);
#endif
