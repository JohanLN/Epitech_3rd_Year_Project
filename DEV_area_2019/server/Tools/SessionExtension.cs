using Microsoft.AspNetCore.Http;
using Newtonsoft.Json;

namespace Area.Tools
{
    public static class SessionExtension
    {
        public static T GetObject<T>(this ISession session, string key)
        {
            string value = session.GetString(key);

            if (value == null)
                return (default);
            return (JsonConvert.DeserializeObject<T>(value));
        }
        public static void SetObject<T>(this ISession session, string key, T value)
        {
            session.SetString(key, JsonConvert.SerializeObject(value));
        }
    }
}
