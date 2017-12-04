using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;
using System.Threading;
namespace Lab5
{
    public class TaskAsyncClient
    {
        //http port
        private const int port = 80;
        //events
        private ManualResetEvent connectDone = new ManualResetEvent(false)
            , sendDone = new ManualResetEvent(false)
            , receiveDone = new ManualResetEvent(false);
        private String response = String.Empty;
        //relative path to the requested page
        private String page;

        public TaskAsyncClient(string page)
        {
            this.page = page;
        }

  

        public void StartClient()
        {
            try
            {
                Socket cl = Connect().Result;
                cl = Send(cl, page).Result;
                Receive(cl);

                cl.Shutdown(SocketShutdown.Both);
                cl.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private Task<Socket> Connect()
        {
            IPHostEntry ipHostInfo = Dns.GetHostEntry("www.cs.ubbcluj.ro");
            //transform the dns host entry to ip
            IPAddress ipAddress = ipHostInfo.AddressList[0];
            IPEndPoint remoteEP = new IPEndPoint(ipAddress, port);

            Socket client = new Socket(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            
            //begin the connection then do the callback
            client.BeginConnect(remoteEP, new AsyncCallback(ConnectCallback), client);
            connectDone.WaitOne();
            return Task.FromResult(client);
        }

        private void ConnectCallback(IAsyncResult ar)
        {
            try
            {
                Socket client = (Socket)ar.AsyncState;
                client.EndConnect(ar);
                Console.WriteLine("Socket connected to {0}", client.RemoteEndPoint.ToString());
                //set the event to done so the execution of the start client function will continue
                connectDone.Set();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private Task<String> Receive(Socket client)
        {
            try
            {
                //one new state
                StateObject state = new StateObject();
                state.workSocket = client;
                client.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0, new AsyncCallback(ReceiveCallback), state);
                receiveDone.WaitOne();
                return Task.FromResult(response);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                throw;
            }
        }

        private void ReceiveCallback(IAsyncResult ar)
        {
            try
            {
                //state refers to the one declared in receive
                StateObject state = (StateObject)ar.AsyncState;
                Socket client = state.workSocket;

                int bytesRead = client.EndReceive(ar);
                //if there are still bytes to read we make a new callback to receive
                if (bytesRead > 0)
                {
                    state.stringBuilder.Append(Encoding.ASCII.GetString(state.buffer, 0, bytesRead));

                    client.BeginReceive(state.buffer, 0, StateObject.BufferSize, 0, new AsyncCallback(ReceiveCallback), state);
                }
                else
                {
                    if (state.stringBuilder.Length > 0)
                    {
                        response = state.stringBuilder.ToString();
                        Console.WriteLine();
                        Console.Write("PAGE : " + page + "\n");
                        //print the content header
                        Console.WriteLine(response);
                        //print the content length
                        Parser.Parse(response);
                        Console.WriteLine();

                    }
                    //set receive event to done if there is no more data to be read
                    receiveDone.Set();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private Task<Socket> Send(Socket client, String page)
        {
            // Convert the string data to byte data using ASCII encoding.  
            // HEAD request for receiving only the headers of the response
            // page
            // HTTP/1.0 http version, 1 endl for passing current row, 1 endl for saying request done
            byte[] byteData = Encoding.ASCII.GetBytes("HEAD " + page + " HTTP/1.0\r\n\r\n");

            // Begin sending the data to the remote device.  
            client.BeginSend(byteData, 0, byteData.Length, 0,
                new AsyncCallback(SendCallback), client);
            sendDone.WaitOne();
            return Task.FromResult(client);
        }

        private void SendCallback(IAsyncResult ar)
        {
            try
            {
                // Retrieve the socket from the state object.  
                Socket client = (Socket)ar.AsyncState;

                // Complete sending the data to the remote device.  
                int bytesSent = client.EndSend(ar);
                Console.WriteLine("Sent {0} bytes to server.", bytesSent);

                // Signal that all bytes have been sent.  
                sendDone.Set();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

    }
}
