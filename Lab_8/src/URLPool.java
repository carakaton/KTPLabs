import java.util.LinkedList;

public class URLPool {
    LinkedList<URLDepthPair> findLink;
    LinkedList<URLDepthPair> viewedLink;
    int maxDepth;
    int cWait;

    public URLPool(int maxDepth)
    {
        this.maxDepth = maxDepth;
        findLink = new LinkedList<URLDepthPair>();
        viewedLink = new LinkedList<URLDepthPair>();
        cWait = 0;
    }

    public synchronized URLDepthPair getPair()
    {
        while (findLink.size() == 0) {
            cWait++;
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                System.out.println("Ignoring InterruptedException");
            }
            cWait--;
        }
        URLDepthPair nextPair = findLink.removeFirst();
        return nextPair;
    }

    public synchronized void addPair(URLDepthPair pair)
    {
        if(URLDepthPair.check(viewedLink,pair))
        {
            viewedLink.add(pair);
            if (pair.getDepth() < maxDepth)
            {
                findLink.add(pair);
                notify();
            }
        }
    }

    public synchronized int getWait()
    {
        return cWait;
    }

    public LinkedList<URLDepthPair> getResult()
    {
        return viewedLink;
    }

}