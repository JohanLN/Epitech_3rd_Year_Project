using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System;
using System.Threading.Tasks;

namespace Area.Scheduler
{
    public static class SchedulerExtensions
    {
        public static IServiceCollection AddScheduler(this IServiceCollection services)
        {
            return (services.AddSingleton<IHostedService, SchedulerHostedService>());
        }
        public static IServiceCollection AddScheduler(this IServiceCollection services, EventHandler<UnobservedTaskExceptionEventArgs> handler)
        {
            return (services.AddSingleton<IHostedService, SchedulerHostedService>(serviceProvider =>
            {
                var output = new SchedulerHostedService(serviceProvider.GetServices<IScheduledTask>());

                return (output);
            }));
        }
    }
}
