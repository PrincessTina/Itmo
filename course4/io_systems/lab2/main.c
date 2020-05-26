#include <linux/module.h>
#include <linux/fs.h>
#include <linux/errno.h>
#include <linux/types.h>
#include <linux/fcntl.h>
#include <linux/vmalloc.h>
#include <linux/genhd.h>
#include <linux/blkdev.h>
#include <linux/bio.h>
#include <linux/string.h>

#define MEMSIZE 0x19000
int c = 0;

#define SECTOR_SIZE 512
#define MBR_SIZE SECTOR_SIZE
#define MBR_DISK_SIGNATURE_OFFSET 440
#define MBR_DISK_SIGNATURE_SIZE 4
#define PARTITION_TABLE_OFFSET 446
#define PARTITION_ENTRY_SIZE 16
#define PARTITION_TABLE_SIZE 64
#define MBR_SIGNATURE_OFFSET 510
#define MBR_SIGNATURE_SIZE 2
#define MBR_SIGNATURE 0xAA55
#define BR_SIZE SECTOR_SIZE
#define BR_SIGNATURE_OFFSET 510
#define BR_SIGNATURE_SIZE 2
#define BR_SIGNATURE 0xAA55

typedef struct
    {
    unsigned char boot_type;
    unsigned char start_head;
    unsigned char start_sec : 6;
    unsigned char start_cyl_hi : 2;
    unsigned char start_cyl;
    unsigned char part_type;
    unsigned char end_head;
    unsigned char end_sec : 6;
    unsigned char end_cyl_hi : 2;
    unsigned char end_cyl;
    unsigned int abs_start_sec;
    unsigned int sec_in_part;
} PartEntry;

typedef PartEntry PartTable[4];

#define SEC_PER_HEAD 63
#define HEAD_PER_CYL 255
#define HEAD_SIZE (SEC_PER_HEAD * SECTOR_SIZE)
#define CYL_SIZE (SEC_PER_HEAD * HEAD_PER_CYL * SECTOR_SIZE)

#define sec4size(s) ((((s) % CYL_SIZE) % HEAD_SIZE) / SECTOR_SIZE)
#define head4size(s) (((s) % CYL_SIZE) / HEAD_SIZE)
#define cyl4size(s) ((s) / CYL_SIZE)

#define PRIMARY_PARTITION_TYPE 0x83
#define SEC_FOR_10_MB 0x5000
#define SEC_FOR_15_MB 0x7800
#define SEC_FOR_25_MB 0xC800

static PartTable def_part_table = {
    {
      boot_type : 0x00,
      start_sec : 0x2,
      start_head : 0x0,
      start_cyl : 0x0,
      part_type : PRIMARY_PARTITION_TYPE,
      end_head : 0x3,
      end_sec : 0x20,
      end_cyl : 0x9F,
      abs_start_sec : 0x1,
      sec_in_part : SEC_FOR_10_MB - 1
    },
    {
      boot_type : 0x00,
      start_sec : 0x1,
      start_head : 0x4,
      start_cyl : 0x0,
      part_type : PRIMARY_PARTITION_TYPE,
      end_sec : 0x20,
      end_head : 0xB,
      end_cyl : 0x9F,
      abs_start_sec : SEC_FOR_10_MB,
      sec_in_part : SEC_FOR_15_MB - 1
    },
    {
      boot_type : 0x00,
      start_sec : 0x1,
      start_head : 0xC,
      start_cyl : 0x0,
      part_type : PRIMARY_PARTITION_TYPE,
      end_sec : 0x20,
      end_head : 0x13,
      end_cyl : 0x9F,
      abs_start_sec : SEC_FOR_10_MB + SEC_FOR_15_MB,
      sec_in_part : SEC_FOR_25_MB
    }
};


static void copy_mbr(u8* disk)
{
    memset(disk, 0x0, MBR_SIZE);
    *(unsigned long*)(disk + MBR_DISK_SIGNATURE_OFFSET) = 0x36E5756D;
    memcpy(disk + PARTITION_TABLE_OFFSET, &def_part_table, PARTITION_TABLE_SIZE);
    *(unsigned short*)(disk + MBR_SIGNATURE_OFFSET) = MBR_SIGNATURE;
}


struct mydiskdrive_dev {
    int size;
    u8* data;
    spinlock_t lock;
    struct request_queue* queue;
    struct gendisk* gd;

} device;

struct mydiskdrive_dev* x;

static int my_open(struct block_device* x, fmode_t mode)
{
    int ret = 0;
    printk(KERN_INFO "mydiskdrive : open \n");

    return ret;
}

static void my_release(struct gendisk* disk, fmode_t mode)
{
    printk(KERN_INFO "mydiskdrive : closed \n");
}

static struct block_device_operations fops = {
    .owner = THIS_MODULE,
    .open = my_open,
    .release = my_release,
};

int mydisk_init(void)
{
    (device.data) = vmalloc(MEMSIZE * SECTOR_SIZE);
    copy_mbr(device.data);
    return MEMSIZE;
}

static int rb_transfer(struct request* req)
{
    int dir = rq_data_dir(req);
    int ret = 0;
    sector_t start_sector = blk_rq_pos(req);
    unsigned int sector_cnt = blk_rq_sectors(req);
    struct bio_vec bv;
		#define BV_PAGE(bv) ((bv).bv_page)
		#define BV_OFFSET(bv) ((bv).bv_offset)
		#define BV_LEN(bv) ((bv).bv_len)
    struct req_iterator iter;
    sector_t sector_offset;
    unsigned int sectors;
    u8* buffer;
    sector_offset = 0;
    rq_for_each_segment(bv, req, iter)
    {
        buffer = page_address(BV_PAGE(bv)) + BV_OFFSET(bv);
        if (BV_LEN(bv) % (SECTOR_SIZE) != 0) {
            printk(KERN_ERR "bio size is not a multiple ofsector size\n");
            ret = -EIO;
        }
        sectors = BV_LEN(bv) / SECTOR_SIZE;
        printk(KERN_DEBUG "my disk: Start Sector: %llu, Sector Offset: %llu;\
		Buffer: %p; Length: %u sectors\n",
            (unsigned long long)(start_sector), (unsigned long long)(sector_offset), buffer, sectors);

        if (dir == WRITE)
        {
            memcpy((device.data) + ((start_sector + sector_offset) * SECTOR_SIZE), buffer, sectors * SECTOR_SIZE);
        }
        else
        {
            memcpy(buffer, (device.data) + ((start_sector + sector_offset) * SECTOR_SIZE), sectors * SECTOR_SIZE);
        }
        sector_offset += sectors;
    }

    if (sector_offset != sector_cnt) {
        printk(KERN_ERR "mydisk: bio info doesn't match with the request info");
        ret = -EIO;
    }
    return ret;
}

static void dev_request(struct request_queue* q)
{
    struct request* req;
    int error;
    while ((req = blk_fetch_request(q)) != NULL)

    {
        error = rb_transfer(req);
        __blk_end_request_all(req, error);
    }
}

void device_setup(void)
{
    mydisk_init();
    c = register_blkdev(c, "mydisk");
    printk(KERN_ALERT "Major Number is : %d", c);
    spin_lock_init(&device.lock);
    device.queue = blk_init_queue(dev_request, &device.lock);

    device.gd = alloc_disk(8);

    (device.gd)->major = c;
    device.gd->first_minor = 0;

    device.gd->fops = &fops;
    device.gd->private_data = &device;
    device.gd->queue = device.queue;
    device.size = mydisk_init();
    printk(KERN_INFO "THIS IS DEVICE SIZE %d", device.size);
    sprintf(((device.gd)->disk_name), "mydisk");
    set_capacity(device.gd, device.size);
    add_disk(device.gd);
}

static int __init mydiskdrive_init(void)
{
    int ret = 0;
    device_setup();

    return ret;
}

void mydisk_cleanup(void)
{
    vfree(device.data);
}

void __exit mydiskdrive_exit(void)
{
    del_gendisk(device.gd);
    put_disk(device.gd);
    blk_cleanup_queue(device.queue);
    unregister_blkdev(c, "mydisk");
    mydisk_cleanup();
}

module_init(mydiskdrive_init);
module_exit(mydiskdrive_exit);
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Laletina Ekaterina & Orlova Kristina");
MODULE_DESCRIPTION("The block driver/lab2");
