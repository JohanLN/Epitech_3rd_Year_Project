using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Area.Models;
using Area.Tools;
using Microsoft.Extensions.Logging;

namespace Area.Scheduler
{
    public class RefreshApiData : IScheduledTask
    {
        public string Schedule => "*/6 * * * *";
        private readonly Database<User> _users = new Database<User>("Users");
        private readonly ActionFactory _actionFactory = new ActionFactory();
        private readonly ILogger<RefreshApiData> _logger;
        public RefreshApiData(ILogger<RefreshApiData> logger)
        {
            _logger = logger;
        }
        public async Task ExecuteAsync(CancellationToken cancellationToken)
        {
            List<User> users = await _users.GetAll();
            Action currentAction;

            foreach (var user in users)
            {
                foreach (var action in user.RegisteredActions)
                {
                    foreach (var service in user.RegisteredServices)
                    {
                        currentAction = _actionFactory.CreateAction(action, service);
                        if (currentAction != null)
                        {
                            currentAction.Refresh();
                            break;
                        }
                    }
                }
            }
        }
    }
}
