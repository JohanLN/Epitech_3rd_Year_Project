using Area.Models;
using Area.Models.Actions;
using Area.Models.Services;

namespace Area.Tools
{
    public class ActionFactory
    {
        private readonly ServiceFactory _serviceFactory = new ServiceFactory();
        public Action CreateAction(Action action, RegisteredService registeredService)
        {
            RegisteredService service = _serviceFactory.CreateService(registeredService);
            
            if (service.Name == "Facebook")
            {
                switch (action.Name)
                {
                    case "GotMessage":
                        return new GotMessageFacebookAction((FacebookService)service);
                    default:
                        break;
                }
            }
            if (service.Name == "Slack")
            {
                switch (action.Name)
                {
                    case "GotMessage":
                        return new GotMessageSlackAction((SlackService)service);
                    default:
                        break;
                }
            }
            return (null);
        }
    }
}
