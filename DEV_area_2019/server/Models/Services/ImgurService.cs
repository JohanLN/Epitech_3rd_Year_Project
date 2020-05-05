namespace Area.Models.Services
{
    public class ImgurService : RegisteredService
    {
        public ImgurService(RegisteredService registeredService) : base(registeredService)
        {
            if (BaseUrl == null)
            {
                BaseUrl = "https://api.imgur.com/";
            }
        }
    }
}
