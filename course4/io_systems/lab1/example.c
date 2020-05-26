#include <linux/module.h>
#include <linux/version.h>
#include <linux/kernel.h>
#include <linux/types.h>
#include <linux/kdev_t.h>
#include <linux/fs.h>
#include <linux/device.h>
#include <linux/cdev.h>
#include <linux/uaccess.h>
#include <linux/slab.h>

static dev_t first;
static struct cdev c_dev; 
static struct class *cl;

static int my_open(struct inode *i, struct file *f)
{
  printk(KERN_INFO "Driver: open()\n");
  return 0;
}

static int my_close(struct inode *i, struct file *f)
{
  printk(KERN_INFO "Driver: close()\n");
  return 0;
}

static ssize_t my_read(struct file *f, char __user *buf, size_t len, loff_t *off)
{

  char data [] = "Data from kernel module\n";
  size_t rlen = strlen(data);

  printk(KERN_INFO "Driver: read()\n");

  if(*off != rlen)
    *off = rlen;
  else
    return 0;

  if(copy_to_user(buf, data, rlen) != 0) {
    return -EFAULT;
  }

  return rlen;
}

static ssize_t my_write(struct file *f, const char __user *buf,  size_t len, loff_t *off)
{

  char fname[] = "wfile";
  size_t wlen = 0;
  struct file* test_file = filp_open(fname, O_RDWR|O_CREAT, 0644);
  char * data = kmalloc(len, GFP_USER);

  if(copy_from_user(data, buf, len) != 0) {
    kfree(data);
    return -EFAULT;
  }
  
  set_fs(KERNEL_DS);

  wlen = vfs_write(test_file, data, len, &test_file->f_pos);
  
  set_fs(USER_DS);

  printk(KERN_INFO "Driver: write() len = %ld, %lld\n", len, test_file->f_pos);
  kfree(data);

  return len;
}

static struct file_operations mychdev_fops =
{
  .owner = THIS_MODULE,
  .open = my_open,
  .release = my_close,
  .read = my_read,
  .write = my_write
};
 
static int __init ch_drv_init(void)
{
    printk(KERN_INFO "Hello!\n");
    if (alloc_chrdev_region(&first, 0, 1, "ch_dev") < 0) 
	{
		return -1;
	}
    if ((cl = class_create(THIS_MODULE, "chardrv")) == NULL)
	{
		unregister_chrdev_region(first, 1);
		return -1;
	}
    if (device_create(cl, NULL, first, NULL, "mychdev") == NULL) 
	{
      class_destroy(cl);
      unregister_chrdev_region(first, 1);
      return -1;
	}
    cdev_init(&c_dev, &mychdev_fops);
    
    if (cdev_add(&c_dev, first, 1) == -1)
	{
      device_destroy(cl, first);
      class_destroy(cl);
      unregister_chrdev_region(first, 1);
      return -1;
	}
    return 0;
}
 
static void __exit ch_drv_exit(void)
{
    cdev_del(&c_dev);
    device_destroy(cl, first);
    class_destroy(cl);
    unregister_chrdev_region(first, 1);
    printk(KERN_INFO "Bye!!!\n");
}
 
module_init(ch_drv_init);
module_exit(ch_drv_exit);
 
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Author");
MODULE_DESCRIPTION("The first kernel module");

