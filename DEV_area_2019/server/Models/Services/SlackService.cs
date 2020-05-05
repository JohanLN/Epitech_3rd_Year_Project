using Newtonsoft.Json;
using RestSharp;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

namespace Area.Models.Services
{
    public class SlackService : RegisteredService
    {
        public SlackService(RegisteredService registeredService) : base(registeredService)
        {
            if (BaseUrl == null)
            {
                BaseUrl = "https://slack.com/api/";
            }
        }

        private string FormatUrl(string path, Dictionary<string, string> urlParams = null)
        {
            string url = BaseUrl + path + "?token=" + AccessToken;

            if (urlParams == null)
                return (url);
            foreach (var param in urlParams)
            {
                url += $"&{param.Key}={param.Value}";
            }
            return (url);
        }

        public async Task<List<string>> GetChannels()
        {
            HttpResponseMessage channelsResponse = await _httpClient.GetAsync(FormatUrl("conversations.list"));
            dynamic channels;
            List<string> output = new List<string>();
            
            channelsResponse.EnsureSuccessStatusCode();
            channels = JsonConvert.DeserializeObject(await channelsResponse.Content.ReadAsStringAsync());
            foreach (var channel in channels.channels)
            {
                if (!channel.is_channel)
                    continue;
                output.Add(channel.id);
            }
            return (output);
        }
        public async Task<List<string>> GetMessagesAsync()
        {
            HttpResponseMessage messagesResponse;
            Dictionary<string, string> urlParams = new Dictionary<string, string>();
            List<string> channelIds = await GetChannels();
            dynamic messages;
            List<string> output = new List<string>();
 
            foreach (var id in channelIds)
            {
                urlParams.Add("channels", id);
                messagesResponse = await _httpClient.GetAsync(FormatUrl("conversations.history", urlParams));
                messagesResponse.EnsureSuccessStatusCode();
                messages = JsonConvert.DeserializeObject(await messagesResponse.Content.ReadAsStringAsync());
                foreach (var message in messages.messages)
                {
                    output.Add(message.text);
                }
            }
            return (output);
        }
        public async Task SendMessageAsync(string target, string message)
        {
            dynamic values = new JsonObject();
            StringContent content;

            values.channel = target;
            values.text = message;
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", AccessToken);
            content = new StringContent(values.ToString(), Encoding.UTF8, "application/json");
            await _httpClient.PostAsync(FormatUrl("chat.postMessage"), content);
        }
    }
}
