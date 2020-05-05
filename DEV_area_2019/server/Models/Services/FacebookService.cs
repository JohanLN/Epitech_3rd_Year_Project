namespace Area.Models.Services
{
    public class FacebookService : RegisteredService
    {
        public FacebookService(RegisteredService service) : base(service)
        {
            if (BaseUrl == null)
            {
                BaseUrl = "https://graph.facebook.com/";
            }
        }
    }
}
