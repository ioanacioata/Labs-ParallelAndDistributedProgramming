using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.Text.RegularExpressions;

namespace Lab5
{

    public static class Parser
    {
        public static void Parse(string page)
        {
            string pattern = @"Content-Length: (\d+)";
            MatchCollection matches = Regex.Matches(page, pattern);

            Console.WriteLine("Length of the content: {0}", matches[0].Groups[1].Value);

        }
    }

    public class StateObject
    {
        
        // Client socket.  
        public Socket workSocket = null;
        // Size of receive buffer.  
        public const int BufferSize = 256;
        // Receive buffer.  
        public byte[] buffer = new byte[BufferSize];
        // Received data string.  
        public StringBuilder stringBuilder = new StringBuilder();
    }

    class Program
    {
        static void Main(string[] args)
        {
            List<string> pages = new List<string>(new string[] { "/files/admitere/2017-septembrie/master/3/SDI.pdf", "/~rlupsa/edu/pdp/lecture-1-intro.html", "/~rlupsa/edu/pdp/lecture-2-smp.html", "/~rlupsa/edu/pdp/lecture-3-handling-concurrency.html", "/~rlupsa/edu/pdp/lecture-4-higher-level-multithreading-concepts.html", "/~rlupsa/edu/pdp/lecture-5-6-parallel-algo.html", "/~rlupsa/edu/pdp/lecture-7-8-opencl.html", "/~rlupsa/edu/pdp/lecture-10-mpi.html", "/~rlupsa/edu/pdp/lecture-12-basic-distributed.html", "/~rlupsa/edu/pdp/lecture-13-fault-tolerance.html", "/~rlupsa/edu/pdp/lab-1-noncooperative-mt.html", "/~rlupsa/edu/pdp/lab-2-parallel-simple.html" });
            int nrPages = pages.Count;
            List<ManualResetEvent> events = new List<ManualResetEvent>();
            
            for (int i = 0; i < nrPages; i++)
            {
                //1 - events
                AsyncClient client = new AsyncClient(pages[i]);

                //2
                //TaskAsyncClient client = new TaskAsyncClient(pages[i]);

                //3 - async await
                //TaskAsyncAwaitClient client = new TaskAsyncAwaitClient(pages[i]);

                client.StartClient();
            }
            Thread.Sleep(3000);

        }
    }
}
