using System.Collections.Generic;
using System.Threading.Tasks;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Area.Models;
using System;

namespace Area.Tools
{
    public class Database<T> where T : Model
    {
        private readonly string _TABLE_NAME; 
        private readonly IFirebaseConfig _firebaseConfig = new FirebaseConfig
        {
            AuthSecret= "zonL4oAI6pzjMcef4yBeKlVf70em7dHzDTpwhwpT",
            BasePath= "https://area-e9d60.firebaseio.com/"
        };
        private readonly IFirebaseClient _client;
        public Database(string tableName)
        {
            _TABLE_NAME = tableName + "/";
            _client = new FireSharp.FirebaseClient(_firebaseConfig);
        }

        public async Task<T> Get(long id)
        {
            FirebaseResponse res = await _client.GetTaskAsync(_TABLE_NAME + id);
            T output;
            
            try
            {
                output = res.ResultAs<T>();
                return (output);
            }
            catch (Exception)
            {
                return (null);
            }
        }
        public async Task<T> Get(string id)
        {
            List<T> res = await GetAll();

            foreach (T datum in res)
            {
                if (datum.Name == id)
                    return (datum);
            }
            return null;
        }
        public async Task<List<T>> GetAll()
        {
            var res = await _client.GetTaskAsync(_TABLE_NAME);
            Dictionary<string, T> data;
            List<T> output = new List<T>();

            try
            {
                data = res.ResultAs<Dictionary<string, T>>();
                foreach (var datum in data)
                {
                    output.Add(datum.Value);
                }
                return (output);
            }
            catch (Exception)
            {
                return (null);
            }
        }
        public async Task<T> Post(long id, T data)
        {
            string path = _TABLE_NAME + id;
            SetResponse res = await _client.SetTaskAsync(path, data);
            T output;
            
            try
            {
                output = res.ResultAs<T>();
                return (output);
            }
            catch (Exception)
            {
                return (null);
            }
        }
        public async Task<T> Put(long id, T data)
        {
            string path = _TABLE_NAME + id;
            FirebaseResponse res = await _client.UpdateTaskAsync(path, data);
            T output;

            try
            {
                output = res.ResultAs<T>();
                return (output);
            }
            catch (Exception)
            {
                return (null);
            }
        }
        public async Task Delete(long id)
        {
            await _client.DeleteTaskAsync(_TABLE_NAME + id);
        }
    }
}
