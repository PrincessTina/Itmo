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
struct file* file;

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
  char* data = kmalloc(255, GFP_USER);
  int rlen;
  loff_t zero = 0;

  if (file != NULL && file_count(file)) {
    set_fs(KERNEL_DS);
    rlen = vfs_read(file, data, 256, &zero);
    set_fs(USER_DS);
  }
  else {
    printk(KERN_ERR "Can not read from the file\n");
    return -EFAULT;
  }

  if (rlen >= 0) {
    data[rlen] = 0;
    printk(KERN_INFO "Contains: %s\n", data);
  }

  if (rlen < 0) {
    printk(KERN_ERR "An error occurred while reading the file\n");
    return -EFAULT;
  }
  return 0;
}

static ssize_t my_write(struct file *f, const char __user *buf,  size_t len, loff_t *off)
{

  char* data = kmalloc(len, GFP_USER);

  if (copy_from_user(data, buf, len) != 0) {
    kfree(data);
    return -EFAULT;
  }

  data[len] = 0;

  char* fname = kmalloc(255, GFP_USER);
  if (sscanf(data, "open %s", fname) == 1) {
    file = filp_open(fname, O_RDWR|O_CREAT, 0644);
    kfree(fname);
    if (!file) {
      printk(KERN_WARNING "Unable to create file with this name %s\n", fname);
      return -EFAULT;
    }
    return len;
  }

  if (file == NULL || file_count(file) == 0) {
    printk(KERN_WARNING "At this moment there is no opened file for writing");
    return -EFAULT;
  }

  if (!strcmp(data, "close")) {
     if (filp_close(file, NULL) < 0) {
         printk(KERN_WARNING "Unable to close the file: it is not opened");
 	return -EFAULT;
     }
     return len;
   }

 if (strstr(data, "close") == NULL && strstr(data, "open") == NULL) {
      int res = 0;
      int i=0;
       for (i=0; data[i] != '\0';i++){
            if(data[i]== ' ') {
            res++;
                      }
            }

     char s_res[256];
     sprintf(s_res, "%d\n", res);
     set_fs(KERNEL_DS);
     if (vfs_write(file, s_res, strlen(s_res), &file->f_pos) < 0) {
       printk(KERN_WARNING "Unable to write to file %s\n", fname);
       set_fs(USER_DS);
       return -EFAULT;
     }
     set_fs(USER_DS);
   }

   kfree(data);
   set_fs(USER_DS);
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
    if (device_create(cl, NULL, first, NULL, "var4") == NULL)
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
MODULE_AUTHOR("Laletina Ekaterina and Orlova Kristina");
MODULE_DESCRIPTION("The character driver/lab1");
