#ifndef LAB5_MAIN_H
#define LAB5_MAIN_H

#pragma pack(push, 1)
typedef struct bmpHeader {
    unsigned char b, m; /* Символы BM*/
    unsigned int bfSize; /* Размер файла*/
    unsigned int bfReserved; /* Зарезервированное поле */
    unsigned int bfOffBytes; /* Местанахождение данных растрового массива */
    unsigned int headerSize; /* Размер структуры */
    unsigned int biWidth; /* Ширина изображения */
    unsigned int biHeight; /* Высота изображения */
    unsigned short biPlanes; /* Число цветовых плоскостей */
    unsigned short biBitCount; /* Бит/пиксель */
    unsigned int biCompression; /* Метод сжатия */
    unsigned int biSizeImage; /* Размер изображения в байт */
    unsigned int biXPelsPerMeter; /* Горизонтальное разрешение */
    unsigned int biYPelsPerMeter; /* Вертикальное разрешение */
    unsigned int biClrUsed; /* Число цветов изображения */
    unsigned int biClrImportant; /* Число основных цветов */

} bmpHeader;
#pragma pack(pop)

typedef struct pixel_t {
    unsigned char b;
    unsigned char g;
    unsigned char r;
} pixel_t;

typedef struct image_t {
    unsigned int width;
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

void readBMP(FILE *file, double degree);

void saveBMP(char *filename, double degree);
#endif
