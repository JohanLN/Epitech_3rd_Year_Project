using NCrontab;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace Area.Scheduler
{
    public class SchedulerHostedService : HostedService
    {
        private readonly List<SchedulerTaskWrapper> _tasks = new List<SchedulerTaskWrapper>();
        public SchedulerHostedService(IEnumerable<IScheduledTask> scheduledTasks)
        {
            foreach (var scheduledTask in scheduledTasks)
            {
                _tasks.Add(new SchedulerTaskWrapper
                {
                    Schedule = CrontabSchedule.Parse(scheduledTask.Schedule),
                    Task = scheduledTask,
                    NextRunTime = DateTime.UtcNow
                });
            }
        }
        private async Task ExecuteOnceAsync(CancellationToken cancellationToken)
        {
            TaskFactory taskFactory = new TaskFactory(TaskScheduler.Current);
            var toRunTasks = _tasks.Where((task) =>
            {
                return (task.ShouldRun(DateTime.UtcNow));
            }).ToList();

            foreach (SchedulerTaskWrapper task in toRunTasks)
            {
                task.Increment();
                await taskFactory.StartNew(async () =>
                {
                    try
                    {
                        await task.Task.ExecuteAsync(cancellationToken);
                    }
                    catch (Exception e)
                    {
                        var args = new UnobservedTaskExceptionEventArgs(new AggregateException(e));

                        if (!args.Observed)
                        {
                            throw;
                        }
                    }
                }, cancellationToken);
            }
        }
        protected override async Task ExecuteAsync(CancellationToken cancellationToken)
        {
            while (!cancellationToken.IsCancellationRequested)
            {
                await ExecuteOnceAsync(cancellationToken);
                await Task.Delay(TimeSpan.FromMinutes(5), cancellationToken);
            }
        }
    }
}
