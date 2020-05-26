#include <linux/init.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/netfilter.h>
#include <linux/netfilter_ipv4.h>
#include <linux/ip.h>
#include <linux/tcp.h>
#include <linux/udp.h>
#include <linux/netdevice.h>
#include <linux/etherdevice.h>
#include <linux/moduleparam.h>
#include <net/arp.h>

#define ERR(...) printk( KERN_ERR "! "__VA_ARGS__ )
#define LOG(...) printk( KERN_INFO "! "__VA_ARGS__ )
#define DBG(...) if(debug != 0) printk(KERN_INFO "! "__VA_ARGS__)

static char* link = "enp0s3";
module_param(link, charp, 0);

static char* ifname = "virt";
module_param(ifname, charp, 0);

static struct net_device *child = NULL;
static struct nf_hook_ops *nfho = NULL;

struct priv {
   struct net_device *parent;
};

static unsigned int hfunc(void *priv, struct sk_buff *skb, const struct nf_hook_state *state)
{
	struct iphdr *iph;
	struct udphdr *udph;
	if (!skb)
		return NF_ACCEPT;
	iph = ip_hdr(skb);

	if (iph->protocol == IPPROTO_UDP) {
		udph = udp_hdr(skb);

    LOG("UDP from %hu to %hu", udph->source, udph->dest);

		if (strpbrk (skb->data, "a")){
      printk(KERN_INFO "%s", skb->data );
			    return NF_ACCEPT;
           }
	}
  
	else if (iph->protocol == IPPROTO_TCP) {
		return NF_ACCEPT;
	}
	return NF_DROP;
}


static rx_handler_result_t handle_frame(struct sk_buff **pskb)
{
   struct sk_buff *skb = *pskb;

   if(child) {
      child->stats.rx_packets++;
      child->stats.rx_bytes += skb->len;

      skb->dev = child;
      return RX_HANDLER_ANOTHER;
   }
   return RX_HANDLER_PASS;
}


static netdev_tx_t start_xmit(struct sk_buff *skb, struct net_device *dev)
 {
   struct priv *priv = netdev_priv(dev);
   child->stats.tx_packets++;
   child->stats.tx_bytes += skb->len;

   if(priv->parent) {
      skb->dev = priv->parent;
      skb->priority = 1;
      dev_queue_xmit(skb);
      return 0;
   }
   return NETDEV_TX_OK;
}


static int open(struct net_device *dev) {
   LOG("%s: device opened", dev->name);
   netif_start_queue(dev);
   return 0;
}

static int stop(struct net_device *dev)
{
   LOG("%s: device closed", dev->name);
   netif_stop_queue(dev);
   return 0;
}

static struct net_device_stats *get_stats(struct net_device *dev)
{
    return &dev->stats;
}

static struct net_device_ops crypto_net_device_ops = {
   .ndo_open = open,
   .ndo_stop = stop,
   .ndo_get_stats = get_stats,
   .ndo_start_xmit = start_xmit,
};

static void setup(struct net_device *dev)
{
   int j;
   ether_setup(dev);
   memset(netdev_priv(dev), 0, sizeof(struct priv));
   dev->netdev_ops = &crypto_net_device_ops;
   for(j = 0; j < ETH_ALEN; ++j)
      dev->dev_addr[j] = (char)j;
}

int __init init(void)
{
   int err = 0;
   struct priv *priv;
   char ifstr[40];
   sprintf(ifstr, "%s%s", ifname, "%d");
   child = alloc_netdev(sizeof(struct priv), ifstr, NET_NAME_UNKNOWN, setup);

   if(child == NULL) {
      ERR("%s: allocate error", THIS_MODULE->name); return -ENOMEM;
   }

   priv = netdev_priv(child);
   priv->parent = __dev_get_by_name(&init_net, link);

   if(!priv->parent) {
      ERR("%s: no such net: %s", THIS_MODULE->name, link);
      err = -ENODEV; goto err;
   }

   if(priv->parent->type != ARPHRD_ETHER && priv->parent->type != ARPHRD_LOOPBACK)
   {
      ERR("%s: illegal net type", THIS_MODULE->name);
      err = -EINVAL; goto err;
   }

   memcpy(child->dev_addr, priv->parent->dev_addr, ETH_ALEN);
   memcpy(child->broadcast, priv->parent->broadcast, ETH_ALEN);

   if((err = dev_alloc_name(child, child->name))) {
      ERR("%s: allocate name, error %i", THIS_MODULE->name, err);
      err = -EIO; goto err;
   }
   register_netdev(child);
   rtnl_lock();
   netdev_rx_handler_register(priv->parent, &handle_frame, NULL);
   rtnl_unlock();


  nfho = (struct nf_hook_ops*)kcalloc(1, sizeof(struct nf_hook_ops), GFP_KERNEL);
	nfho->hook = (nf_hookfn*)hfunc;
	nfho->hooknum = NF_INET_PRE_ROUTING;
	nfho->pf = PF_INET;
	nfho->priority = NF_IP_PRI_FIRST;
	nf_register_net_hook(&init_net, nfho);


   LOG("module %s loaded", THIS_MODULE->name);
   LOG("%s: create link %s", THIS_MODULE->name, child->name);
   LOG("%s: registered rx handler for %s", THIS_MODULE->name, priv->parent->name);

   return 0;
err:
   free_netdev(child);
   return err;
}

void __exit exit(void) {
   struct priv *priv = netdev_priv(child);

   if(priv->parent) {
      rtnl_lock();
      netdev_rx_handler_unregister(priv->parent);
      rtnl_unlock();
      LOG("unregister rx handler for %s\n", priv->parent->name);
   }

   nf_unregister_net_hook(&init_net, nfho);
   kfree(nfho);

   unregister_netdev(child);
   free_netdev(child);
   LOG("module %s unloaded", THIS_MODULE->name);
}

module_init(init);
module_exit(exit);

MODULE_AUTHOR("Laletina Ekaterina & Orlova Kristina");
MODULE_LICENSE("GPL");
