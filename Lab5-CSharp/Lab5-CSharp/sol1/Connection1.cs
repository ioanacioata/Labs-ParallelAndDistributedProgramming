using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;

namespace Lab5_CSharp
{
    //1. Directly implement the parser on the callbacks(event-driven)
    class Connection1
    {
        string url;

        public Connection1(string url)
        {
            this.url = url;
        }

        public IAsyncResult StartConnection1()
        {
            Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            return socket.BeginConnect(url, 80, requestCallback: RecieveConnection1, state: socket);
        }

        public void RecieveConnection1(IAsyncResult ar)
        {
            Console.WriteLine("Enter ReceiveConnection 1");
            Socket socket = (Socket)ar.AsyncState;  
            Send(socket, "GET / HTTP/1.1 \nHost: " + url + " \n");
            StateObject stateObject = new StateObject { workSocket = socket };
            socket.BeginReceive(stateObject.buffer, 0, StateObject.BUFFER_SIZE, 0, Read_Callback, stateObject);
            System.Threading.Thread.Sleep(500);
            Console.WriteLine("Exit ReceiveConnection 1");
        }

        //read hearder from download
        public static void Read_Callback(IAsyncResult ar)
        {
            Console.WriteLine("Read");
            System.Threading.Thread.Sleep(500);
            StateObject stateObject = (StateObject)ar.AsyncState;
            Socket socket = stateObject.workSocket;
            int read = socket.EndReceive(ar);
            if (read > 0)
            {
                stateObject.sb.Append(Encoding.ASCII.GetString(stateObject.buffer, 0, read));
                socket.BeginReceive(stateObject.buffer, 0, StateObject.BUFFER_SIZE, 0, new AsyncCallback(Read_Callback), stateObject);
            }
            else
            {
                if (stateObject.sb.Length > 1)
                {
                    //All of the data has been read, so displays it to the console
                    string strContent;
                    int index1 = stateObject.sb.ToString().IndexOf("Content-Length:");
                    int index2 = stateObject.sb.ToString().IndexOf("Connection:");
                    strContent = stateObject.sb.ToString().Substring(index1, index2 - index1 - 1);
                    Console.WriteLine(strContent);
                }
                socket.Close();

            }

        }

        public static void Send(Socket client, String data)
        {
            byte[] byteData = Encoding.ASCII.GetBytes(data);

            client.BeginSend(byteData, 0, byteData.Length, SocketFlags.None, new AsyncCallback(SendCallback), client);
        }

        public static void SendCallback(IAsyncResult ar)
        {
            try
            {
                Socket client = (Socket)ar.AsyncState;

                int bytesSent = client.EndSend(ar);
                Console.WriteLine("Sent {0} bytes to server.", bytesSent);

            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }
    }
}
