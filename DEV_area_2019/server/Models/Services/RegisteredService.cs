using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;

namespace Area.Models
{
    public class RegisteredService : Service
    {
        protected readonly HttpClient _httpClient = new HttpClient();
        public string AccessToken { get; set; }
        public string RefreshToken { get; set; }
        public Dictionary<string, string> Params { get; set; }

        public RegisteredService()
        {
            //_httpClient.BaseAddress = new Uri(BaseUrl);
            //_httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        public RegisteredService(RegisteredService service) : this()
        {
            Id = service.Id;
            Name = service.Name;
            BaseUrl = service.BaseUrl;
            Actions = service.Actions;
            Reactions = service.Reactions;
            AccessToken = service.AccessToken;
            RefreshToken = service.RefreshToken;
        }
    }
}
