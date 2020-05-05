using Area.Models;
using Area.Models.Services;

namespace Area.Tools
{
    public class ServiceFactory
    {
        public RegisteredService CreateService(RegisteredService service)
        {
            switch (service.Name)
            {
                case "Facebook":
                    return new FacebookService(service);
                case "Slack":
                    return new SlackService(service);
                default:
                    break;
            }
            return (null);
        }
    }
}
