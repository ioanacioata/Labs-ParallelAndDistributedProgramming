#region Using

using System;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

#endregion

//3. Like the previous, but also use the async/await mechanism.

namespace Lab5_CSharp
{
    /// <summary>
    ///     A simple file downloader.
    ///     Note the "sealed" modifier placed in the declaration.
    ///     This means that this class can not be inherited.
    ///     Somewhat equivalent to Java's "final" keyword.
    /// </summary>
    public sealed class Downloader
    {

        /// <summary>
        ///     The url of the file we wish to download.
        /// </summary>
        private readonly string _url;

        /// <summary>
        ///     And the corresponding WebClient instance.
        ///     Note the readonly modifier which means these variables can only be assigned inside a constructor.
        /// </summary>
        private readonly WebClient _client;

        public Downloader(string url)
        {
            _url = url;
            _client = new WebClient();
        }

        /// <summary>
        ///     Async methods must return Task type.
        ///     This downloads a file at a given URL and names it randomly, according to a generated GUID (Globally Unique
        ///     Identifier).
        /// </summary>
        /// <returns>The task to be returned.</returns>
        public async Task DoSingleJobAsync()
        {
            // Please see documentation on MSDN...
            await _client.DownloadFileTaskAsync(_url, Guid.NewGuid() + ".jpg"); 
            // Most methods in the WebClient class have "async" overloads which are named appropriately.
        }

        /// <summary>
        ///     This static method downloads all files from the urls passed as arguments.
        /// </summary>
        /// <param name="urls">
        ///     The list of URLs. Note the params keyword which specifies that this method can take a variable
        ///     number of arguments.
        /// </param>
        /// <returns></returns>
        public static async Task DoAllJobsAsync(params string[] urls)
        {
            // This method returns when all tasks have been completed.
            await Task.WhenAll(from url in urls select new Downloader(url).DoSingleJobAsync());
            // This is a LINQ statement equivalent to iterating over all URLs with a foreach and calling "DoSingleJobAsync".
        }


    }
}
