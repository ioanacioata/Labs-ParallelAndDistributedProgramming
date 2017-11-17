using System;
using System.Collections.Generic;
using System.Net;
using System.Threading.Tasks;

namespace Lab5_CSharp
{

    public static class Program
    {
        public static void Main()
        {
            //Exemplu();
            //Impl1();
            Impl2();
        }

        private static void Exemplu()
        {
            string[] files =
            {
                "https://orig00.deviantart.net/02d9/f/2009/357/6/8/shortest_day_sunrise_by_ccplusplus.jpg",
                "https://orig00.deviantart.net/f135/f/2010/099/0/a/tranquility_by_ccplusplus.jpg",
                "https://orig00.deviantart.net/77ea/f/2010/188/0/e/stop__watch__wonder____by_ccplusplus.jpg"
            };

            // This is a trick to call an async method in the main method of a console application.
            Downloader.DoAllJobsAsync(files).GetAwaiter().GetResult();

        }

        private static void Impl1()
        {
            Connection1 connect = new Connection1("www.cs.ubbcluj.ro");
            Connection1 connect2 = new Connection1("www.ubbcluj.ro");

            IAsyncResult result = connect.StartConnection1();
            IAsyncResult result2 = connect2.StartConnection1();

            System.Threading.Thread.Sleep(3000);
            while (!result.IsCompleted || !result2.IsCompleted) { }
            Console.ReadLine();
        }

        private static void Impl2()
        {
            Connection2 connect = new Connection2("www.cs.ubbcluj.ro");
            Connection2 connect2 = new Connection2("www.ubbcluj.ro");

            Task<int> task = new Task<int>(connect.DoTask);
            Task<int> task2 = new Task<int>(connect2.DoTask);

            task.Start();
            task2.Start();
            task.Wait();
            task2.Wait();

            int result = task.Result;
            int result2 = task.Result;

            Console.WriteLine("result " + result);
            Console.WriteLine("result2 " + result2);
            System.Threading.Thread.Sleep(2000);
        }
    }
}
